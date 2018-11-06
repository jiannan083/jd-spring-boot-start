package cn.wangjiannan.jd.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 商品价格.
 *
 * @author wangjiannan
 */
public class GoodsPrice {
    /**
     * goodsInfoId.
     */
    private Long goodsInfoId;
    /**
     * originalPrice.
     */
    @JSONField(name = "op")
    private Double originalPrice;
    /**
     * normalPrice.
     */
    @JSONField(name = "p")
    private Double normalPrice;
    /**
     * plusPrice.
     */
    @JSONField(name = "tpp")
    private Double plusPrice;

    public Long getGoodsInfoId() {
        return goodsInfoId;
    }

    public void setGoodsInfoId(Long goodsInfoId) {
        this.goodsInfoId = goodsInfoId;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(Double normalPrice) {
        this.normalPrice = normalPrice;
    }

    public Double getPlusPrice() {
        return plusPrice;
    }

    public void setPlusPrice(Double plusPrice) {
        this.plusPrice = plusPrice;
    }

    @Override
    public String toString() {
        return "GoodsPrice{" +
                "goodsInfoId=" + goodsInfoId +
                ", originalPrice=" + originalPrice +
                ", normalPrice=" + normalPrice +
                ", plusPrice=" + plusPrice +
                '}';
    }
}