package com.example.mingchao_boot.Pojo;

import com.example.mingchao_boot.model.SkillBranch;
import lombok.Data;

import java.util.List;

@Data
public class RoleSkills {
    private String skillName;
    private int level = 1;
    private String skillImage;
    private String skillDescribe;
    private List<SkillBranch> branchList;
    private List<SkillDescribe> skillDescribeList;
    private List<Skill> skillData;
    private List<List<BreakthroughMaterial>> skillBreakthroughMaterials;
}
