package com.example.mingchao_boot.Pojo;

import com.example.mingchao_boot.model.SoundBonesData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class TestData {
    private String roleName;
    @JsonProperty(value = "weaponName")
    private String weaponName;
    private int roleLevel;
    private int weaponLevel;
    private int weaponRefine;
    private String type;
    private String weaponMainCt;
    private TestBasicData basicData;
    private List<SkillTraining> skills;
    @JsonProperty(value = "HmEffect")
    private List<HmEffect> HmEffect;
    @JsonProperty(value = "SoundBones")
    private List<SoundBonesData> SoundBones;
}

