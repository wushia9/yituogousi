package com.tencent.wxcloudrun.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 股票信息
 * @TableName stocks
 */
@TableName(value ="stocks")
@Data
public class Stocks implements Serializable {
    /**
     * 股票ID
     */
    @TableId(type = IdType.AUTO)
    private Integer stockId;

    /**
     * 股票代码
     */
    private String stockSymbol;

    /**
     * 股票名称
     */
    private String stockName;

    /**
     * 交易所代码
     */
    private String exchangeId;

    /**
     * 国家地区
     */
    private String country;

    /**
     * 货币
     */
    private String currency;

    /**
     * 日期
     */
    private Date stockTime;

    /**
     * 今日开盘价
     */
    private BigDecimal stockOpen;

    /**
     * 最高价
     */
    private BigDecimal stockHigh;

    /**
     * 最低价
     */
    private BigDecimal stockLow;

    /**
     * 昨日收盘价
     */
    private BigDecimal stockClose;

    /**
     * 成交量
     */
    private BigDecimal stockVolume;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Stocks other = (Stocks) that;
        return (this.getStockId() == null ? other.getStockId() == null : this.getStockId().equals(other.getStockId()))
            && (this.getStockSymbol() == null ? other.getStockSymbol() == null : this.getStockSymbol().equals(other.getStockSymbol()))
            && (this.getStockName() == null ? other.getStockName() == null : this.getStockName().equals(other.getStockName()))
            && (this.getExchangeId() == null ? other.getExchangeId() == null : this.getExchangeId().equals(other.getExchangeId()))
            && (this.getCountry() == null ? other.getCountry() == null : this.getCountry().equals(other.getCountry()))
            && (this.getCurrency() == null ? other.getCurrency() == null : this.getCurrency().equals(other.getCurrency()))
            && (this.getStockTime() == null ? other.getStockTime() == null : this.getStockTime().equals(other.getStockTime()))
            && (this.getStockOpen() == null ? other.getStockOpen() == null : this.getStockOpen().equals(other.getStockOpen()))
            && (this.getStockHigh() == null ? other.getStockHigh() == null : this.getStockHigh().equals(other.getStockHigh()))
            && (this.getStockLow() == null ? other.getStockLow() == null : this.getStockLow().equals(other.getStockLow()))
            && (this.getStockClose() == null ? other.getStockClose() == null : this.getStockClose().equals(other.getStockClose()))
            && (this.getStockVolume() == null ? other.getStockVolume() == null : this.getStockVolume().equals(other.getStockVolume()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStockId() == null) ? 0 : getStockId().hashCode());
        result = prime * result + ((getStockSymbol() == null) ? 0 : getStockSymbol().hashCode());
        result = prime * result + ((getStockName() == null) ? 0 : getStockName().hashCode());
        result = prime * result + ((getExchangeId() == null) ? 0 : getExchangeId().hashCode());
        result = prime * result + ((getCountry() == null) ? 0 : getCountry().hashCode());
        result = prime * result + ((getCurrency() == null) ? 0 : getCurrency().hashCode());
        result = prime * result + ((getStockTime() == null) ? 0 : getStockTime().hashCode());
        result = prime * result + ((getStockOpen() == null) ? 0 : getStockOpen().hashCode());
        result = prime * result + ((getStockHigh() == null) ? 0 : getStockHigh().hashCode());
        result = prime * result + ((getStockLow() == null) ? 0 : getStockLow().hashCode());
        result = prime * result + ((getStockClose() == null) ? 0 : getStockClose().hashCode());
        result = prime * result + ((getStockVolume() == null) ? 0 : getStockVolume().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", stockId=").append(stockId);
        sb.append(", stockSymbol=").append(stockSymbol);
        sb.append(", stockName=").append(stockName);
        sb.append(", exchangeId=").append(exchangeId);
        sb.append(", country=").append(country);
        sb.append(", currency=").append(currency);
        sb.append(", stockTime=").append(stockTime);
        sb.append(", stockOpen=").append(stockOpen);
        sb.append(", stockHigh=").append(stockHigh);
        sb.append(", stockLow=").append(stockLow);
        sb.append(", stockClose=").append(stockClose);
        sb.append(", stockVolume=").append(stockVolume);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}