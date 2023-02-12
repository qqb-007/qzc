package com.sankuai.meituan.waimai.opensdk.vo;

/**
 * Created by zhangzhidong on 15/10/28.
 */
public class PoiTagParam {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public PoiTagParam setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PoiTagParam setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "PoiTagParam [" +
                "id=" + id +
                ", name='" + name + '\'' +
                ']';
    }
}
