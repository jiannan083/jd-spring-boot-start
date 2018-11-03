package cn.wangjiannan.jd.model;

import lombok.Data;

import java.util.Date;

@Data
public class GoodsCoupon {
    private Long id;

    private Long goodsInfoId;

    private String couponType;

    private String roleId;

    private Double quota;

    private Double discount;

    private Double coupon;

    private Integer mark;

    private String discountdesc;

    private String couponstyle;

    private String hourcoupon;

    private String name;

    private String timeDesc;

    private Date createTime;

    private Date updateTime;

}