package com.example.mingchao_boot.Service;

import com.example.mingchao_boot.Pojo.TestBasicData;
import com.example.mingchao_boot.Pojo.TestData;
import com.example.mingchao_boot.model.DamageCalculate;
import com.example.mingchao_boot.model.DamageCalculateResult;
import com.example.mingchao_boot.model.SoundBonesCalculate;
import com.example.mingchao_boot.model.SoundBonesData;

import java.util.List;

public interface calculateService {

    DamageCalculateResult CalculateDamage(TestData testData);

    List<SoundBonesCalculate> CalculateSoundBonesRating(List<SoundBonesData> soundBones,String roleName);
    TestBasicData calculateAttribute(TestData testData);
}
