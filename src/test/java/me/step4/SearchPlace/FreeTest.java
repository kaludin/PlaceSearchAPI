package me.step4.SearchPlace;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class FreeTest {
	private static final String API_HOST_KAKAO = "https://dapi.kakao.com";
	private static final String API_HOST_KAKAO_MAP = "https://map.kakao.com";
	private static final String API_URI_KAKAO_SEARCH = "/v2/local/search/keyword.json";
	private static final String API_URI_KAKAO_MAP = "/link/map";

    private String token = "3ec2ecad3c005f756b06ad32d13ddd01";
    private String header = "KakaoAK " + token;

    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
	RestTemplate template = new RestTemplate(requestFactory);

	public HttpHeaders makeCommonKakaoHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", header);
		return headers;
	}
	
	public void testSearchKeyword() throws Exception {
		HttpHeaders headers = makeCommonKakaoHeader();

//		JSONObject body = new JSONObject();
//		body.put("query", "병원");
		UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(API_HOST_KAKAO+ API_URI_KAKAO_SEARCH)
				.queryParam("query", "병원")
				.build();
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> responseEntity = template.exchange(uriBuilder.toUriString(), HttpMethod.GET, entity, String.class, "병원");
		
        System.out.println("------------------ TEST 결과 ------------------");
        System.out.println(responseEntity.getBody());
	}
	
	public void testGetMapLinkForID(int id) throws Exception {
		HttpHeaders headers = makeCommonKakaoHeader();

//		JSONObject body = new JSONObject();
//		body.put("query", "병원");
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> responseEntity = template.exchange(
				API_HOST_KAKAO_MAP + API_URI_KAKAO_MAP + "/{id}",
				HttpMethod.GET,
				entity,
				String.class,
				id);
		
        System.out.println("------------------ TEST 결과 ------------------");
        System.out.println(responseEntity.getBody());
	}

	public static void main(String args[]) throws Exception {
	    FreeTest test = new FreeTest();
	    test.testSearchKeyword();
//	    test.testGetMapLinkForID(10764517);
	}
}
