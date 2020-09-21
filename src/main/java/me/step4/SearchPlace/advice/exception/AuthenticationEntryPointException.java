package me.step4.SearchPlace.advice.exception;

/**
 * 인증 예외
 * @author Sihun
 *
 */
@SuppressWarnings("serial")
public class AuthenticationEntryPointException extends RuntimeException {
    public AuthenticationEntryPointException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthenticationEntryPointException(String msg) {
        super(msg);
    }

    public AuthenticationEntryPointException() {
        super();
    }
}