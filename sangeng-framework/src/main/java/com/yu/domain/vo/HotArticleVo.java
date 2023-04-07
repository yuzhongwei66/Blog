package com.yu.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleVo
{
    private Long id;
    //标题
    private String title;
    //文章内容
    private Long viewCount;
    //文章摘要
}
