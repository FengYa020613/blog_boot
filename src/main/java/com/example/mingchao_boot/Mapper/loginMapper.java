package com.example.mingchao_boot.Mapper;

import com.example.mingchao_boot.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface loginMapper {

    User checkPhone(String phone);
    boolean addUser(String phone);
}
