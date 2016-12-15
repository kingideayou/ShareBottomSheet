package me.next.sharemanager.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import me.next.sharemanager.beans.AppShareCount;

/**
 * Created by NeXT on 16/12/14.
 */

public class MapUtil {

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list =
                new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        Map<K, V> result = new LinkedHashMap<K, V>();
        for (int i = list.size() - 1; i >= 0; i--) {
            result.put(list.get(i).getKey(), list.get(i).getValue());
        }
        /*
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        */
        return result;
    }

    public static Map<String, Integer> getAppShareCountMap(List<AppShareCount> appShareCounts) {
        Map<String, Integer> appShareCountMap = new HashMap<>();
        for (AppShareCount appShareCount : appShareCounts) {
            appShareCountMap.put(appShareCount.getActivityName(), appShareCount.getClickCount());
        }
        return appShareCountMap;
    }

    public static List<AppShareCount> getAppShareCountList(Map<String, Integer> map) {
        if (map == null || map.isEmpty()) {
            return new ArrayList<>();
        }
        List<AppShareCount> appShareCountList = new ArrayList<>();
        for (String key : map.keySet()) {
            AppShareCount appShareCount = new AppShareCount();
            appShareCount.setActivityName(key);
            appShareCount.setClickCount(map.get(key));
            appShareCountList.add(appShareCount);
        }
        return appShareCountList;
    }
}
