package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.model.Stocks;
import com.tencent.wxcloudrun.service.StocksService;
import com.tencent.wxcloudrun.mapper.StocksMapper;
import org.springframework.stereotype.Service;

/**
* @author 35793
* @description 针对表【stocks(股票信息)】的数据库操作Service实现
* @createDate 2024-04-02 22:00:21
*/
@Service
public class StocksServiceImpl extends ServiceImpl<StocksMapper, Stocks>
    implements StocksService{

}




