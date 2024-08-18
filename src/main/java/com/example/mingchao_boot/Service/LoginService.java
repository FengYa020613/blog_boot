package com.example.mingchao_boot.Service;

import com.example.mingchao_boot.ResultModel.ResultNormal;
import com.example.mingchao_boot.model.AdminLogin;
import com.example.mingchao_boot.model.User;
import com.example.mingchao_boot.model.UserCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;

public interface LoginService {
    ResultNormal sendCode(String phone,int type);
    ResultNormal userLogin(UserCode userCode);
    ResultNormal getUserInfo(String token);
    ResultNormal changePhone(UserCode userCode, HttpServletRequest request);
    ResultNormal getUser(String token);
    ResultNormal saveUser(User user, HttpServletRequest request);
    ResultNormal getUserFromList(int userId,HttpServletRequest request);
    ResultNormal loginAdmin(AdminLogin admin);
}
