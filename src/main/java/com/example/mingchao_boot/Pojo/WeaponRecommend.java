package com.example.mingchao_boot.Pojo;

import lombok.Data;

import java.util.List;
@Data
public class WeaponRecommend {
    private String weaponName;
    private String weaponImage;
    private String weaponEffect;
    private String firstChoose;
    private List<Info> weaponEffectDescribe;
}
