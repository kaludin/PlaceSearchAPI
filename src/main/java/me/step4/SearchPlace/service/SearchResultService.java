package me.step4.SearchPlace.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import me.step4.SearchPlace.model.kakao.KakaoPlaceResult;

/**
 * 검색결과 집계 서비스
 * @author Sihun
 *
 */
@Slf4j
@Service
public class SearchResultService {
	public static final String REDIS_PRE_KEYWORD = "SEARCHPLACE:KEYWORD:";
	@Resource(name="redisTemplate")
	private RedisTemplate<String, Object> redisTemplate;
	@Resource(name="redisTemplate")
	private ValueOperations<String, Object> valueOperation;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");

	public void writeKeyword(KakaoPlaceResult result) {
		String key = "";
		try {
			// validation
			if(null==result) { return; }
			else if(result!=null) {
				if(result.getMeta()==null) return;
				if(result.getMeta().getSame_name()==null) return;
			}
			// 분단위로 키워드를 기록
			key = String.format("%s%s:%s", REDIS_PRE_KEYWORD, result.getMeta().getSame_name().getKeyword(), sdf.format(new Date()));
			valueOperation.increment(key, 1);
		} catch (Exception e) {
			log.error(e.getMessage());
			Runnable retry = () -> {
				try {
					TimeUnit.MICROSECONDS.sleep(5000);
					// 재시도 1번만..
					valueOperation.increment(String.format("%s%s:%s", REDIS_PRE_KEYWORD, result.getMeta().getSame_name().getKeyword(), sdf.format(new Date())));
				} catch (InterruptedException e1) {}
			};
			retry.run();
		} finally {
		}
		
	}

}
