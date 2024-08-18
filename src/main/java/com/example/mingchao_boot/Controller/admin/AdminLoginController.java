package com.example.mingchao_boot.Controller.admin;

import com.example.mingchao_boot.ResultModel.ResultNormal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/adminLogin")
public class AdminLoginController {

    @RequestMapping("/login")
    public ResultNormal Login(){
        return ResultNormal.success();
    }

}
