package info.batcloud.wxc.merchant.api.controller.vo;

public class EnumVo {

    private String title;
    private String value;
    private String help;

    public EnumVo(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public EnumVo(String title, String value, String help) {
        this.title = title;
        this.value = value;
        this.help = help;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
