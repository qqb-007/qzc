package info.batcloud.wxc.core.entity;

import info.batcloud.wxc.core.enums.RegionStatus;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Cacheable
public class Region implements Serializable {

    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_DISTRICT = 2;

    @Id
    private Long id;
    private String name;
    private Long parentId;
    @Enumerated(EnumType.STRING)
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
