package cn.wangjiannan.jd.model;

/**
 * 商品促销.
 *
 * @author wangjiannan
 */
public class GoodsPromotion {
    /**
     * goodsInfoId.
     */
    private Long goodsInfoId;
    /**
     * promotionContent.
     */
    private String promotionContent;
    /**
     * needMoney.
     */
    private Double needMoney;
    /**
     * rewardMoney.
     */
    private Double rewardMoney;
    /**
     * rebate.
     */
    private Double rebate;
    /**
     * promotion.
     */
    private Double promotion;
    /**
     * mark.
     */
    private Integer mark;

    public Long getGoodsInfoId() {
        return goodsInfoId;
    }

    public void setGoodsInfoId(Long goodsInfoId) {
        this.goodsInfoId = goodsInfoId;
    }

    public String getPromotionContent() {
        return promotionContent;
    }

    public void setPromotionContent(String promotionContent) {
        this.promotionContent = promotionContent;
    }

    public Double getNeedMoney() {
        return needMoney;
    }

    public void setNeedMoney(Double needMoney) {
        this.needMoney = needMoney;
    }

    public Double getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(Double rewardMoney) {
        this.rewardMoney = rewardMoney;
    }

    public Double getRebate() {
        return rebate;
    }

    public void setRebate(Double rebate) {
        this.rebate = rebate;
    }

    public Double getPromotion() {
        return promotion;
    }

    public void setPromotion(Double promotion) {
        this.promotion = promotion;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "GoodsPromotion{" +
                "goodsInfoId=" + goodsInfoId +
                ", promotionContent='" + promotionContent + '\'' +
                ", needMoney=" + needMoney +
                ", rewardMoney=" + rewardMoney +
                ", rebate=" + rebate +
                ", promotion=" + promotion +
                ", mark=" + mark +
                '}';
    }
}