package cn.wangjiannan.jd.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class GoodsPrice {
    private Long goodsInfoId;

    @JSONField(name = "op")
    private Double originalPrice;

    @JSONField(name = "p")
    private Double normalPrice;

    @JSONField(name = "tpp")
    private Double plusPrice;

}