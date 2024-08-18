package com.example.mingchao_boot.Service.ServiceImpl;

import com.example.mingchao_boot.Mapper.getDataMapper;
import com.example.mingchao_boot.Mapper.getRoleDataMapper;
import com.example.mingchao_boot.Pojo.Role;
import com.example.mingchao_boot.Pojo.RoleAttribute;
import com.example.mingchao_boot.Pojo.SkillTraining;
import com.example.mingchao_boot.Service.getRoleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class getRoleDataServiceImpl implements getRoleDataService {

    @Autowired
    getRoleDataMapper getRoleDataMapper;
    @Autowired
    getDataMapper getDataMapper;

    @Override
    public List<Role> getRoleList(String rarity,String type,String weapon) {
        int star = 0;
        if(rarity.equals("五星")){
            star = 5;
        }else if(rarity.equals("四星")){
            star = 4;
        }
        return getRoleDataMapper.getRoleList(star,type,weapon);
    }

    @Override
    public List<RoleAttribute> getRoleAttributes(String characterName) {
        List<RoleAttribute> roleAttributes = new ArrayList<>();
        if (characterName!=null){
            roleAttributes = getDataMapper.getRoleAttribute(characterName);
        }
        return roleAttributes;
    }

    @Override
    public List<SkillTraining> getRoleSkills(String characterName) {
        return getRoleDataMapper.getRoleSkills(characterName);
    }
}
