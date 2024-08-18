package com.example.mingchao_boot.Service.ServiceImpl;

import com.example.mingchao_boot.Mapper.getSoundBonesDataMapper;
import com.example.mingchao_boot.Pojo.AcousticSkeleton;
import com.example.mingchao_boot.Service.LoginService;
import com.example.mingchao_boot.Service.getSoundBonesDataService;
import com.example.mingchao_boot.model.SoundBoneInfo;
import com.example.mingchao_boot.model.SoundBonesAttribute;
import com.example.mingchao_boot.model.SoundBonesData;
import com.example.mingchao_boot.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class getSoundBonesDataServiceImpl implements getSoundBonesDataService {

    @Autowired
    getSoundBonesDataMapper soundBonesDataMapper;
    @Autowired
    LoginService loginService;

    @Override
    public List<AcousticSkeleton> getSoundBonesList(String level, String suit, int cost) {
        return soundBonesDataMapper.getSoundBonesList(level,suit,cost);
    }

    @Override
    @Transactional
    public void addSoundBone(SoundBoneInfo soundBoneInfo, HttpServletRequest request) {
        SoundBonesData soundBonesData = soundBoneInfo.getSoundBone();
        int userId = soundBoneInfo.getUserId();
        if (userId==0){
            User user = (User) loginService.getUserInfo(request.getHeader("token")).getData();
            if (user==null) return;
            userId = (int) user.getUserId();
        }
        soundBonesData.setUserId(userId);
        soundBonesDataMapper.addSoundBone(soundBonesData);
        int id = soundBoneInfo.getSoundBone().getId();
        for (SoundBonesAttribute attribute:soundBonesData.getAttribute()){
            attribute.setAttributeId(id);
            if(!attribute.getType().equals("")) {
                soundBonesDataMapper.addAttribute(attribute);
            }
        }
    }

    @Override
    public List<SoundBonesData> getMySoundBones(int userId) {
        List<SoundBonesData> bonesDataList = soundBonesDataMapper.getMySoundBones(userId);
        for (SoundBonesData soundBone:bonesDataList) {
            int id = soundBone.getId();
            List<SoundBonesAttribute> attribute = soundBonesDataMapper.getAttributes(id);
            soundBone.setAttribute(attribute);
        }
        return bonesDataList;
    }
}
