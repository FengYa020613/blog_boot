package com.example.mingchao_boot.Mapper;

import com.example.mingchao_boot.Pojo.Material;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface getMaterialDataMapper {
    List<Material> getMaterialList(String type);
}
