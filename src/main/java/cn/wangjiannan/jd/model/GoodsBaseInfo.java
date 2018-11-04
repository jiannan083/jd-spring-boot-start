package cn.wangjiannan.jd.model;

import lombok.Data;

@Data
public class GoodsBaseInfo {
    private Long goodsInfoId;

    private String skuid;

    private String name;

    private String size;

    private String color;

    private String imagePath;

    private String spec;

}