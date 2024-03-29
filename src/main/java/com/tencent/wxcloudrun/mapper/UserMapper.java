package com.tencent.wxcloudrun.mapper;

import com.tencent.wxcloudrun.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 35793
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2024-03-19 23:38:22
* @Entity com.tencent.wxcloudrun.model.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




