package me.step4.SearchPlace.model.searchplace;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * TOP10 검색결과 인터페이스
 * @author Sihun
 *
 */
@Getter
@Setter
public class VestKeywordResult {
	
	List<VestKeywordResultData> data;
	
	@Getter
	@Setter
	public class VestKeywordResultData{
		private int rank;
		private String keyword;
		private Long count;
	}
}
