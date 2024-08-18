package com.example.mingchao_boot.model;

import lombok.Data;

@Data
public class SoundBonesRating {
    private String CtType;//词条类型
    private String CtValue;//词条数值
    private Float score;//评分
    private int IsUseful;//是否有效词条
}
