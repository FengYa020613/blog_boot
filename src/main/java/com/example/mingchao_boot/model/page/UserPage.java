package com.example.mingchao_boot.model.page;

import com.example.mingchao_boot.model.User;
import lombok.Data;

import java.util.List;

@Data
public class UserPage {
    private Integer total;
    private List<User> userList;
}
