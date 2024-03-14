package org.zerock.service;

import org.zerock.domain.UserVO;

public interface UserService {
    // 회원가입 (사용자 생성)
    public void insertUser(UserVO user);

    // 사용자 비밀번호 조회
    public String getPasswordByUsername(String username);
    
    // 사용자 아이디번호 조회
    public Long getIdByUsername(String username);
    
    // 아이디 중복 체크
    public boolean isUsernameDuplicate(String username);
    
}
