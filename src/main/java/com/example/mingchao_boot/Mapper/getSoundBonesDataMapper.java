package com.example.mingchao_boot.Mapper;

import com.example.mingchao_boot.Pojo.AcousticSkeleton;
import com.example.mingchao_boot.model.SoundBonesAttribute;
import com.example.mingchao_boot.model.SoundBonesData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface getSoundBonesDataMapper {
    List<AcousticSkeleton> getSoundBonesList(String level, String suit, int cost);
    int addSoundBone(SoundBonesData soundBonesData);
    void addAttribute(SoundBonesAttribute attribute);
    List<SoundBonesData> getMySoundBones(int userId);
    List<SoundBonesAttribute> getAttributes(int id);
}
