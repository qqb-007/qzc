package info.batcloud.wxc.core.service;

import info.batcloud.wxc.core.enums.Plat;

public interface SingleCallService {
    Boolean sendCall(Plat plat, String phone, Long orderId);

    Boolean sendCustomerCall(String info, Long orderId);

    class CallPlat {
        private String plat;

        public String getPlat() {
            return plat;
        }

        public void setPlat(String plat) {
            this.plat = plat;
        }
    }

    class CallCustomer {
        private String info;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
