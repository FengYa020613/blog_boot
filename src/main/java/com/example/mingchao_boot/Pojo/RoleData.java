package com.example.mingchao_boot.Pojo;

import lombok.Data;

import java.util.List;

@Data
public class RoleData {
    private BasicData basicData;
    private List<RoleAttribute> roleAttributes;
    private List<RoleSkills> roleSkills;
}
