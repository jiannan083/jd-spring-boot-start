package cn.wangjiannan.jd.model;

import java.util.List;

/**
 * 商品促销返回.
 *
 * @author wangjiannan
 */
public class GoodsPromotionResult {
    /**
     * errcode.
     */
    private String errcode;
    /**
     * errmsg.
     */
    private String errmsg;
    /**
     * data.
     */
    private List<ResultData> data;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public List<ResultData> getData() {
        return data;
    }

    public void setData(List<ResultData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GoodsPromotionResult{" +
                "errcode='" + errcode + '\'' +
                ", errmsg='" + errmsg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class ResultData {
        //private String id;
        /**
         * pis.
         */
        private List<PromotionInfo> pis;

        public List<PromotionInfo> getPis() {
            return pis;
        }

        public void setPis(List<PromotionInfo> pis) {
            this.pis = pis;
        }

        @Override
        public String toString() {
            return "ResultData{" +
                    "pis=" + pis +
                    '}';
        }
    }

    public static class PromotionInfo {
        /**
         * subextinfo.
         */
        private String subextinfo;

        public String getSubextinfo() {
            return subextinfo;
        }

        public void setSubextinfo(String subextinfo) {
            this.subextinfo = subextinfo;
        }

        @Override
        public String toString() {
            return "PromotionInfo{" +
                    "subextinfo='" + subextinfo + '\'' +
                    '}';
        }
    }

    public static class Subextinfo {
        /**
         * extType.
         */
        private Integer extType;
        /**
         * subExtType.
         */
        private Integer subExtType;
        /**
         * needMoney.
         */
        private String needMoney;
        /**
         * rewardMoney.
         */
        private String rewardMoney;
        /**
         * subRuleList.
         */
        private List<SubRuleList> subRuleList;

        public Integer getExtType() {
            return extType;
        }

        public void setExtType(Integer extType) {
            this.extType = extType;
        }

        public Integer getSubExtType() {
            return subExtType;
        }

        public void setSubExtType(Integer subExtType) {
            this.subExtType = subExtType;
        }

        public String getNeedMoney() {
            return needMoney;
        }

        public void setNeedMoney(String needMoney) {
            this.needMoney = needMoney;
        }

        public String getRewardMoney() {
            return rewardMoney;
        }

        public void setRewardMoney(String rewardMoney) {
            this.rewardMoney = rewardMoney;
        }

        public List<SubRuleList> getSubRuleList() {
            return subRuleList;
        }

        public void setSubRuleList(List<SubRuleList> subRuleList) {
            this.subRuleList = subRuleList;
        }

        @Override
        public String toString() {
            return "Subextinfo{" +
                    "extType=" + extType +
                    ", subExtType=" + subExtType +
                    ", needMoney='" + needMoney + '\'' +
                    ", rewardMoney='" + rewardMoney + '\'' +
                    ", subRuleList=" + subRuleList +
                    '}';
        }
    }

    public static class SubRuleList {
        /**
         * needMoney.
         */
        private String needMoney;
        /**
         * rewardMoney.
         */
        private String rewardMoney;
        /**
         * rebate.
         */
        private String rebate;

        public String getNeedMoney() {
            return needMoney;
        }

        public void setNeedMoney(String needMoney) {
            this.needMoney = needMoney;
        }

        public String getRewardMoney() {
            return rewardMoney;
        }

        public void setRewardMoney(String rewardMoney) {
            this.rewardMoney = rewardMoney;
        }

        public String getRebate() {
            return rebate;
        }

        public void setRebate(String rebate) {
            this.rebate = rebate;
        }

        @Override
        public String toString() {
            return "SubRuleList{" +
                    "needMoney='" + needMoney + '\'' +
                    ", rewardMoney='" + rewardMoney + '\'' +
                    ", rebate='" + rebate + '\'' +
                    '}';
        }
    }

}
