package org.zerock.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.UserVO;
import org.zerock.service.UserService;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/user/*")
@AllArgsConstructor
public class UserController {
	@Setter(onMethod_ = {@Autowired})
    private UserService userService;

    @GetMapping("/login")
    public void loginForm() {
        log.info("로그인 폼으로 이동합니다.");
    }

    @PostMapping("/login")
    public String login(String username, String password, HttpSession session, RedirectAttributes rttr) {
        log.info("로그인을 시도합니다. 사용자명: " + username);

        String storedPassword = userService.getPasswordByUsername(username);
        Long user_id = userService.getIdByUsername(username);
        if (storedPassword != null && storedPassword.equals(password)) {
            log.info("로그인 성공");
            session.setAttribute("username", username); // 세션에 사용자명 저장
            session.setAttribute("user_id", user_id);
            return "redirect:/post/list"; // 로그인 성공 시 메인 페이지로 리다이렉트
        } else {
            log.info("로그인 실패");
            rttr.addFlashAttribute("modalTitle", "로그인 실패");
            rttr.addFlashAttribute("modalMessage", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "redirect:/user/login"; // 로그인 실패 시 로그인 폼으로 리다이렉트
        }
    }

    @GetMapping("/register")
    public void registerForm() {
        log.info("회원가입 폼으로 이동합니다.");
    }

    @PostMapping("/register")
    public String register(UserVO user, RedirectAttributes rttr) {
        log.info("회원가입을 시도합니다. 사용자 정보: " + user);

        if (userService.isUsernameDuplicate(user.getUsername())) {
            log.info("아이디 중복으로 회원가입 실패");
            rttr.addFlashAttribute("modalTitle", "회원가입 실패");
            rttr.addFlashAttribute("modalMessage", "이미 존재하는 아이디입니다.");
            return "redirect:/user/register"; // 회원가입 폼으로 리다이렉트
        } else {
            userService.insertUser(user);
            rttr.addFlashAttribute("modalTitle", "회원가입 성공");
            rttr.addFlashAttribute("modalMessage", "회원가입이 성공적으로 완료되었습니다.");
            return "redirect:/user/login"; // 회원가입 성공 시 로그인 폼으로 리다이렉트
        }


    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        log.info("로그아웃 처리");
        session.invalidate(); // 세션 무효화
        return "redirect:/post/list"; // 메인 페이지로 리다이렉트
    }
    
    
}
