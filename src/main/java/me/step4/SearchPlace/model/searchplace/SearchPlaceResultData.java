package me.step4.SearchPlace.model.searchplace;

/**
 * 장소검색 결과 데이터 인터페이스
 * @author Sihun
 *
 */
public interface SearchPlaceResultData {
	public String getPlace_id();
	public String getPlace_name();
	public String getPlace_map_link();
	public String getCategory_group_name();
	public String getRoad_address_name();
	public String getAddress_name();
	public String getPhone();
	public Double getX();
	public Double getY();
}
