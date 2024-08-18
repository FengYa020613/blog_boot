package com.example.mingchao_boot.Controller;

import com.example.mingchao_boot.Pojo.Material;
import com.example.mingchao_boot.ResultModel.ResultNormal;
import com.example.mingchao_boot.Service.getMaterialDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/getMaterialData")
public class getMaterialDataController {
    @Autowired
    private getMaterialDataService materialDataService;

    @GetMapping("/getMaterialList")
    public ResultNormal getMaterialList(@RequestParam String type){
        List<Material> materialList = materialDataService.getMaterialList(type);
        return ResultNormal.success(materialList);
    }
}
