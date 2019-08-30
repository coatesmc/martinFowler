package com.coates.oauth2;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

/**
 * @ClassName LotteryTrend
 * @Description TODO
 * @Author mc
 * @Date 2019/8/26 15:28
 * @Version 1.0
 **/
public class LotteryTrend {
    //所有彩票名称数组
    public static final String[] titleArray = {"超级大乐透", "七星彩", "排列三", "排列五", "双色球", "福彩3D", "江西11选5", "11运夺金", "广东11选5", "上海11选5", "浙江11选5", "七乐彩", "重庆时时彩", "胜负彩", "安徽快三", "湖北快三", "吉林快三", "江苏快三", "江苏11选5", "浙江12选5"};

    //彩票的id数组
    public static final String[] lotteryidArray = {"19", "18", "2", "13", "16", "1", "9", "8", "10", "24", "27", "17", "3", "71", "32", "33", "34", "35", "28", "22"};
    //每种彩票的区域或者数字域
    public static final String[][] headArray = {{"红一区", "红二区", "红三区", "后区"}, {"第七位", "第六位", "第五位", "第四位", "第三位", "第二位", "第一位"}, {"百位", "十位", "个位"},
            {"万位", "千位", "百位", "十位", "个位"}, {"红一区", "红二区", "红三区", "篮球"}, {"百位", "十位", "个位"},
            {"万位", "千位", "百位", "十位", "个位"}, {"万位", "千位", "百位", "十位", "个位"}, {"万位", "千位", "百位", "十位", "个位"},
            {"万位", "千位", "百位", "十位", "个位"}, {"万位", "千位", "百位", "十位", "个位"}, {"一区", "二区", "三区"},
            {"万位", "千位", "百位", "十位", "个位"}, {"3", "1", "0"}, {"百位", "十位", "个位"},
            {"百位", "十位", "个位"}, {"百位", "十位", "个位"}, {"百位", "十位", "个位"},
            {"万位", "千位", "百位", "十位", "个位"}, {"万位", "千位", "百位", "十位", "个位"}};

    //区域或者数字域的id
    public static final String[][] idxArray = {{"area1", "area2", "area3", "back"}, {"num_info7", "num_info6", "num_info5", "num_info4", "num_info3", "num_info2", "num_info1"}, {"num_info3", "num_info2", "num_info1"},
            {"num_info5", "num_info4", "num_info3", "num_info2", "num_info1"}, {"area1", "area2", "area3", "num_info7"}, {"num_info3", "num_info2", "num_info1"},
            {"num_info5", "num_info4", "num_info3", "num_info2", "num_info1"}, {"num_info5", "num_info4", "num_info3", "num_info2", "num_info1"}, {"num_info5",
            "num_info4", "num_info3", "num_info2", "num_info1"}, {"num_info5", "num_info4", "num_info3", "num_info2", "num_info1"}, {"num_info5", "num_info4", "num_info3", "num_info2", "num_info1"},
            {"area1", "area2", "area3"}, {"num_info5", "num_info4", "num_info3", "num_info2", "num_info1"}, {"3", "1", "0"}, {"num_info3", "num_info2", "num_info1"},
            {"num_info3", "num_info2", "num_info1"}, {"num_info3", "num_info2", "num_info1"}, {"num_info3", "num_info2", "num_info1"}, {"num_info5", "num_info4", "num_info3", "num_info2", "num_info1"},
            {"num_info5", "num_info4", "num_info3", "num_info2", "num_info1"}};

