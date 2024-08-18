package com.example.mingchao_boot.Pojo;

import lombok.Data;

import java.util.List;

@Data
public class ResonanceChain {
    private int resonanceChainNumber;
    private String resonanceChainName;
    private String resonanceChainImage;
    private int key;
    private int id;
    private List<List<ResonanceChainDescribe>> chainDescribeList;
}
