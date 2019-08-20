package com.coates.cloud.bus.springbus.util;

import com.coates.tools.enums.HttpStatusCode;
import com.coates.tools.util.MD5Util;
import com.coates.tools.util.StringUtils;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName SignUtil
 * @Description TODO
 * @Author mc
 * @Date 2019/5/8 16:10
 * @Version 1.0
 **/
public class SignUtil {
    private final Logger logger = LoggerFactory.getLogger(SignUtil.class);

    private volatile static SignUtil signUtil;

    private SignUtil() {

    }

    public static SignUtil getSingleton() {
        if (signUtil == null) {
            synchronized (SignUtil.class) {
                if (signUtil == null) {
                    signUtil = new SignUtil();
                }
            }
        }
        return signUtil;
    }

    /**
     * 加密签名校验
     *
     * @return
     */
    public boolean verifySignature(RequestContext ctx, Map<String, String[]> mapSign) throws IOException {
        HttpServletResponse response = ctx.getResponse();
        //转换map成普通的key，value格式
        Map<String, String> map = mapArrayToMap(mapSign);
        logger.info("this is  request param >>>>>{}", map);
        if (StringUtils.isEmpty(map.get("timeStamp")) || StringUtils.isEmpty(map.get("nonceStr")) || StringUtils.isEmpty("sign")) {
            ctx.setSendZuulResponse(false);
            response.sendError(HttpStatusCode.MISSING_PARAMETERS.getCode(), HttpStatusCode.MISSING_PARAMETERS.getMsg());
            return  false;
        }

        //时间验证
        if (checkTime(map.get("timeStamp"))) {
            ctx.setSendZuulResponse(false);
            response.sendError(HttpStatusCode.ILLEGAL_REQUEST.getCode(), HttpStatusCode.ILLEGAL_REQUEST.getMsg());
            return  false;
        }

        String sign = map.get("sign");
        //排除公共参数 不纳入加密参数内
        map.remove("sign");
        map.remove("pageIndex");
        map.remove("pageSize");
        String mapSortStr = MapSort.getSingleton().MapTurnTheString(MapSort.getSingleton().sortMapByKey(map));
        //后续添加私密取值
        if (!sign.equals(MD5Util.GetMD5Code(mapSortStr))) {
            ctx.setSendZuulResponse(false);
            response.sendError(HttpStatusCode.VERIFICATION_FAILED.getCode(), HttpStatusCode.VERIFICATION_FAILED.getMsg());
        }
        return true;
    }

    /**
     * 时间校验
     *
     * @param timeStamp
     */
    private boolean checkTime(String timeStamp) {
        //如果越过10分钟表示有问题，如果客户时间有问题，也算     就表示是重复请求，或者恶意请求
        long l = System.currentTimeMillis();
        long timeStampL = Long.parseLong(timeStamp);
        long time = (l - timeStampL) / 1000;
        logger.info("{} >>>> {} >>>>>{}", timeStamp, l, time);
        if (time > 150) {
            return true;
        }
        return false;
    }

    /**
     * 将Map<String, String[]> TO map<<String, obj>
     *
     * @param mapSign
     */
    private Map<String, String> mapArrayToMap(Map<String, String[]> mapSign) {
        Map<String, String> map = new HashMap<>();
        Set keSet = mapSign.entrySet();
        for (Iterator itr = keSet.iterator(); itr.hasNext(); ) {
            Map.Entry me = (Map.Entry) itr.next();
            Object ok = me.getKey();
            Object ov = me.getValue();
            String[] value = new String[1];
            if (ov instanceof String[]) {
                value = (String[]) ov;
            } else {
                value[0] = ov.toString();
            }

            for (int k = 0; k < value.length; k++) {
                map.put(ok.toString(), value[k]);
            }
        }
        return map;
    }
}
