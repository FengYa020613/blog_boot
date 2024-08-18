package com.example.mingchao_boot.util;

import com.example.mingchao_boot.model.UserCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {
    //保存登录信息到Session
    public static void setSession(HttpServletRequest request, UserCode userCode){
        HttpSession session = request.getSession();
        session.setAttribute(userCode.getPhone(), "8888");
    }
    //获取登录信息
    public static String getSession(HttpServletRequest request,String phone){
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute(phone);
        return code;
    }
    //删除信息
    public static void removeSession(HttpServletRequest request,String phone){
        HttpSession session = request.getSession();
        session.removeAttribute(phone);
    }
}
