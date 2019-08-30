package com.coates.oauth2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MissingValue
 * @Description TODO 计算遗漏值的工具类
 * @Author mc
 * @Date 2019/8/26 15:28
 * @Version 1.0
 **/
public class MissingValue {
    /**
     * 获取整个区域的遗漏值
     * lis 开奖数据的集合（从数据查询到的开奖数据集合）
     * row 行数
     * column 列数
     */
    public static int[][] getMissingvalue(List<List<Integer>> lis, Integer beginNum, Integer row, Integer horizontalSpan) {
        int[][] arr = new int[row][horizontalSpan];

        //获取整个区域的遗漏值
        for (int k = 1; k <= horizontalSpan; k++) {

            //获取每期开奖数据在当前列的位置
            List<Integer> countList = new ArrayList<Integer>();
            for (int i = 0; i <= lis.size() - 1; i++) {
                for (int j = 0; j < lis.get(i).size(); j++) {
                    if (lis.get(i).get(j) > beginNum + horizontalSpan - 1) {
                        break;
                    }
                    if (lis.get(i).get(j) == k) {
                        countList.add(i);//记录当前列的开奖号码的行号
                    }
                }
            }

            if (countList.size() != 0 && countList != null) {
                int[] arrj = getOthers(countList, arr.length);
                for (int j = 0; j < arrj.length; j++) {
                    arr[j][k - 1] = arrj[j];
                }
            } else {
                for (int j = arr.length - 1; j >= 0; j--) {
                    arr[j][k - 1] = arr.length - j;
                }
            }
        }

        return arr;
    }


    //获取每列的遗漏值数据
    public static int[] getOthers(List<Integer> list, int row) {
        int[] array = new int[row];
        int n = 0;
        while (true) {
            if (list.size() == 1) {
                if (list.get(n) == 0) {//中奖号码只有一个且在顶部
                    array[n] = 0;
                    for (int i = array.length - 1; i > 0; i--) {
                        array[i] = array.length - i;
                    }
                } else if (list.get(n) == row - 1) {//中奖号码只有一个且在底部
                    for (int i = list.get(n); i >= 0; i--) {
                        array[i] = list.get(n) - i;
                    }
                } else if (list.get(n) > 0 && list.get(n) < row - 1) {//中奖号码只有一个且在中间
                    for (int i = array.length - 1; i > list.get(n); i--) {
                        array[i] = array.length - i;
                    }
                    for (int i = list.get(n); i >= 0; i--) {
                        array[i] = list.get(n) - i;
                    }
                }
            } else if (list.size() > 1) {
                if (n == 0) {//第1个开奖号码
                    if (list.get(n) == 0) {//当前中奖号码在顶部
                        array[n] = 0;
                    } else if (list.get(n) < row - 1) {//当前号码在中间
                        for (int i = list.get(n); i >= 0; i--) {
                            array[i] = list.get(n) - i;
                        }
                    }
                } else if (n + 1 < list.size() && n > 0) {//当前为第n+1个开奖号码,而且不是最后一个,那么当前号码一定在中间
                    if (list.get(n) - list.get(n - 1) - 1 != 0) {//开奖号码有间隔
                        for (int i = list.get(n); i > list.get(n - 1); i--) {
                            array[i] = list.get(n) - i;
                        }
                    } else {//开奖号码没有间隔
                        array[list.get(n)] = 0;
                    }
                } else if (n + 1 == list.size() && n > 0) {//当前开奖号码为这一列的最后一个开奖号码
                    if (list.get(n) - list.get(n - 1) - 1 > 0) {//开奖号码有间隔
                        if (list.get(n) == row - 1) {//当前中奖号码在底部
                            for (int i = list.get(n); i > list.get(n - 1); i--) {
                                array[i] = list.get(n) - i;
                            }
                        } else if (list.get(n) < row - 1) {//当前号码在中间
                            for (int i = list.get(n); i > list.get(n - 1); i--) {
                                array[i] = list.get(n) - i;
                            }
                            for (int i = array.length - 1; i >= list.get(n) + 1; i--) {
                                array[i] = array.length - i;
                            }
                        }
                    } else if (list.get(n) - list.get(n - 1) - 1 == 0) {//开奖号码没有间隔
                        if (list.get(n) == row - 1) {//当前中奖号码在底部
                            array[list.get(n)] = 0;
                        } else if (list.get(n) < row - 1) {//当前号码在中间
                            array[list.get(n)] = 0;
                            for (int i = array.length - 1; i > list.get(n); i--) {
                                array[i] = array.length - i;
                            }
                        }
                    }

                    break;
                }

            }

            if (n == list.size() - 1) {
                break;
            }
            n++;
        }
        return array;
    }

    /**
     * 处理开奖数据的方法
     * 实现的功能：将每一期在当前区域的开奖数据获取出来，并转换为int 类型
     * lis 开奖数据的集合
     */
    public static List<List<Integer>> convertAwardData(List<Map<String, String>> lis) {
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < lis.size(); i++) {
            List<Integer> lii = new ArrayList<>();
            String[] sp = lis.get(i).get("result").replaceFirst("\\+", ",").split(",");
            for (int j = 0; j < sp.length; j++) {
                lii.add(Integer.parseInt(sp[j]));
            }
            list.add(lii);
        }
        return list;
    }

    /**
     * 计算每列开奖的总次数（即：出现总次数）
     * <p>
     * arr 遗漏值数据的数组
     */
    public static List<Integer> getTotal(int[][] arr) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr[0].length; i++) {
            int count = 0;
            for (int j = 0; j < arr.length; j++) {
                if (arr[j][i] == 0) {
                    count++;
                }
            }
            list.add(count);
        }
        return list;
    }

    /**
     * 计算平均遗漏值
     * <p>
     * lis 出现总次数集合
     * row  总行数
     */
    public static List<Integer> getCoverage(List<Integer> lis, Integer row) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < lis.size(); i++) {
            if (lis.get(i) != 0) {
                list.add(row / lis.get(i));
            } else {
                list.add(0);
            }
        }
        return list;
    }

    /**
     * 计算最大遗漏值
     * <p>
     * arr 遗漏值的集合
     */
    public static List<Integer> getMax(int[][] arr) {
        List<Integer> ll = new ArrayList<Integer>();
        for (int i = 0; i < arr[0].length; i++) {
            int max = arr[0][i];
            for (int j = 0; j < arr.length; j++) {
                if (arr[j][i] > max)
                    max = arr[j][i];
            }
            ll.add(max);
        }
        return ll;
    }


    /**
     * 计算最大连出值
     * <p>
     * arr 遗漏值的集合
     */
    public static List<Integer> getContinuous(int[][] arr) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr[0].length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < arr.length; j++) {
                sb.append(arr[j][i]);
            }
            list.add(max(sb.toString()));
        }
        return list;
    }

    //统计相同字符连续出现的最大子序列的长度
    public static int max(String s) {
        int max = 0, tmp_m = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                tmp_m++;
            } else {
                max = max > tmp_m ? max : tmp_m;
                tmp_m = 1;
            }
        }
        max = max > tmp_m ? max : tmp_m;//最后的连续数与最大连续的比较
        return max;
    }

}
