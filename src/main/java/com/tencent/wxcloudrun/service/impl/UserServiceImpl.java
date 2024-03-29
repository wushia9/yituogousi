package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 35793
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-03-19 23:38:22
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




