package org.example.jelog.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String userId;
    private String pw;
    private String checkPw;
    private String name;
    private String email;
}
