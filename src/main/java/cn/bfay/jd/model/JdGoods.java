package cn.bfay.jd.model;

/**
 * 商品实体.
 *
 * @author wangjiannan
 */
public class JdGoods {
    /**
     * 商品id.
     */
    private String skuid;
    /**
     * 商品名称.
     */
    private String name;
    /**
     * 类别id.
     */
    private String cid;

    public JdGoods(String skuid, String name, String cid) {
        this.skuid = skuid;
        this.name = name;
        this.cid = cid;
    }

    public String getSkuid() {
        return skuid;
    }

    public void setSkuid(String skuid) {
        this.skuid = skuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "JdGoods{" +
                "skuid='" + skuid + '\'' +
                ", name='" + name + '\'' +
                ", cid='" + cid + '\'' +
                '}';
    }
}