package me.ele.sdk.up.response;

import me.ele.sdk.up.Response;

import java.util.List;

public class PropertyListResponse extends Response<PropertyListResponse.Data, String> {

    public static class Data {
        private Long propertyId;
        private Long categoryId;
        private String propertyName;
        private Boolean required;
        private Boolean multiSelect;
        private Boolean enumProp;
        private Boolean inputProp;
        private Boolean saleProp;
        private Boolean sortOrder;
        private List<CatPropertyValueDTO> propertyValues;

        public Long getPropertyId() {
            return propertyId;
        }

        public void setPropertyId(Long propertyId) {
            this.propertyId = propertyId;
        }

        public Long getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Long categoryId) {
            this.categoryId = categoryId;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }

        public Boolean getRequired() {
            return required;
        }

        public void setRequired(Boolean required) {
            this.required = required;
        }

        public Boolean getMultiSelect() {
            return multiSelect;
        }

        public void setMultiSelect(Boolean multiSelect) {
            this.multiSelect = multiSelect;
        }

        public Boolean getEnumProp() {
            return enumProp;
        }

        public void setEnumProp(Boolean enumProp) {
            this.enumProp = enumProp;
        }

        public Boolean getInputProp() {
            return inputProp;
        }

        public void setInputProp(Boolean inputProp) {
            this.inputProp = inputProp;
        }

        public Boolean getSaleProp() {
            return saleProp;
        }

        public void setSaleProp(Boolean saleProp) {
            this.saleProp = saleProp;
        }

        public Boolean getSortOrder() {
            return sortOrder;
        }

        public void setSortOrder(Boolean sortOrder) {
            this.sortOrder = sortOrder;
        }

        public List<CatPropertyValueDTO> getPropertyValues() {
            return propertyValues;
        }

        public void setPropertyValues(List<CatPropertyValueDTO> propertyValues) {
            this.propertyValues = propertyValues;
        }
    }

    public static class CatPropertyValueDTO {
        private Long valueId;
        private String valueData;
        private Integer sortOrder;

        public Long getValueId() {
            return valueId;
        }

        public void setValueId(Long valueId) {
            this.valueId = valueId;
        }

        public String getValueData() {
            return valueData;
        }

        public void setValueData(String valueData) {
            this.valueData = valueData;
        }

        public Integer getSortOrder() {
            return sortOrder;
        }

        public void setSortOrder(Integer sortOrder) {
            this.sortOrder = sortOrder;
        }
    }
}
