package cn.wangjiannan.jd.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class GoodsInfo {
    private Long id;

    private String skuid;

    private String name;

    private Double normalPrice;

    private Double plusPrice;

    private String promotionDes;

    private Double promotion;

    private String couponDes;

    private Double coupon;

    private Double finalPrice;

    @JSONField(format = "MM月dd日HH点")
    private Date createTime;

    private Date updateTime;

}