package info.batcloud.wxc.core.mapper.stat.domain;

/**
 * @author lvling
 * Date: 2020/2/3
 * Time: 18:45
 */
public class SupplierFoodSaleInfo {

    private Long foodSupplierId;
    private Long foodId;
    private String foodName;
    private String foodPicture;
    /**
     * 销售数量
     * */
    private Integer sales;
    /**
     * 销售金额
     * */
    private Float money;

    private String unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getFoodSupplierId() {
        return foodSupplierId;
    }

    public void setFoodSupplierId(Long foodSupplierId) {
        this.foodSupplierId = foodSupplierId;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPicture() {
        return foodPicture;
    }

    public void setFoodPicture(String foodPicture) {
        this.foodPicture = foodPicture;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }
}
