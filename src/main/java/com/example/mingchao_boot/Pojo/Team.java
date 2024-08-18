package com.example.mingchao_boot.Pojo;

import lombok.Data;

import java.util.List;

@Data
public class Team {
    private String mainC;
    private String mainCImage;
    private String deputyC;
    private String deputyCImage;
    private String deputyCSkill;
    private List<GmSkill> deputyCSkillInfo;
    private String wetNurse;
    private String wetNurseImage;
    private String wetNurseSkill;
    private List<GmSkill> wetNurseSkillInfo;
    private String type;
}