    //定义每个区域的起始数字
    public static final String[][] beginNum = {{"01", "13", "25", "01"}, {"0", "0", "0", "0", "0", "0", "0"}, {"0", "0", "0"},
            {"0", "0", "0", "0", "0"}, {"01", "12", "23", "01"}, {"0", "0", "0"},
            {"1", "1", "1", "1", "1"}, {"1", "1", "1", "1", "1"}, {"1", "1", "1", "1", "1"}, {"1", "1", "1", "1", "1"},
            {"1", "1", "1", "1", "1"}, {"01", "11", "21"}, {"0", "0", "0", "0", "0"}, {"1", "1", "1"}, {"1", "1", "1"},
            {"1", "1", "1"}, {"1", "1", "1"}, {"1", "1", "1"}, {"1", "1", "1", "1", "1"}, {"1", "1", "1", "1", "1"}};
    //定义每个区域的长度
    public static final int[][] horizontalSpans = {{12, 12, 11, 12}, {10, 10, 10, 10, 10, 10, 10}, {10, 10, 10},
            {10, 10, 10, 10, 10}, {11, 11, 11, 16}, {10, 10, 10},
            {11, 11, 11, 11, 11}, {11, 11, 11, 11, 11}, {11, 11, 11, 11, 11}, {11, 11, 11, 11, 11},
            {11, 11, 11, 11, 11}, {10, 10, 10}, {10, 10, 10}, {14, 14, 14}, {6, 6, 6},
            {6, 6, 6}, {6, 6, 6}, {6, 6, 6}, {11, 11, 11, 11, 11}, {12, 12, 12, 12, 12}};
    //是否需要两位，例如01或者1
    public static final String[] isHaveTwoPosition = {"1", "0", "0", "0", "1", "0", "0", "0", "0", "0", "0", "1", "0", "0", "0", "0", "0", "0", "0", "0"};

    //冷热
    public static final String[][] codeAndHotIdxArray = {{"num_info1", "num_info2", "num_info3", "num_info4", "num_info5", "num_info6", "num_info7"},
            {"num_info1", "num_info2", "num_info3", "num_info4", "num_info5", "num_info6", "num_info7"}, {"num_info1", "num_info2", "num_info3"},
            {"num_info3", "num_info2", "num_info1"}, {"num_info1", "num_info2", "num_info3", "num_info4", "num_info5", "num_info6", "num_info7"},
            {"num_info3", "num_info2", "num_info1"}, {"num_info1", "num_info2", "num_info3", "num_info4", "num_info5"}, {"num_info1", "num_info2", "num_info3", "num_info4", "num_info5"},
            {"num_info1", "num_info2", "num_info3", "num_info4", "num_info5"}, {"num_info1", "num_info2", "num_info3", "num_info4", "num_info5"},
            {"num_info1", "num_info2", "num_info3", "num_info4", "num_info5"}, {"num_info1", "num_info2", "num_info3", "num_info4", "num_info5", "num_info6", "num_info7"},
            {"num_info3", "num_info2", "num_info1"}, {}, {"num_info1", "num_info2", "num_info3"}, {"num_info1", "num_info2", "num_info3"}, {"num_info1", "num_info2", "num_info3"},
            {"num_info1", "num_info2", "num_info3"}, {"num_info1", "num_info2", "num_info3", "num_info4", "num_info5"}, {"num_info1", "num_info2", "num_info3", "num_info4", "num_info5"}};

//封装走势图所有的数据

