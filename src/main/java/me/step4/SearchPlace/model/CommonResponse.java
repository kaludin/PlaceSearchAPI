package me.step4.SearchPlace.model;

/**
 * 표준 응답
 * @author Sihun
 *
 */
public enum CommonResponse{
	SUCCESS(0, "정상처리 되었습니다."),
	FAIL(-1, "요청 처리를 실패하였습니다."),
	BAD_REQUEST(101, "처리할 수 없는 요청입니다."),
	USER_NOT_FOUND(102, "사용자 정보를 찾을 수 없습니다."),
	USER_SIGN_FAIL(103, "접속 정보가 정확하지 않습니다."),
	UN_AUTH(110, "인증이 필요한 기능입니다."),
	FORBIDDEN(111, "해당 리소스에 접근 권한이 없습니다."),
	NOT_ACCEPTABLE(112, "다시 접속하여 이용하시기 바랍니다.");
	
	int code;
	String message;
	CommonResponse(int code, String message){
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return this.code;
	}
	public String getMessage() {
		return this.message;
	}
}