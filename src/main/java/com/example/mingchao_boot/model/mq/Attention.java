package com.example.mingchao_boot.model.mq;

import lombok.Data;

@Data
public class Attention {
    private int userId;//谁关注的
    private int targetId;//关注的谁
    private int num;//1就是关注 -1就是取消关注
}
