package com.cos.security1.controller;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"", "/"})
    public String index() {
        // mustache 기본폴더 src/main/resources
        // 뷰리졸버 설정 templates (prefix), .mustache (suffix) 생략 가능
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    // 스프링 시큐리티가 해당 주소 낚아챔 - SecurityConfig 파일 생성 후 작동하지 않음
    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        System.out.println(user);
        user.setRole("ROLE_ USER");

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);

        userRepository.save(user); // 회원가입은 잘 되나 패스워드 암호화가 되지 않아 시큐리티 로그인 불가
        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "데이터정보";
    }
}
