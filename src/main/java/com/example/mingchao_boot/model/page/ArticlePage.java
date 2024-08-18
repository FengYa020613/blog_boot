package com.example.mingchao_boot.model.page;
import com.example.mingchao_boot.model.article.Article;
import lombok.Data;

import java.util.List;

@Data
public class ArticlePage {
    private Integer total;
    private List<Article> articleList;
}
