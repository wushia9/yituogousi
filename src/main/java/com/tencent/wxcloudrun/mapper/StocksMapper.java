package com.tencent.wxcloudrun.mapper;

import com.tencent.wxcloudrun.model.Stocks;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 35793
* @description 针对表【stocks(股票信息)】的数据库操作Mapper
* @createDate 2024-04-02 22:00:21
* @Entity com.tencent.wxcloudrun.model.Stocks
*/
@Mapper
public interface StocksMapper extends BaseMapper<Stocks> {

}




