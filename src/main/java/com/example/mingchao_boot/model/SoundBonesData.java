package com.example.mingchao_boot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SoundBonesData {
    private int id;
    private int userId;
    private int Index;
    private String PackageType;
    private String Image;
    private String Name;
    @JsonProperty(value = "COST")
    private int COST;
    @JsonProperty(value = "level")
    private int Level;
    @JsonProperty(value = "MainCt")
    private String MainCt;
    @JsonProperty(value = "MainCtValue")
    private String MainCtValue;
    @JsonProperty(value = "GdCt")
    private String GdCt;
    @JsonProperty(value = "GdCtValue")
    private String GdCtValue;
    @JsonProperty(value = "Attribute")
    private List<SoundBonesAttribute> Attribute;
}
