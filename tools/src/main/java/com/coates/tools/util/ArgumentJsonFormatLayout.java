package com.coates.tools.util;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.alibaba.fastjson.JSON;
import org.slf4j.helpers.MessageFormatter;

import java.util.stream.Stream;


/**
 * @ClassName ArgumentJsonFormatLayout
 * @Description TODO
 * @Author mc
 * @Date 2019/11/6 9:11
 * @Version 1.0
 **/
/**
 * 参数JSON格式化类
 *
 * @author Hollis
 */
public class ArgumentJsonFormatLayout extends MessageConverter {

    public String Converter(ILoggingEvent event){
        try {
            return MessageFormatter.arrayFormat(event.getMessage(), Stream.of(event.getArgumentArray())
                    .map(JSON::toJSONString).toArray()).getMessage();
        } catch (Exception e) {
            return event.getMessage();
        }
    }
}
