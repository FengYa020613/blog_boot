package com.example.mingchao_boot.util;

import com.example.mingchao_boot.Mapper.getWeaponDataMapper;
import com.example.mingchao_boot.model.WeaponIp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WeaponUtil {

    @Autowired
    getWeaponDataMapper weaponDataMapper;
    public List<WeaponIp> getAttribute(String name){
        return weaponDataMapper.getWeaponIp(name);
    }
}
