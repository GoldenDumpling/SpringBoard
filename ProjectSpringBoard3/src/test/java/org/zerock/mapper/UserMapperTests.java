package org.zerock.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.UserVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class UserMapperTests {
    @Setter(onMethod_ = {@Autowired})
    private UserMapper mapper;

    // 회원가입 (사용자 생성)
    @Test
    public void testInsertUser() {
        UserVO user = new UserVO();
        user.setUsername("zig");
        user.setPassword("1234");
        user.setEmail("zig@gmail.com");
        mapper.insertUser(user);
        log.info("Inserted User: " + user);
    }

    // 사용자 비밀번호 조회
    @Test
    public void testGetPasswordByUsername() {
        String username = "zig0"; // 가정: 사용자 이름이 'newUser'인 경우
        String password = mapper.getPasswordByUsername(username);
        log.info("Password for " + username + ": " + password);
    }
    
    @Test
    public void testGetUserIdByUsername() {
    	String username = "zig2"; 
        Long user_id = mapper.getIdByUsername(username);
        log.info("user_id for " + username + ": " + user_id);
    }
}