    /**
     * list  数据库查询到的当前彩种开奖数据集合
     * issueCount  页面传来的指定的查看的总期数
     * totalIssue  当前开奖数据历史以来的总期数
     */
    public static Map<String, Object> trendInfo(List<Map<String, String>> list, String lotteryid, String idx, Integer issueCount, Integer totalIssue) {
        int m = 0, n = 0, horizontalSpan = 0;//m代表当前彩种在彩票id数组的位置；n代表要展示的区域(如红一区，后区等)或者数字域(百位，十位，各位等);horizontalSpan水平方向的跨度
        Map<String, Object> map = new LinkedHashMap<String, Object>();//封装返回数据的集合,包括alldiffs,average_omission,infolist,max_omission,max_output,placenum,result

        //获取m
        for (int i = 0; i < lotteryidArray.length; i++) {
            if (lotteryidArray[i].equals(lotteryid)) {
                m = i;
                break;
            }
        }
        //System.out.println("当前彩种在彩票id数组的位置:"+m);
        //获取n
        for (int i = 0; i < idxArray[m].length; i++) {
            if (idxArray[i].equals(idx)) {
                n = i;
                break;
            }
        }
        //System.out.println("要展示走势的当前彩票的区域:"+n);

        //获取水平跨度
        horizontalSpan = horizontalSpans[m][n];

        //封装开奖数据
        List<Map<String, String>> infolist = new ArrayList<>();
        if (issueCount <= totalIssue) {
            for (int j = 0; j < issueCount; j++) {
                //封装每一条开奖数据
                String[] sp = list.get(j).get("result").replaceFirst("\\+", ",").split(",");
                Map<String, String> ma1 = new LinkedHashMap<String, String>();
                //封装期号
                ma1.put("id", list.get(j).get("id"));

                //封装id
                ma1.put("lottery_num", list.get(j).get("termNo"));
                //封装开奖号码
                for (int i = 0; i < codeAndHotIdxArray[m].length; i++) {
                    ma1.put(codeAndHotIdxArray[m][i], sp[i]);
                }
                infolist.add(ma1);
            }
        } else {
            for (int j = 0; j < totalIssue; j++) {
                //封装每一条开奖数据
                String[] sp = list.get(j).get("result").replaceFirst("\\+", ",").split(",");
                Map<String, String> ma1 = new LinkedHashMap<String, String>();
                //封装期号
                ma1.put("id", list.get(j).get("id"));

                //封装id
                ma1.put("lottery_num", list.get(j).get("termNo"));
                //封装开奖号码
                for (int i = 0; i < codeAndHotIdxArray[m].length; i++) {
                    ma1.put(codeAndHotIdxArray[m][i], sp[i]);
                }
                infolist.add(ma1);
            }
        }
        map.put("infolist", infolist);
        //转换数据格式
        List<List<Integer>> convertList = MissingValue.convertAwardData(list);

        //获得当前彩种的所有遗漏值数据
        int[][] alldiff_totalIssue = MissingValue.getMissingvalue(convertList, Integer.parseInt(beginNum[m][n]), totalIssue, horizontalSpan);
        //获得当前彩种的指定期的遗漏值数据
        int[][] alldiffs = null;
        if (issueCount <= totalIssue) {
            alldiffs = new int[issueCount][horizontalSpan];
            for (int i = 0; i < alldiffs.length; i++) {
                for (int j = 0; j < horizontalSpan; j++) {
                    alldiffs[i][j] = alldiff_totalIssue[i][j];
                }
            }
        } else {
            alldiffs = new int[totalIssue][horizontalSpan];
            for (int i = 0; i < alldiffs.length; i++) {
                for (int j = 0; j < horizontalSpan; j++) {
                    alldiffs[i][j] = alldiff_totalIssue[i][j];
                }
            }
        }
        map.put("alldiffs", alldiffs);

        //出现总次数
        List<Integer> placenum = MissingValue.getTotal(alldiffs);

        //平均遗漏值
        List<Integer> average_omission = null;
        if (issueCount <= totalIssue) {
            average_omission = MissingValue.getCoverage(placenum, issueCount);
        } else {
            average_omission = MissingValue.getCoverage(placenum, totalIssue);
        }
        //最大遗漏值
        List<Integer> max_omission = MissingValue.getMax(alldiff_totalIssue);

        //最大连出值
        List<Integer> max_output = MissingValue.getContinuous(alldiffs);
        map.put("max_output", max_output);
        map.put("placenum", placenum);
        map.put("average_omission", average_omission);
        map.put("max_omission", max_omission);
        return map;
    }

