package com.tencent.wxcloudrun.mapper;

import com.tencent.wxcloudrun.model.Articles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 12951
* @description 针对表【articles(文章)】的数据库操作Mapper
* @createDate 2024-04-23 15:42:20
* @Entity com.tencent.wxcloudrun.model.Articles
*/
@Mapper
public interface ArticlesMapper extends BaseMapper<Articles> {

}




