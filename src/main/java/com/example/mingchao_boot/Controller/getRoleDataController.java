package com.example.mingchao_boot.Controller;

import com.example.mingchao_boot.ResultModel.ResultNormal;
import com.example.mingchao_boot.Service.getRoleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/getRoleData")
public class getRoleDataController {

    @Autowired
    getRoleDataService roleDataService;

    @GetMapping("/getRoleList")
    public ResultNormal getRoleList(@RequestParam String rarity,@RequestParam String type,@RequestParam String weapon){
        System.out.println("success");
        return ResultNormal.success(roleDataService.getRoleList(rarity,type,weapon));
    }
}
