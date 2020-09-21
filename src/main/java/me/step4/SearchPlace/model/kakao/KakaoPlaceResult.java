package me.step4.SearchPlace.model.kakao;

import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.step4.SearchPlace.model.searchplace.SearchPlaceResult;
import me.step4.SearchPlace.model.searchplace.SearchPlaceResultData;
import lombok.Getter;
import lombok.Setter;

/**
 * KAKAO 장소 검색 결과
 * @author Sihun
 *
 */
@Getter
@Setter
public class KakaoPlaceResult implements SearchPlaceResult {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	public KakaoPlaceResultMeta meta;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	public List<KakaoPlaceResultDocuments> documents;

    private Integer page = 1;
    private Integer block_size = 10;
    private List<SearchPlaceResultData> data;

    @Override
	public Integer getTotal_count() {
		if(null==meta) return -1;
		return meta.total_count;
	}
    @Override
	public Integer getBlock_size() {
		if(null==meta) return -1;
		return block_size;
	}
    @Override
	public boolean getIs_first() {
		if(null==meta) return false;
		if(1 >= page) return true;
		return false;
	}
    @Override
	public boolean getIs_last() {
		if(null==meta) return false;
		if(((page+1) * block_size) > meta.total_count)
			return true;
		return false;
	}
    @Override
	public List<SearchPlaceResultData> getData(){
    	if(data==null) {
    		data = new ArrayList<SearchPlaceResultData>();
    		for(KakaoPlaceResultDocuments document : this.documents) {
    			data.add(document);
    		}
    	}
		return data;
	}

    /**
     * 분석정보
     * @author Sihun
     *
     */
	@Getter
	@Setter
	public class SameName{
	    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
		private String[] region;
	    
	    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
		private String keyword;
	    
	    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
		private String selected_region;
	}
	
	/**
	 * 검색결과 META
	 * @author Sihun
	 *
	 */
	@Getter
	@Setter
	public class KakaoPlaceResultMeta {
	    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
		private SameName same_name;
	    
	    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
		private int pageable_count;
	    
	    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
		private int total_count;
	    
	    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
		private boolean is_end;
	}
	/**
	 * 검색 결과
	 * @author Sihun
	 *
	 */
	@Getter
	@Setter
	public class KakaoPlaceResultDocuments implements SearchPlaceResultData{
		private String place_name;
		private String place_url;
		private String address_name;
		private String road_address_name;
		private String phone;
		private String category_group_name;
		private Double x;
		private Double y;
		
	    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
		private String id;
		
	    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
		private String distance;
	    
	    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
		private String category_name;
		
	    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
		private String category_group_code;
		
		@Override
		public String getPlace_id() {
			return this.id;
		}
		@Override
		public String getPlace_name() {
			return this.place_name;
		}
		@Override
		public String getPlace_map_link() {
			if(null==this.id) return null;
			return "https://map.kakao.com/link/map/"+this.id;
		}
		@Override
		public String getCategory_group_name() {
			return this.category_group_name;
		}
		@Override
		public String getRoad_address_name() {
			return this.road_address_name;
		}
		@Override
		public String getAddress_name() {
			return this.address_name;
		}
		@Override
		public String getPhone() {
			return this.phone;
		}
		@Override
		public Double getX() {
			return this.x;
		}
		@Override
		public Double getY() {
			return this.y;
		}
	}
}
