package info.batcloud.wxc.core.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import info.batcloud.wxc.core.domain.FoodSku;
import info.batcloud.wxc.core.domain.Propertie;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FoodHelper {

    public static List<Propertie> parseFoodPropertieList(String propertieJson) {
        if (StringUtils.isBlank(propertieJson)) {
            return new ArrayList<>();
        }
        return JSON.parseObject(propertieJson, new TypeReference<List<Propertie>>() {
        });
    }

    public static List<FoodSku> parseFoodSkuList(String foodSkuJson) {
        return JSON.parseObject(foodSkuJson, new TypeReference<List<FoodSku>>() {
        });
    }


    public static FoodSku findSku(String skuId, List<FoodSku> skuList) {
        if (skuList == null) {
            return null;
        }
        for (FoodSku sku : skuList) {
            if (sku.getSkuId().equals(skuId)) {
                return sku;
            }
        }
        return null;
    }
}
