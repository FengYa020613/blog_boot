package com.example.mingchao_boot.Pojo;

import lombok.Data;

import java.util.List;

@Data
public class AcousticSkeleton {
    private String packageName;
    private String asName;
    private String level;
    private int SoundBoneLevel;
    private String asImage;
    private int cost;
    private List<Info> asEffect;
}
