package me.step4.SearchPlace.model.searchplace;

import java.util.List;

/**
 * 장소검색 결과 인터페이스
 * @author Sihun
 *
 */
public interface SearchPlaceResult {
	public Integer getTotal_count();
	public Integer getBlock_size();
	public Integer getPage();
	public boolean getIs_first();
	public boolean getIs_last();
	
	public List<SearchPlaceResultData> getData();
}
