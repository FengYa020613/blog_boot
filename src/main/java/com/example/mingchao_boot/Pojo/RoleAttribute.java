package com.example.mingchao_boot.Pojo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class  RoleAttribute {
    private int breakthroughMaterialId;//突破材料清单Id
    private ArrayList<BreakthroughMaterial> BreakthroughMaterials;//突破材料
    private String rolePostion;//角色定位
    private int HeathValue;//基础生命
    private int Aggressivity;//基础攻击力
    private int DefensePower;//基础防御力
    private int HeathValueBefore;//突破前生命值
    private int HeathValueAfter;//突破后生命值
    private int AggressivityBefore;//突破前攻击力
    private int AggressivityAfter;//突破后攻击力
    private int DefensePowerBefore;//突破前防御力
    private int DefensePowerAfter;//突破后防御力
    private String critRate;//暴击率
    private String attackEnhancement;//攻击提升
    private String critDamage;//暴击伤害
    private String treatmentEffect;//治疗效果加成
    private int maxre;//最大共鸣能量
    private String damageBonus;//伤害加成
}
