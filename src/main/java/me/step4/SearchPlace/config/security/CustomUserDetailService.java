package me.step4.SearchPlace.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.step4.SearchPlace.advice.exception.UserNotFoundException;
import me.step4.SearchPlace.repo.UserRepository;

/**
 * 사용자 서비스
 * @author Sihun
 *
 */
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String userPk) {
        return (UserDetails) userRepository.findById(Long.valueOf(userPk)).orElseThrow(UserNotFoundException::new);
    }
}