package cn.wangjiannan.jd.model;

import lombok.Data;

@Data
public class GoodsPromotion {
    private Long goodsInfoId;

    private String promotionContent;

    private Double needMoney;

    private Double rewardMoney;

    private Double rebate;

    private Double promotion;

    private Integer mark;

}