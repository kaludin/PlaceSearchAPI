package me.step4.SearchPlace.advice.exception;

/**
 * 사용자 접속 오류 예외 처리
 * @author Sihun
 *
 */
@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public UserNotFoundException(String msg) {
        super(msg);
    }
    
    public UserNotFoundException() {
        super();
    }
}