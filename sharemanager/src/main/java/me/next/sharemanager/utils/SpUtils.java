package me.next.sharemanager.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.next.sharemanager.beans.AppShareCount;

public class SpUtils {

    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "share_helper";
    private static String APP_SHARE_COUNT = "app_share_count";


    public static SharedPreferences getSp(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void save(Context context, String key, Object value) {
        SharedPreferences.Editor editor = getSp(context).edit();
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }
        editor.apply();
    }

    public static <T> T get(Context context, String key, T defaultValue) {
        SharedPreferences sp = getSp(context);
        Object value = null;
        if (defaultValue instanceof Boolean) {
            value = sp.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof String) {
            value = sp.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Float) {
            value = sp.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            value = sp.getLong(key, (Long) defaultValue);
        } else if (defaultValue instanceof Integer) {
            value = sp.getInt(key, (Integer) defaultValue);
        }
        return (T) value;
    }

    /**
     * 移除某个key值已经对应的值
     */
    public static void remove(Context context, String key) {
        SharedPreferences.Editor editor = getSp(context).edit();
        editor.remove(key);
        editor.apply();
    }


    /**
     * 清除所有数据
     */
    public static void clear(Context context) {
        SharedPreferences.Editor editor = getSp(context).edit();
        editor.clear();
        editor.apply();
    }

    public static Map<String, Integer> getAppShareCountMap(Context context) {
        HashMap<String, Integer> map = new LinkedHashMap<>();
        String appShareCountMapStr = get(context, APP_SHARE_COUNT, "");
        if (!TextUtils.isEmpty(appShareCountMapStr)) {
            try {
                List<AppShareCount> appShareCountList = JSON.parseArray(appShareCountMapStr, AppShareCount.class);
                return MapUtil.getAppShareCountMap(appShareCountList);
            } catch (Exception e) {
                e.printStackTrace();
                return map;
            }
        }
        return map;
    }

    private static void saveAppShareCount(Context context, Map<String, Integer> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        List<AppShareCount> appShareCountList = MapUtil.getAppShareCountList(map);
        save(context, APP_SHARE_COUNT, JSON.toJSONString(appShareCountList));
    }

    public static void saveAppShare(Context context, String activityName) {
        Map<String, Integer> appShareCountMap = getAppShareCountMap(context);
        if (appShareCountMap.isEmpty() || !appShareCountMap.containsKey(activityName)) {
            appShareCountMap.put(activityName, 1);
        } else {
            int clickCount = appShareCountMap.get(activityName) + 1;
            appShareCountMap.put(activityName, clickCount);
        }
        saveAppShareCount(context, appShareCountMap);
    }
}
