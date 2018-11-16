package cn.bfay.jd.autoconfigure;

import cn.bfay.jd.crawler.JdCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 自动化配置.
 *
 * @author wangjiannan
 */
@Configuration //开启配置
@EnableConfigurationProperties(JdProperties.class) //开启使用映射实体对象
@ConditionalOnProperty // 存在对应配置信息时初始化该配置类
        (
                prefix = "jd", //存在配置前缀hello
                value = "enabled", //开启
                matchIfMissing = true //缺失检查
        )
public class JdAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(JdAutoConfiguration.class);
    private final JdProperties properties;

    public JdAutoConfiguration(JdProperties properties) {
        this.properties = properties;
    }

    // 构造函数之后执行
    @PostConstruct
    public void checkConfig() {
        //if (!StringUtils.hasText(properties.getAppid())) {
        //    Assert.state(false, "Cannot find wechat config:appid");
        //}
        //if (!StringUtils.hasText(properties.getSecret())) {
        //    Assert.state(false, "Cannot find wechat config:secret");
        //}
        //if (!StringUtils.hasText(properties.getToken())) {
        //    Assert.state(false, "Cannot find wechat config:token");
        //}
    }


    @Bean
    @ConditionalOnMissingBean//缺失时，初始化bean并添加到SpringIoc
    public JdCrawler jdCrawler() {
        log.info(">>>The JdCrawler Not Found，Execute Create New Bean.");
        //JdCrawler crawler = new JdCrawler();
        return new JdCrawler();
    }

}
