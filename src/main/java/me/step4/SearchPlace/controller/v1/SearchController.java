package me.step4.SearchPlace.controller.v1;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.step4.SearchPlace.advice.exception.BadRequestException;
import me.step4.SearchPlace.model.SingleResult;
import me.step4.SearchPlace.model.kakao.KakaoPlaceResult;
import me.step4.SearchPlace.model.searchplace.SearchPlaceResult;
import me.step4.SearchPlace.model.searchplace.SearchQuery;
import me.step4.SearchPlace.model.searchplace.VestKeywordResult;
import me.step4.SearchPlace.service.KeywordHistoryService;
import me.step4.SearchPlace.service.ResponseService;
import me.step4.SearchPlace.service.SearchResultService;
import me.step4.SearchPlace.service.kakao.KakaoSearchPlaceService;

/**
 * 검색 컨트롤러
 * @author Sihun
 *
 */
@Slf4j
@Api(tags = {"3. Search"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/search")
public class SearchController {
	@Autowired
	private final KakaoSearchPlaceService kakaoSearchPlaceService;
	@Autowired
	private final ResponseService responseService;
	@Autowired
	private final SearchResultService searchResultService;
	@Autowired
	private final KeywordHistoryService keywordHistoryService;

    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
	
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 후 token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "장소 검색", notes = "장소 검색")
    @GetMapping(value = "/place/{vendor}")
    public SingleResult<SearchPlaceResult> searchPlace(
			@ApiParam(value="검색서비스제공자", required = true)
			@PathVariable String vendor,
			@ApiParam(value="검색어", required = true)
			@RequestParam String query,
			@ApiParam(value="검색 페이지", defaultValue = "1")
			@RequestParam String page,
			@ApiParam(value="가져올 데이터 수", defaultValue = "15")
			@RequestParam String block_size,
			final HttpServletResponse resp) {
    	
    	log.debug("start search");
		SearchQuery queryObj = new SearchQuery();
    	// validation
    	if(!kakaoSearchPlaceService.validation(queryObj, query, page, block_size)) {
        	log.debug("validation fail");
    		throw new BadRequestException();
    	}
    	log.debug("validation ok");
    	
    	RestTemplate template = new RestTemplate(requestFactory);
		HttpHeaders headers = makeCommonHeader();
    	log.debug("makeCommonHeader ok");

    	SearchPlaceResult result = null;
    	if("kakao".equals(vendor)){
        	result = kakaoSearchPlaceService.callApi(template, headers, queryObj);
        	log.debug("callApi ok");
    		// redis에 키워드 기록
    		searchResultService.writeKeyword((KakaoPlaceResult)result);
    	}
		
    	resp.setStatus(HttpServletResponse.SC_OK);
        return responseService.getSingleResult(result);
    }
    
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 후 token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "인기 검색어 목록", notes = "인기 검색어 목록")
    @GetMapping(value = "/vest-keyword/{vendor}")
    public SingleResult<VestKeywordResult> vestKeyword(
			@ApiParam(value="검색서비스제공자", required = true)
			@PathVariable String vendor,
			final HttpServletResponse resp) {

    	VestKeywordResult result = keywordHistoryService.vestKeywordCountAll();
		
    	resp.setStatus(HttpServletResponse.SC_OK);
        return responseService.getSingleResult(result);
    }
    
	private HttpHeaders makeCommonHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}
