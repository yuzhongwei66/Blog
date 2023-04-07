package com.yu.utils;

import com.yu.domain.entity.Article;
import com.yu.domain.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {

    public static <V> V copyBean(Object source,Class<V> clazz) {
        //创建目标对象，实现属性拷贝，返回结果
        V result = null;
        try {
            result = clazz.newInstance();
            BeanUtils.copyProperties(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
    public  static <O,V> List<V> copyBeanList(List<O> list,Class<V> clazz) {
        List<V> collect = list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
        return collect;
    }
}
