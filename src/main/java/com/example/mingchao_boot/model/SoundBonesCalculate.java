package com.example.mingchao_boot.model;

import com.example.mingchao_boot.Pojo.AcousticSkeleton;
import lombok.Data;

import java.util.List;

@Data
public class SoundBonesCalculate {
    private Float score;//总评分
    private int num;//词条数
    private Double GraduationLevel;//毕业进度
    private AcousticSkeleton soundBones;//声骸
    private String mainCt;//主词条
    private String mainCtValue;//主词条数值
    private int GdCtValue;//固定攻击数值
    private List<SoundBonesRating> ratingList;//副词条列表
}
