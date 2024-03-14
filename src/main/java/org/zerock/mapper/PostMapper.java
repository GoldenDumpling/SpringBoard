package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.PostVO;


public interface PostMapper {
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
	
	// 게시글 총 수 조회 메서드 추가
	int getTotalCount(); 
	
    // 페이지별 게시글 목록 가져오기
    // 페이지 처리를 위한 시작 및 종료 인덱스를 매개변수로 받음
	List<PostVO> getPaginatedPosts(@Param("start") int start, @Param("end") int end);
	
	// 추천수 증가
	public void increaseLikes(Long post_id);
	
	// 추천수 조회
	public Long getLikes(Long post_id);
	
	// 검색
	List<PostVO> searchPosts(@Param("searchType") String searchType, @Param("searchValue") String searchValue, @Param("start") int start, @Param("end") int end);

	// 검색 갯수 조회
	int getSearchCount(@Param("searchType") String searchType, @Param("searchValue") String searchValue);
	
	// 조회수 증가
    int increaseReadCount(Long postId);
    
    
    
   
}
