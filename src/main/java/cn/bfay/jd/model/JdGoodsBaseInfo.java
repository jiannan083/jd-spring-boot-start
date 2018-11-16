package cn.bfay.jd.model;

/**
 * 商品基本信息.
 *
 * @author wangjiannan
 */
public class JdGoodsBaseInfo {
    /**
     * 商品id.
     */
    private String skuid;
    /**
     * 商品名称.
     */
    private String name;
    /**
     * 商品规格.
     */
    private String size;
    /**
     * 商品颜色.
     */
    private String color;
    /**
     * 图片路径.
     */
    private String imagePath;
    /**
     * 描述.
     */
    private String spec;

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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    @Override
    public String toString() {
        return "JdGoodsBaseInfo{" +
                "skuid='" + skuid + '\'' +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", spec='" + spec + '\'' +
                '}';
    }
}