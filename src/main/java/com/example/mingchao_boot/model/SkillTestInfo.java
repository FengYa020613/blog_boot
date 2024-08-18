package com.example.mingchao_boot.model;

import lombok.Data;

@Data
public class SkillTestInfo {
    private String roleName;
    private String describe;//技能描述
    private String skillType;//技能类型
    private String damageType;//伤害类型
    private int start;//开始行
    private int end;//结束行
}
