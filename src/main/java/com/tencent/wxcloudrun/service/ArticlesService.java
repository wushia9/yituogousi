package com.tencent.wxcloudrun.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.model.Articles;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 12951
* @description 针对表【articles(文章)】的数据库操作Service
* @createDate 2024-04-23 15:42:20
*/
public interface ArticlesService extends IService<Articles> {

    Page<Articles> getArticlesPage(String info, int page);
}
