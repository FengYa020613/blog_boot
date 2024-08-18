package com.example.mingchao_boot.Pojo;

import lombok.Data;

import java.util.List;

@Data
public class SkillDescribe {
    private int id;
    private String name;
    private List<List<Info>> skillInfoList;
}
