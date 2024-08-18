package com.example.mingchao_boot.Service;

import com.example.mingchao_boot.Pojo.*;

import java.util.List;

public interface getDataService {
    List<Skill> getSkillList(String role, String skill);
    BasicData getCharacterInfo(String characterName);
    RoleData getRoleBasicAttribute(String characterName);
    List<RoleSkills> getRoleSkillInfo(String characterName);
    List<RoleSkills> getRoleSkill(String characterName);
    List<List<BreakthroughMaterial>> getSkillBreakthroughMaterials(String characterName,String skill);
    List<ResonanceChain> getResonanceChainData(String characterName);
    RoleDevelop getRoleDevelop(String characterName);
    String getRoleImage(String roleName);
    String getWeaponImage(String weaponName);
}
