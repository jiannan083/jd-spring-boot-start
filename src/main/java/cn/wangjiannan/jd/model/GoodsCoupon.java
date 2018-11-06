package cn.wangjiannan.jd.model;

/**
 * 商品优惠.
 *
 * @author wangjiannan
 */
public class GoodsCoupon {
    /**
     * goodsInfoId.
     */
    private Long goodsInfoId;
    /**
     * couponType.
     */
    private String couponType;
    /**
     * roleId.
     */
    private String roleId;
    /**
     * quota.
     */
    private Double quota;
    /**
     * discount.
     */
    private Double discount;
    /**
     * coupon.
     */
    private Double coupon;
    /**
     * mark.
     */
    private Integer mark;
    /**
     * discountdesc.
     */
    private String discountdesc;
    /**
     * couponstyle.
     */
    private String couponstyle;
    /**
     * hourcoupon.
     */
    private String hourcoupon;
    /**
     * name.
     */
    private String name;
    /**
     * timeDesc.
     */
    private String timeDesc;

    public Long getGoodsInfoId() {
        return goodsInfoId;
    }

    public void setGoodsInfoId(Long goodsInfoId) {
        this.goodsInfoId = goodsInfoId;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Double getQuota() {
        return quota;
    }

    public void setQuota(Double quota) {
        this.quota = quota;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getCoupon() {
        return coupon;
    }

    public void setCoupon(Double coupon) {
        this.coupon = coupon;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getDiscountdesc() {
        return discountdesc;
    }

    public void setDiscountdesc(String discountdesc) {
        this.discountdesc = discountdesc;
    }

    public String getCouponstyle() {
        return couponstyle;
    }

    public void setCouponstyle(String couponstyle) {
        this.couponstyle = couponstyle;
    }

    public String getHourcoupon() {
        return hourcoupon;
    }

    public void setHourcoupon(String hourcoupon) {
        this.hourcoupon = hourcoupon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    @Override
    public String toString() {
        return "GoodsCoupon{" +
                "goodsInfoId=" + goodsInfoId +
                ", couponType='" + couponType + '\'' +
                ", roleId='" + roleId + '\'' +
                ", quota=" + quota +
                ", discount=" + discount +
                ", coupon=" + coupon +
                ", mark=" + mark +
                ", discountdesc='" + discountdesc + '\'' +
                ", couponstyle='" + couponstyle + '\'' +
                ", hourcoupon='" + hourcoupon + '\'' +
                ", name='" + name + '\'' +
                ", timeDesc='" + timeDesc + '\'' +
                '}';
    }
}