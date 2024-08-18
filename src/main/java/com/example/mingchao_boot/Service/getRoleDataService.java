package com.example.mingchao_boot.Service;

import com.example.mingchao_boot.Pojo.Role;
import com.example.mingchao_boot.Pojo.RoleAttribute;
import com.example.mingchao_boot.Pojo.SkillTraining;

import java.util.List;

public interface getRoleDataService {
    List<Role> getRoleList(String rarity,String type,String weapon);
    List<RoleAttribute> getRoleAttributes(String characterName);
    List<SkillTraining> getRoleSkills(String characterName);
}
