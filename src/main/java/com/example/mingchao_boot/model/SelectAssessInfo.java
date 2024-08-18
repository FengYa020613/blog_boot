package com.example.mingchao_boot.model;

import lombok.Data;

@Data
public class SelectAssessInfo {
    private Integer page;
    private Integer pageSize;
    private String userName;
    private String startTime;
    private String endTime;
}
