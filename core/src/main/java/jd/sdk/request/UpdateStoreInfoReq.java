package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.UpdateStoreInfoRes;

public class UpdateStoreInfoReq extends Request<UpdateStoreInfoRes> {
    private String stationNo;
    private String operator;
    private Byte deliveryRangeType;//划分配送服务范围的类型（3、圆心半径、2、不规则多边形（手动画范围））
    private Integer coordinateType;//使用的地图类型(1,谷歌), (2,百度), (3,高德), (4,腾讯)
    private String coordinatePoints;//坐标点集合（如果服务范围为类型2的话，该字段有值），每个点以：经度,纬度 的格式表示,用“;”隔开多个点；对于腾讯地图、谷歌地图和高德地图，整个coordinatePoints的长度必须小于2k；对于百度地图，整个coordinatePoints的长度必须小于1k

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }


    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Byte getDeliveryRangeType() {
        return deliveryRangeType;
    }

    public void setDeliveryRangeType(Byte deliveryRangeType) {
        this.deliveryRangeType = deliveryRangeType;
    }

    public Integer getCoordinateType() {
        return coordinateType;
    }

    public void setCoordinateType(Integer coordinateType) {
        this.coordinateType = coordinateType;
    }

    public String getCoordinatePoints() {
        return coordinatePoints;
    }

    public void setCoordinatePoints(String coordinatePoints) {
        this.coordinatePoints = coordinatePoints;
    }


    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/store/updateStoreInfo4Open";
    }

    @Override
    public Class<UpdateStoreInfoRes> getResponseClass() {
        return UpdateStoreInfoRes.class;
    }
}
