package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.CommentVO;

public interface CommentMapper {

    // 특정 게시글의 댓글 목록 조회
    public List<CommentVO> getListByPostId(Long post_id);

    // 댓글 추가
    public void insert(CommentVO comment);

    // 댓글 수정
    public int update(CommentVO comment);

    // 댓글 삭제
    public int delete(Long commentId);
}
