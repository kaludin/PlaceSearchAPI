package me.step4.SearchPlace.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import me.step4.SearchPlace.advice.exception.AuthenticationEntryPointException;
import me.step4.SearchPlace.model.CommonResult;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 예외처리 컨트롤러
 * @author Sihun
 *
 */
@ApiIgnore
@Api(tags = {"0. ExceptionController"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

    @GetMapping(value = "/entrypoint")
    public CommonResult entrypointException() {
        throw new AuthenticationEntryPointException();
    }
    
    @GetMapping(value = "/accessdenied")
    public CommonResult accessdeniedException() {
            throw new AccessDeniedException("");
    }
}