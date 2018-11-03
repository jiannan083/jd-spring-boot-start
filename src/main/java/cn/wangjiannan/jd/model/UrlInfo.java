package cn.wangjiannan.jd.model;

import lombok.Data;

import java.util.Date;

@Data
public class UrlInfo {
    private Long id;

    private String urlKey;

    private String urlContent;

    private Date createTime;

    private Date updateTime;

}