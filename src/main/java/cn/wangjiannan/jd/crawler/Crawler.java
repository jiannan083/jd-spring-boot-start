package cn.wangjiannan.jd.crawler;

import cn.wangjiannan.jd.model.Goods;
import cn.wangjiannan.jd.model.GoodsBaseInfo;
import cn.wangjiannan.jd.model.GoodsCoupon;
import cn.wangjiannan.jd.model.GoodsPrice;
import cn.wangjiannan.jd.model.GoodsPromotion;
import cn.wangjiannan.jd.model.GoodsPromotionResult;
import cn.wangjiannan.util.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 京东抓取类.
 *
 * @author wangjiannan
 */
@Slf4j
public class Crawler {
    private static final String HTML_URL = "https://item.m.jd.com/product/%s.html";
    private static final String BASE_INFO_URL = "https://yx.3.cn/service/info.action?ids=%s";
    private static final String PRICE_URL = "https://pe.3.cn/prices/mgets?skuids=%s";
    private static final String PROMOTION_URL = "https://wq.jd.com/commodity/promo/get?skuid=%s";
    private static final String COUPON_URL = "https://wq.jd.com/mjgj/fans/queryusegetcoupon?platform=%s&cid=%s&sku=%s&popId=%s";


    /**
     * 抓取商品html.
     *
     * @param skuid 商品id
     * @return {@link Goods}
     */
    public Goods crawlerGoodsHtml(String skuid) {
        String htmlPage;
        try {
            htmlPage = Jsoup.connect(String.format(HTML_URL, skuid)).get().toString();
        } catch (IOException e) {
            log.error("解析xml失败", e);
            throw new RuntimeException("解析xml失败");
        }
        Document document = Jsoup.parse(htmlPage);
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
            throw new RuntimeException("!skuid.equals(skuId)");
        }
        String skuName = jsonObject.getString("skuName");
        if (StringUtils.isEmpty(skuName)) {
            throw new RuntimeException("skuName is empty");
        }
        JSONArray jsonArray = JSON.parseArray(jsonObject.getString("category"));
        String category = jsonArray.get(2).toString();

        return Goods.builder().skuid(skuId).name(skuName).cid(category).build();

