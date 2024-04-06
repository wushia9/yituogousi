package com.tencent.wxcloudrun.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author liusheng
 * @date 2024/4/222:13
 * @desc TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockData {
    private String stockCode; // 股票代码
    private String stockName; // 股票名称
    private BigDecimal latestPrice; // 最新价
    private BigDecimal changePercent; // 涨跌幅
    private BigDecimal changeAmount; // 涨跌额
    private Long volume; // 成交量(手)
    private BigDecimal turnover; // 成交额
    private BigDecimal amplitude; // 振幅
    private BigDecimal highestPrice; // 最高价
    private BigDecimal lowestPrice; // 最低价
    private BigDecimal openPrice; // 今开价
    private BigDecimal lastClosePrice; // 昨收价
    private Boolean isActive; // 是否活跃
    private String exchangeCode; // 交易所代码
    private String countryCode; // 国家代码
    private String currencyCode; // 货币代码
    private Date stockTime; // 股票时间
}
