package me.step4.SearchPlace.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import me.step4.SearchPlace.repo.entity.KeywordHistory;

/**
 * 검색 히스토리 스케쥴
 * 검색 데이터 집계
 * @author Sihun
 *
 */
@Slf4j
@Component
public class KeywordHistoryScheduler {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
	@Resource(name="redisTemplate")
	private RedisTemplate<String, Object> redisTemplate;
	@Resource(name="redisTemplate")
	private ValueOperations<String, Object> valueOperation;
	
	@Autowired
	KeywordHistoryService keywordHistoryService;

	@Value("${custom.schedule.keyword.run}")
	private Boolean isRunSchedule;
	
	/**
	 * 1분 주기로 레디스에 기록되어있는 키워드를 DB에 저장
	 */
	@Scheduled(fixedDelay = (1000*60*1))  // , initialDelay = (1000*60*1)
	public void schedule() {
		if(!isRunSchedule) {
			log.info("stop keyword schedule..");
			return;
		}
		log.info("keyword schedule start..");
		
		Date date = new Date();
		int passCnt=0, createCnt=0;
		Set<String> keys = null;
		List<String> keyList = null;
		String currDate = null;
		String key = null;
		
		try {
			// 1. 2분 전
			date.setTime(date.getTime() - (1000*60*2));
			currDate = sdf.format(date);
			log.debug("filter time: {}", currDate);
			
			// 2. 키를 전부 가져와서
			keys = redisTemplate.keys(SearchResultService.REDIS_PRE_KEYWORD+"*");
			keyList = new ArrayList<String>(keys);
			log.debug("key size: {}", keyList.size());
			
			for(int idx= keyList.size()-1; idx >=0; idx--) {
				key = keyList.get(idx);
				log.debug("[{}/{}] {}", idx, keyList.size(), key);
				
				// 3. 걸러주기
				if( Long.valueOf(currDate) < Long.valueOf(key.split(":")[3]) ) {
					++passCnt;
					continue;
				}
				
				long count = 0;
				try {
					count = Long.valueOf((String)valueOperation.get(key));
					
					// 4. insert
					keywordHistoryService.createKeywordHistory(KeywordHistory.builder()
							.keyword(key.split(":")[2])
							.search_date(key.split(":")[3])
							.count(count)
							.build());
					
					redisTemplate.delete(key);
					++createCnt;
				} catch(Exception e) {
					log.error("[{}/{}] {}", idx, keyList.size(), e.getMessage());
				} finally {
					log.debug("[{}({}): {}] work fin.", key.split(":")[2], key.split(":")[3], count);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			keys = null;
			keyList = null;
			log.info("fin [create: {}, pass: {}] {}", createCnt, passCnt, currDate);
		}
		
		log.info("keyword schedule end..");
	}
}
