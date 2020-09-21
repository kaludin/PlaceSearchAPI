package me.step4.SearchPlace.controller.v1;

import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import me.step4.SearchPlace.advice.exception.UserSigninFailedException;
import me.step4.SearchPlace.config.security.JwtTokenProvider;
import me.step4.SearchPlace.model.SingleResult;
import me.step4.SearchPlace.repo.UserRepository;
import me.step4.SearchPlace.repo.entity.User;
import me.step4.SearchPlace.service.ResponseService;

/**
 * 로그인 컨트롤러
 * @author Sihun
 *
 */
@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

    private final UserRepository repositoryService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "로그인", notes = "회원 로그인")
    @PostMapping(value = "/login")
    public SingleResult<String> signin(@ApiParam(value = "회원정보", required = true) @RequestBody User p_user) {
        User user = repositoryService.findByUserId(p_user.getUserId()).orElseThrow(UserSigninFailedException::new);
        try {
			if (!passwordEncoder.matches(p_user.getPassword(), user.getPassword()))
			    throw new UserSigninFailedException();
		} catch (Exception e) {
			throw new UserSigninFailedException();
		}

        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getSeq()), Collections.singletonList("ROLE_USER")));
    }
}