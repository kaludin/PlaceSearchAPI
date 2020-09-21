package me.step4.SearchPlace.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.step4.SearchPlace.model.searchplace.VestKeywordResult;
import me.step4.SearchPlace.model.searchplace.VestKeywordResult.VestKeywordResultData;
import me.step4.SearchPlace.repo.KeywordHistoryRepository;
import me.step4.SearchPlace.repo.entity.KeywordHistory;

/**
 * KeywordHistory Service
 * @author Sihun
 *
 */

@RequiredArgsConstructor
@Service
public class KeywordHistoryService {
	
	@Autowired
	private KeywordHistoryRepository keywordHistoryRepository;
	
	/**
	 * Create KeywordHistory
	 * @param keywordHIstory
	 * @return
	 */
	public boolean createKeywordHistory(KeywordHistory keywordHIstory){
		try{
			keywordHistoryRepository.save(keywordHIstory);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
	/**
	 * Get All KeywordHistory
	 * @return
	 */
	public List<KeywordHistory> getAllKeywordHistory(){
		return keywordHistoryRepository.findAll();
	}
	
	/**
	 * Get KeywordHistory
	 * @param seq
	 * @return
	 */
	public Optional<KeywordHistory> getKeywordHistory(Long seq){
		return keywordHistoryRepository.findById(seq);
	}
	
	/**
	 * Update KeywordHistory
	 * @param seq
	 * @param keywordHistory
	 * @return
	 */
	public KeywordHistory updateKeywordHistory(Long seq, KeywordHistory keywordHistory){
		final Optional<KeywordHistory> fetchedKeywordHistory = keywordHistoryRepository.findById(seq);
		if(fetchedKeywordHistory.isPresent()){
			keywordHistory.setSeq(seq);
			return keywordHistoryRepository.save(keywordHistory);
		}
		else{
			return null;
		}
	}
	
	/**
	 * Patch KeywordHistory
	 * @param seq
	 * @param keywordHistory
	 * @return
	 */
	public KeywordHistory patchKeywordHistory(Long seq, KeywordHistory keywordHistory){
		final Optional<KeywordHistory> fetchedKeywordHistory = keywordHistoryRepository.findById(seq);
		if(fetchedKeywordHistory.isPresent()){
			if(keywordHistory.getKeyword() != null){
				fetchedKeywordHistory.get().setKeyword(keywordHistory.getKeyword());
			}
			if(keywordHistory.getSearch_date() != null){
				fetchedKeywordHistory.get().setSearch_date(keywordHistory.getSearch_date());
			}
			if(keywordHistory.getCount() > 0){
				fetchedKeywordHistory.get().setCount(keywordHistory.getCount());
			}
			return keywordHistoryRepository.save(fetchedKeywordHistory.get());
		}
		else{
			return null;
		}
	}
	
	/**
	 * Delete KeywordHistory
	 * @param seq
	 * @return
	 */
	public boolean deleteKeywordHistory(Long seq){
		final Optional<KeywordHistory> fetchedKeywordHistory = keywordHistoryRepository.findById(seq);
		if(fetchedKeywordHistory.isPresent()){
			keywordHistoryRepository.deleteById(seq);
			return true;
		}
		else{
			return false;
		}
	}
	
	public VestKeywordResult vestKeywordCountAll(){
		List<Map> list = keywordHistoryRepository.vestKeywordCountAll();
		VestKeywordResult result = new VestKeywordResult();
		result.setData(new ArrayList<VestKeywordResultData>());
		
		for(int idx =0; idx < list.size(); idx++) {
			VestKeywordResultData data = result.getData().get(idx);
			data.setRank(idx+1);
			data.setKeyword((String)list.get(idx).get("keyword"));
			data.setCount((Long)list.get(idx).get("count"));
		}
		return result;
	}
}