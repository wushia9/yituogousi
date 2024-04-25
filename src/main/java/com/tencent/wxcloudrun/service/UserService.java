package com.tencent.wxcloudrun.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 12951
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-04-25 10:15:06
*/
public interface UserService extends IService<User> {

    boolean logout(String openId);

    Page<User> getUsersPage(String info, int page);
}
