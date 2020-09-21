package me.step4.SearchPlace.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import me.step4.SearchPlace.model.CommonResponse;

/**
 * JWT 인증 필터
 * Spring 기본로그인 필터보다 먼저 체크
 * @author Sihun
 *
 */
public class JwtAuthenticationFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        else if(StringUtils.isEmpty(token)) {}
        else {
        	HttpServletResponse resp = (HttpServletResponse) response;
        	resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        	resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, CommonResponse.UN_AUTH.getMessage());
        	return;
        }
        filterChain.doFilter(request, response);
    }
}