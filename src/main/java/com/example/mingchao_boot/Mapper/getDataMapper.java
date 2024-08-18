package com.example.mingchao_boot.Mapper;

import com.example.mingchao_boot.Pojo.*;
import com.example.mingchao_boot.model.AttributeWeight;
import com.example.mingchao_boot.model.SkillBranch;
import com.example.mingchao_boot.model.SkillTestInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface getDataMapper {
    List<Skill> getSkillTableData(String character, String skill);
    BasicData getCharacterBasicData(String characterName);
    List<RoleAttribute> getRoleAttribute(String characterName);
    List<BreakthroughMaterial> getBreakthroughMaterialList(int id);
    List<RoleSkills> getSkills(String characterName);
    List<SkillDescribe> getSkillDescribeList(String describe);
    List<Info> getSkillText(int id);
    List<BreakthroughMaterial> getSkillBreakthroughMaterialList(String skill, int level);
    List<ResonanceChain> getResonanceChain(String characterName);
    List<ResonanceChainDescribe> getResonanceChainDescribe(int id);
    List<WeaponRecommend> getWeaponRecommend(String characterName);
    List<Info> getWeaponEffectInfo(String effect);
    AsPackageRecommend getAsPackageRecommend(String characterName);
    AcousticSkeleton getAcousticSkeleton(String acousticSkeleton);
    List<Info> getAsDescribe(String acousticSkeleton);
    AsPackage getAsPackage(String packageName);
    List<Team> getTeam(String characterName);
    List<GmSkill> getGmSkill(String skillName);
    List<SkillList> getSkillRecommend(String characterName);
    List<SkillTestInfo> getSkillDamageTypeInfo(String roleName);
    AttributeWeight getWeightList(String roleName);
    List<SkillBranch> getSkillBranchs(String skill);
    String getRoleImage(String roleName);
    String getWeaponImage(String weaponName);
}

