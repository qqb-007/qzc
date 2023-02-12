package info.batcloud.wxc.core.domain;

import java.util.List;

public class Propertie {

    private String propertyName;

    private List<String> values;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
