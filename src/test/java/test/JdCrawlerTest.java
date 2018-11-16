package test;

import cn.bfay.jd.crawler.JdCrawler;
import org.junit.Test;

/**
 * JdCrawlerTest.
 *
 * @author wangjiannan
 */
public class JdCrawlerTest {
    @Test
    public void test() {
        JdCrawler jdCrawler = new JdCrawler();
        System.out.println("--------" + jdCrawler.processGoodsBaseInfo("5067550"));
        System.out.println("--------" + jdCrawler.processGoodsPrice("5067550"));
        System.out.println("--------" + jdCrawler.processGoodsPromotion("5067550"));
        System.out.println("--------" + jdCrawler.processGoodsCoupon("5067550"));
    }
}
