package com.example.mingchao_boot.Controller;

import com.example.mingchao_boot.Pojo.*;
import com.example.mingchao_boot.ResultModel.ResultNormal;
import com.example.mingchao_boot.Service.getDataService;
import com.example.mingchao_boot.Service.getRoleDataService;
import com.example.mingchao_boot.Service.getWeaponDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/getData")
public class getDataController {

    @Autowired
    getDataService getDataService;
    @Autowired
    getWeaponDataService getWeaponDataService;
    @Autowired
    getRoleDataService getRoleDataService;

    @GetMapping("/getCharacterInfo")
    public ResultNormal getCharacterInfo(@RequestParam String CharacterName){
        BasicData basicData = getDataService.getCharacterInfo(CharacterName);
        List<RoleSkills> roleSkills = getDataService.getRoleSkillInfo(CharacterName);
        RoleData roleData = getDataService.getRoleBasicAttribute(CharacterName);
        roleData.setBasicData(basicData);
        roleData.setRoleSkills(roleSkills);
        return ResultNormal.success(roleData);
    }

    @GetMapping("/getRoleSkill")
    public ResultNormal getRoleSkill(@RequestParam String CharacterName){
        List<RoleSkills> roleSkills = getDataService.getRoleSkill(CharacterName);
        return ResultNormal.success(roleSkills);
    }

    @GetMapping("/getSkillList")
    public ResultNormal getSkillList(@RequestParam String role,@RequestParam String skill){
        List<Skill> list = getDataService.getSkillList(role,skill);
        List<List<Skill>> result = new ArrayList<>();
        result.add(list);
        return ResultNormal.success(result);
    }

    @GetMapping("/getResonanceChainData")
    public ResultNormal getResonanceChainData(@RequestParam String CharacterName){
        return ResultNormal.success(getDataService.getResonanceChainData(CharacterName));
    }
    @GetMapping("/getRoleDevelop")
    public ResultNormal getRoleDevelop(@RequestParam String CharacterName){
        RoleDevelop roleDevelop = getDataService.getRoleDevelop(CharacterName);
        return ResultNormal.success(roleDevelop);
    }
    @GetMapping("/getAttribute")
    public ResultNormal getRoleAttribute(@RequestParam String characterName){
        List<RoleAttribute> roleAttributes = getRoleDataService.getRoleAttributes(characterName);
        List<SkillTraining> skillLists = getRoleDataService.getRoleSkills(characterName);
        RoleTraining roleTraining = new RoleTraining();
        roleTraining.setRoleAttributes(roleAttributes);
        roleTraining.setRoleData(getDataService.getCharacterInfo(characterName));
        roleTraining.setSkills(skillLists);
        return ResultNormal.success(roleTraining);
    }
    @GetMapping("/getWeaponAttribute")
    public ResultNormal getWeaponAttribute(@RequestParam String weaponName){
        List<WeaponAttribute> weaponAttributes = getWeaponDataService.getWeaponAttribute(weaponName);
        RoleTraining roleTraining = new RoleTraining();
        roleTraining.setWeaponAttributes(weaponAttributes);
        roleTraining.setWeapon(getWeaponDataService.getWeapon(weaponName));
        return ResultNormal.success(roleTraining);
    }

}
