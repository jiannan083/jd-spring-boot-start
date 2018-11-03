package cn.wangjiannan.jd.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Goods {

    private String skuid;

    private String name;

    private String cid;
}