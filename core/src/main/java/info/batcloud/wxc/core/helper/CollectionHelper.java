package info.batcloud.wxc.core.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CollectionHelper {

    public static <K, T> Map<K, T> toMap(List<T> list, Function<T, K> function) {
        Map<K, T> map = new HashMap<>();
        list.forEach(t -> map.put(function.apply(t), t));
        return map;
    }

}
