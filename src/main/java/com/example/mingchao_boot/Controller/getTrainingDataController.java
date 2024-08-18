package com.example.mingchao_boot.Controller;

import com.example.mingchao_boot.Mapper.getDataMapper;
import com.example.mingchao_boot.Pojo.HmEffect;
import com.example.mingchao_boot.Pojo.RoleSkills;
import com.example.mingchao_boot.Pojo.TestBasicData;
import com.example.mingchao_boot.Pojo.TestData;
import com.example.mingchao_boot.ResultModel.ResultNormal;
import com.example.mingchao_boot.Service.calculateService;
import com.example.mingchao_boot.Service.getDataService;
import com.example.mingchao_boot.model.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/getTestData")
public class getTrainingDataController {

    @Autowired
    calculateService calculateService;
    @Autowired
    getDataService getDataService;
    @Autowired
    getDataMapper getDataMapper;

    @PostMapping("/getData")
    public ResultNormal getTrainingData(@RequestBody TestData testData){
        System.out.println(testData.getHmEffect());
        TestBasicData basicData = calculateService.calculateAttribute(testData);
        System.out.println(basicData);
        testData.setBasicData(basicData);
        AttributeWeight weightList = getDataMapper.getWeightList(testData.getRoleName());
        DamageCalculateResult calculateResult = calculateService.CalculateDamage(testData);
        List<SoundBonesCalculate> soundBonesCalculateList =
                calculateService.CalculateSoundBonesRating(testData.getSoundBones(),testData.getRoleName());
        float score = 0F;
        for (SoundBonesCalculate bonesCalculate : soundBonesCalculateList){
            score += bonesCalculate.getScore();
        }
        calculateResult.setSoundBonesGraduation(score/(weightList.getGraduationScore()*5));
        calculateResult.setSoundBonesCalculateList(soundBonesCalculateList);
        calculateResult.setTotalSoundBonesScore(score);
        System.out.println(soundBonesCalculateList);
        calculateResult.setSoundBonesCalculateList(soundBonesCalculateList);
        calculateResult.setBasicData(testData.getBasicData());
        calculateResult.setSkills(testData.getSkills());
        calculateResult.setRoleImage(getDataMapper.getRoleImage(testData.getRoleName()));
        calculateResult.setRoleName(testData.getRoleName());
        calculateResult.setWeaponImage(getDataMapper.getWeaponImage(testData.getWeaponName()));
        calculateResult.setWeaponName(testData.getWeaponName());
        calculateResult.setWeaponRefine(testData.getWeaponRefine());
        calculateResult.setType(testData.getType());
        calculateResult.setGraduationScore(getDataMapper.getWeightList(testData.getRoleName()).getGraduationScore());
        return ResultNormal.success(calculateResult);
    }

    @GetMapping("/getRoleSkill")
    public ResultNormal getRoleSkill(@RequestParam String CharacterName){
        List<RoleSkills> roleSkills = getDataService.getRoleSkill(CharacterName);
        return ResultNormal.success(roleSkills);
    }

    @PostMapping("/getSoundBonesData")
    public ResultNormal getTrainingSoundBonesData(@RequestBody List<SoundBonesData> SoundBones){
        return ResultNormal.success();
    }

    @GetMapping("/test")
    public ResultNormal test(){
        return ResultNormal.success("success");
    }
}
