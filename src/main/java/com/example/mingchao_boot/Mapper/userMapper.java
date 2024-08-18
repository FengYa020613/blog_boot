package com.example.mingchao_boot.Mapper;

import com.example.mingchao_boot.ResultModel.ResultNormal;
import com.example.mingchao_boot.model.Admin;
import com.example.mingchao_boot.model.SelectUserInfo;
import com.example.mingchao_boot.model.User;
import com.example.mingchao_boot.model.UserSetting;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface userMapper {
    User getUser(String phone);
    boolean addUser(String phone,String nickName);
    boolean changePhone(String phone,long userId);
    User getUserById(String userId);
    void incrArticleNum(int authorId);
    List<User> getAttention(int uid);
    List<User> getFans(int uid);
    void saveUser(User user);
    UserSetting getUserSetting(int userId);
    void updateUserSetting(UserSetting setting);
    List<User> getUserList(SelectUserInfo search);
    Integer getUserNum(SelectUserInfo search);
    int deleteUser(int userId);
    void decrArticleNum(int userId);
    Admin getAdmin(String username);
    int findAttention(int userId, int uid);
}
