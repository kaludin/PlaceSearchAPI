package me.step4.SearchPlace.model;

import java.util.HashMap;

import lombok.Data;

/**
 * 검색 API 정보
 * @author Sihun
 *
 */
@Data
public class ApiVendor{
	private String host;
	private String authHeader;
	private String token;
	private HashMap<String,Object> uri;
}