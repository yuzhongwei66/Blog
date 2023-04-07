package com.yu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.constants.SystemContants;
import com.yu.domain.entity.Article;
import com.yu.domain.entity.Category;
import com.yu.domain.entity.ResponseResult;
import com.yu.domain.vo.CategoryVo;
import com.yu.mapper.CategoryMapper;
import com.yu.service.ArticleService;
import com.yu.service.CategoryService;
import com.yu.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-04-07 16:12:03
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
  @Autowired
    private ArticleService articleService;
    @Override
    public ResponseResult getCategoryList() {
        //先查询文章表，状态为已发布的
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemContants.ARTICLE_STATUS_NORMAL);
        List<Article> list = articleService.list(queryWrapper);
        //获取文章的分类id，去重
        Set<Long> categoryIds = list.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categories = this.listByIds(categoryIds);
        List<Category> collect = categories.stream().filter(category -> category.getStatus().equals(SystemContants.STATUS_NORMAL))
                .collect(Collectors.toList());
        //封装成vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(collect, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}
