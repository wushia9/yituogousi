package com.tencent.wxcloudrun.mapper;

import com.tencent.wxcloudrun.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 12951
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2024-04-25 10:15:06
* @Entity com.tencent.wxcloudrun.model.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    int getTotal();
    int getToday();
}




