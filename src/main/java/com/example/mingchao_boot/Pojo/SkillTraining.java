package com.example.mingchao_boot.Pojo;

import lombok.Data;

import java.util.List;

@Data
public class SkillTraining {
    private String type;
    private int level;
    private List<Integer> branch;
}
