package org.zerock.domain;

import java.util.Date;

import lombok.Data;

@Data
public class PostVO {
	private Long post_id;
	private Long user_id;
	private String title;
	private String content;
	private Date created_at;
	private Date updated_at;
	private Long readcount;
	private Long likes;
	private String username;
	private String file_uuid; 
    private String file_type; 
    private String originalFileName;
}
