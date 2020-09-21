package me.step4.SearchPlace.advice.exception;
/**
 * 사용자 접속 오류 예외 처리
 * @author Sihun
 *
 */
@SuppressWarnings("serial")
public class UserSigninFailedException extends RuntimeException {
    public UserSigninFailedException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserSigninFailedException(String msg) {
        super(msg);
    }

    public UserSigninFailedException() {
        super();
    }
}