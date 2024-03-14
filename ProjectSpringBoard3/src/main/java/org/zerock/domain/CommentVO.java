package org.zerock.domain;

import java.util.Date;

import lombok.Data;

@Data
public class CommentVO {
	private Long comment_id;
	private Long post_id;
	private Long user_id;
	private String content;
	private Date created_at;
    // 사용자 정보 필드 추가
    private String username;
}
