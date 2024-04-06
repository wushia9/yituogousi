package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.mapper.UserMapper;
import com.tencent.wxcloudrun.model.Bill;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.model.vo.BillListVo;
import com.tencent.wxcloudrun.service.BillService;
import com.tencent.wxcloudrun.mapper.BillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author 35793
* @description 针对表【bill(账单表)】的数据库操作Service实现
* @createDate 2024-03-19 23:38:06
*/
@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill>
    implements BillService{


    final BillMapper billMapper;
    final UserMapper userMapper;
    final Logger logger;
    
    public BillServiceImpl(@Autowired BillMapper billMapper, @Autowired UserMapper userMapper) {
        this.billMapper = billMapper;
        this.userMapper = userMapper;
        this.logger = LoggerFactory.getLogger(BillServiceImpl.class);
    }

    @Override
    public List<BillListVo> getBills(String openId) {
        return billMapper.getBills(openId);
    }

    @Override
    public void putBill(Bill bill, String openId) {
        // 判断是否存在该openId的用户，存在则进行插入操作，不存在则不创建相应用户然后插入
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.lambda()
                        .eq(User::getWechatOpenid, openId)
                        .eq(User::getIsDeleted, 0);
        User user = userMapper.selectOne(userQueryWrapper);
        if (user == null){
            logger.info("数据库中不存在该openId的用户，进行创建");
            user = new User();
            user.setWechatOpenid(openId);
            user.setIsDeleted(0);
            user.setCreatedAt(new Date());
            userMapper.insert(user);
        }
        if (user.getUserId() != null){
            bill.setUserId(user.getUserId());
            bill.setCreatedAt(new Date());
            bill.setIsDeleted(0);
            billMapper.insert(bill);
        }else{
            throw new RuntimeException("数据库插入user失败");
        }
    }
}




