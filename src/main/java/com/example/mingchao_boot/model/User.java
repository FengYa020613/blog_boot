package com.example.mingchao_boot.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private long userId;
    private String phone;
    private String nickName;
    private String avatar;
    private int articleNum;
    private int likeNum;
    private int commentNum;
    private int flag = 0;
    private int fans = 0;
    private int sex;
    private int attention;
    private int fansNum = 0;
    private int attentionNum = 0;
    private String signature;
}
