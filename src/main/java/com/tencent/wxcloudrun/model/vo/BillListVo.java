package com.tencent.wxcloudrun.model.vo;

import com.tencent.wxcloudrun.model.Bill;
import lombok.Data;

import java.sql.Date;
import java.util.List;

/**
 * @author liusheng
 * @date 2024/3/204:12
 * @desc TODO
 */
@Data
public class BillListVo {
    private String date;
    private List<Bill> bills;
}
