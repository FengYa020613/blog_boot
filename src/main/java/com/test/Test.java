package com.test;

import com.example.mingchao_boot.Pojo.Skill;
import com.example.mingchao_boot.Pojo.TestBasicData;
import com.example.mingchao_boot.Pojo.TestData;
import com.example.mingchao_boot.model.WeaponIp;
import org.jetbrains.annotations.TestOnly;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        /*String value = "5*6+2*8";
        List<String> operate = new ArrayList<>();
        String regex = "[+*]";
        List<String> list = List.of(value.split(regex));
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        while(matcher.find()){
            operate.add(matcher.group());
        }
        System.out.println(list);
        System.out.println(operate);
        int num = 0;
        for (int i=0;i<operate.size();i++){
            String s = operate.get(i);
            switch (s){
                case "+":
                    num = Integer.parseInt(list.get(i))
            }
        }*/
        String expression = "11.87%*7+77.18%*2".replace("%",""); // 这里可以是任意合法的JavaScript表达式
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        try {
            Object result = engine.eval(expression);
            Double e = Double.valueOf(result.toString())/100;
            System.out.println("结果: " + e);
        } catch (ScriptException e) {
            System.err.println("表达式计算错误: " + e.getMessage());
        }
    }
    /**
     * 共鸣技能伤害计算
     * @param testData
     * @return
     */
    /*private int getResonanceSkillDamage(TestData testData) {
        int damage = 0;
        String skill = testData.getRoleName() + "共鸣技能";
        switch (testData.getRoleName()){
            case "忌炎":
                damage=getResonanceSkillCalculate(testData,0,1,skill);
        }
        return damage;
    }*/

    /**
     * 共鸣技能计算公式
     * @return
     */
   /* private int getResonanceSkillCalculate(TestData testData,int start,int end,String skill,String IdType) {
        String roleName = testData.getRoleName();
        Float skillBl = Float.valueOf(0);
        int damage = 0;
        TestBasicData base = testData.getBasicData();
        int lv = testData.getRoleLevel();
        List<Skill> skillTable= getDataMapper.getSkillTableData(roleName,skill);
        int skillLevel = testData.getBasicData().getSkills().get(1).getLevel();
        for(int i=start;i<end;i++){
            skillBl += getSkillBl(skillTable.get(i),skillLevel);
        }
        //增伤计算
        Float zs = 1+getFloat(base.getAttributeBonus())+ getFloat(base.getESkillBonus());
        int aggressive = testData.getBasicData().getAggressivity() + testData.getBasicData().getExtraAggressivity();
        List<WeaponIp> effect = weaponUtil.getAttribute(testData.getWeaponName());
        for(WeaponIp weaponIp:effect){
            if(weaponIp.getType().equals("全属性伤害加成")||weaponIp.getType().equals("共鸣技能伤害加成")){
                zs+=Float.parseFloat(weaponIp.getNum())*testData.getWeaponRefine()/100;
            }else if(weaponIp.getType().equals("攻击提升")){
                aggressive+=testData.getBasicData().getAggressivity()*
                        Float.parseFloat(weaponIp.getNum())*testData.getWeaponRefine()/100;
            }
        }
        damage = (int) Math.round(aggressive*skillBl*zs*getFloat(base.getCritDamage())/100*(100 + lv)/(299 + lv)*0.009);
        return damage;
    }*/


    /**
     * 共鸣解放伤害计算
     * @param testData
     * @return
     */
    /*private int getResonanceLiberationDamage(TestData testData){
        int damage = 0;
        String skill = testData.getRoleName() + "共鸣解放";
        switch (testData.getRoleName()){
            case "忌炎":
                damage=getResonanceLiberationCalculate(testData,0,1,skill);
        }
        return damage;
     *//*   String roleName = testData.getRoleName();
        Float skillBl = Float.valueOf(0);
        int damage = 0;
        String skill = roleName + "共鸣解放";
        TestBasicData base = testData.getBasicData();
        int lv = testData.getRoleLevel();
        List<Skill> skillTable= getDataMapper.getSkillTableData(roleName,skill);
        int skillLevel = testData.getBasicData().getSkills().get(3).getLevel();
        switch (roleName){
            case "忌炎":
                for(int i=0;i<3;i++){
                    skillBl+=getSkillBl(skillTable.get(i),skillLevel);
                }
                int aggressive = testData.getBasicData().getAggressivity() + testData.getBasicData().getExtraAggressivity();
                damage = (int) Math.round(aggressive*skillBl*(1+getFloat(base.getAttributeBonus())+
                        getFloat(base.getTypeBBonus()))*getFloat(base.getCritDamage())/100*(100 + lv)/(299 + lv)*0.009);
        }
        return damage;*//*
    }*/

    /**
     * 共鸣解放公式计算
     * @param testData
     * @param start
     * @param end
     * @param skill
     * @return
     */
  /*  private int getResonanceLiberationCalculate(TestData testData, int start, int end, String skill) {
        String roleName = testData.getRoleName();
        Float skillBl = Float.valueOf(0);
        int damage = 0;
        TestBasicData base = testData.getBasicData();
        int lv = testData.getRoleLevel();
        List<Skill> skillTable= getDataMapper.getSkillTableData(roleName,skill);
        //倍率计算
        int skillLevel = testData.getBasicData().getSkills().get(3).getLevel();
        for(int i=start;i<end;i++){
            skillBl += getSkillBl(skillTable.get(i),skillLevel);
        }
        return 0;
    }

    private Float getSkillBl(Skill skill, int skillLevel) {
        Float sum = Float.valueOf(0);
        switch (skillLevel){
            case 1:
                sum =  calculater.getCalculate(skill.getLevel1());
                break;
            case 2:
                sum =  calculater.getCalculate(skill.getLevel2());
                break;
            case 3:
                sum =  calculater.getCalculate(skill.getLevel3());
                break;
            case 4:
                sum =  calculater.getCalculate(skill.getLevel4());
                break;
            case 5:
                sum =  calculater.getCalculate(skill.getLevel5());
                break;
            case 6:
                sum =  calculater.getCalculate(skill.getLevel6());
                break;
            case 7:
                sum =  calculater.getCalculate(skill.getLevel7());
                break;
            case 8:
                sum =  calculater.getCalculate(skill.getLevel8());
                break;
            case 9:
                sum =  calculater.getCalculate(skill.getLevel9());
                break;
            case 10:
                sum = calculater.getCalculate(skill.getLevel10());
        }
        return sum;
    }

    private Float getFloat(String value){
        return Float.valueOf(value.replace("%",""));
    }

    private List<String> getSkillDamageDescribe(String roleName) {
        List<String> list = new ArrayList<>();
        switch (roleName){
            case "忌炎":
                list.add("共鸣技能伤害");
                list.add("共鸣解放一轮伤害");
                list.add("苍躣八荒·后动伤害");
                break;
        }
        return list;
    }*/
}
