package com.example.mingchao_boot.Pojo;

import lombok.Data;

@Data
public class TestTable {
    private int Aggressivity;
    private int DefensivePower;
    private int HeathValue;
    private int ExtraAggressivity;
    private int ExtraDefensivePower;
    private int ExtraHeathValue;
    private Float ResonanceEfficiency;
    private Float CriticalHitRate;
    private Float CritDamage;
    private Float TypeABonus;
    private Float TypeBBonus;
    private Float ESkillBonus;
    private Float QSkillBonus;
    private Float AttributeBonus;
    private SkillLevel skillLevel;
}
