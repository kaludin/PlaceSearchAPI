package me.step4.SearchPlace.model.searchplace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 검색 질의 인터페이스
 * @author Sihun
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchQuery {
	// 검색어
	private String query;
	// 현재 페이지
	private Integer page = 1;
	// 한 페이지에 보여질 문서의 개수, 1~15 사이의 값, 기본 값 15
	private Integer block_size = 15;

	public void setPage(String page, int maxPage) {
    	try {
    		this.page = Integer.valueOf(page);
    		if(null== this.page || 1 > this.page) this.page = 1;
    		if(null != this.page && maxPage < this.page) this.page = maxPage;
    	}catch(Exception e) {
    		this.page = 1;
    	}
	}

	public void setBlock_size(String p_block_size, int maxBlockSize) {
    	try {
        	this.block_size = Integer.valueOf(p_block_size);
    		if(null== this.block_size || 1 > this.block_size) this.block_size = 1;
    		if(null != this.block_size && maxBlockSize < this.block_size) this.block_size = maxBlockSize;
    	}catch(Exception e) {
    		this.block_size = maxBlockSize;
    	}
	}
}
