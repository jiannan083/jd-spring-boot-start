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
        //Goods goods= crawler.crawlerGoodsHtml("5067550");
        //System.out.println(goods.toString());

        //String result = Jsoup.connect(String.format("https://yx.3.cn/service/info.action?ids=%s", "5067550")).get().toString();
        //        //System.out.println(result);
        crawler.processGoodsBaseInfo("5067550");
    }

    @Test
    public void testProcessGoodsBaseInfo() {
        Crawler crawler = new Crawler();
        crawler.processGoodsBaseInfo("5067550");
    }
}
