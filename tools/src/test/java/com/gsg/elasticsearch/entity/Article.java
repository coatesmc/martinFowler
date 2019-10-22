package com.gsg.elasticsearch.entity;

import lombok.Data;

/**
 * @ClassName Article
 * @Description 操作所用到的实体类
 * @Author mc
 * @Date 2019/9/6 9:20
 * @Version 1.0
 **/
@Data
public class Article {
    private long id;
    private String title;

    public Article(long id, String title) {
        this.id = id;
        this.title = title;
    }
}
