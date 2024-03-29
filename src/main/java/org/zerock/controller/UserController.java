package org.zerock.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        UserVO user = userService.getUserByUsername(username); // User 정보를 가져옵니다.

        if (storedPassword != null && storedPassword.equals(password) && user != null) {
            if ("Y".equals(user.getConfirmed())) {
                log.info("로그인 성공");
                session.setAttribute("username", username);
                session.setAttribute("user_id", user_id);
                return "redirect:/post/list";
            } else {
                log.info("이메일 인증이 완료되지 않았습니다.");
                rttr.addFlashAttribute("modalTitle", "로그인 실패");
                rttr.addFlashAttribute("modalMessage", "이메일 인증이 완료되지 않았습니다. 이메일을 확인하여 인증을 완료해주세요.");
                return "redirect:/user/login";
            }
        } else {
            log.info("로그인 실패");
            rttr.addFlashAttribute("modalTitle", "로그인 실패");
            rttr.addFlashAttribute("modalMessage", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "redirect:/user/login";
        }
    }

    @GetMapping("/register")
    public void registerForm() {
        log.info("회원가입 폼으로 이동합니다.");
    }

    @PostMapping("/register")
    public String register(UserVO user, RedirectAttributes rttr, HttpServletRequest request) {
        log.info("회원가입을 시도합니다. 사용자 정보: " + user);
        
        if (userService.isUsernameDuplicate(user.getUsername())) {
            log.info("아이디 중복으로 회원가입 실패");
            rttr.addFlashAttribute("modalTitle", "회원가입 실패");
            rttr.addFlashAttribute("modalMessage", "이미 존재하는 아이디입니다.");
            return "redirect:/user/register";
        } else {
            userService.insertUser(user, request);
            // 회원가입 성공 후 이메일 인증 요청 모달 메시지
            rttr.addFlashAttribute("modalTitle", "회원가입 성공");
            rttr.addFlashAttribute("modalMessage", "회원가입이 완료되었습니다. 이메일을 확인하여 인증을 완료해주세요.");
            return "redirect:/user/login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        log.info("로그아웃 처리");
        session.invalidate(); // 세션 무효화
        return "redirect:/post/list"; // 메인 페이지로 리다이렉트
    }
    
    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token, RedirectAttributes rttr) {
        if (userService.confirmUser(token)) {
            rttr.addFlashAttribute("modalTitle", "이메일 인증 성공");
            rttr.addFlashAttribute("modalMessage", "이메일 인증이 완료되었습니다.");
            return "redirect:/user/login";
        } else {
            rttr.addFlashAttribute("modalTitle", "이메일 인증 실패");
            rttr.addFlashAttribute("modalMessage", "유효하지 않거나 만료된 인증 토큰입니다.");
            return "redirect:/user/login";
        }
    }
}
