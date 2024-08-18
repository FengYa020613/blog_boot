package com.example.mingchao_boot.Pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class
TestBasicData {
    private int Aggressivity;//基础攻击
    private int DefensivePower;//防御
    private int HeathValue;//生命
    private int ExtraAggressivity;//额外攻击
    private int ExtraDefensivePower;//额外防御
    private int ExtraHeathValue;//额外生命
    private float ResonanceEfficiency;//共鸣效率
    private float CriticalHitRate;//暴击
    private float CritDamage;//暴击伤害
    private float TypeABonus;//普攻加成
    private float TypeBBonus;//重击加成
    private float ESkillBonus;//共鸣技能加成
    private float QSkillBonus;//共鸣解放加成
    private float AttributeBonus;//属性伤害
}
