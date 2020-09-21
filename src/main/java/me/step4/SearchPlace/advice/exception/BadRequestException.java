package me.step4.SearchPlace.advice.exception;
/**
 * 잘못된 요청 예외처리
 * @author Sihun
 *
 */
@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException {
    public BadRequestException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public BadRequestException(String msg) {
        super(msg);
    }
    
    public BadRequestException() {
        super();
    }
}