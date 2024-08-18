package com.example.mingchao_boot.Pojo;

import lombok.Data;

import java.util.List;

@Data
public class RoleTraining {
    private BasicData roleData;
    private Weapon weapon;
    private List<SkillTraining> skills;
    private List<RoleAttribute> roleAttributes;
    private List<WeaponAttribute> weaponAttributes;
}
