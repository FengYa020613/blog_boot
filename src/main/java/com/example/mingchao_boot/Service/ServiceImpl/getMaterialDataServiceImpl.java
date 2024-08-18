package com.example.mingchao_boot.Service.ServiceImpl;

import com.example.mingchao_boot.Mapper.getMaterialDataMapper;
import com.example.mingchao_boot.Pojo.Material;
import com.example.mingchao_boot.Service.getMaterialDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class getMaterialDataServiceImpl implements getMaterialDataService {

    @Autowired
    getMaterialDataMapper materialDataMapper;

    @Override
    public List<Material> getMaterialList(String type) {
        return materialDataMapper.getMaterialList(type);
    }
}
