package com.example.mingchao_boot.Service;

import com.example.mingchao_boot.Pojo.Material;

import java.util.List;

public interface getMaterialDataService {
    List<Material> getMaterialList(String type);
}
