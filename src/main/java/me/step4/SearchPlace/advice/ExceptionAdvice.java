package me.step4.SearchPlace.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.step4.SearchPlace.advice.exception.AuthenticationEntryPointException;
import me.step4.SearchPlace.advice.exception.BadRequestException;
import me.step4.SearchPlace.advice.exception.UserNotFoundException;
import me.step4.SearchPlace.advice.exception.UserSigninFailedException;
import me.step4.SearchPlace.model.CommonResponse;
import me.step4.SearchPlace.model.CommonResult;
import me.step4.SearchPlace.service.ResponseService;

/**
 * 예외 처리
 * @author Sihun
 *
 */
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

	@Autowired
    private final ResponseService responseService;

    @ExceptionHandler(Exception.class) 
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
    	log.error(e.getMessage());
        return responseService.getFailResult(CommonResponse.FAIL);
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected CommonResult userNotFoundException(HttpServletRequest request, Exception e) {
    	log.error(e.getMessage());
    	return responseService.getFailResult(CommonResponse.USER_NOT_FOUND);
    }
    
    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult badRequest2Exception(HttpServletRequest request, Exception e) {
    	log.error(e.getMessage());
    	return responseService.getFailResult(CommonResponse.BAD_REQUEST, e.getMessage());
    }
    
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult badRequestException(HttpServletRequest request, Exception e) {
    	log.error(e.getMessage());
    	return responseService.getFailResult(CommonResponse.BAD_REQUEST);
    }
   
    @ExceptionHandler(UserSigninFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult userSigninFailed(HttpServletRequest request, UserSigninFailedException e) {
    	log.error(e.getMessage());
        return responseService.getFailResult(CommonResponse.USER_SIGN_FAIL);
    }
    
    @ExceptionHandler(AuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CommonResult authenticationEntryPointException(HttpServletRequest request, AuthenticationEntryPointException e) {
    	log.error(e.getMessage());
        return responseService.getFailResult(CommonResponse.UN_AUTH);
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonResult accessDeniedException(HttpServletRequest request, AccessDeniedException e) {
    	log.error(e.getMessage());
        return responseService.getFailResult(CommonResponse.FORBIDDEN);
    }
}