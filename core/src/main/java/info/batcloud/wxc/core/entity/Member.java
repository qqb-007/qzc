package info.batcloud.wxc.core.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String poiMemberId;

    private String mobile;

    private String cardCode;

    private String idCard;

    private int gender;

    private String name;

    private String birthday;

    private int registerTime;

    private String totalScore;

    private String levelCode;

    private String registerPoiCode;

    private String registerPoiName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoiMemberId() {
        return poiMemberId;
    }

    public void setPoiMemberId(String poiMemberId) {
        this.poiMemberId = poiMemberId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(int registerTime) {
        this.registerTime = registerTime;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public String getRegisterPoiCode() {
        return registerPoiCode;
    }

    public void setRegisterPoiCode(String registerPoiCode) {
        this.registerPoiCode = registerPoiCode;
    }

    public String getRegisterPoiName() {
        return registerPoiName;
    }

    public void setRegisterPoiName(String registerPoiName) {
        this.registerPoiName = registerPoiName;
    }
}
