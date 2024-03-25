package org.zerock.mapper;

import org.zerock.domain.UserVO;

public interface UserMapper {

    // 사용자 추가
    void insertUser(UserVO user);

    // 사용자 이름으로 비밀번호 조회
    String getPasswordByUsername(String username);

    // 사용자 이름으로 사용자 ID 조회
    Long getIdByUsername(String username);

    // 사용자 정보 업데이트
    void updateUser(UserVO user);

    // 인증 토큰으로 사용자 조회
    UserVO getUserByToken(String confirmationToken);

    // 사용자 이름으로 사용자 조회
    UserVO getUserByUsername(String username);

    // 사용자 이메일 인증
    void confirmUser(String confirmationToken);
}
