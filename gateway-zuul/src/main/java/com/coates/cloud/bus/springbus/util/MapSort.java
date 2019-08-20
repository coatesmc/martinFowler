package com.coates.cloud.bus.springbus.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TreeMap;

/**
 * <p>Title: MapSort.java </p>
 * <p>Package com.shenpinkj.jm </p>
 * <p>Description: 请求参数加密  </p>
 * <p>Company: www.shenpinkj.cn/</p>
 *
 * @author 牟超
 * @version 1.0
 * @date 2017年10月21日上午9:27:43
 */
public class MapSort {
    private final Logger logger = LoggerFactory.getLogger(MapSort.class);

    private volatile static MapSort mapSort;

    private MapSort() {
    }

    public static MapSort getSingleton() {
        if (mapSort == null) {
            synchronized (MapSort.class) {
                if (mapSort == null) {
                    mapSort = new MapSort();
                }
            }
        }
        return mapSort;
    }

    /**
     * 创  建  人：牟 超
     * 创建时间：2017年10月21日
     * 方法描述：Map转字符串工具类
     *
     * @param resultmap
     * @return
     */
    public String MapTurnTheString(Map<String, String> resultmap) {
        String mapString = "";
        for (Map.Entry<String, String> entry : resultmap.entrySet()) {
            mapString = mapString + entry.getKey() + entry.getValue();
        }
        logger.info("String stitching-->>{}", mapString);
        return mapString;
    }


    /**
     * 创  建  人：牟 超
     * 创建时间：2017年10月21日
     * 方法描述：使用 Map按key进行排序
     *
     * @param map 排序参数
     * @return
     */
    public Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<String, String>(
                new MapKeyComparator());
        sortMap.putAll(map);

        logger.info("this is map sort-->>{}", sortMap);
        return sortMap;
    }
}
