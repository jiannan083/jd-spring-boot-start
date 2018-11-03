package cn.wangjiannan.jd.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class Goods {
    @JSONField(serialize = false)
    private Long id;

    private String skuid;

    private String name;

    @JSONField(serialize = false)
    private String platform;

    @JSONField(serialize = false)
    private String cid;

    @JSONField(serialize = false)
    private String popId;

    @JSONField(serialize = false)
    private Date createTime;

    @JSONField(serialize = false)
    private Date updateTime;
}