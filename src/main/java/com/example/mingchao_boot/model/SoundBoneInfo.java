package com.example.mingchao_boot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SoundBoneInfo {
    private int userId;
    @JsonProperty(value = "soundBone")
    private SoundBonesData soundBone;

}
