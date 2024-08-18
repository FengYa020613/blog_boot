package com.example.mingchao_boot.Pojo;

import lombok.Data;

import java.util.List;

@Data
public class WeaponInfo {
    private String weaponName;
    private String weaponImage;
    private String weaponEffect;
    private int star;
    private List<Info> weaponEffectDescribe;
    private String type;
    private String mainCt;
    private String intro;
    private String getWay;
    private List<WeaponAttribute> weaponAttributes;
}
