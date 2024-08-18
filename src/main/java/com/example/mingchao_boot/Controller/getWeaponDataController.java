package com.example.mingchao_boot.Controller;

import com.example.mingchao_boot.ResultModel.ResultNormal;
import com.example.mingchao_boot.Service.getWeaponDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/getWeaponData")
public class getWeaponDataController {

    @Autowired
    private getWeaponDataService weaponDataService;

    @GetMapping("/getWeaponList")
    public ResultNormal getWeaponList(@RequestParam String type,@RequestParam String mainCt,@RequestParam int star){
        return ResultNormal.success(weaponDataService.getWeaponList(type,mainCt,star));
    }

    @GetMapping("/getWeaponInfo")
    public ResultNormal getWeaponInfo(@RequestParam String weaponName){
        return ResultNormal.success(weaponDataService.getWeaponInfo(weaponName));
    }
}
