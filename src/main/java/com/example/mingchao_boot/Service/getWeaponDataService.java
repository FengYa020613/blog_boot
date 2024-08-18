package com.example.mingchao_boot.Service;

import com.example.mingchao_boot.Pojo.Weapon;
import com.example.mingchao_boot.Pojo.WeaponAttribute;
import com.example.mingchao_boot.Pojo.WeaponInfo;

import java.util.List;

public interface getWeaponDataService {
    List<Weapon> getWeaponList(String type, String mainCt, int star);
    WeaponInfo getWeaponInfo(String weaponName);
    List<WeaponAttribute> getWeaponAttribute(String weaponName);
    Weapon getWeapon(String weaponName);
}
