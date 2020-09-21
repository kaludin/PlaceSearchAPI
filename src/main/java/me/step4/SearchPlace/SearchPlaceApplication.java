package me.step4.SearchPlace;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import me.step4.SearchPlace.repo.entity.User;
import me.step4.SearchPlace.service.UserService;

@SpringBootApplication
@EnableScheduling
public class SearchPlaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchPlaceApplication.class, args);
	}
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Autowired
    PasswordEncoder passwordEncoder;
    
	@Autowired
	private UserService userService;
	@Bean
	public void createUser() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		userService.createUser(new User(Long.getLong("0"), "tester1", "테스터1", passwordEncoder.encode("123451"),null,null, Collections.singletonList("ROLE_USER")));
		userService.createUser(new User(Long.getLong("0"), "tester2", "테스터2", passwordEncoder.encode("123452"),null,null, Collections.singletonList("ROLE_USER")));
		userService.createUser(new User(Long.getLong("0"), "tester3", "테스터3", passwordEncoder.encode("123453"),null,null, Collections.singletonList("ROLE_USER")));
		userService.createUser(new User(Long.getLong("0"), "tester4", "테스터4", passwordEncoder.encode("123454"),null,null, Collections.singletonList("ROLE_USER")));
		userService.createUser(new User(Long.getLong("0"), "tester5", "테스터5", passwordEncoder.encode("123455"),null,null, Collections.singletonList("ROLE_USER")));
	}

	@Autowired
	private RedisTemplate redisTemplate;
	@Bean
	public void setRedisTemplate() {
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());

        // the following is not required      
		redisTemplate.setHashValueSerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
	}
}
