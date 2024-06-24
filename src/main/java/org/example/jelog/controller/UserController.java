package org.example.jelog.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.jelog.domain.user.User;
import org.example.jelog.dto.UserDTO;
import org.example.jelog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/jelog")
public class UserController {
    private final UserService userService;

    @GetMapping
    public String index() {
        return "jelog/index";
    }

    @GetMapping("/userreg")
    public String userreg(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "jelog/userregform";
    }

    @PostMapping("/userregform")
    public String userregform(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
        if (userService.isUserIdTaken(userDTO.getUserId())) {
            model.addAttribute("error", "이미 존재하는 아이디입니다.");
            return "jelog/userregform";
        }
        if (userService.isUserEmailTaken(userDTO.getEmail())) {
            model.addAttribute("error", "이미 존재하는 이메일입니다.");
            return "jelog/userregform";
        }
        if (!userDTO.getPw().equals(userDTO.getCheckPw())) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "jelog/userregform";
        }

        userService.save(userDTO);
        return "redirect:/jelog";
    }

    @GetMapping("/login")
    public String loginform() {
        return "jelog/loginform";
    }

    @PostMapping("/loginform")
    public String login(@RequestParam("userId") String userId, @RequestParam("pw") String pw, Model model, HttpServletResponse response) {
        String errorMsg = userService.login(userId, pw);
        if (errorMsg != null) {
            model.addAttribute("error", errorMsg);
            return "jelog/loginform";
        }

        User user = userService.findByUserId(userId).get();

        Cookie cookie = new Cookie("userId", user.getUserId());
        cookie.setPath("/");

        response.addCookie(cookie);

        return "redirect:/jelog";
    }

    @PostMapping("/logout")
    public String logout(@CookieValue(value = "userId", required = false) Cookie cookie, HttpServletResponse response) {
        cookie = new Cookie("userId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/jelog";
    }
}
