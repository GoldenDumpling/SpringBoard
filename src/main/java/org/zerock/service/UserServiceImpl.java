package org.zerock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.domain.UserVO;
import org.zerock.mapper.UserMapper;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.SimpleMailMessage;

import lombok.extern.log4j.Log4j;

import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

@Log4j
@Service
public class UserServiceImpl implements UserService {

    private UserMapper mapper;
    private JavaMailSenderImpl mailSender;

    @Autowired
    public UserServiceImpl(UserMapper mapper) {
        this.mapper = mapper;
        this.mailSender = new JavaMailSenderImpl();
        // 메일 서버 설정
        mailSender.setHost("smtp.gmail.com"); // SMTP 서버 주소
        mailSender.setPort(587); // SMTP 서버 포트
        mailSender.setUsername("rxd2345@gmail.com"); // 메일 계정
        mailSender.setPassword("jsbg itcz auzf trmn"); // 메일 앱 비밀번호
        
        // STARTTLS 활성화 설정
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
    }

    @Override
    public void insertUser(UserVO user, HttpServletRequest request) {
        log.info("사용자를 등록합니다: " + user);
        String confirmationToken = UUID.randomUUID().toString();
        user.setConfirmation_token(confirmationToken);
        mapper.insertUser(user);
        sendVerificationEmail(user, request);
    }


    @Override
    public String getPasswordByUsername(String username) {
        return mapper.getPasswordByUsername(username);
    }

    @Override
    public Long getIdByUsername(String username) {
        return mapper.getIdByUsername(username);
    }

    @Override
    public void updateUser(UserVO user) {
        mapper.updateUser(user);
    }

    @Override
    public UserVO getUserByToken(String confirmationToken) {
        return mapper.getUserByToken(confirmationToken);
    }

    @Override
    public UserVO getUserByUsername(String username) {
        return mapper.getUserByUsername(username);
    }

    @Override
    public boolean confirmUser(String confirmationToken) {
        UserVO user = mapper.getUserByToken(confirmationToken);
        if (user != null && "N".equals(user.getConfirmed())) {
            user.setConfirmed("Y");
            mapper.updateUser(user);
            return true;
        }
        return false;
    }

    private void sendVerificationEmail(UserVO user, HttpServletRequest request) {
    	String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    	
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("이메일 인증");
        message.setText("이메일 인증을 위해 링크를 클릭하세요: " +
                baseUrl + "/user/confirm?token=" + user.getConfirmation_token());
        mailSender.send(message);
        log.info("인증 이메일 발송: " + user.getEmail());
    }
    
    @Override
    public boolean isUsernameDuplicate(String username) {
        log.info("아이디 중복 체크를 진행합니다: " + username);
        return mapper.getIdByUsername(username) != null;
    }
}