    //封装冷热的数据
    public static Map<String, Object> saveColoHot(List<Map<String, String>> list, String lotteryid, String idx, Integer issueCount, Integer totalIssue) {
        int m = 0, n = 0;//m代表当前彩种在彩票id数组的位置,n代表冷热的区域
        Map<String, Object> map = new LinkedHashMap<String, Object>();//封装返回数据的集合,包括alldiffs,average_omission,infolist,max_omission,max_output,placenum,result

        //获取m
        for (int i = 0; i < lotteryidArray.length; i++) {
            if (lotteryidArray[i].equals(lotteryid)) {
                m = i;
                break;
            }
        }

        //获取n
        for (int i = 0; i < codeAndHotIdxArray[m].length; i++) {
            if (codeAndHotIdxArray[i].equals(idx)) {
                n = i;
                break;
            }
        }
        //获取开奖数据
        List<List<String>> infolist = new ArrayList<>();
        if (issueCount <= totalIssue) {
            for (int j = 0; j < issueCount; j++) {
                //封装每一条开奖数据
                String[] sp = list.get(j).get("result").replaceFirst("\\+", ",").split(",");
                List<String> li = new ArrayList<>();
                //封装开奖号码
                for (int i = 0; i < sp.length; i++) {
                    li.add(sp[i]);
                }
                infolist.add(li);
            }
        } else {
            for (int j = 0; j < totalIssue; j++) {
                //封装每一条开奖数据
                String[] sp = list.get(j).get("result").replaceFirst("\\+", ",").split(",");
                List<String> li = new ArrayList<>();
                //封装开奖号码
                for (int i = 0; i < sp.length; i++) {
                    li.add(sp[i]);
                }
                infolist.add(li);
            }
        }

        List<LinkedHashMap<String, String>> placenum = getColdHot(infolist, idx, n);
        map.put("placenum", placenum);
        map.put("result", "success");
        return map;
    }


    //获得冷热的数据
    public static List<LinkedHashMap<String, String>> getColdHot(List<List<String>> infolist, String idx, Integer n) {
        List<LinkedHashMap<String, String>> list = new ArrayList<>();

        //对出现的次数做降序排列
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < infolist.size(); i++) {
            if (map.containsKey(infolist.get(i).get(n))) {
                map.put(infolist.get(i).get(n), map.get(infolist.get(i).get(n)) + 1);
            } else {
                map.put(infolist.get(i).get(n), 1);
            }
        }
        List<Entry<String, Integer>> sortMap = sortMap(map);

        //封装冷热图的数据
        for (Entry<String, Integer> entry : sortMap) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            BigDecimal b1 = new BigDecimal(Double.toString(value * 100));
            BigDecimal b2 = new BigDecimal(Double.toString(infolist.size()));
            double countunm = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
            LinkedHashMap<String, String> lhm = new LinkedHashMap<>();
            lhm.put(idx, key);
            lhm.put("countid", value + "");
            String countunm1 = String.valueOf(countunm);
            if ((countunm1.split("\\.")[1]).length() == 1) {//此情况代表countunm是整数，小数点后面只有一个0，为满足数据(数据格式:xx.yy00)封装要求，要添加三个0
                lhm.put("countunm", countunm1 + "000");
            } else {
                lhm.put("countunm", countunm1 + "00");
            }
            list.add(lhm);
        }

        return list;
    }

    //对map集合value实现降序排列
    public static List<Map.Entry<String, Integer>> sortMap(Map<String, Integer> map) {
        //获取entrySet
        Set<Map.Entry<String, Integer>> mapEntries = map.entrySet();

        //使用链表来对集合进行排序，使用LinkedList，利于插入元素
        List<Map.Entry<String, Integer>> result = new LinkedList<>(mapEntries);
        //自定义比较器来比较链表中的元素
        Collections.sort(result, new Comparator<Entry<String, Integer>>() {
            //基于entry的值（Entry.getValue()），来排序链表
            @Override
            public int compare(Entry<String, Integer> o1,
                               Entry<String, Integer> o2) {

                return o2.getValue().compareTo(o1.getValue());
            }

        });

        //将排好序的存入到LinkedHashMap(可保持顺序)中，需要存储键和值信息对到新的映射中。
        Map<String, Integer> linkMap = new LinkedHashMap<>();
        for (Entry<String, Integer> newEntry : result) {
            linkMap.put(newEntry.getKey(), newEntry.getValue());
        }
    /*//根据entrySet()方法遍历linkMap
    for(Map.Entry<String, Integer> mapEntry : linkMap.entrySet()){
        System.out.println("key:"+mapEntry.getKey()+"  value:"+mapEntry.getValue());
    }*/

        return result;
    }
}
