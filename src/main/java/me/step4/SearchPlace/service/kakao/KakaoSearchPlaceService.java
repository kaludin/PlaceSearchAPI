package me.step4.SearchPlace.service.kakao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import me.step4.SearchPlace.model.ApiVendor;
import me.step4.SearchPlace.model.kakao.KakaoPlaceResult;
import me.step4.SearchPlace.model.searchplace.SearchQuery;

/**
 * 카카오 장소 검색 서비스
 * @author Sihun
 *
 */
@Service
public class KakaoSearchPlaceService {
	@Autowired
	KakaosearchApi kakaosearchApiProp;
	
	public boolean validation(SearchQuery queryObj, String p_query, String p_page, String p_block_size) {
    	if(null==p_query || StringUtils.isEmpty(p_query)) {
    		return false;
    	}
    	queryObj.setQuery(p_query);
    	queryObj.setPage(p_page, 45);
    	queryObj.setBlock_size(p_block_size, 15);
		return true;
	}
	public KakaoPlaceResult callApi(RestTemplate template, HttpHeaders headers, SearchQuery queryObj) {
		headers.add("Authorization", kakaosearchApiProp.getAuthHeader()+" "+kakaosearchApiProp.getToken());

		UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaosearchApiProp.getHost()+ kakaosearchApiProp.getUri().get("keyword"))
				.queryParam("query", queryObj.getQuery())
				.queryParam("page", null==queryObj.getPage()? 1 : queryObj.getPage())
				.queryParam("size", queryObj.getBlock_size())
				.build();
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> responseEntity = template.exchange(uriBuilder.toUriString(),HttpMethod.GET, entity, String.class);
		
		Gson gson = new Gson();
		KakaoPlaceResult result = gson.fromJson(responseEntity.getBody(), KakaoPlaceResult.class);
		result.setPage(queryObj.getPage());
		result.setBlock_size(queryObj.getBlock_size());
		
		return result;
	}
	
	/**
	 * 카카오 검색 API 환경 설정
	 * @author Sihun
	 *
	 */
	@Component
	@ConfigurationProperties(prefix = "api.kakaosearch")
	public class KakaosearchApi extends ApiVendor{
	}
}
