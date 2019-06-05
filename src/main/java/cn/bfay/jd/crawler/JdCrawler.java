package cn.bfay.jd.crawler;

import cn.bfay.commons.okhttp.OkHttpUtils;
import cn.bfay.jd.model.GoodsPromotionResult;
import cn.bfay.jd.model.JdGoods;
import cn.bfay.jd.model.JdGoodsBaseInfo;
import cn.bfay.jd.model.JdGoodsCoupon;
import cn.bfay.jd.model.JdGoodsPrice;
import cn.bfay.jd.model.JdGoodsPromotion;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 京东抓取类.
 *
 * @author wangjiannan
 */
public class JdCrawler {
    private static final Logger log = LoggerFactory.getLogger(JdCrawler.class);

    private static final String HTML_URL = "https://item.m.jd.com/product/%s.html";
    private static final String BASE_INFO_URL = "https://yx.3.cn/service/info.action?ids=%s";
    private static final String PRICE_URL = "https://pe.3.cn/prices/mgets?skuids=%s";
    private static final String PROMOTION_URL = "https://wq.jd.com/commodity/promo/get?skuid=%s";
    private static final String COUPON_URL = "https://wq.jd.com/mjgj/fans/queryusegetcoupon?platform=%s&cid=%s&sku=%s&popId=%s";


