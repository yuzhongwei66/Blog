package com.yu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.constants.SystemContants;
import com.yu.domain.entity.Article;
import com.yu.domain.entity.Category;
import com.yu.domain.entity.ResponseResult;
import com.yu.domain.vo.ArticleDetailVo;
import com.yu.domain.vo.ArticleListVo;
import com.yu.domain.vo.HotArticleVo;
import com.yu.domain.vo.PageVo;
import com.yu.mapper.ArticleMapper;
import com.yu.service.ArticleService;
import com.yu.service.CategoryService;
import com.yu.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper,Article> implements ArticleService {
   @Autowired
   private CategoryService categoryService;
    @Override

    //查询热门文章封装成ResponseResult返回
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式发布的文章，按照浏览量降序，最多查询十条消息
        queryWrapper.eq(Article::getStatus, SystemContants.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getViewCount);
        Page<Article> page = new Page(1,10);
        page(page, queryWrapper);
        List<Article> articles =page.getRecords();
        //bean拷贝

        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(vs);
    }



    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        //如果有categoryid 就要求查询时要和传入的相同
        queryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //只能是正式发布的文章
        queryWrapper.eq(Article::getStatus,SystemContants.ARTICLE_STATUS_NORMAL);
        //对isTop进行降序
        queryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page, queryWrapper);
        //查询categoryname
        List<Article> records = page.getRecords();
       // for (Article record : records) {
       //     Category category = categoryService.getById(record.getCategoryId());
     //       record.setCategoryName(category.getName());
 //       }
         records = records.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(records, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查文章
        Article article = getById(id);
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据id查询对应的分类名称
        Long categoryid = articleDetailVo.getCategoryid();
        Category category = categoryService.getById(categoryid);
        if(category != null)
        {
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }
}
