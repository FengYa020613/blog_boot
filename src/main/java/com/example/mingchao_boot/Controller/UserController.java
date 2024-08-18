package com.example.mingchao_boot.Controller;

import com.example.mingchao_boot.ResultModel.ResultNormal;
import com.example.mingchao_boot.Service.UserService;
import com.example.mingchao_boot.model.SelectUserInfo;
import com.example.mingchao_boot.model.UserSetting;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/getSetting")
    public ResultNormal getUserSetting(HttpServletRequest request){
        return userService.getUserSetting(request);
    }
    @PostMapping("/setSetting")
    public ResultNormal setUserSetting(@RequestBody UserSetting setting, HttpServletRequest request){
        return userService.setUserSetting(setting,request);
    }
    @PostMapping("/getUserList")
    public ResultNormal getUserList(@RequestBody SelectUserInfo search){
        return userService.getUserList(search);
    }
    @GetMapping("/delete")
    public ResultNormal deleteUser(@RequestParam int userId){
        return userService.deleteUser(userId);
    }
}
