package com.example.mingchao_boot.Pojo;

import lombok.Data;

import java.util.List;

@Data
public class WeaponAttribute {
    private String level;
    private String aggressivity;
    private String aggressivityBefore;
    private String aggressivityAfter;
    private String mainCt;
    private int breakthroughId;
    private List<BreakthroughMaterial> breakthroughMaterialList;
}
