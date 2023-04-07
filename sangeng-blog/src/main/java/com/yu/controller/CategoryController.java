package com.yu.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.domain.entity.Category;
import com.yu.domain.entity.ResponseResult;
import com.yu.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 分类表(Category)表控制层
 *
 * @author makejava
 * @since 2023-04-07 16:12:01
 */
@RestController
@RequestMapping("category")
public class CategoryController  {
    /**
     * 服务对象
     */
    @Resource
    private CategoryService categoryService;
@GetMapping("/getCategoryList")
    public ResponseResult getCategoryList() {
       return categoryService.getCategoryList();
    }

}

