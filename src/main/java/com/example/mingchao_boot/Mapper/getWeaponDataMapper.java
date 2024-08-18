package com.example.mingchao_boot.Mapper;

import com.example.mingchao_boot.Pojo.BreakthroughMaterial;
import com.example.mingchao_boot.Pojo.Weapon;
import com.example.mingchao_boot.Pojo.WeaponAttribute;
import com.example.mingchao_boot.Pojo.WeaponInfo;
import com.example.mingchao_boot.model.WeaponIp;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface getWeaponDataMapper{
    List<Weapon> getWeaponList(String type,String mainCt,int star);
    WeaponInfo getWeaponInfo(String weaponName);
    List<WeaponAttribute> getWeaponAttributes(String weaponName);
    List<BreakthroughMaterial> getWeaponBreakthroughMaterials(int id);
    Weapon getWeapon(String weaponName);
    List<WeaponIp> getWeaponIp(String weaponName);
}
