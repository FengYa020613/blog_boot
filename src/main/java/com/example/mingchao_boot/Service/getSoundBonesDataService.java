package com.example.mingchao_boot.Service;

import com.example.mingchao_boot.Pojo.AcousticSkeleton;
import com.example.mingchao_boot.model.SoundBoneInfo;
import com.example.mingchao_boot.model.SoundBonesData;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface getSoundBonesDataService {
    List<AcousticSkeleton> getSoundBonesList(String level, String suit, int cost);

    void addSoundBone(SoundBoneInfo soundBoneInfo, HttpServletRequest request);
    List<SoundBonesData> getMySoundBones(int userId);
}
