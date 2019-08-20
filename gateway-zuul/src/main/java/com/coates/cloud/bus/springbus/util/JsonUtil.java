package com.coates.cloud.bus.springbus.util;

import com.google.gson.Gson;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName JsonUtil
 * @Description TODO
 * @Author mc
 * @Date 2019/5/7 15:40
 * @Version 1.0
 **/
public class JsonUtil {

    /**
     * 转换json
     *
     * @param response
     * @param map
     */
    public static void sendJsonMessage(HttpServletResponse response, Object map) {
        Gson GSON = new Gson();
        PrintWriter printWriter = null;
        response.setContentType("application/json; charset=utf-8");
        try {
            printWriter = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printWriter.write(GSON.toJson(map));
        printWriter.close();
        try {
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
