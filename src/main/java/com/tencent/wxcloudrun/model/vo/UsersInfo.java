package com.tencent.wxcloudrun.model.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersInfo {
    private int total;
    private int today;
    private int todayMoney;
    private int todayCount;
}
