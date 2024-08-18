package com.example.mingchao_boot.Mapper;

import com.example.mingchao_boot.model.SelectArticleInfo;
import com.example.mingchao_boot.model.SelectAssessInfo;
import com.example.mingchao_boot.model.article.Article;
import com.example.mingchao_boot.model.article.Assess;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface articleMapper {
    Boolean SendArticle(Article article);
    List<Article> getAllArticles(int module);
    Article getStrategy(int arid);
    Boolean SendAssess(Assess assess);
    List<Assess> getAssess(int arid,int deep);
    List<Article> getSearchList(String keyword);
    void setAssessLikeNum(int id, int num);
    void setArticleLikeNum(int id, int num);
    void updateAssessNum(int articleId,int num);
    List<Article> getMyCollection(int userId);
    List<Article> getUserArticle(int userId);
    List<Article> getMyArticle(int userId);
    Integer getCommentNum(int userId);
    Integer getLikeNum(int userId);
    int checkCollect(int userId, int articleId);
    int getCollectNum(int articleId);
    void collection(int articleId, int userId);
    void cancelCollection(int articleId, int userId);
    int checkAttention(int user1, int user2);
    void addAttention(int tUid, int uid);
    void delAttention(int tUid, int uid);
    int findAttention(int tUid, int uid);
    Integer getUserAttention(int userId);
    Integer getUserFans(int userId);
    List<Assess> getMyAssess(int userId);
    List<Article> getArticles(SelectArticleInfo search);
    Integer getArticleNum(SelectArticleInfo search);
    List<Assess> getAssesses(SelectAssessInfo search);
    Integer getAssessNum();
    int deleteAssess(int assessId);
    Assess getAssessById(int assessId);
    Article getArticleById(int articleId);
    int deleteArticle(int articleId);
    void setState(int articleId, int state);
    void setPublicState(int articleId, int state);
    Integer getCollection(int arid, int uid);
}
