package com.example.mingchao_boot.util;

import com.example.mingchao_boot.ResultModel.ResultNormal;
import com.example.mingchao_boot.model.article.Article;
import lombok.Data;
import org.springframework.stereotype.Component;
@Data
@Component
public class CheckArticle {
    private Article article;
    private Boolean isEmpty = true;
    private Boolean isPassing = true;
    private Boolean isPublic = true;

    public  CheckArticle setThisArticle(Article article) {
        this.article = article;
        return this;
    }

    public CheckArticle IsEmpty(){
        if (article!=null){
            this.isEmpty=false;
        }
        return this;
    }

    public CheckArticle IsPassing(){
        if (!isEmpty){
            if (article.getState()<1){
                System.out.println("文章状态"+article.getState()+"不通过");
                this.isPassing=false;
            }else{
                this.isPassing=true;
                System.out.println("文章状态"+article.getState()+"通过");
            }
        }
        return this;
    }

    public CheckArticle IsPublic(){
        if (isPassing){
            if (article.getPublicState()<1){
                System.out.println("文章设置"+article.getPublicState()+"隐私");
                this.isPublic=false;
            }else{
                this.isPublic=true;
                System.out.println("文章设置"+article.getState()+"公开");
            }
        }
        return this;
    }

    public ResultNormal getResult(){
        if (isEmpty){
            return ResultNormal.error("文章不存在或已删除");
        }else if(!isPassing){
            return ResultNormal.error("文章审核中");
        }else if(!isPublic){
            return ResultNormal.success("文章未公开");
        }else {
            return ResultNormal.success();
        }
    }
}
