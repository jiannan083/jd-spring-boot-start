package cn.wangjiannan.jd.model;

import lombok.Data;

import java.util.List;

/**
 * MESSAGE.
 *
 * @author wangjiannan
 */
@Data
public class GoodsPromotionResult {
    private String errcode;
    private String errmsg;
    private List<ResultData> data;

    @Data
    public static class ResultData {
        //private String id;
        private List<PromotionInfo> pis;
    }

    @Data
    public static class PromotionInfo {
        private String subextinfo;
    }

    @Data
    public static class Subextinfo {
        private Integer extType;
        private Integer subExtType;

        private String needMoney;
        private String rewardMoney;

        private List<SubRuleList> subRuleList;
    }

    @Data
    public static class SubRuleList {
        private String needMoney;
        private String rewardMoney;

        private String rebate;

    }

}
