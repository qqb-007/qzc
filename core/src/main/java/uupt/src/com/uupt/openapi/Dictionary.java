package uupt.src.com.uupt.openapi;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Dictionary<K, V> {

	Map<K, V> itemMap = new HashMap<K, V>();

	public Dictionary() {
		super();
	}

	public void Add(K string, V appid) {
		itemMap.put(string, appid);
	}

	public boolean isEmpty() {
		return itemMap.isEmpty();
	}

	public Map<K, V> getHashMap() {
		return itemMap;
	}

	public Set<K> keySet() {
		return itemMap.keySet();
	}

	public V get(K key) {
		return itemMap.get(key);
	}

}
