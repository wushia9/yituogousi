package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 12951
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-04-25 10:15:06
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean logout(String openId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("wechat_openid", openId);
        queryWrapper.eq("is_deleted", 0);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            user.setIsLogin(0);
            userMapper.updateById(user);
            return true;
        }else{
            return false;
        }
    }
}




