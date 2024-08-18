package com.example.mingchao_boot.Service;

import com.example.mingchao_boot.ResultModel.ResultNormal;
import com.example.mingchao_boot.model.SelectArticleInfo;
import com.example.mingchao_boot.model.SelectAssessInfo;
import com.example.mingchao_boot.model.article.Article;
import com.example.mingchao_boot.model.article.Assess;
import com.example.mingchao_boot.model.article.Like;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ArticleService {
    //发送文章
    ResultNormal SendArticle(Article article);
    //页面展示
    ResultNormal getShowStrategy(HttpServletRequest request);
    ResultNormal getShowPicture(HttpServletRequest request);
    ResultNormal getShowArticle(HttpServletRequest request);
    //通过文章ID查询文章
    ResultNormal getArticleByArticleId(int arid, HttpServletRequest request);
    //发送评论
    ResultNormal sendAssess(Assess assess, HttpServletRequest request);
    //获取评论
    List<Assess> getAssess(int arid,HttpServletRequest request);
    //获取搜索文章列表
    List<Article> getSearchList(String keyword);
    //点赞文章
    ResultNormal clickLike(Like like,HttpServletRequest request);
    //收藏文章
    ResultNormal collection(int arid, HttpServletRequest request);
    ResultNormal cancelCollection(int arid, HttpServletRequest request);
    //关注
    ResultNormal addAttention(int uid, HttpServletRequest request);
    ResultNormal delAttention(int uid, HttpServletRequest request);

    //查看个人页面相关信息
    //type==1表示的是查看别人页面《type==0表示的是查看自己页面
    ResultNormal getMyCollection(int userId,int type);
    ResultNormal getMyArticle(int userId,int type);
    ResultNormal getAttention(int userId,int type,int uid);
    ResultNormal getFans(int userId,int type,int uid);
    ResultNormal getMyAssess(int userId,int type);
    ResultNormal getMyArticles(SelectArticleInfo search);
    //
    ResultNormal getArticle(int arid);
    ResultNormal getAssesses(SelectAssessInfo search);
    ResultNormal deleteAssess(int assessId, HttpServletRequest request);
    ResultNormal deleteArticle(int articleId, HttpServletRequest request);
    ResultNormal deleteArticleByUser(int articleId, HttpServletRequest request);
    ResultNormal setState(int articleId, int state, HttpServletRequest request);
    ResultNormal setPublicState(int articleId, int state, HttpServletRequest request);
    ResultNormal deleteAssessUser(int assessId, HttpServletRequest request);
}
