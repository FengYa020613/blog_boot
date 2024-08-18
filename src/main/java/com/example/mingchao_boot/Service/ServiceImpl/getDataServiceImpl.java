package com.example.mingchao_boot.Service.ServiceImpl;

import com.example.mingchao_boot.Mapper.getDataMapper;
import com.example.mingchao_boot.Pojo.*;
import com.example.mingchao_boot.Service.getDataService;
import com.example.mingchao_boot.model.SkillBranch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
public class getDataServiceImpl implements getDataService {

    @Autowired
    getDataMapper getDataMapper;

    @Override
    public List<Skill> getSkillList(String role, String skill) {
        List<Skill> skillList = getDataMapper.getSkillTableData(role,skill);
        return skillList;
    }
    @Override
    public BasicData getCharacterInfo(String characterName) {
        BasicData basicData = getDataMapper.getCharacterBasicData(characterName);
        return basicData;
    }
    @Override
    public RoleData getRoleBasicAttribute(String characterName) {
        RoleData roleData = new RoleData();
        List<RoleAttribute> list = new ArrayList<>();
        list = getDataMapper.getRoleAttribute(characterName);
        for (RoleAttribute roleAttribute:list) {
            int Id = roleAttribute.getBreakthroughMaterialId();
            List<BreakthroughMaterial> materialList = getDataMapper.getBreakthroughMaterialList(Id);
            roleAttribute.setBreakthroughMaterials((ArrayList<BreakthroughMaterial>) materialList);
        }
        roleData.setRoleAttributes(list);
        return roleData;
    }

    @Override
    public List<RoleSkills> getRoleSkillInfo(String characterName) {
        List<RoleSkills> skills = getDataMapper.getSkills(characterName);
        for (RoleSkills skill : skills) {
            String s = skill.getSkillDescribe();
            List<List<BreakthroughMaterial>> SkillBreakthroughMaterials = getSkillBreakthroughMaterials(characterName,s);
            List<SkillDescribe> skillDescribeList = getDataMapper.getSkillDescribeList(s);
            List<Skill> skillData = getDataMapper.getSkillTableData(characterName,s);
            for (SkillDescribe skillDescribe : skillDescribeList) {
                int id = skillDescribe.getId();
                List<List<Info>> list = new ArrayList<>();
                List<Info> skillInfoList = getDataMapper.getSkillText(id);
                List<Info> skillInfos = new ArrayList<>();
                for (int i = 0;i<skillInfoList.size();i++) {
                    Info skillInfo = skillInfoList.get(i);
                    if (!skillInfo.getText().equals("split")&&i!=skillInfoList.size()-1) {
                        skillInfos.add(skillInfo);
                    }else if(skillInfo.getText().equals("split")){
                        List<Info> newList = new ArrayList<>(skillInfos.stream().toList());
                        Collections.copy(newList,skillInfos);
                        list.add(newList);
                        skillInfos.clear();
                    }else{
                        skillInfos.add(skillInfo);
                        List<Info> newList = new ArrayList<>(skillInfos.stream().toList());
                        Collections.copy(newList,skillInfos);
                        list.add(newList);
                        skillInfos.clear();
                    }
                }
                skillDescribe.setSkillInfoList(list);
            }
            skill.setSkillDescribeList(skillDescribeList);
            skill.setSkillData(skillData);
            skill.setSkillBreakthroughMaterials(SkillBreakthroughMaterials);
        }
        return skills;
    }

    @Override
    public List<RoleSkills> getRoleSkill(String characterName) {
        List<RoleSkills> skills = getDataMapper.getSkills(characterName);
        for (RoleSkills roleSkill:skills) {
            String skill = roleSkill.getSkillName();
            List<SkillBranch> branches = getDataMapper.getSkillBranchs(skill);
            roleSkill.setBranchList(branches);
        }
        return skills;
    }

    @Override
    public List<List<BreakthroughMaterial>> getSkillBreakthroughMaterials(String characterName, String skill) {
        List<List<BreakthroughMaterial>> breakthroughMaterials = new ArrayList<>();
        for (int i = 1; i <= 10 ; i++) {
            List<BreakthroughMaterial> materialList = getDataMapper.getSkillBreakthroughMaterialList(characterName,i);
            breakthroughMaterials.add(materialList);
        }
        return breakthroughMaterials;
    }

