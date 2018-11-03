package cn.wangjiannan.jd.model;

import lombok.Data;

import java.util.Date;

@Data
public class GoodsPromotion {
    private Long id;

    private Long goodsInfoId;

    private String promotionContent;

    private Double needMoney;

    private Double rewardMoney;

    private Double rebate;

    private Double promotion;

    private Integer mark;

    private Date createTime;

    private Date updateTime;

}