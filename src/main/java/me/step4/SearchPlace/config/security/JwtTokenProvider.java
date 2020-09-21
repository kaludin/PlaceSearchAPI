package me.step4.SearchPlace.config.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT 토큰 생성, 검증 모듈
 * @author Sihun
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
	public static final String REDIS_PRE_USER = "SEARCHPLACE:USER:";
	@Resource(name="redisTemplate")
	private RedisTemplate<String, Object> redisTemplate;
	@Resource(name="redisTemplate")
	private ValueOperations<String, Object> valueOperation;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private long tokenMilisec = 1000L * 60 * 60; // 1시간 유효

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // Jwt 토큰 생성
    public String createToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();
        String jwt= Jwts.builder()
		        .setClaims(claims) // 데이터
		        .setIssuedAt(now) // 토큰 발행일자
		        .setExpiration(new Date(now.getTime() + tokenMilisec)) // set Expire Time
		        .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
		        .compact();

        log.debug("start redis key setting:{}", userPk);
        // 이전 토큰 만료
        if(redisTemplate.persist(REDIS_PRE_USER+userPk)) {
            log.debug("delete redis key:{}", userPk);
        	redisTemplate.delete(REDIS_PRE_USER+userPk);
        }
        valueOperation.set(REDIS_PRE_USER+userPk, jwt, tokenMilisec, TimeUnit.MILLISECONDS);
        log.debug("end redis key setting");
        return jwt;
    }

    // Jwt 토큰으로 인증 정보를 조회
    public Authentication getAuthentication(String token) {
    	String userPk = this.getUserPk(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userPk);
        
        // redis reset
        if(redisTemplate.persist(REDIS_PRE_USER+userPk)) {
            log.debug("redis key touch!:{}", userDetails.getUsername());
            redisTemplate.expire(REDIS_PRE_USER+userPk, tokenMilisec, TimeUnit.MILLISECONDS);
        } else {
            log.debug("redis key make:{}", userDetails.getUsername());
            valueOperation.set(REDIS_PRE_USER+userPk, token, tokenMilisec, TimeUnit.MILLISECONDS);
        }
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Jwt 토큰에서 회원 구별 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 파싱 : "X-AUTH-TOKEN: jwt토큰"
    public String resolveToken(HttpServletRequest req) {
        return req.getHeader("X-AUTH-TOKEN");
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
        	// 만료시 여기
            return false;
        }
    }
}