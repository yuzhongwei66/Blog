package com.yu.controller;

import com.yu.domain.entity.Article;
import com.yu.domain.entity.ResponseResult;
import com.yu.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        //查询热门文章封装成ResponseResult返回
        ResponseResult result = articleService.hotArticleList();
        return result;
    }
  @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
      return articleService.articleList(pageNum, pageSize, categoryId);
  }
  @GetMapping("/{id}")
  public ResponseResult getArticleDetail(@PathVariable Long id)
  {
   return articleService.getArticleDetail(id);
  }
}