        //Map<String, String> goodsMap = new HashMap<>();
        //goodsMap.put("skuid", skuId);
        //goodsMap.put("name", skuName);
        //goodsMap.put("cid", category);
        //return goodsMap;
    }

    //@Override
    //public Map<String, String> crawlGoods(String skuid) {
    //    try {
    //        String htmlPage = Jsoup.connect(String.format(URL, skuid)).get().toString();
    //        //System.out.println("---" + htmlPage);
    //
    //        Document doc = Jsoup.parse(htmlPage);
    //
    //        //System.out.println(doc);
    //        String name = doc.select("div[id=itemName]").text();
    //        System.out.println("---" + name);
    //
    //        // sku
    //        String sku = "";
    //        Elements elements = doc.select("a[report-pageparam]");
    //        for (Element ele : elements) {
    //            if (ele.hasAttr("report-pageparam")) {
    //                sku = ele.attr("report-pageparam");
    //                break;
    //            }
    //        }
    //        System.out.println("---" + sku);
    //
    //        Elements elements1 = doc.select("script");
    //        String sss = "";
    //        for (Element ele : elements1) {
    //            //System.out.println(ele.data());
    //            if (ele.data().contains("window._itemOnly")) {
    //                sss = ele.data();
    //                break;
    //            }
    //        }
    //        sss = StringUtils.trimAllWhitespace(sss);
    //        int start = sss.indexOf("{");
    //        int end = sss.indexOf(";");
    //        sss = sss.substring(start, end - 1);
    //        //System.out.println(start + "--" + end);
    //        System.out.println("sss---" + sss);
    //        //String cat = doc.select("a[clstag=shangpin|keycount|product|mbNav-1]").text();
    //        //
    //        //System.out.println("---" + cat);
    //        sss = JSON.parseObject(sss).getString("item");
    //        JSONObject jsonObject = JSON.parseObject(sss);
    //        System.out.println(jsonObject.getString("skuId"));
    //        System.out.println(jsonObject.getString("skuName"));
    //        JSONArray jsonArray = JSON.parseArray(jsonObject.getString("category"));
    //        System.out.println(jsonArray);
    //        System.out.println(jsonArray.get(2));
    //        //System.out.println(jsonObject.getString("unLimit_cid"));
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //    }
    //}

    /**
     * 获取商品基本信息.
     *
     * @param skuid 商品id
     * @return {@link GoodsBaseInfo}
     */
    public GoodsBaseInfo processGoodsBaseInfo(String skuid) {
        String infoContent = StringUtils.trimAllWhitespace(HttpUtils.getContent(String.format(BASE_INFO_URL, skuid)));
        log.debug("详情爬取返回={}", infoContent);
        // {"5837306":{"spec":"","color":"","imagePath":"jfs/t25054/72/763917350/155194/f36307c6/5b7a888cN6a8bb4b5.jpg","name":"蒙牛风味发酵乳欧式炭烧焦香原味1kg","size":""}}
        infoContent = infoContent.substring(infoContent.indexOf(":") + 1, infoContent.length() - 1);
        log.info("详情爬取返回={}", infoContent);
        // {"spec":"","color":"","imagePath":"jfs/t25054/72/763917350/155194/f36307c6/5b7a888cN6a8bb4b5.jpg","name":"蒙牛风味发酵乳欧式炭烧焦香原味1kg","size":""}
        return JSON.parseObject(infoContent, GoodsBaseInfo.class);
    }


    /**
     * 获取商品价格.
     *
     * @param skuid 商品id
     * @return {@link GoodsPrice}
     */
    public GoodsPrice processGoodsPrice(String skuid) {
        String priceContent = StringUtils.trimAllWhitespace(HttpUtils.getContent(String.format(PRICE_URL, skuid)));
        log.debug("价格爬取返回={}", priceContent);
        // [{"op":"179.00","m":"179.00","id":"J_17757120747","p":"79.00"}]
        //priceContent=priceContent.replaceAll("id", "J_id");
        priceContent = priceContent.replace("\"id\"", "\"J_id\"");
        log.info("价格爬取返回={}", priceContent);
        // [{"op":"179.00","m":"179.00","J_id":"J_17757120747","p":"79.00"}]
        List<GoodsPrice> goodsPrices = JSON.parseArray(priceContent, GoodsPrice.class);
        if (!CollectionUtils.isEmpty(goodsPrices)) {
            return goodsPrices.get(0);
        } else {
            return null;
        }
    }

    /**
     * 获取商品促销list.
     *
     * @param skuid 商品id
     * @return {@link GoodsPromotion}
     */
    public List<GoodsPromotion> processGoodsPromotion(String skuid) {
        String promotionContent = StringUtils.trimAllWhitespace(HttpUtils.getContent(String.format(PROMOTION_URL, skuid)));
        log.debug("促销爬取返回={}", promotionContent);
        // callback({"errcode":"0","errmsg":"","data":[{"id":"2384709","pis":[{"d":"1539705599","subextinfo":"{\"extType\":15,\"subExtType\":23,\"subRuleList\":[{\"needNum\":\"2\",\"rebate\":\"8\",\"subRuleList\":[]},{\"needNum\":\"3\",\"rebate\":\"7\",\"subRuleList\":[]}]}","19":"满2件，总价打8折；满3件，总价打7折","adurl":"http://sale.jd.com/act/BpSkJQaq657MCIiF.html","pid":"237107540_10","st":"1539014400","customtag":"{}","ori":"1"},{"d":"1541001599","subextinfo":"{\"extType\":2,\"needMoney\":\"129\",\"rewardMoney\":\"10\",\"subExtType\":9,\"subRuleList\":[]}","adurl":"https://mall.jd.com/index-1000100622.html","15":"每满129元，可减10元现金","pid":"236319400_10","st":"1538323200","customtag":"{}","ori":"1"}]}]})
        promotionContent = promotionContent.substring(promotionContent.indexOf("(") + 1, promotionContent.length() - 1);
        log.debug("促销爬取返回={}", promotionContent);
        // {"errcode":"0","errmsg":"","data":[{"pis":[{"15":"每满129元，可减10元现金","d":"1539673572","ori":"1","st":"1538323200","adurl":"https://mall.jd.com/index-1000100622.html","pid":"236319400_10","subextinfo":"{\"extType\":2,\"needMoney\":\"129\",\"rewardMoney\":\"10\",\"subExtType\":9,\"subRuleList\":[]}","customtag":"{}"},{"19":"满2件，总价打8折；满3件，总价打7折","d":"1539673553","ori":"1","st":"1539014400","adurl":"http://sale.jd.com/act/BpSkJQaq657MCIiF.html","pid":"237107540_10","subextinfo":"{\"extType\":15,\"subExtType\":23,\"subRuleList\":[{\"needNum\":\"2\",\"rebate\":\"8\",\"subRuleList\":[]},{\"needNum\":\"3\",\"rebate\":\"7\",\"subRuleList\":[]}]}","customtag":"{}"}],"id":"2384709"}]}
        GoodsPromotionResult goodsPromotionResult = JSON.parseObject(promotionContent, GoodsPromotionResult.class);
        log.debug("促销:goodsPromotionResult={}", goodsPromotionResult);
        if (!"0".equals(goodsPromotionResult.getErrcode())) {
            return null;
        }
        List<GoodsPromotionResult.PromotionInfo> pis = goodsPromotionResult.getData().get(0).getPis();
        //List<GoodsPromotionResult.Subextinfo> subextinfos = new ArrayList<>();
        List<GoodsPromotion> goodsPromotions = new ArrayList<>();
        if (CollectionUtils.isEmpty(pis)) {
            return null;
        }
        pis.forEach(pi -> {
            GoodsPromotionResult.Subextinfo si = JSON.parseObject(pi.getSubextinfo(), GoodsPromotionResult.Subextinfo.class);
            log.debug("促销:subextinfo={}", si);
            if (si != null) {
                //subextinfos.add(subextinfo);
                if ((si.getExtType() == 2 && si.getSubExtType() == 8) || (si.getExtType() == 2 && si.getSubExtType() == 9)) {
                    // 每满减 每满减 2、8 或2、9   needMoney=199, rewardMoney=100
                    GoodsPromotion gp = new GoodsPromotion();
                    gp.setPromotionContent(pis.toString());
                    gp.setNeedMoney(Double.valueOf(si.getNeedMoney()));
                    gp.setRewardMoney(Double.valueOf(si.getRewardMoney()));
                    gp.setPromotion((gp.getNeedMoney() - gp.getRewardMoney()) / gp.getNeedMoney());
                    goodsPromotions.add(gp);
                } else if ((si.getExtType() == 1 && si.getSubExtType() == 1) || (si.getExtType() == 6 && si.getSubExtType() == 14)) {
                    // 满减  1、1或6/14     subRuleList=[{"needMoney":"99","subRuleList":[],"subRuleType":1,"rewardMoney":"20"}]
                    si.getSubRuleList().forEach(e -> {
                        GoodsPromotion gp = new GoodsPromotion();
                        gp.setNeedMoney(Double.valueOf(e.getNeedMoney()));
                        gp.setRewardMoney(Double.valueOf(e.getRewardMoney()));
                        gp.setPromotion((gp.getNeedMoney() - gp.getRewardMoney()) / gp.getNeedMoney());
                        gp.setPromotionContent(pis.toString());
                        goodsPromotions.add(gp);
                    });
                } else if (si.getExtType() == 15 && si.getSubExtType() == 23) {
                    // 折扣  15、23
                    si.getSubRuleList().forEach(e -> {
                        GoodsPromotion gp = new GoodsPromotion();
                        gp.setPromotionContent(pis.toString());
                        gp.setRebate(Double.valueOf(e.getRebate()));
                        gp.setPromotion(gp.getRebate() / 10.0);
                        goodsPromotions.add(gp);
                    });
                } else {
                    log.info("无满足条件的促销");
                }
            }
        });
        return goodsPromotions;
    }


    /**
     * 获取商品优惠list.
     *
     * @param skuid 商品id
     * @return {@link GoodsCoupon}
     */
    public List<GoodsCoupon> processGoodsCoupon(String skuid) {
        return processGoodsCoupon("3", "111", skuid, "8888");
    }

    /**
     * 获取商品优惠list.
     *
     * @param platform 平台
     * @param cid      类别id
     * @param skuid    商品id
     * @param popId    popId
     * @return {@link GoodsCoupon}
     */
    public List<GoodsCoupon> processGoodsCoupon(String platform, String cid, String skuid, String popId) {
        String couponContent = StringUtils.trimAllWhitespace(HttpUtils.getContent(String.format(COUPON_URL, platform, cid, skuid, popId)));
        log.debug("优惠券爬取返回={}", couponContent);
        // {"ret":0,"msg":"success","coupons":[{"name":"仅可购买生鲜部分商品","key":"6cc3d387ee2440c6bc76671210c0d379","timeDesc":"有效期2018-10-12至2018-10-12","hourcoupon":1,"couponType":1,"quota":159,"roleId":14614495,"discount":50,"couponstyle":0,"discountdesc":{}},{"name":"仅可购买生鲜部分商品","key":"fba9f5b3c3614c3199fe8a4b069f2427","timeDesc":"有效期2018-10-12至2018-10-12","hourcoupon":1,"couponType":1,"quota":259,"roleId":14614496,"discount":100,"couponstyle":0,"discountdesc":{}},{"name":"仅可购买生鲜自营肉禽冷冻部分商品","key":"17f088bf56294be3938359ae9033f55b","timeDesc":"有效期2018-10-01至2018-10-15","hourcoupon":1,"couponType":1,"quota":198,"roleId":14640168,"discount":40,"couponstyle":0,"discountdesc":{}}],"use_coupons":[],"sku_info":{"sku":"4155087","useJing":"1","useDong":"1","global":"0","jdPrice":"0","limitCouponType":[],"limitCouponDesc":""}}
        JSONObject couponContentJsonObject = JSON.parseObject(couponContent);
        couponContent = couponContentJsonObject.getString("coupons");
        log.info("优惠券爬取返回={}", couponContent);
        // [{"couponType":1,"roleId":14614495,"quota":159,"name":"仅可购买生鲜部分商品","timeDesc":"有效期2018-10-12至2018-10-12","discount":50,"discountdesc":{},"couponstyle":0,"key":"6cc3d387ee2440c6bc76671210c0d379","hourcoupon":1},{"couponType":1,"roleId":14614496,"quota":259,"name":"仅可购买生鲜部分商品","timeDesc":"有效期2018-10-12至2018-10-12","discount":100,"discountdesc":{},"couponstyle":0,"key":"fba9f5b3c3614c3199fe8a4b069f2427","hourcoupon":1},{"couponType":1,"roleId":14640168,"quota":198,"name":"仅可购买生鲜自营肉禽冷冻部分商品","timeDesc":"有效期2018-10-01至2018-10-15","discount":40,"discountdesc":{},"couponstyle":0,"key":"17f088bf56294be3938359ae9033f55b","hourcoupon":1}]
        return JSON.parseArray(couponContent, GoodsCoupon.class);
    }
}
