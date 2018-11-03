package cn.wangjiannan.jd.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JdProperties.
 *
 * @author wangjiannan
 */
@Data
@ConfigurationProperties(prefix = JdProperties.JD_PREFIX)
public class JdProperties {
    public static final String JD_PREFIX = "jd";

    //private String appid;
    //private String secret;
    //private String token;

}
