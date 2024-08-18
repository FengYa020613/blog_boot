package com.example.mingchao_boot.Service.ServiceImpl;
import com.example.mingchao_boot.Annotations.MethodFunction;
import com.example.mingchao_boot.Aspect.Log;
import com.example.mingchao_boot.Mapper.articleMapper;
import com.example.mingchao_boot.Mapper.userMapper;
import com.example.mingchao_boot.Redis.RedisUtil;
import com.example.mingchao_boot.ResultModel.ResultNormal;
import com.example.mingchao_boot.Service.ArticleService;
import com.example.mingchao_boot.Service.LoginService;
import com.example.mingchao_boot.model.SelectArticleInfo;
import com.example.mingchao_boot.model.SelectAssessInfo;
import com.example.mingchao_boot.model.User;
import com.example.mingchao_boot.model.article.Article;
import com.example.mingchao_boot.model.article.Assess;
import com.example.mingchao_boot.model.article.Like;
import com.example.mingchao_boot.model.mq.Attention;
import com.example.mingchao_boot.model.page.ArticlePage;
import com.example.mingchao_boot.model.page.AssessPage;
import com.example.mingchao_boot.util.Base64Match;
import com.example.mingchao_boot.util.Base64ToImage;
import com.example.mingchao_boot.util.CheckArticle;
import com.example.mingchao_boot.util.DateUtil;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataUnit;

