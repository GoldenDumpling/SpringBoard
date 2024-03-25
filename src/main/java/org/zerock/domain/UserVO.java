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
    private String confirmed; // 이메일 인증 여부
    private String confirmation_token; // 이메일 인증 토큰
}
