package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.domain.PostVO;
import org.zerock.mapper.PostMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    @Setter(onMethod_ = {@Autowired})
    private PostMapper mapper;
    
    @Override
    public List<PostVO> getList() {
        log.info("게시글 목록을 조회합니다.");
        return mapper.getList();
    }

    @Override
    public void insertSelectKey(PostVO post) {
        log.info("게시글을 등록합니다: {}" + post);
        mapper.insertSelectKey(post);
    }

    @Override
    public PostVO read(Long post_id) {
        log.info("게시글(ID: {})을 조회합니다." + post_id);
        return mapper.read(post_id);
    }

    @Override
    public int delete(Long post_id) {
        log.info("게시글(ID: {})을 삭제합니다." + post_id);
        return mapper.delete(post_id);
    }

    @Override
    public int update(PostVO post) {
        log.info("게시글을 수정합니다: {}" + post);
        return mapper.update(post);
    }
    
    @Override
    public int getTotalCount() {
        log.info("게시글 총 수를 조회합니다.");
        return mapper.getTotalCount();
    }

    @Override
    public List<PostVO> getPaginatedPosts(int start, int end) {
        log.info("페이지별 게시글 목록을 조회합니다. 시작: " + start + ", 끝: " + end);
        return mapper.getPaginatedPosts(start, end);
    }
    
    @Override
	public void increaseLikes(Long post_id) {
	    log.info("게시글 번호 " + post_id + "의 추천수를 증가시킵니다.");
	    mapper.increaseLikes(post_id);
    }
    
    @Override
	public Long getLikes(Long post_id) {
	    log.info("게시글 번호 " + post_id + "의 추천수를 조회합니다.");
	    return mapper.getLikes(post_id);
    }
    
    @Override
    public void increaseReadCount(Long post_id) {
    	log.info("게시글 번호 " + post_id + "의 조회수를 증가합니다.");
        mapper.increaseReadCount(post_id);
    }
    
    @Override
    public List<PostVO> searchPosts(String searchType, String searchValue, int start, int end) {
    	log.info("검색한 페이지별 게시글 목록을 조회합니다. 시작: " + start + ", 끝: " + end);
        return mapper.searchPosts(searchType, searchValue, start, end);
    }
    
    @Override
    public int getSearchCount(String searchType, String searchValue) {
        return mapper.getSearchCount(searchType, searchValue);
    }
}
