package com.github.binarywang.demo.wx.mp.Cache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MapCacheManager {
    private volatile static MapCacheManager mapCacheObject;// 缓存实例对象

    private volatile static Map<String, String> cacheMap = new ConcurrentHashMap<String, String>();// 缓存map
    private volatile static List<String> cacheList= new ArrayList<String>();// 缓存List
    private volatile static Map<String, String> ypcacheMap = new ConcurrentHashMap<String, String>();// 缓存map
    private volatile static List<String> ypcacheList= new ArrayList<String>();// 缓存List
    private volatile static List<String> userList  = new ArrayList<>();

    public static MapCacheManager getInstance() {
        if (null == mapCacheObject) {
            synchronized (MapCacheManager.class) {
                if (null == mapCacheObject) {
                    mapCacheObject = new MapCacheManager();
                }
            }
        }
        return mapCacheObject;
    }

    //set
    public static void setMapCache(Map<String, String> map) {
        Set<String> set = map.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = it.next();
            cacheMap.put(key, map.get(key));
        }
    }

    //set
    public static void addCacheList(String key) {
        cacheList.add(key);
    }
    //get
    public static Map<String, String> getMapCache() {

        return cacheMap;
    }

    public static List<String> getListCache() {

        return cacheList;
    }

    //set
    public static void setypMapCache(Map<String, String> map) {
        Set<String> set = map.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = it.next();
            ypcacheMap.put(key, map.get(key));
        }
    }

    //set
    public static void addypCacheList(String key) {
        ypcacheList.add(key);
    }
    //get
    public static Map<String, String> getypMapCache() {

        return ypcacheMap;
    }

    public static List<String> getypListCache() {

        return ypcacheList;
    }
    //清除cache
    public static void clear(){
        cacheMap.clear();
        cacheList.clear();
    }

    public static void initUserList(List<String> userList){
        userList.addAll(userList);
    }

    public static List<String> listUserId(){
        return userList;
    }
}
