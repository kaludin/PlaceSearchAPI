package me.step4.SearchPlace.repo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 검색 히스토리
 * @author Sihun
 *
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tb_keyword_history")
public class KeywordHistory {
	@Id @GeneratedValue
	@Column(name="keyword_seq")
	private Long seq;
	@Column(name="keyword", length=200, nullable=false)
	private String keyword;
	@Column(name="search_date", length=12, nullable=false)
	private String search_date;
	@Column(name="count", nullable=false)
	private Long count;
}
