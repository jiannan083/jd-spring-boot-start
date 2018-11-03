package cn.wangjiannan.jd.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class GoodsPrice {
    private Long id;

    private Long goodsInfoId;

    @JSONField(name = "op")
    private Double originalPrice;

    @JSONField(name = "p")
    private Double normalPrice;

    @JSONField(name = "tpp")
    private Double plusPrice;

    private Date createTime;

    private Date updateTime;

}