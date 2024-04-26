package com.tencent.wxcloudrun.model.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersInfo {
    private Integer total;
    private Integer today;
    private Integer todayMoney;
    private Integer todayCount;
}
