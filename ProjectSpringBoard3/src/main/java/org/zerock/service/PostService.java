package org.zerock.service;

import java.util.List;

import org.zerock.domain.PostVO;

public interface PostService {
	// select
	public List<PostVO> getList();
	
	// insert
	public void insertSelectKey(PostVO post);
	
	// read
	public PostVO read(Long post_id);
	
	// delete
	public int delete(Long post_id);
	
	// update
	public int update(PostVO post);
	
	// 새로운 메서드: 게시글 총 수 반환
	int getTotalCount();
	
	// 새로운 메서드: 페이지별 게시글 목록
    List<PostVO> getPaginatedPosts(int start, int end); 
    
	// 추천수 증가
	public void increaseLikes(Long post_id);
	
	// 추천수 조회
	public Long getLikes(Long post_id);
	
	// 조회수 증가
	public void increaseReadCount(Long postId);
	
	// 검색
	List<PostVO> searchPosts(String searchType, String searchValue, int start, int end);
	
	// 검색 갯수
	int getSearchCount(String searchType, String searchValue);
}
