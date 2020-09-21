package me.step4.SearchPlace.repo;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import me.step4.SearchPlace.repo.entity.KeywordHistory;

/**
 * 검색어 TOP10 Repository
 * @author Sihun
 *
 */
@Repository
public interface KeywordHistoryRepository extends JpaRepository<KeywordHistory, Long> {
	@Query(value = "SELECT keyword, sum(count) as count "+
            "FROM tb_keyword_history "+
            "GROUP BY keyword " +
            "ORDER BY sum(count) DESC " +
            "LIMIT 10", nativeQuery = true)
	List<Map> vestKeywordCountAll();
}