    /**
     * 抓取商品html.
     *
     * @param skuid 商品id
     * @return {@link JdGoods}
     */
    public JdGoods crawlerGoodsHtml(String skuid) {
        try {
            JdGoodsPrice jdGoodsPrice = processGoodsPrice(skuid);
            if (jdGoodsPrice == null || jdGoodsPrice.getNormalPrice() == null || jdGoodsPrice.getNormalPrice() == -1) {
                return null;
            }
            //
            Document document = Jsoup.connect(String.format(HTML_URL, skuid)).get();
            Elements elements = document.select("script");
            String windowItemOnly = "";
            for (Element ele : elements) {
                if (ele.data().contains("window._itemOnly")) {
                    windowItemOnly = ele.data();
                    break;
                }
            }
            windowItemOnly = StringUtils.trimAllWhitespace(windowItemOnly);
            int start = windowItemOnly.indexOf("{");
            int end = windowItemOnly.indexOf(";");
            windowItemOnly = windowItemOnly.substring(start, end - 1);

            String item = JSON.parseObject(windowItemOnly).getString("item");
            JSONObject jsonObject = JSON.parseObject(item);
            String skuId = jsonObject.getString("skuId");
            if (!skuid.equals(skuId)) {
                return null;
            }
            String skuName = jsonObject.getString("skuName");
            if (StringUtils.isEmpty(skuName)) {
                return null;
            }
            JSONArray jsonArray = JSON.parseArray(jsonObject.getString("category"));
            String category = jsonArray.get(2).toString();

            return new JdGoods(skuid, skuName, category);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    /**
     * 获取商品基本信息.
     *
     * @param skuid 商品id
     * @return {@link JdGoodsBaseInfo}
     */
    public JdGoodsBaseInfo processGoodsBaseInfo(String skuid) {
        try {
            String goodsBaseInfoResponse = OkHttpUtils.executeGet(String.format(BASE_INFO_URL, skuid), String.class);
            if (goodsBaseInfoResponse == null) {
                return null;
            }
            String infoContent = StringUtils.trimAllWhitespace(goodsBaseInfoResponse);
            if (StringUtils.isEmpty(infoContent)) {
                return null;
            }
            // {"5837306":{"spec":"","color":"","imagePath":"jfs/t25054/72/763917350/155194/f36307c6/5b7a888cN6a8bb4b5.jpg","name":"蒙牛风味发酵乳欧式炭烧焦香原味1kg","size":""}}
            infoContent = infoContent.substring(infoContent.indexOf(":") + 1, infoContent.length() - 1);
            // {"spec":"","color":"","imagePath":"jfs/t25054/72/763917350/155194/f36307c6/5b7a888cN6a8bb4b5.jpg","name":"蒙牛风味发酵乳欧式炭烧焦香原味1kg","size":""}
            return JSON.parseObject(infoContent, JdGoodsBaseInfo.class);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }


    /**
     * 获取商品价格.
     *
     * @param skuid 商品id
     * @return {@link JdGoodsPrice}
     */
    public JdGoodsPrice processGoodsPrice(String skuid) {
        try {
            String goodsPriceResponse = OkHttpUtils.executeGet(String.format(PRICE_URL, skuid), String.class);
            if (goodsPriceResponse == null) {
                return null;
            }
            String priceContent = StringUtils.trimAllWhitespace(goodsPriceResponse);
            if (StringUtils.isEmpty(priceContent)) {
                return null;
            }
            // [{"op":"179.00","m":"179.00","id":"J_17757120747","p":"79.00"}]
            priceContent = priceContent.replace("\"id\"", "\"J_id\"");
            // [{"op":"179.00","m":"179.00","J_id":"J_17757120747","p":"79.00"}]
            List<JdGoodsPrice> jdGoodsPrices = JSON.parseArray(priceContent, JdGoodsPrice.class);
            if (!CollectionUtils.isEmpty(jdGoodsPrices)) {
                return jdGoodsPrices.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    /**
     * 获取商品促销list.
     *
     * @param skuid 商品id
     * @return {@link JdGoodsPromotion}
     */
    public List<JdGoodsPromotion> processGoodsPromotion(String skuid) {
        try {
            String goodsPromotionResponse = OkHttpUtils.executeGet(String.format(PROMOTION_URL, skuid), String.class);
            if (goodsPromotionResponse == null) {
                return null;
            }
            String promotionContent = StringUtils.trimAllWhitespace(goodsPromotionResponse);
            if (StringUtils.isEmpty(promotionContent)) {
                return null;
            }
            // callback({"errcode":"0","errmsg":"","data":[{"id":"2384709","pis":[{"d":"1539705599","subextinfo":"{\"extType\":15,\"subExtType\":23,\"subRuleList\":[{\"needNum\":\"2\",\"rebate\":\"8\",\"subRuleList\":[]},{\"needNum\":\"3\",\"rebate\":\"7\",\"subRuleList\":[]}]}","19":"满2件，总价打8折；满3件，总价打7折","adurl":"http://sale.jd.com/act/BpSkJQaq657MCIiF.html","pid":"237107540_10","st":"1539014400","customtag":"{}","ori":"1"},{"d":"1541001599","subextinfo":"{\"extType\":2,\"needMoney\":\"129\",\"rewardMoney\":\"10\",\"subExtType\":9,\"subRuleList\":[]}","adurl":"https://mall.jd.com/index-1000100622.html","15":"每满129元，可减10元现金","pid":"236319400_10","st":"1538323200","customtag":"{}","ori":"1"}]}]})
            promotionContent = promotionContent.substring(promotionContent.indexOf("(") + 1, promotionContent.length() - 1);
            // {"errcode":"0","errmsg":"","data":[{"pis":[{"15":"每满129元，可减10元现金","d":"1539673572","ori":"1","st":"1538323200","adurl":"https://mall.jd.com/index-1000100622.html","pid":"236319400_10","subextinfo":"{\"extType\":2,\"needMoney\":\"129\",\"rewardMoney\":\"10\",\"subExtType\":9,\"subRuleList\":[]}","customtag":"{}"},{"19":"满2件，总价打8折；满3件，总价打7折","d":"1539673553","ori":"1","st":"1539014400","adurl":"http://sale.jd.com/act/BpSkJQaq657MCIiF.html","pid":"237107540_10","subextinfo":"{\"extType\":15,\"subExtType\":23,\"subRuleList\":[{\"needNum\":\"2\",\"rebate\":\"8\",\"subRuleList\":[]},{\"needNum\":\"3\",\"rebate\":\"7\",\"subRuleList\":[]}]}","customtag":"{}"}],"id":"2384709"}]}
            GoodsPromotionResult goodsPromotionResult = JSON.parseObject(promotionContent, GoodsPromotionResult.class);
            if (!"0".equals(goodsPromotionResult.getErrcode())) {
                return null;
            }
            List<GoodsPromotionResult.PromotionInfo> pis = goodsPromotionResult.getData().get(0).getPis();
            if (CollectionUtils.isEmpty(pis)) {
                return null;
            }
            List<JdGoodsPromotion> jdGoodsPromotions = new ArrayList<>();
            pis.forEach(pi -> {
                GoodsPromotionResult.Subextinfo si = JSON.parseObject(pi.getSubextinfo(), GoodsPromotionResult.Subextinfo.class);
                if (si != null) {
                    if ((si.getExtType() == 2 && si.getSubExtType() == 8) || (si.getExtType() == 2 && si.getSubExtType() == 9)) {
                        // 每满减 每满减 2、8 或2、9   needMoney=199, rewardMoney=100
                        JdGoodsPromotion gp = new JdGoodsPromotion();
                        gp.setPromotionContent(pis.toString());
                        gp.setNeedMoney(Double.valueOf(si.getNeedMoney()));
                        gp.setRewardMoney(Double.valueOf(si.getRewardMoney()));
                        gp.setPromotion((gp.getNeedMoney() - gp.getRewardMoney()) / gp.getNeedMoney());
                        jdGoodsPromotions.add(gp);
                    } else if ((si.getExtType() == 1 && si.getSubExtType() == 1) || (si.getExtType() == 6 && si.getSubExtType() == 14)) {
                        // 满减  1、1或6/14     subRuleList=[{"needMoney":"99","subRuleList":[],"subRuleType":1,"rewardMoney":"20"}]
                        si.getSubRuleList().forEach(e -> {
                            JdGoodsPromotion gp = new JdGoodsPromotion();
                            gp.setNeedMoney(Double.valueOf(e.getNeedMoney()));
                            gp.setRewardMoney(Double.valueOf(e.getRewardMoney()));
                            gp.setPromotion((gp.getNeedMoney() - gp.getRewardMoney()) / gp.getNeedMoney());
                            gp.setPromotionContent(pis.toString());
                            jdGoodsPromotions.add(gp);
                        });
                    } else if (si.getExtType() == 15 && si.getSubExtType() == 23) {
                        // 折扣  15、23
                        si.getSubRuleList().forEach(e -> {
                            JdGoodsPromotion gp = new JdGoodsPromotion();
                            gp.setPromotionContent(pis.toString());
                            gp.setRebate(Double.valueOf(e.getRebate()));
                            gp.setPromotion(gp.getRebate() / 10.0);
                            jdGoodsPromotions.add(gp);
                        });
                    }
                }
            });
            return jdGoodsPromotions;
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }


    /**
     * 获取商品优惠list.
     *
     * @param skuid 商品id
     * @return {@link JdGoodsCoupon}
     */
    public List<JdGoodsCoupon> processGoodsCoupon(String skuid) {
        return processGoodsCoupon("3", "111", skuid, "8888");
    }

    /**
     * 获取商品优惠list.
     *
     * @param platform 平台
     * @param cid      类别id
     * @param skuid    商品id
     * @param popId    popId
     * @return {@link JdGoodsCoupon}
     */
    public List<JdGoodsCoupon> processGoodsCoupon(String platform, String cid, String skuid, String popId) {
        try {
            String goodsCouponResponse = OkHttpUtils.executeGet(String.format(COUPON_URL, platform, cid, skuid, popId), String.class);
            if (goodsCouponResponse == null) {
                return null;
            }
            String couponContent = StringUtils.trimAllWhitespace(goodsCouponResponse);
            if (StringUtils.isEmpty(couponContent)) {
                return null;
            }
            // {"ret":0,"msg":"success","coupons":[{"name":"仅可购买生鲜部分商品","key":"6cc3d387ee2440c6bc76671210c0d379","timeDesc":"有效期2018-10-12至2018-10-12","hourcoupon":1,"couponType":1,"quota":159,"roleId":14614495,"discount":50,"couponstyle":0,"discountdesc":{}},{"name":"仅可购买生鲜部分商品","key":"fba9f5b3c3614c3199fe8a4b069f2427","timeDesc":"有效期2018-10-12至2018-10-12","hourcoupon":1,"couponType":1,"quota":259,"roleId":14614496,"discount":100,"couponstyle":0,"discountdesc":{}},{"name":"仅可购买生鲜自营肉禽冷冻部分商品","key":"17f088bf56294be3938359ae9033f55b","timeDesc":"有效期2018-10-01至2018-10-15","hourcoupon":1,"couponType":1,"quota":198,"roleId":14640168,"discount":40,"couponstyle":0,"discountdesc":{}}],"use_coupons":[],"sku_info":{"sku":"4155087","useJing":"1","useDong":"1","global":"0","jdPrice":"0","limitCouponType":[],"limitCouponDesc":""}}
            JSONObject couponContentJsonObject = JSON.parseObject(couponContent);
            if (couponContentJsonObject.getInteger("ret") != 0) {
                return null;
            }
            couponContent = couponContentJsonObject.getString("coupons");
            // [{"couponType":1,"roleId":14614495,"quota":159,"name":"仅可购买生鲜部分商品","timeDesc":"有效期2018-10-12至2018-10-12","discount":50,"discountdesc":{},"couponstyle":0,"key":"6cc3d387ee2440c6bc76671210c0d379","hourcoupon":1},{"couponType":1,"roleId":14614496,"quota":259,"name":"仅可购买生鲜部分商品","timeDesc":"有效期2018-10-12至2018-10-12","discount":100,"discountdesc":{},"couponstyle":0,"key":"fba9f5b3c3614c3199fe8a4b069f2427","hourcoupon":1},{"couponType":1,"roleId":14640168,"quota":198,"name":"仅可购买生鲜自营肉禽冷冻部分商品","timeDesc":"有效期2018-10-01至2018-10-15","discount":40,"discountdesc":{},"couponstyle":0,"key":"17f088bf56294be3938359ae9033f55b","hourcoupon":1}]
            return JSON.parseArray(couponContent, JdGoodsCoupon.class);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }
}
