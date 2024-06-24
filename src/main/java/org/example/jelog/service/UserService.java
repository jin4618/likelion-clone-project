package org.example.jelog.service;

import lombok.RequiredArgsConstructor;
import org.example.jelog.domain.user.Role;
import org.example.jelog.domain.user.User;
import org.example.jelog.domain.user.UserRole;
import org.example.jelog.domain.user.UserRoleId;
import org.example.jelog.dto.UserDTO;
import org.example.jelog.repository.RoleRepository;
import org.example.jelog.repository.UserRepository;
import org.example.jelog.repository.UserRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    // 회원가입 시 아이디 중복 확인, 로그인 시 아이디 존재 확인
    public boolean isUserIdTaken(String userId) {
        return userRepository.findByUserId(userId).isPresent();
    }

    // 회원가입 시 이메일 중복 확인
    public boolean isUserEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // 사용자 아이디 일치 확인
    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    // 회원가입

    @Transactional
    public void save(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setPw(userDTO.getPw());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setRegistrationDate(LocalDateTime.now((ZoneId.of("Asia/Seoul"))));

        userRepository.save(user);

        Role userRole = roleRepository.findByName("ROLE_USER").get();
        UserRole userRoleMapping = new UserRole();
        userRoleMapping.setUser(user);
        userRoleMapping.setRole(userRole);
        userRoleMapping.setId(new UserRoleId(user.getId(), userRole.getId()));
        userRoleRepository.save(userRoleMapping);
    }

    public String login(String userId, String password) {
        Optional<User> user = userRepository.findByUserId(userId);

        if (user.isEmpty()) {
            return "존재하지 않는 아이디입니다.";
        }

        User getUser = user.get();
        if (!getUser.getPw().equals(password)) {
            return "비밀번호가 일치하지 않습니다.";
        }

        return null;    // 로그인 성공
    }
}
