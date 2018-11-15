package test;

import cn.wangjiannan.jd.crawler.Crawler;
import org.junit.Test;

import java.io.IOException;

/**
 * CrawlerTest.
 *
 * @author wangjiannan
 */
public class CrawlerTest {
    @Test
    public void test() throws IOException {
        Crawler crawler = new Crawler();
        System.out.println(crawler.processGoodsBaseInfo("5067550"));
        System.out.println(crawler.processGoodsPrice("5067550"));
        System.out.println(crawler.processGoodsPromotion("5067550"));
        System.out.println(crawler.processGoodsCoupon("5067550"));
    }

    @Test
    public void testProcessGoodsBaseInfo() {
        Crawler crawler = new Crawler();
        crawler.processGoodsBaseInfo("5067550");
    }

    //@Test
    //public void test11() {
    //    OkHttpClient okHttpClient = new OkHttpClient(); // 创建OkHttpClient对象
    //    Request request = new Request.Builder().url(String.format("https://yx.3.cn/service/info.action?ids=%s", "5067550")).build(); // 创建一个请求
    //    okHttpClient.newCall(request).enqueue(new Callback() { // 回调
    //
    //        public void onResponse(Call call, Response response) throws IOException {
    //            // 请求成功调用，该回调在子线程
    //            System.out.println("----------"+response.body().string());
    //        }
    //
    //        public void onFailure(Call call, IOException e) {
    //            // 请求失败调用
    //            System.out.println(e.getMessage());
    //        }
    //    });
    //}
}
