package com.coates.tools.hadoop;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @ClassName MyMapper1
 * @Description TODO
 * @Author mc
 * @Date 2019/11/12 10:58
 * @Version 1.0
 **/
public class MyMapper1 extends Reducer<Text, Text, Text, LongWritable> {
    //得到的数据为：// <k2取消原因：v2s月份[1,1,2,2,2,..]>
    @Override
    protected void reduce(Text k2, Iterable<Text> v2s, Reducer<Text, Text, Text, LongWritable>.Context context)
            throws IOException, InterruptedException {

        long sum = 0;
        //对1月份进行统计
        for(Text month : v2s) {
            if(month.toString().equals("1")) {
                sum += 1L;
            };
        }

        //输出格式为：  <原因:次数>
        context.write(k2, new LongWritable(sum));
    }
}
