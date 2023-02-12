package com.sankuai.meituan.waimai.opensdk.vo;

import java.util.List;

public class RetailCatParam {
    private String name;
    private String sequence;
    private List<RetailCatParam> children;

    public String getName() {
        return name;
    }

    public RetailCatParam setName(String name) {
        this.name = name;
        return this;
    }

    public String getSequence() {
        return sequence;
    }

    public RetailCatParam setSequence(String sequence) {
        this.sequence = sequence;
        return this;
    }

    public List<RetailCatParam> getChildren() {
        return children;
    }

    public RetailCatParam setChildren(List<RetailCatParam> children) {
        this.children = children;
        return this;
    }

    @Override
    public String toString() {
        return "RetailCatParam [" +
                "name='" + name + '\'' +
                ", sequence='" + sequence + '\'' +
                ", children=" + children +
                ']';
    }
}
