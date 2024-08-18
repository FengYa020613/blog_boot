package com.example.mingchao_boot.model.article;

import lombok.Data;

import java.util.List;

@Data
public class Assess {
    private int id;
    private int userId;
    private int articleId;
    private String content;
    private String time;
    private int deep;
    private int assessId;
    private int answerUserId;
    private String avatar;
    private String answerAvatar;
    private String nickname;
    private String answerNickname;
    private int cnum;
    private int lnum;
    private int like;
    private List<Assess> childrenAssessList;
}
