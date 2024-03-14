package org.zerock.domain;

import java.util.Date;

import lombok.Data;

@Data
public class UserVO {
	private Long user_id;
	private String username;
	private String password;
	private String email;
	private Date created_at;

}
