package org.zerock.service;

import java.util.List;


import org.zerock.domain.CommentVO;


public interface CommentService {
    // 특정 게시글의 댓글 목록 조회
    List<CommentVO> getListByPostId(Long post_id);

    // 댓글 추가
    void insert(CommentVO comment);

    // 댓글 수정
    int update(CommentVO comment);

    // 댓글 삭제
    int delete(Long commentId);
}
