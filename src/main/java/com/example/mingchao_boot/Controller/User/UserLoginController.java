package com.example.mingchao_boot.Controller.User;

import com.example.mingchao_boot.Mapper.userMapper;
import com.example.mingchao_boot.ResultModel.ResultNormal;
import com.example.mingchao_boot.Service.LoginService;
import com.example.mingchao_boot.model.User;
import com.example.mingchao_boot.model.UserCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/userLogin")
public class UserLoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private userMapper userMapper;

    /**
     * 通过测试
     * @param phone
     * @return
     */
    @GetMapping("/sendCode")
    ResultNormal sendLoginCode(@RequestParam String phone){
        //1为登录验证码,2为修改密码验证码
        return loginService.sendCode(phone,1);
    }

    /**
     * 通过测试
     * 登录接口
     * @param userCode
     * @return
     */
    @PostMapping("/login")
    ResultNormal Login(@RequestBody UserCode userCode){
        return loginService.userLogin(userCode);
    }

    @SneakyThrows
    @PostMapping("/changePhone")
    ResultNormal Change(@RequestBody UserCode change,HttpServletRequest request){
        return loginService.changePhone(change,request);
    }

    @GetMapping("/sendChangeCode")
    ResultNormal sendCode(@RequestParam String phone,@RequestParam int userId){
        User user = userMapper.getUser(phone);
        if (user!=null&&user.getUserId()!=userId){
            return ResultNormal.error("该手机号已被注册");
        }
        return loginService.sendCode(phone,2);
    }

    @PostMapping("/getUserInfo")
    ResultNormal getUserInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        return loginService.getUserInfo(token);
    }

    @GetMapping("/getUserInfo/user")
    ResultNormal getUserFromList(@RequestParam int userId,HttpServletRequest request){
        return loginService.getUserFromList(userId,request);
    }


    @PostMapping("/getUser")
    ResultNormal getUser(HttpServletRequest request){
        String token = request.getHeader("token");
        return loginService.getUser(token);
    }

    @PostMapping("/saveUser")
    ResultNormal saveUser(@RequestBody User user,HttpServletRequest request){
        return loginService.saveUser(user,request);
    }
}
