package com.example.mingchao_boot.model.article;

import lombok.Data;

import java.util.List;

@Data
public class Article {
    private int id;
    private int authorId;
    private String mainTitle;
    private String title;
    private String content;
    private String time;
    private String tag;
    private List<String> tagList;
    private int module;
    private String describe;
    private String avatar;
    private String nickName;
    private String images;
    private int like;
    private int collect;
    private int attention;
    private int lnum;
    private int cnum;
    private int snum;
    private int state = 0;
    private int publicState;
    private List<String> imageList;
}
