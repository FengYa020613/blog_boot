package com.example.mingchao_boot.model;

import lombok.Data;

@Data
public class SelectArticleInfo {
    private Integer page;
    private Integer pageSize;
    private String keyword;
    private String module;
    private int moduleInt;
    private String status;
    private int statusInt;
}
