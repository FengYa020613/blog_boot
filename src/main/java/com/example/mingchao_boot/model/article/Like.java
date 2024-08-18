package com.example.mingchao_boot.model.article;

import lombok.Data;

@Data
public class Like {
    private int id;
    private int type;
    private int num;
    private String userId;
}
