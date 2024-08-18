package com.example.mingchao_boot.model;

import lombok.Data;

@Data
public class SelectUserInfo {
    private Integer page;
    private Integer pageSize;
    private String type;
    private String keyword;
}
