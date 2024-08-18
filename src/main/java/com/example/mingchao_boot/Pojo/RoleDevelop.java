package com.example.mingchao_boot.Pojo;

import lombok.Data;
import java.util.List;

@Data
public class RoleDevelop{
    private List<WeaponRecommend> weaponRecommend;
    private AsPackageRecommend asPackageRecommend;
    private List<Team> team;
    private List<SkillList> skillLists;
}
