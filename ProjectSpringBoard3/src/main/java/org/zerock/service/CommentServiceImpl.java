package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.domain.CommentVO;
import org.zerock.mapper.CommentMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{
	@Setter(onMethod_ = {@Autowired})
	private CommentMapper mapper;
	
    @Override
    public List<CommentVO> getListByPostId(Long post_id) {
        log.info("특정 게시글(ID: {})의 댓글 목록을 조회합니다." + post_id);
        return mapper.getListByPostId(post_id);
    }
    @Override
    public void insert(CommentVO comment) {
        log.info("댓글을 추가합니다: {}"+ comment);
        mapper.insert(comment);
    }

    @Override
    public int update(CommentVO comment) {
        log.info("댓글을 수정합니다: {}"+ comment);
        return mapper.update(comment);
    }

    @Override
    public int delete(Long commentId) {
        log.info("댓글(ID: {})을 삭제합니다."+ commentId);
        return mapper.delete(commentId);
    }
}
