package com.example.mingchao_boot.Service;

import com.example.mingchao_boot.ResultModel.ResultNormal;
import com.example.mingchao_boot.model.SelectUserInfo;
import com.example.mingchao_boot.model.UserSetting;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    ResultNormal getUserSetting(HttpServletRequest request);
    ResultNormal setUserSetting(UserSetting setting, HttpServletRequest request);
    ResultNormal getUserList(SelectUserInfo search);
    ResultNormal deleteUser(int userId);
}
