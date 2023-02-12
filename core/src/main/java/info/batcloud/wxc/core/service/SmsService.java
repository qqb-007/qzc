package info.batcloud.wxc.core.service;

public interface SmsService {

    Result<String> sendPhoneLoginMsg(String phone);

    boolean validatePhoneCode(String phone, String code);

    void unlockPhone(String phone);

    class Result<T> {
        private int cooldown;
        private boolean success;
        private T data;
        private long timestamp;
        private String errMsg;
        private String phone;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public int getCooldown() {
            return cooldown;
        }

        public void setCooldown(int cooldown) {
            this.cooldown = cooldown;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
    }

}
