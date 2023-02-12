package info.batcloud.wxc.core.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class FoodCategory implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Integer idx;

    @NotNull
    private String name;

    @NotNull
    private Integer level;

    private Long parentId;

    private Integer wanteId;//自平台对应分类的id

    private Long jddjId;

    private Long jddjzId;

    private Long meituanTagId;
    private Long jingdongTagId;
    private String meituanTagName;
    private String jingdongTagName;

    public Long getJddjzId() {
        return jddjzId;
    }

    public void setJddjzId(Long jddjzId) {
        this.jddjzId = jddjzId;
    }

    public Long getJingdongTagId() {
        return jingdongTagId;
    }

    public void setJingdongTagId(Long jingdongTagId) {
        this.jingdongTagId = jingdongTagId;
    }

    public String getJingdongTagName() {
        return jingdongTagName;
    }

    public void setJingdongTagName(String jingdongTagName) {
        this.jingdongTagName = jingdongTagName;
    }

    public Long getMeituanTagId() {
        return meituanTagId;
    }

    public void setMeituanTagId(Long meituanTagId) {
        this.meituanTagId = meituanTagId;
    }

    public String getMeituanTagName() {
        return meituanTagName;
    }

    public void setMeituanTagName(String meituanTagName) {
        this.meituanTagName = meituanTagName;
    }

    public Long getJddjId() {
        return jddjId;
    }

    public void setJddjId(Long jddjId) {
        this.jddjId = jddjId;
    }

    public Integer getWanteId() {
        return wanteId;
    }

    public void setWanteId(Integer wanteId) {
        this.wanteId = wanteId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
