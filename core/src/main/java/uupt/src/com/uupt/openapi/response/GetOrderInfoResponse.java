package uupt.src.com.uupt.openapi.response;

public class GetOrderInfoResponse {
    private String driver_lastloc;

    private String state;

    private String order_price;

    public String getDriver_lastloc() {
        return driver_lastloc;
    }

    public void setDriver_lastloc(String driver_lastloc) {
        this.driver_lastloc = driver_lastloc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    @Override
    public String toString() {
        return "GetOrderInfoResponse{" +
                "driver_lastloc='" + driver_lastloc + '\'' +
                ", state='" + state + '\'' +
                ", order_price='" + order_price + '\'' +
                '}';
    }
}
