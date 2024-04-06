package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Bill;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tencent.wxcloudrun.model.vo.BillListVo;

import java.util.List;

/**
* @author 35793
* @description 针对表【bill(账单表)】的数据库操作Service
* @createDate 2024-03-19 23:38:06
*/
public interface BillService extends IService<Bill> {

    List<BillListVo> getBills(String openId);

    void putBill(Bill bill, String openId);
}