import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    articleMapper articleMapper;
    @Autowired
    Base64ToImage base64ToImage;
    @Autowired
    LoginService loginService;
    @Autowired
    userMapper userMapper;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    CheckArticle checkArticle;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private Gson gson;


    /**
     * 发布文章
     * @param article 文章内容
     * @return
     */
    @Override
    @Transactional
    @MethodFunction(Function="发送文章")
    public ResultNormal SendArticle(Article article) {
        List<String> imageBase64List = Base64Match.match(article.getContent());
        int limit = Math.min(imageBase64List.size(), 9);
        for (int i=0;i<limit;i++) {
            String base64 = imageBase64List.get(i);
            if (base64.length()>120){
                String newUrl = base64ToImage.LoadArticleImage(base64,article.getAuthorId());
                article.setContent(article.getContent().replace(base64,newUrl));
            }
        }
        List<String> urlList = Base64Match.matchUrl(article.getContent());
        article.setTag(String.join("/tag/",article.getTagList()));
        article.setImages(String.join("/split/",urlList));
        LocalDateTime localDateTime = LocalDateTime.now();
        String time = DateUtil.SimpleLocalDateTime(localDateTime);
        article.setTime(time);
        article.setContent(article.getContent().replace("24px","20rem"));
        article.setDescribe(Base64Match.MatchFirstP(article.getContent()));
        if (articleMapper.SendArticle(article))
        {
            userMapper.incrArticleNum(article.getAuthorId());
            return ResultNormal.success("发布成功");
        }
        return ResultNormal.error("发布失败");
    }

    /**
     * 获取全部文章
     * @param request
     * @return
     */
    @Override
    @MethodFunction(Function="获取全部文章")
    public ResultNormal getShowArticle(HttpServletRequest request) {
        return ResultNormal.success(getArList(0,request));
    }

    /**
     * 点赞
     * @param like
     * @param request
     * @return
     */
    @Override
    @Transactional
    @MethodFunction(Function="点赞文章")
    public ResultNormal clickLike(Like like,HttpServletRequest request) {
        String userId = redisUtil.get(request.getHeader("token"));
        //如果登录了
        if (userId!=null){
            like.setUserId(userId);
            //如果1则点赞文章
            if (like.getType()==0){
                //获取点赞文章的id
                int arid = like.getId();
                Article article = articleMapper.getArticleById(arid);
                if (article == null) {
                    return ResultNormal.error("文章已被删除");
                }
                //通过审核
                if (article.getState()>0){
                    //不是作者并且文章隐私了
                    if (article.getAuthorId()!=Integer.parseInt(userId)&&article.getPublicState()<1){
                        return ResultNormal.error("文章已隐私");
                    }
                    rabbitTemplate.convertAndSend("fy_blog_boot_topic_exchange","like.click",gson.toJson(like));
                    return ResultNormal.success();
                }
                //未通过审核
                else{
                    return ResultNormal.error("文章还未通过审核");
                }
            }else{
                Assess assess = articleMapper.getAssessById(like.getId());
                if (assess==null){
                    return ResultNormal.error("评论已被删除");
                }
                rabbitTemplate.convertAndSend("fy_blog_boot_topic_exchange","like.click",gson.toJson(like));
            }
            return ResultNormal.success();
        }
        return ResultNormal.error("未登录");
    }


    /**
     * 获取我的收藏
     * @param userId
     * @param type
     * @return
     */
    @Override
    public ResultNormal getMyCollection(int userId,int type) {
        if (type==1){
            if(userMapper.getUserSetting(userId).getShowCollection()==0){
                return ResultNormal.error("hidden");
            }
        }
        List<Article> list = articleMapper.getMyCollection(userId);
        return getArticleInfo(userId, list);
    }

    /**
     *
     * @param userId
     * @param type
     * @return
     */
    @Override
    @MethodFunction(Function="获取我的发布")
    public ResultNormal getMyArticle(int userId,int type) {
        List<Article> list;
        if (type==1){
            list = articleMapper.getUserArticle(userId);
            return getArticleInfo(userId, list);
        }
        list = articleMapper.getMyArticle(userId);
        return getArticleInfo(userId, list);
    }

    @Override
    @Transactional
    public ResultNormal collection(int arid, HttpServletRequest request) {
        Article article = articleMapper.getArticleById(arid);
        if (article==null){
            return ResultNormal.error("文章已被删除");
        }
        String userId = redisUtil.get(request.getHeader("token"));
        if (userId!=null){
            if (articleMapper.getCollection(arid,Integer.parseInt(userId))==0){
                articleMapper.collection(arid,Integer.parseInt(userId));
            }else{
                return ResultNormal.error("你已经点过赞了");
            }
        }
        return ResultNormal.success();
    }

    @Override
    @Transactional
    public ResultNormal cancelCollection(int arid, HttpServletRequest request) {
        String userId = redisUtil.get(request.getHeader("token"));
        if (userId!=null){
            articleMapper.cancelCollection(arid,Integer.parseInt(userId));
        }
        return ResultNormal.success();
    }

    @Override
    @Transactional
    public ResultNormal addAttention(int uid, HttpServletRequest request) {
       return SetAttention(uid,request,1);
    }

    @Override
    public ResultNormal delAttention(int uid, HttpServletRequest request) {
        return SetAttention(uid,request,-1);
    }
    private ResultNormal SetAttention(int uid, HttpServletRequest request, int num) {
        //获取redis中的userId
        String userId = redisUtil.get(request.getHeader("token"));
        //如果为null则未登录
        if (userId!=null){
            //添加需要处理的信息
            Attention attention = new Attention();
            attention.setNum(num);
            attention.setUserId(Integer.parseInt(userId));
            attention.setTargetId(uid);
            //发送消息
            rabbitTemplate.convertAndSend("fy_blog_boot_topic_exchange","attention.commit",
                    gson.toJson(attention));
            return ResultNormal.success();
        }
        //返回error
        return ResultNormal.error("未登录");
    }



    @Override
    public ResultNormal getAttention(int userId,int type,int uid) {
        if (type==1){
            if(userMapper.getUserSetting(userId).getShowAttention()==0){
                return ResultNormal.error("hidden");
            }
            List<User> list = userMapper.getAttention(userId);
            for (User u:list) {
                if (userMapper.findAttention(uid, (int) u.getUserId())!=0){
                    u.setFlag(1);
                }
                if (userMapper.findAttention((int) u.getUserId(),uid)!=0){
                    u.setFans(1);
                }
            }
            return ResultNormal.success(list);
        }
        List<User> list = userMapper.getAttention(userId);
        for (User u:list) {
            if (userMapper.findAttention((int) u.getUserId(),uid)!=0){
                u.setFans(1);
            }
            u.setFlag(1);
        }
        return ResultNormal.success(list);
    }

    /**
     *
     * flag表示uid关注了列表id
     * fans表示列表id关注了uid
     * @param userId
     * @param type
     * @param uid
     * @return
     */
    @Override
    @Transactional
    public ResultNormal getFans(int userId,int type,int uid) {
        if (type==1){
            if(userMapper.getUserSetting(userId).getShowFans()==0){
                return ResultNormal.error("hidden");
            }
            List<User> list = userMapper.getFans(userId);
            for (User u:list) {
                if (userMapper.findAttention(uid, (int) u.getUserId())!=0){
                    u.setFlag(1);
                }
                if (userMapper.findAttention((int) u.getUserId(),uid)!=0){
                    u.setFans(1);
                }
            }
            return ResultNormal.success(list);
        }
        List<User> list = userMapper.getFans(userId);
        for (User u:list) {
            if (userMapper.findAttention(uid, (int) u.getUserId())!=0){
                u.setFlag(1);
            }
            u.setFans(1);
        }
        return ResultNormal.success(list);
    }

    @Override
    public ResultNormal getMyAssess(int userId,int type) {
        List<Assess> list = articleMapper.getMyAssess(userId);
        for (Assess assess : list){
            List<String> imgs = Base64Match.getIMG(assess.getContent());
            if (!imgs.isEmpty()){
                for (String img:imgs){
                    assess.setContent(assess.getContent().replace(img,img.contains("id=\"emoji\"")?"[/表情]":"[/图片]"));
                }
            }
        }
        return ResultNormal.success(list);
    }

    @Override
    public ResultNormal getMyArticles(SelectArticleInfo search) {
        ArticlePage articlePage = new ArticlePage();
        int module = switch (search.getModule()) {
            case "文章" -> 0;
            case "攻略" -> 1;
            case "同人" -> 2;
            default -> -1;
        };
        int status = switch (search.getStatus()) {
            case "审核中" -> 0;
            case "未通过" -> -1;
            case "已通过" -> 1;
            default -> -2;
        };
        search.setStatusInt(status);
        search.setModuleInt(module);
        search.setPage((search.getPage()-1)*search.getPageSize());
        List<Article> list = articleMapper.getArticles(search);
        for(Article article:list){
            if (article.getTag()!=null){
                article.setTagList(Arrays.stream(article.getTag().split("/tag/")).toList());
            }
        }
        Integer total = articleMapper.getArticleNum(search);
        articlePage.setTotal(total);
        articlePage.setArticleList(list);
        return ResultNormal.success(articlePage);
    }

    /**
     * 获取文章
     * @param arid
     * @return
     */
    @Override
    public ResultNormal getArticle(int arid) {
        Article article = articleMapper.getStrategy(arid);
        if (article.getTag()!=null){
            article.setTagList(Arrays.stream(article.getTag().split("/tag/")).toList());
        }
        return ResultNormal.success(article);
    }

    @Override
    public ResultNormal getAssesses(SelectAssessInfo search) {
        AssessPage assessPage = new AssessPage();
        search.setPage((search.getPage()-1)*search.getPageSize());
        List<Assess> list = articleMapper.getAssesses(search);
        int total = articleMapper.getAssessNum();
        assessPage.setTotal(total);
        assessPage.setAssessList(list);
        return ResultNormal.success(assessPage);
    }

    @Override
    public ResultNormal deleteAssess(int assessId, HttpServletRequest request) {
        Assess assess = articleMapper.getAssessById(assessId);
        if (assess==null){
            return ResultNormal.success();
        }
        List<String> urlList = Base64Match.matchUrl(assess.getContent());
        if (articleMapper.deleteAssess(assessId)>0){
            articleMapper.updateAssessNum(assess.getArticleId(),-1);
            if (!urlList.isEmpty()){
                for (String url:urlList){
                    base64ToImage.deleteImage(url);
                }
            }
            return ResultNormal.success();
        }
        return ResultNormal.error("删除失败");
    }

    @Override
    public ResultNormal deleteArticle(int articleId, HttpServletRequest request) {
        Article article = articleMapper.getArticleById(articleId);
        if (article==null){
            return ResultNormal.success();
        }
        List<String> urlList = Arrays.stream(article.getImages().split("/split/")).toList();
        if (articleMapper.deleteArticle(articleId)>0){
            if (!urlList.isEmpty()){
                for (String url:urlList){
                    base64ToImage.deleteImage(url);
                }
            }
            int userId = article.getAuthorId();
            userMapper.decrArticleNum(userId);
            return ResultNormal.success();
        }
        return ResultNormal.error("删除失败");
    }

    @Override
    public ResultNormal deleteArticleByUser(int articleId, HttpServletRequest request) {
        String uid = redisUtil.get(request.getHeader("token"));
        if (uid==null){
            return ResultNormal.error("未登录");
        }
        Article article = articleMapper.getArticleById(articleId);
        if (article==null){
            return ResultNormal.success();
        }
        List<String> urlList = Arrays.stream(article.getImages().split("/split/")).toList();
        if (articleMapper.deleteArticle(articleId)>0){
            if (!urlList.isEmpty()){
                for (String url:urlList){
                    base64ToImage.deleteImage(url);
                }
            }
            int userId = article.getAuthorId();
            userMapper.decrArticleNum(userId);
            return ResultNormal.success();
        }
        return ResultNormal.error("删除失败");
    }

    @Override
    @Transactional
    @MethodFunction(Function = "修改文章审核状态")
    public ResultNormal setState(int articleId, int state, HttpServletRequest request) {
        Article article = articleMapper.getArticleById(articleId);
        if (article==null){
            return ResultNormal.error("文章不存在");
        }
        articleMapper.setState(articleId,state);
        return ResultNormal.success();
    }

    @Override
    @Transactional
    @MethodFunction(Function = "用户修改文章公开状态")
    public ResultNormal setPublicState(int articleId, int state, HttpServletRequest request) {
        String userId = redisUtil.get(request.getHeader("token"));
        if (userId==null){
            return ResultNormal.error("token已过期");
        }
        Article article = articleMapper.getArticleById(articleId);
        if (article==null){
            return ResultNormal.error("文章不存在");
        }
        if (article.getState()<1){
            return ResultNormal.error("文章还未通过审核");
        }
        articleMapper.setPublicState(articleId,state);
        return ResultNormal.success();
    }

    @Override
    @Transactional
    @MethodFunction(Function = "用户删除评论")
    public ResultNormal deleteAssessUser(int assessId, HttpServletRequest request) {
        String userId = redisUtil.get(request.getHeader("token"));
        if (userId==null){
            return ResultNormal.error("token已过期");
        }
        Assess assess = articleMapper.getAssessById(assessId);
        if (assess==null){
            return ResultNormal.success();
        }
        int articleId = assess.getArticleId();
        articleMapper.updateAssessNum(articleId,-1);
        articleMapper.deleteAssess(assessId);
        return ResultNormal.success();
    }


    /**
     * 获取文章内容---文章详情页面
     * @param userId
     * @param list
     * @return
     */
    @NotNull
    @MethodFunction(Function = "获取文章详情页面")
    private ResultNormal getArticleInfo(int userId, List<Article> list) {
        list = setArticleInfo(list,userId);
        return ResultNormal.success(list);
    }

    /**
     * 设置文章属性
     * @param list
     * @param userId
     * @return
     */
    private List<Article> setArticleInfo(List<Article> list, int userId) {
        for (Article article : list) {
            if (Boolean.TRUE.equals(redisTemplate.opsForSet().isMember("articleLike:" + article.getId(), userId + ""))){
                article.setLike(1);
            }
            article.setLnum(Math.toIntExact(redisTemplate.opsForSet().size("articleLike:" + article.getId())));
            if (article.getImages()!=null){
                article.setImageList(Arrays.stream(article.getImages().split("/split/")).toList());
            }
            if (article.getTag()!=null){
                article.setTagList(Arrays.stream(article.getTag().split("/tag/")).toList());
            }
        }
        return list;
    }

    @Override
    public ResultNormal getShowStrategy(HttpServletRequest request) {
        return ResultNormal.success(getArList(1,request));
    }

    @Override
    public ResultNormal getShowPicture(HttpServletRequest request) {
        return ResultNormal.success(getArList(2,request));
    }

    private List<Article> getArList(int module,HttpServletRequest request){
        String userId = redisUtil.get(request.getHeader("token"));
        if (userId==null){
            userId = "-1";
        }
        List<Article> list = articleMapper.getAllArticles(module);
        return setArticleInfo(list, Integer.parseInt(userId));
    }

    @Override
    public ResultNormal getArticleByArticleId(int arid, HttpServletRequest request) {
        Article article = articleMapper.getStrategy(arid);
        CheckArticle au = checkArticle.setThisArticle(article).IsEmpty().IsPassing().IsPublic();
        ResultNormal result = au.getResult();
        if (!result.getCode().equals("200")){
            return result;
        }
        String userId = "";
        if (!"".equals(request.getHeader("token"))){
            userId = redisUtil.get(request.getHeader("token"));
            if (Boolean.TRUE.equals(redisTemplate.opsForSet().isMember("articleLike:" + article.getId(), userId + ""))){
                article.setLike(1);
            }
            article.setLnum(Math.toIntExact(redisTemplate.opsForSet().size("articleLike:" + article.getId())));
            if(articleMapper.checkCollect(Integer.parseInt(userId),article.getId())>0){
                article.setCollect(1);
            }
            if (articleMapper.checkAttention(Integer.parseInt(userId),article.getAuthorId())>0){
                article.setAttention(1);
            }
        }
        if (au.getIsPublic()||article.getAuthorId()==Integer.parseInt(userId)){
            if (article.getTag()!=null){
                article.setTagList(Arrays.stream(article.getTag().split("/tag/")).toList());
            }
            int collectNum = articleMapper.getCollectNum(arid);
            article.setSnum(collectNum);
            return ResultNormal.success(article);
        }
        return ResultNormal.error("文章未公开");
    }

    @Override
    @Transactional
    public ResultNormal sendAssess(Assess assess, HttpServletRequest request) {
        String token = request.getHeader("token");
        ResultNormal result = loginService.getUserInfo(token);
        if (!result.getCode().equals("200")){
            Log.error("暂未登录");
            return ResultNormal.error("暂未登录");
        }
        User user = (User) result.getData();
        if(user==null){
            Log.error("token已过期");
            return ResultNormal.error("token已过期");
        }
        Article article = articleMapper.getArticleById(assess.getArticleId());
        if (article==null){
            return ResultNormal.error("文章已被删除或隐私");
        }
        assess.setUserId((int) user.getUserId());
        LocalDateTime dateTime = LocalDateTime.now();
        String time = DateUtil.SimpleLocalDateTime(dateTime);
        assess.setContent(assess.getContent().replace("</p>\n" +
                        "<p>","<br>")
                .replace("<p>","").replace("</p>",""));
        assess.setTime(time);
        if(articleMapper.SendAssess(assess)){
            articleMapper.updateAssessNum(assess.getArticleId(),1);
            return ResultNormal.success();
        }
        Log.error("插入数据库失败");
        return ResultNormal.error("插入数据库失败");
    }

    @Override
    public List<Assess> getAssess(int arid,HttpServletRequest request) {
        List<Assess> rootAssess = articleMapper.getAssess(arid,0);
        String userId = redisUtil.get(request.getHeader("token"));
        if (userId==null){
            userId = "-1";
        }
        if (rootAssess!=null&&rootAssess.size()>0){
            for (Assess assess:rootAssess){
                if (Boolean.TRUE.equals(redisTemplate.opsForSet().isMember("assessLike:" + assess.getId(), userId))){
                    assess.setLike(1);
                }
                assess.setLnum(Math.toIntExact(redisTemplate.opsForSet().size("assessLike:" + assess.getId())));
            }
            fillRootAssess(arid,1,rootAssess,userId);
        }
        return rootAssess;
    }

    @Override
    @MethodFunction(Function = "获取搜索文章列表")
    public List<Article> getSearchList(String keyword) {
        List<Article> searchList = articleMapper.getSearchList(keyword);
        for (Article article : searchList) {
            if (article.getImages()!=null&&!"".equals(article.getImages())){
                article.setImageList(Arrays.stream(article.getImages().split("/split/")).toList());
            }
            String mainTitle = article.getMainTitle();
            String target = replaceIgnoreCase(keyword,mainTitle);
            if (target!=null){
                String replacement = article.getMainTitle().replace(target,
                        "<span style=\"color:red\">" + target + "</span>");
                article.setMainTitle(replacement);
            }
            if (article.getTag()!=null){
                article.setTagList(Arrays.stream(article.getTag().split("/tag/")).toList());
            }
        }
        return searchList;
    }

    private String replaceIgnoreCase(String keyword,String origin) {
       Pattern pattern = Pattern.compile(keyword,Pattern.CASE_INSENSITIVE);
       Matcher matcher = pattern.matcher(origin);
       if (matcher.find()){
           return matcher.group();
       }
       return null;
    }


    private void fillRootAssess(int arid,int deep,List<Assess> rootAssess,String userId) {
        List<Assess> childAssessList = articleMapper.getAssess(arid,deep);
        HashMap<Integer,Integer> rootIndexMap = getRootIndexMap(rootAssess);
        HashMap<Integer,Integer> assessIndexMap = getAssessIndexMap(rootAssess,childAssessList);
        if (childAssessList==null||childAssessList.size()==0) return;
        for (Assess children:childAssessList) {
            int rootIndex = getRootIndex(children.getAssessId(),assessIndexMap);
            Assess root = rootAssess.get(rootIndexMap.get(rootIndex));
            List<Assess> childrenList = root.getChildrenAssessList();
            if(childrenList==null){
                childrenList = new LinkedList<>();
                root.setChildrenAssessList(childrenList);
            }
            if (!userId.equals("-1")){
                if (redisUtil.get(userId+"assess"+children.getId())!=null){
                    children.setLike(1);
                }
            }
            childrenList.add(children);
        }
    }

    private int getRootIndex(int childId, HashMap<Integer, Integer> assessIndexMap) {
        int rootIndex = assessIndexMap.get(childId);
        if (rootIndex!=0){
            return getRootIndex(rootIndex,assessIndexMap);
        }
        return childId;
    }


    private HashMap<Integer, Integer> getRootIndexMap(List<Assess> rootAssess) {
        HashMap<Integer, Integer> indexMap = new HashMap<>();
        for (int index = 0;index < rootAssess.size();index++){
            indexMap.put(rootAssess.get(index).getId(),index);
        }
        return indexMap;
    }

    private HashMap<Integer, Integer> getAssessIndexMap(List<Assess> rootAssess,List<Assess> childrenAssess) {
        HashMap<Integer, Integer> indexMap = new HashMap<>();
        for (Assess value : rootAssess) {
            indexMap.put(value.getId(), value.getAssessId());
        }
        for (Assess assess : childrenAssess) {
            indexMap.put(assess.getId(), assess.getAssessId());
        }
        return indexMap;
    }
}
