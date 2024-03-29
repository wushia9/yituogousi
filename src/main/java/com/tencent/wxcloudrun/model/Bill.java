package com.tencent.wxcloudrun.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 账单表
 * @TableName bill
 */
@TableName(value ="bill")
@Data
public class Bill implements Serializable {
    /**
     * 账单ID
     */
    @TableId(type = IdType.AUTO)
    private Integer billId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 类型
     */
    private String type;

    /**
     * 支付方式
     */
    private String paymentMethod;

    /**
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date time;

    /**
     * 备注
     */
    private String note;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 是否删除
     */
    private Integer isDeleted;

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
        Bill other = (Bill) that;
        return (this.getBillId() == null ? other.getBillId() == null : this.getBillId().equals(other.getBillId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getPaymentMethod() == null ? other.getPaymentMethod() == null : this.getPaymentMethod().equals(other.getPaymentMethod()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getNote() == null ? other.getNote() == null : this.getNote().equals(other.getNote()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBillId() == null) ? 0 : getBillId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getPaymentMethod() == null) ? 0 : getPaymentMethod().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getNote() == null) ? 0 : getNote().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", billId=").append(billId);
        sb.append(", userId=").append(userId);
        sb.append(", amount=").append(amount);
        sb.append(", type=").append(type);
        sb.append(", paymentMethod=").append(paymentMethod);
        sb.append(", time=").append(time);
        sb.append(", note=").append(note);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}