package com.example.mingchao_boot.Mapper;

import com.example.mingchao_boot.Pojo.Role;
import com.example.mingchao_boot.Pojo.SkillTraining;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface getRoleDataMapper {
    List<Role> getRoleList(int rarity,String type,String weapon);
    List<SkillTraining> getRoleSkills(String characterName);
}
