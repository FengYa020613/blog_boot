package com.example.mingchao_boot.Service.ServiceImpl;

import com.example.mingchao_boot.Mapper.getDataMapper;
import com.example.mingchao_boot.Pojo.*;
import com.example.mingchao_boot.Service.calculateService;
import com.example.mingchao_boot.Service.getDataService;
import com.example.mingchao_boot.Service.getRoleDataService;
import com.example.mingchao_boot.Service.getWeaponDataService;
import com.example.mingchao_boot.model.*;
import com.example.mingchao_boot.util.Calculater;
import com.example.mingchao_boot.util.WeaponUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class calculateServiceImpl implements calculateService {

    @Autowired
    private getDataMapper getDataMapper;
    @Autowired
    private getRoleDataService getRoleDataService;
    @Autowired
    private getDataService getDataService;
    @Autowired
    private getWeaponDataService getWeaponDataService;
    @Autowired
    private WeaponUtil weaponUtil;
    private Calculater calculater = new Calculater();
    private ExtraBasicData extraBasicData = new ExtraBasicData();

    @Override
    public DamageCalculateResult CalculateDamage(TestData testData) {
        DamageCalculateResult damageCalculateResult = new DamageCalculateResult();
        List<DamageCalculate> list = new ArrayList<>();
        List<SkillTestInfo> skillDamageTypeList = getDataMapper.getSkillDamageTypeInfo(testData.getRoleName());
        System.out.println("技能组"+skillDamageTypeList);
        for(SkillTestInfo skillDamageTypeInfo:skillDamageTypeList){
            Float skillBl = getSkillBl(skillDamageTypeInfo,testData,skillDamageTypeInfo.getStart(),skillDamageTypeInfo.getEnd());
            System.out.println("技能倍率"+skillBl);
            DamageCalculate damageCalculate = getDamage(skillDamageTypeInfo.getDamageType(),testData,skillBl);
            damageCalculate.setInfo(skillDamageTypeInfo.getDescribe());
            System.out.println("技能"+damageCalculate.getInfo());
            System.out.println("技能伤害"+damageCalculate.getDamage());
            damageCalculate.setInfo(skillDamageTypeInfo.getDescribe());
            list.add(damageCalculate);
        }
        damageCalculateResult.setDamageCalculateList(list);
        return damageCalculateResult;
    }

    @Override
    public List<SoundBonesCalculate> CalculateSoundBonesRating(List<SoundBonesData> soundBones,String roleName) {
        List<SoundBonesCalculate> bonesCalculateList = new ArrayList<>();
        AttributeWeight weightList = getDataMapper.getWeightList(roleName);
        for (SoundBonesData bone : soundBones) {
            if(!bone.getMainCt().isEmpty()){
                List<SoundBonesAttribute> attributes = bone.getAttribute();
                AcousticSkeleton soundBone = new AcousticSkeleton();
                soundBone.setCost(bone.getCOST());
                soundBone.setAsName(bone.getName());
                soundBone.setPackageName(bone.getPackageType());
                soundBone.setAsImage(bone.getImage());
                soundBone.setSoundBoneLevel(bone.getLevel());
                SoundBonesCalculate soundBonesCalculate = new SoundBonesCalculate();
                List<SoundBonesRating> ratingList = new ArrayList<>();
                double totalScore = 0;
                for (SoundBonesAttribute soundBonesAttribute : attributes) {
                    SoundBonesRating soundBonesRating = new SoundBonesRating();
                    if (!soundBonesAttribute.getType().isEmpty()) {
                        double score = getAttributeScore(soundBonesAttribute.getType(), soundBonesAttribute.getValue(), weightList);
                        totalScore = totalScore + score;
                        soundBonesRating.setIsUseful(score != 0 ? 1 : 0);
                        soundBonesRating.setScore(Float.parseFloat(String.valueOf(totalScore)));
                    }
                    soundBonesRating.setCtType(soundBonesAttribute.getType());
                    soundBonesRating.setCtValue(soundBonesAttribute.getValue());
                    ratingList.add(soundBonesRating);
                }
                soundBonesCalculate.setGraduationLevel((totalScore/weightList.getGraduationScore())*100);
                soundBonesCalculate.setScore((float) totalScore);
                soundBonesCalculate.setSoundBones(soundBone);
                soundBonesCalculate.setRatingList(ratingList);
                soundBonesCalculate.setMainCt(bone.getMainCt());
                soundBonesCalculate.setMainCtValue(bone.getMainCtValue());
                soundBonesCalculate.setGdCtValue(Integer.parseInt(bone.getGdCtValue()));
                bonesCalculateList.add(soundBonesCalculate);
            }
        }
        return bonesCalculateList;
    }

    /**
     * 计算角色面板属性
     * @param testData
     * @return
     */
    @Override
    public TestBasicData calculateAttribute(TestData testData) {
        TestBasicData testBasicData = new TestBasicData();
        List<RoleAttribute> attributeList = getRoleDataService.getRoleAttributes(testData.getRoleName());
        roleAttribute roleAttribute = getRoleAttribute(attributeList,testData.getRoleLevel());
        getExtraBasicData(testData.getSoundBones(),testData.getType());
        List<WeaponAttribute> weaponAttributes = getWeaponDataService.getWeaponAttribute(testData.getWeaponName());
        weaponAttribute weaponAttribute = getWeaponAttribute(weaponAttributes,testData.getWeaponLevel());
        weaponAttribute.setMainCt(testData.getWeaponMainCt());
        List<RoleSkills> roleSkills = getDataService.getRoleSkill(testData.getRoleName());
        for (int i=0;i<testData.getSkills().size();i++) {
            SkillTraining skillTraining = testData.getSkills().get(i);
            for (int j = 0; j < skillTraining.getBranch().size(); j++) {
                if (skillTraining.getBranch().get(j)==1){
                    for (SkillBranch branch: roleSkills.get(i).getBranchList()) {
                        if (branch.getFlag() == Math.abs(j - 1)) {
                            setAttributes(branch.getSkillName(),branch.getValue(),testData.getType());
                        }
                    }
                }
            }
        }
        setHmEffects(testData.getHmEffect(),testData.getType());
        setAttributes(weaponAttribute.getMainCt(),weaponAttribute.getMainCtValue(), testData.getType());
        //设置基础属性
        testBasicData.setAggressivity(roleAttribute.getAggressive()+Integer.parseInt(weaponAttribute.getAggressive()));
        testBasicData.setDefensivePower(roleAttribute.getDefensivePower());
        testBasicData.setHeathValue(roleAttribute.getHeathValue());
        //设置额外基础属性
        testBasicData.setExtraAggressivity(Math.round(testBasicData.getAggressivity()*
                extraBasicData.getAggressivePercent()/100)+extraBasicData.getAggressive());
        testBasicData.setExtraDefensivePower(Math.round(testBasicData.getDefensivePower()*
                extraBasicData.getDefensivePowerPercent()/100)+extraBasicData.getDefensivePower());
        testBasicData.setExtraHeathValue(Math.round(testBasicData.getHeathValue()*
                extraBasicData.getHeathValuePercent()/100)+extraBasicData.getHeathValue());
        //设置特殊属性
        testBasicData.setCriticalHitRate(extraBasicData.getCriticalHitRate()+5);
        testBasicData.setCritDamage(extraBasicData.getCritDamage()+150);
        testBasicData.setESkillBonus(extraBasicData.getESkillBonus());
        testBasicData.setQSkillBonus(extraBasicData.getQSkillBonus());
        testBasicData.setTypeABonus(extraBasicData.getTypeABonus());
        testBasicData.setTypeBBonus(extraBasicData.getTypeBBonus());
        testBasicData.setResonanceEfficiency(extraBasicData.getResonanceEfficiency());
        testBasicData.setAttributeBonus(extraBasicData.getAttributeBonus());
        extraBasicData = new ExtraBasicData();
        return testBasicData;
    }


    private void setHmEffects(List<HmEffect> hmEffect,String type) {
        for (HmEffect effect : hmEffect) {
            switch (effect.getType()){
                case "啸谷长风":
                    if(effect.getNum()>=2){
                        setAttributes("气动伤害加成","10%",type);
                        if (effect.getNum()==5){
                            setAttributes("气动伤害加成","30%",type);
                        }
                    }
                    break;
                case "浮星祛暗":
                    if(effect.getNum()>=2){
                        setAttributes("衍射伤害加成","10%",type);
                        if (effect.getNum()==5){
                            setAttributes("衍射伤害加成","30%",type);
                        }
                    }
                    break;
                case "凝夜白霜":
                    if(effect.getNum()>=2){
                        setAttributes("冷凝伤害加成","10%",type);
                        if (effect.getNum()==5){
                            setAttributes("冷凝伤害加成","30%",type);
                        }
                    }
                    break;
                case "隐世回光":
                    if(effect.getNum()>=2){
                        setAttributes("治疗效果加成","10%",type);
                        if (effect.getNum()==5){
                            setAttributes("攻击","15%",type);
                        }
                    }
                    break;
                case "轻云出月":
                    if(effect.getNum()>=2){
                        setAttributes("共鸣效率","10%",type);
                    }
                    break;
                case "沉日劫明":
                    if(effect.getNum()>=2){
                        setAttributes("湮灭伤害加成","10%",type);
                        if (effect.getNum()==5){
                            setAttributes("湮灭伤害加成","30%",type);
                        }
                    }
                    break;
                case "彻空冥雷":
                    if(effect.getNum()>=2){
                        setAttributes("气动伤害加成","10%",type);
                        if (effect.getNum()==5){
                            setAttributes("气动伤害加成","15%",type);
                        }
                    }
                    break;
                case "不绝余音":
                    if(effect.getNum()>=2){
                        setAttributes("导电伤害加成","10%",type);
                        if (effect.getNum()==5){
                            setAttributes("导电伤害加成","15%",type);
                        }
                    }
                    break;
                case "熔山裂谷":
                    if(effect.getNum()>=2){
                        setAttributes("热熔伤害加成","10%",type);
                        if (effect.getNum()==5){
                            setAttributes("热熔伤害加成","30%",type);
                        }
                    }
                    break;
            }
        }
    }

    private weaponAttribute getWeaponAttribute(List<WeaponAttribute> weaponAttributes, int weaponLevel) {
        weaponAttribute weaponAttribute = new weaponAttribute();
        weaponAttribute.setMainCtValue(weaponAttributes.get(weaponLevel-1).getMainCt());
        switch (weaponLevel) {
            case 1 -> {
                weaponAttribute.setAggressive(weaponAttributes.get(0).getAggressivity());
            }
            case 8 -> {
                weaponAttribute.setAggressive(weaponAttributes.get(7).getAggressivity());
            }
            default -> {
                weaponAttribute.setAggressive(weaponAttributes.get(weaponLevel-1).getAggressivityBefore());
            }
        }
        return weaponAttribute;
    }

    /**
     * 获取声骸的基础数据
     * @param soundBones
     * @return
     */
    private void getExtraBasicData(List<SoundBonesData> soundBones,String type) {
        for (SoundBonesData bonesData : soundBones){
            if(!bonesData.getMainCt().isEmpty()){
                setAttributes(bonesData.getMainCt(),bonesData.getMainCtValue(),type);
                setAttributes(bonesData.getGdCt(),bonesData.getGdCtValue(),type);
                for(SoundBonesAttribute bonesAttribute : bonesData.getAttribute()){
                    if(!bonesAttribute.getType().isEmpty()&&!bonesAttribute.getValue().isEmpty()){
                        setAttributes(bonesAttribute.getType(),bonesAttribute.getValue(),type);
                    }
                }
            }
        }
    }

    /**
     * 设置词条数组
     * @param mainCt
     * @param mainCtValue
     * @return
     */
    private void setAttributes(String mainCt, String mainCtValue,String type) {
        String value = mainCtValue.replace("%","");
        if(mainCt.contains(type)){
            extraBasicData.setAttributeBonus(extraBasicData.getAttributeBonus()+Float.parseFloat(value));
        }else{
            switch (mainCt){
                case "攻击":
                    if (mainCtValue.contains("%")){
                        extraBasicData.setAggressivePercent(extraBasicData.getAggressivePercent()+Float.parseFloat(value));
                    }else{
                        extraBasicData.setAggressive(extraBasicData.getAggressive()+Integer.parseInt(value));
                    }
                    break;
                case "防御":
                    if (mainCtValue.contains("%")){
                        extraBasicData.setDefensivePowerPercent(extraBasicData.getDefensivePowerPercent()+Float.parseFloat(value));
                    }else{
                        extraBasicData.setDefensivePower(extraBasicData.getDefensivePower()+Integer.parseInt(value));
                    }
                    break;
                case "暴击":
                    extraBasicData.setCriticalHitRate(extraBasicData.getCriticalHitRate()+Float.parseFloat(value));
                    break;
                case "暴击伤害":
                    extraBasicData.setCritDamage(extraBasicData.getCritDamage()+Float.parseFloat(value));
                    break;
                case "共鸣效率":
                    extraBasicData.setResonanceEfficiency(extraBasicData.getResonanceEfficiency()+Float.parseFloat(value));
                    break;
                case "共鸣技能伤害加成":
                    extraBasicData.setESkillBonus(extraBasicData.getESkillBonus()+Float.parseFloat(value));
                    break;
                case "共鸣解放伤害加成":
                    extraBasicData.setQSkillBonus(extraBasicData.getQSkillBonus()+Float.parseFloat(value));
                    break;
                case "普攻伤害加成":
                    extraBasicData.setTypeABonus(extraBasicData.getTypeABonus()+Float.parseFloat(value));
                    break;
                case "重击伤害加成":
                    extraBasicData.setTypeBBonus(extraBasicData.getTypeBBonus()+Float.parseFloat(value));
                    break;
            }
        }
    }

    /**
     * 获取角色基础数据
     * @param attributeList
     * @param roleLevel
     * @return
     */
    private roleAttribute getRoleAttribute(List<RoleAttribute> attributeList, int roleLevel) {
        roleAttribute roleAttribute = new roleAttribute();
        switch (roleLevel) {
            case 1 -> {
                roleAttribute.setAggressive(attributeList.get(0).getAggressivity());
                roleAttribute.setDefensivePower(attributeList.get(0).getDefensePower());
                roleAttribute.setHeathValue(attributeList.get(0).getHeathValue());
            }
            case 8 -> {
                roleAttribute.setAggressive(attributeList.get(7).getAggressivity());
                roleAttribute.setDefensivePower(attributeList.get(7).getDefensePower());
                roleAttribute.setHeathValue(attributeList.get(7).getHeathValue());
            }
            default -> {
                roleAttribute.setAggressive(attributeList.get(roleLevel - 1).getAggressivityBefore());
                roleAttribute.setDefensivePower(attributeList.get(roleLevel - 1).getDefensePowerBefore());
                roleAttribute.setHeathValue(attributeList.get(roleLevel - 1).getHeathValueBefore());
            }
        }
        return roleAttribute;
    }

    /**
     * 计算词条得分
     * @param type
     * @param value
     * @return
     */
    private Double getAttributeScore(String type, String value,AttributeWeight weightList) {
        double attribute = Double.parseDouble(value.replace("%", ""));
        switch (type){
            case "暴击":
                return attribute * 2 * weightList.getCriticalHitRate();
            case "暴击伤害":
                return attribute*1*weightList.getCritDamage();
            case "攻击":
                if(attribute>20){
                    return attribute*0.167*0.58*1.33*weightList.getAggressive();
                }else{
                    return attribute*1.33*weightList.getAggressive();
                }
            case "生命":
                if(attribute>20){
                    return attribute*0.017*0.552*1.33*weightList.getHeathValue();
                }else{
                    return attribute*1.33*weightList.getHeathValue();
                }
            case "防御":
                if(attribute>20){
                    return attribute*0.167*0.52*1.33*weightList.getDefensePower();
                }else{
                    return attribute*1.33*weightList.getDefensePower();
                }
            case "共鸣技能伤害加成":
                return attribute*1.33*weightList.getResonanceSkill();
            case "共鸣解放伤害加成":
                return attribute*1.33*weightList.getResonanceLiberation();
            case "普攻伤害加成":
                return attribute*1.33*weightList.getNormalAttack();
            case "重击伤害加成":
                return attribute*1.33*weightList.getHeavyAttack();
            default:
                return 0.0;
        }
    }

    /**
     * 获取伤害
     * @param damageType
     * @return
     */
    private DamageCalculate getDamage(String damageType,TestData testData,Float skillBl) {
        TestBasicData base = testData.getBasicData();
        DamageCalculate damageCalculate = new DamageCalculate();
        int lv = testData.getRoleLevel()==1?1:
                    testData.getRoleLevel()==2?20:
                        testData.getRoleLevel()==3?40:
                            (testData.getRoleLevel()+1)*10;
        System.out.println("角色等级:"+lv);
        Float tsZs = 0F;
        switch (damageType){
            case "重击":
                tsZs = Float.valueOf(base.getTypeBBonus());
                break;
            case "普攻":
                tsZs = Float.valueOf(base.getTypeABonus());
                break;
            case "共鸣技能":
                tsZs = Float.valueOf(base.getESkillBonus());
                break;
            case "共鸣解放":
                tsZs = Float.valueOf(base.getQSkillBonus());
                break;
            default:
                tsZs = Float.valueOf(base.getAttributeBonus());
        }
        Float zs = 1F + testData.getBasicData().getAttributeBonus()/100 + tsZs/100;
        int aggressive = testData.getBasicData().getAggressivity() + testData.getBasicData().getExtraAggressivity();
        List<WeaponIp> effect = weaponUtil.getAttribute(testData.getWeaponName());
        for(WeaponIp weaponIp:effect){
            System.out.println("武器加成:"+weaponIp.getType());
            System.out.println("加成伤害:"+weaponIp.getNum());
            if(weaponIp.getType().equals("全属性伤害加成")||weaponIp.getType().contains(damageType)){
                zs+=Float.parseFloat(weaponIp.getNum())*testData.getWeaponRefine()/100;
            }else if(weaponIp.getType().equals("攻击提升")){
                aggressive+=testData.getBasicData().getAggressivity()*
                        Float.parseFloat(weaponIp.getNum())*testData.getWeaponRefine()/100;
            }
        }
        System.out.println("攻击力:"+aggressive);
        float criticalRate = base.getCriticalHitRate();
        int damage = (int) Math.round(aggressive * skillBl * zs * (base.getCritDamage()-100) / 100 * (100 + lv) / (299 + lv) * 0.009);
        int criticalDamage = (int) Math.round(aggressive * skillBl * zs * base.getCritDamage() / 100 * (100 + lv) / (299 + lv) * 0.009);
        int expect = (int) (criticalDamage*criticalRate/100 + (1-criticalRate/100)*damage);
        damageCalculate.setDamage(criticalDamage);
        damageCalculate.setExpect(expect);
        return damageCalculate;
    }

    /**
     * 百分数转小数
     * @param value
     * @return
     */
    private Float getFloat(String value){
        return Float.valueOf(value.replace("%",""));
    }

    /**
     * 获取技能倍率
     * @param skillDamage
     * @param testData
     * @return
     */
    private Float getSkillBl(SkillTestInfo skillDamage, TestData testData,int start,int end) {
        String skill = skillDamage.getRoleName() + skillDamage.getSkillType();
        Float skillBl = Float.valueOf(0);
        //倍率计算
        List<Skill> skillTable= getDataMapper.getSkillTableData(testData.getRoleName(), skill);
        int index = 0;
        switch (skillDamage.getSkillType()){
            case "常态攻击":
                index = 0;
                break;
            case "共鸣技能":
                index = 1;
                break;
            case "共鸣回路":
                index = 2;
                break;
            case "共鸣解放":
                index = 3;
                break;
            case "变奏技能":
                index = 4;
                break;
            case "延奏技能":
                index = 5;
                break;
        }
        int skillLevel = 1;
        if(index<4){
            skillLevel = testData.getSkills().get(index).getLevel();
        }
        for(int i=start;i<end;i++){
            skillBl += getSkillBl(skillTable.get(i),skillLevel);
        }
        if(skillDamage.getDescribe().equals("共鸣技能·惊龙破空")){
            skillBl += getSkillBl(skillTable.get(9),skillLevel)*50;
        }
        return skillBl;
    }

    /**
     * 技能相应等级的倍率
     * @param skill
     * @param skillLevel
     * @return
     */
    private Float getSkillBl(Skill skill, int skillLevel) {
        Float sum = (float) 0;
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
}
