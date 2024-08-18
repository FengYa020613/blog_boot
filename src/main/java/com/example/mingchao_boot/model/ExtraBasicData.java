package com.example.mingchao_boot.model;

import com.example.mingchao_boot.Pojo.SkillTraining;
import lombok.Data;

import java.util.List;
@Data
public class ExtraBasicData {
    private int Aggressive;//额外攻击
    private int DefensivePower;//额外防御
    private int HeathValue;//额外生命
    private float AggressivePercent;//攻击百分比
    private float DefensivePowerPercent;//防御百分比
    private float HeathValuePercent;//生命百分比
    private float ResonanceEfficiency;//共鸣效率
    private float CriticalHitRate;//暴击
    private float CritDamage;//暴击伤害
    private float TypeABonus;//普攻加成
    private float TypeBBonus;//重击加成
    private float ESkillBonus;//共鸣技能加成
    private float QSkillBonus;//共鸣解放加成
    private float AttributeBonus;//属性伤害
}
