package com.example.mingchao_boot.model;

import lombok.Data;

@Data
public class UserCode {
    private int userId;
    private String phone;
    private String code;
    private String token;
    private String nickName;

    public UserCode(String phone, String code) {
        this.phone = phone;
        this.code = code;
    }
}
