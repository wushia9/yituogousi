package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.model.Articles;
import com.tencent.wxcloudrun.service.ArticlesService;
import com.tencent.wxcloudrun.mapper.ArticlesMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 12951
* @description 针对表【articles(文章)】的数据库操作Service实现
* @createDate 2024-04-23 15:42:20
*/
@Service
public class ArticlesServiceImpl extends ServiceImpl<ArticlesMapper, Articles>
    implements ArticlesService{
    private final ArticlesMapper articlesMapper;

    public ArticlesServiceImpl(ArticlesMapper articlesMapper) {
        this.articlesMapper = articlesMapper;
    }

    @Override
    public Page<Articles> getArticlesPage(String info, int page) {
        Page<Articles> articlesPage = new Page<>(page, 10);
        QueryWrapper<Articles> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(Articles::getName, info)
                .orderByDesc(Articles::getCreateTime);
        articlesPage = articlesMapper.selectPage(articlesPage, queryWrapper);
        return articlesPage;
    }

    @Override
    public List<Articles> getArticlesList() {
        List<Articles> articles = articlesMapper.selectList(null);
        for (Articles art :
                articles) {
            art.setContent("");
        }
        return articles;
    }
}