    @Override
    public List<ResonanceChain> getResonanceChainData(String characterName) {
        List<ResonanceChain> chainList = getDataMapper.getResonanceChain(characterName);
        for (ResonanceChain resonanceChain : chainList) {
            List<List<ResonanceChainDescribe>> chainDescribeList = new ArrayList<>();
            List<ResonanceChainDescribe> list = getDataMapper.getResonanceChainDescribe(resonanceChain.getId());
            List<ResonanceChainDescribe> resonanceChainDescribeList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                ResonanceChainDescribe describe = list.get(i);
                if (!describe.getText().equals("split")&&i!=list.size()-1){
                    resonanceChainDescribeList.add(describe);
                }else if(describe.getText().equals("split")){
                    List<ResonanceChainDescribe> newList = new ArrayList<>(resonanceChainDescribeList.stream().toList());
                    Collections.copy(newList,resonanceChainDescribeList);
                    chainDescribeList.add(newList);
                    resonanceChainDescribeList.clear();
                }else{
                    resonanceChainDescribeList.add(describe);
                    List<ResonanceChainDescribe> newList = new ArrayList<>(resonanceChainDescribeList.stream().toList());
                    Collections.copy(newList,resonanceChainDescribeList);
                    chainDescribeList.add(newList);
                    resonanceChainDescribeList.clear();
                }
            }
            resonanceChain.setChainDescribeList(chainDescribeList);
        }
        return chainList;
    }

    @Override
    public RoleDevelop getRoleDevelop(String characterName) {
        RoleDevelop roleDevelop = new RoleDevelop();
        List<WeaponRecommend> weaponRecommends = getDataMapper.getWeaponRecommend(characterName);
        for (WeaponRecommend weaponRecommend : weaponRecommends) {
            String effect = weaponRecommend.getWeaponEffect();
            List<Info> effectInfoList = getDataMapper.getWeaponEffectInfo(effect);
            weaponRecommend.setWeaponEffectDescribe(effectInfoList);
        }
        roleDevelop.setWeaponRecommend(weaponRecommends);
        AsPackageRecommend asPackageRecommend = getDataMapper.getAsPackageRecommend(characterName);
        String acousticSkeleton = asPackageRecommend.getAcousticSkeleton();
        String packageName = asPackageRecommend.getAsPackage();
        AsPackage asPackage = getDataMapper.getAsPackage(packageName);
        asPackageRecommend.setPackage(asPackage);
        AcousticSkeleton acousticSkeletonInfo = getDataMapper.getAcousticSkeleton(acousticSkeleton);
        List<Info> asDescribe = getDataMapper.getAsDescribe(acousticSkeleton);
        acousticSkeletonInfo.setAsEffect(asDescribe);
        asPackageRecommend.setAcousticSkeletonInfo(acousticSkeletonInfo);
        roleDevelop.setSkillLists(getSkillRecommend(characterName));
        roleDevelop.setTeam(getTeam(characterName));
        roleDevelop.setAsPackageRecommend(asPackageRecommend);
        return roleDevelop;
    }

    @Override
    public String getRoleImage(String roleName) {
        return getDataMapper.getRoleImage(roleName);
    }

    @Override
    public String getWeaponImage(String weaponName) {
        return getDataMapper.getWeaponImage(weaponName);
    }

    private List<SkillList> getSkillRecommend(String characterName) {
        List<SkillList> skills = getDataMapper.getSkillRecommend(characterName);
        return skills;
    }

    private List<Team> getTeam(String characterName) {
        List<Team> team = getDataMapper.getTeam(characterName);
        for(Team teamItem:team){
            String deputyCSkill = teamItem.getDeputyCSkill();
            String wetNurseSkill = teamItem.getWetNurseSkill();
            List<GmSkill> skill1 = getDataMapper.getGmSkill(deputyCSkill);
            List<GmSkill> skill2 = getDataMapper.getGmSkill(wetNurseSkill);
            teamItem.setDeputyCSkillInfo(skill1);
            teamItem.setWetNurseSkillInfo(skill2);
        }
        return team;
    }
}
