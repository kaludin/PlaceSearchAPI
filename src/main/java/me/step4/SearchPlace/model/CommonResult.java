package me.step4.SearchPlace.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 결과값 표준
 * @author Sihun
 *
 */
@Getter
@Setter
public class CommonResult {

	@ApiModelProperty(value="응답 성공여부 : true/false")
	private boolean success;
	
	@ApiModelProperty(value="응답코드: 0 이상: 정상, 0 미만: 에러")
	private int code;

	@ApiModelProperty(value="응답메시지")
	private String message;

	@ApiModelProperty(value="에러 상세메시지")
	private String detailErrorMessage;
}
