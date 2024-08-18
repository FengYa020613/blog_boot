package com.example.mingchao_boot.model;

import lombok.Data;

@Data
public class UserSetting {
    private int userId;
    private int showAttention;
    private int showFans;
    private int showCollection;
}
