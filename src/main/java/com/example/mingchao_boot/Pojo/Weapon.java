package com.example.mingchao_boot.Pojo;

import lombok.Data;

import java.util.List;

@Data
public class Weapon {
    private String weaponName;
    private String weaponImage;
    private String weaponEffect;
    private int star;
    private String mainCt;
    private List<Info> weaponEffectDescribe;
}
