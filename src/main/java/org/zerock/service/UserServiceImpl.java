package org.zerock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.domain.UserVO;
import org.zerock.mapper.UserMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Setter(onMethod_ = {@Autowired})
    private UserMapper mapper;

    @Override
    public void insertUser(UserVO user) {
        log.info("사용자를 등록합니다: {}" + user);
        mapper.insertUser(user);
    }

    @Override
    public String getPasswordByUsername(String username) {
        log.info("사용자(ID: {})의 비밀번호를 조회합니다." + username);
        return mapper.getPasswordByUsername(username);
    }
    
    @Override
    public Long getIdByUsername(String username) {
    	log.info("사용자(ID: {})의 유저 번호를 조회합니다." + username);
    	return mapper.getIdByUsername(username);
    }
    
    // 아이디 중복 체크
    @Override
    public boolean isUsernameDuplicate(String username) {
        Long userId = mapper.getIdByUsername(username);
        return userId != null;
    }
}
