package com.example.mingchao_boot.Service.ServiceImpl;

import com.example.mingchao_boot.Mapper.getWeaponDataMapper;
import com.example.mingchao_boot.Pojo.BreakthroughMaterial;
import com.example.mingchao_boot.Pojo.Weapon;
import com.example.mingchao_boot.Pojo.WeaponAttribute;
import com.example.mingchao_boot.Pojo.WeaponInfo;
import com.example.mingchao_boot.Service.getWeaponDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class getWeaponDataServiceImpl implements getWeaponDataService {

    @Autowired
    getWeaponDataMapper weaponMapper;

    @Override
    public List<Weapon> getWeaponList(String type, String mainCt, int star) {
        return weaponMapper.getWeaponList(type,mainCt,star);
    }

    @Override
    public WeaponInfo getWeaponInfo(String weaponName) {
        WeaponInfo weaponInfo = weaponMapper.getWeaponInfo(weaponName);
        if(weaponInfo!=null){
            List<WeaponAttribute> weaponAttributes = weaponMapper.getWeaponAttributes(weaponInfo.getWeaponName());
            for (WeaponAttribute weaponAttribute:weaponAttributes) {
                List<BreakthroughMaterial> weaponBreakthroughMaterials
                        = weaponMapper.getWeaponBreakthroughMaterials(weaponAttribute.getBreakthroughId());
                weaponAttribute.setBreakthroughMaterialList(weaponBreakthroughMaterials);
            }
            weaponInfo.setWeaponAttributes(weaponAttributes);
        }
        return weaponInfo;
    }

    @Override
    public List<WeaponAttribute> getWeaponAttribute(String weaponName) {
        List<WeaponAttribute> weaponAttributes = new ArrayList<>();
        if (weaponName!=null){
            weaponAttributes = weaponMapper.getWeaponAttributes(weaponName);
        }
        return weaponAttributes;
    }

    @Override
    public Weapon getWeapon(String weaponName) {
        return weaponMapper.getWeapon(weaponName);
    }
}
