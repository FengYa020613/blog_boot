package com.example.mingchao_boot.model;

import com.example.mingchao_boot.Pojo.BasicData;
import com.example.mingchao_boot.Pojo.SkillTraining;
import com.example.mingchao_boot.Pojo.TestBasicData;
import com.example.mingchao_boot.Pojo.TestData;
import lombok.Data;

import java.util.List;

@Data
public class DamageCalculateResult {
    private Float graduationScore;
    private String RoleName;
    private String RoleImage;
    private String WeaponName;
    private String WeaponImage;
    private String type;
    private int roleChain;
    private int WeaponRefine;
    private String DamageLevel;
    private Float totalSoundBonesScore;
    private Float soundBonesGraduation;
    private TestBasicData basicData;
    private List<SkillTraining> skills;
    private List<DamageCalculate> damageCalculateList;
    private List<SoundBonesCalculate> soundBonesCalculateList;
}
