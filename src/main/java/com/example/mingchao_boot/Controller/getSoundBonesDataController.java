package com.example.mingchao_boot.Controller;

import com.example.mingchao_boot.Redis.RedisUtil;
import com.example.mingchao_boot.ResultModel.ResultNormal;
import com.example.mingchao_boot.Service.LoginService;
import com.example.mingchao_boot.Service.getSoundBonesDataService;
import com.example.mingchao_boot.model.SoundBoneInfo;
import com.example.mingchao_boot.model.SoundBonesData;
import com.example.mingchao_boot.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/getSoundBonesData")
public class getSoundBonesDataController {
    @Autowired
    getSoundBonesDataService soundBonesDataService;
    @Autowired
    LoginService loginService;

    @GetMapping("/getSoundBonesList")
    public ResultNormal getSoundBonesList(@RequestParam String level,@RequestParam String suit,
    @RequestParam int cost){
        System.out.println(level+suit+cost);
        return ResultNormal.success(soundBonesDataService.getSoundBonesList(level,suit,cost));
    }

    @PostMapping("/addSoundBones")
    public ResultNormal addSoundBones(@RequestBody SoundBoneInfo soundBoneInfo,HttpServletRequest request){
        System.out.println(soundBoneInfo);
        soundBonesDataService.addSoundBone(soundBoneInfo,request);
        return ResultNormal.success();
    }

    @GetMapping("/getMySoundBones")
    public ResultNormal getSoundBones(@RequestParam int userId,@RequestParam String suit,
                                      @RequestParam int cost,HttpServletRequest request){
        System.out.println(userId);
        if (userId==0){
            String token = request.getHeader("token");
            User user = (User) loginService.getUserInfo(token).getData();
            if (user!=null){
                userId = (int) user.getUserId();
            }else{
                return ResultNormal.error("未登录");
            }
        }
        List<SoundBonesData> list = soundBonesDataService.getMySoundBones(userId);
        return ResultNormal.success(list);
    }
}

