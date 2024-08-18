package com.example.mingchao_boot.Controller;

import com.example.mingchao_boot.Redis.RedisUtil;
import com.example.mingchao_boot.ResultModel.ResultNormal;
import com.example.mingchao_boot.Service.ArticleService;
import com.example.mingchao_boot.model.SelectArticleInfo;
import com.example.mingchao_boot.model.SelectAssessInfo;
import com.example.mingchao_boot.model.article.Article;
import com.example.mingchao_boot.model.article.Assess;
import com.example.mingchao_boot.model.article.Like;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 发送文章接口
     * @param article
     * @return
     */
    @PostMapping("/send")
    public ResultNormal SendArticle(@RequestBody Article article){
        return articleService.SendArticle(article);
    }

    /**
     * 获取攻略列表接口
     * @param request
     * @return
     */
    @GetMapping("/strategies")
    public ResultNormal getStrategy(HttpServletRequest request){
        return articleService.getShowStrategy(request);
    }

    /**
     * 获取同人列表接口
     * @param request
     * @return
     */
    @GetMapping("/pictures")
    public ResultNormal getPicture(HttpServletRequest request){
        return articleService.getShowPicture(request);
    }

    /**
     * 获取文章列表接口
     * @param request
     * @return
     */
    @GetMapping("/articles")
    public ResultNormal getArticle(HttpServletRequest request){
        return articleService.getShowArticle(request);
    }

    /**
     * 通过文章Id查文章
     * @param arid
     * @param request
     * @return
     */
    @GetMapping("/strategy/{arid}")
    public ResultNormal getStrategy(@PathVariable int arid,HttpServletRequest request){
        return articleService.getArticleByArticleId(arid,request);
    }


    /**
     * 通过id获取文章
     * @param arid
     * @return
     */
    @GetMapping("/getArticle/{arid}")
    public ResultNormal getArticle(@PathVariable int arid){
        return articleService.getArticle(arid);
    }

    /**
     * 发送评论
     * @param assess
     * @param request
     * @return
     */
    @PostMapping("/sendAssess")
    public ResultNormal sendAssess(@RequestBody Assess assess, HttpServletRequest request){
        System.out.println(assess);
        return articleService.sendAssess(assess,request);
    }
    @GetMapping("/getAssess/{arid}")
    public ResultNormal getAssess(@PathVariable int arid,HttpServletRequest request){
        List<Assess> list = articleService.getAssess(arid,request);
        return ResultNormal.success(list);
    }
    @GetMapping("/search")
    public ResultNormal getSearchList(@RequestParam String keyword){
        return ResultNormal.success(articleService.getSearchList(keyword));
    }

    /**
     * 点击like
     * @param like
     * @param request
     * @return
     */
    @PostMapping("/like")
    ResultNormal clickLike(@RequestBody Like like,HttpServletRequest request){
        return articleService.clickLike(like,request);
    }
    @GetMapping("/collection")
    public ResultNormal collection(@RequestParam int arid,HttpServletRequest request){
        return articleService.collection(arid,request);
    }
    @GetMapping("/cancelCollection")
    public ResultNormal cancelCollection(@RequestParam int arid,HttpServletRequest request){
        return articleService.cancelCollection(arid,request);
    }
    @GetMapping("/attention")
    public ResultNormal addAttention(@RequestParam int userId,@RequestParam int type,HttpServletRequest request){
        if(type==0){
            return articleService.addAttention(userId,request);
        }else{
            return articleService.delAttention(userId,request);
        }
    }

    @PostMapping("/getCollection/mine")
    public ResultNormal getMyCollection(HttpServletRequest request){
        String id = redisUtil.get(request.getHeader("token"));
        if (id!=null){
            int userId = Integer.parseInt(id);
            return articleService.getMyCollection(userId,0);
        }
        return ResultNormal.error("token过期");
    }
    @PostMapping("/getArticle/mine")
    public ResultNormal getMyArticle(HttpServletRequest request){
        String id = redisUtil.get(request.getHeader("token"));
        if (id!=null){
            int userId = Integer.parseInt(id);
            return articleService.getMyArticle(userId,0);
        }
        return ResultNormal.error("token过期");
    }
    @PostMapping("/getAssess/mine")
    public ResultNormal getMyAssess(HttpServletRequest request){
        String id = redisUtil.get(request.getHeader("token"));
        if (id!=null){
            int userId = Integer.parseInt(id);
            return articleService.getMyAssess(userId,0);
        }
        return ResultNormal.error("token过期");
    }
    @PostMapping("/getAttention/mine")
    public ResultNormal getMyAttention(HttpServletRequest request){
        String id = redisUtil.get(request.getHeader("token"));
        if (id!=null){
            int userId = Integer.parseInt(id);
            return articleService.getAttention(userId,0,userId);
        }
        return ResultNormal.error("token过期");
    }
    @PostMapping("getFans/mine")
    public ResultNormal getMyFans(HttpServletRequest request){
        String id = redisUtil.get(request.getHeader("token"));
        if (id!=null){
            int userId = Integer.parseInt(id);
            return articleService.getFans(userId,0,userId);
        }
        return ResultNormal.error("token过期");
    }


    @GetMapping("/getCollection/user")
    public ResultNormal getUserCollection(@RequestParam int userId){
        return articleService.getMyCollection(userId,1);
    }
    @GetMapping("/getArticle/user")
    public ResultNormal getUserArticle(@RequestParam int userId){
        return articleService.getMyArticle(userId,1);
    }
    @GetMapping("/getAssess/user")
    public ResultNormal getUserAssess(@RequestParam int userId){
        return articleService.getMyAssess(userId,1);
    }
    @GetMapping("/getAttention/user")
    public ResultNormal getUserAttention(@RequestParam int userId,HttpServletRequest request){
        String id = redisUtil.get(request.getHeader("token"));
        if (id!=null){
            int uid = Integer.parseInt(id);
            return articleService.getAttention(userId,1,uid);
        }
        return articleService.getAttention(userId,1,-1);
    }
    @GetMapping("getFans/user")
    public ResultNormal getUserFans(@RequestParam int userId,HttpServletRequest request){
        String id = redisUtil.get(request.getHeader("token"));
        if (id!=null){
            int uid = Integer.parseInt(id);
            return articleService.getFans(userId,1,uid);
        }
        return articleService.getFans(userId,1,-1);
    }



    @GetMapping("/user/setPublicState")
    ResultNormal setPublicState(@RequestParam int articleId,@RequestParam int state,HttpServletRequest request){
        return articleService.setPublicState(articleId,state,request);
    }
    @GetMapping("/user/deleteAssess")
    ResultNormal deleteAssessUser(@RequestParam int assessId,HttpServletRequest request){
        return articleService.deleteAssessUser(assessId,request);
    }
    @GetMapping("/user/deleteArticle")
    ResultNormal deleteArticleByUser(@RequestParam int articleId,HttpServletRequest request){
        return articleService.deleteArticleByUser(articleId,request);
    }


    @PostMapping("/admin/getArticle")
    ResultNormal getArticles(@RequestBody SelectArticleInfo search){
        return articleService.getMyArticles(search);
    }
    @GetMapping("/admin/deleteArticle")
    ResultNormal deleteArticle(@RequestParam int articleId,HttpServletRequest request){
        return articleService.deleteArticle(articleId,request);
    }
    @PostMapping("/admin/getAssess")
    ResultNormal getAssess(@RequestBody SelectAssessInfo search){
        return articleService.getAssesses(search);
    }
    @GetMapping("/admin/deleteAssess")
    ResultNormal deleteAssess(@RequestParam int assessId,HttpServletRequest request){
        return articleService.deleteAssess(assessId,request);
    }
    @GetMapping("/admin/setState")
    ResultNormal setState(@RequestParam int articleId,@RequestParam int state,HttpServletRequest request){
        return articleService.setState(articleId,state,request);
    }

}
