package cn.wangjiannan.jd.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class GoodsBaseInfo {
    private Long id;

    private Long goodsInfoId;

    private String skuid;

    private String name;

    private String size;

    private String color;

    private String imagePath;

    private String spec;

    private Date createTime;

    private Date updateTime;

}