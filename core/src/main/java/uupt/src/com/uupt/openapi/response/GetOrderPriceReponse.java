package uupt.src.com.uupt.openapi.response;

public class GetOrderPriceReponse {
    private String origin_id;
    private String price_token;
    private String total_money;
    private String need_paymoney;
    private String total_priceoff;
    private String distance;
    private String freight_money;
    private String couponid;
    private String coupon_amount;
    private String addfee;
    private String goods_insurancemoney;
    private String expires_in;
    private String nonce_str;
    private String sign;
    private String appid;
    private String return_msg;
    private String return_code;


    public String getOrigin_id() {
        return origin_id;
    }

    public void setOrigin_id(String origin_id) {
        this.origin_id = origin_id;
    }

    public String getPrice_token() {
        return price_token;
    }

    public void setPrice_token(String price_token) {
        this.price_token = price_token;
    }

    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public String getNeed_paymoney() {
        return need_paymoney;
    }

    public void setNeed_paymoney(String need_paymoney) {
        this.need_paymoney = need_paymoney;
    }

    public String getTotal_priceoff() {
        return total_priceoff;
    }

    public void setTotal_priceoff(String total_priceoff) {
        this.total_priceoff = total_priceoff;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getFreight_money() {
        return freight_money;
    }

    public void setFreight_money(String freight_money) {
        this.freight_money = freight_money;
    }

    public String getCouponid() {
        return couponid;
    }

    public void setCouponid(String couponid) {
        this.couponid = couponid;
    }

    public String getCoupon_amount() {
        return coupon_amount;
    }

    public void setCoupon_amount(String coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public String getAddfee() {
        return addfee;
    }

    public void setAddfee(String addfee) {
        this.addfee = addfee;
    }

    public String getGoods_insurancemoney() {
        return goods_insurancemoney;
    }

    public void setGoods_insurancemoney(String goods_insurancemoney) {
        this.goods_insurancemoney = goods_insurancemoney;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    @Override
    public String toString() {
        return "GetOrderPriceReponse{" +
                "origin_id='" + origin_id + '\'' +
                ", price_token='" + price_token + '\'' +
                ", total_money='" + total_money + '\'' +
                ", need_paymoney='" + need_paymoney + '\'' +
                ", total_priceoff='" + total_priceoff + '\'' +
                ", distance='" + distance + '\'' +
                ", freight_money='" + freight_money + '\'' +
                ", couponid='" + couponid + '\'' +
                ", coupon_amount='" + coupon_amount + '\'' +
                ", addfee='" + addfee + '\'' +
                ", goods_insurancemoney='" + goods_insurancemoney + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", sign='" + sign + '\'' +
                ", appid='" + appid + '\'' +
                ", return_msg='" + return_msg + '\'' +
                ", return_code='" + return_code + '\'' +
                '}';
    }
}
