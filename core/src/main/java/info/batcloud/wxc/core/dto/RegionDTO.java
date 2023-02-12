package info.batcloud.wxc.core.dto;

import info.batcloud.wxc.core.enums.RegionStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.io.Serializable;

public class RegionDTO implements Serializable {

    private Long id;
    private String name;
    private Long parentId;
    private RegionStatus status;

    private int level;

    private String pinyin;

    private String shortPinyin;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getShortPinyin() {
        return shortPinyin;
    }

    public void setShortPinyin(String shortPinyin) {
        this.shortPinyin = shortPinyin;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public RegionStatus getStatus() {
        return status;
    }

    public void setStatus(RegionStatus status) {
        this.status = status;
    }
}
