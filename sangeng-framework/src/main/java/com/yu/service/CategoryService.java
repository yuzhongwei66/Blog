package com.yu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yu.domain.entity.Category;
import com.yu.domain.entity.ResponseResult;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-04-07 16:12:03
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}

