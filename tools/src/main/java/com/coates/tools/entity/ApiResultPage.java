package com.coates.tools.entity;

import com.alibaba.fastjson.JSONArray;
import com.coates.tools.enums.ErrorCode;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @ClassName ApiResult
 * @Description 接口统一返回json
 * @Author mc
 * @Date 2019/4/26 11:34
 * @Version 1.0
 **/
@Data
public class ApiResultPage {
    private int code;
    private String message;
    private long timestamp;
    //总页数
    private int totelCount;
    //当前页数
    private int pageIndex;
    //当前条数
    private int pageSize;

    private int totalPages;


    private Object data = new HashMap<>();


    private ApiResultPage(int code, String message, int pageSize, int pageIndex, int totalCount) {
        this.setTimestamp(System.currentTimeMillis());
        this.code = code;
        this.message = message;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totelCount = totalCount;
        this.totalPages = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
    }

    /**
     * 请求成功
     *
     * @return
     */
    public static ApiResultPage successInstance(int totelCount, int pageIndex, int pageSize) {
        return new ApiResultPage(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMsg(), pageSize, pageIndex, totelCount);
    }

    /**
     * 权限不足
     *
     * @return
     */
    public static ApiResultPage InsufficientPermissions(int totelCount, int pageIndex, int pageSize) {
        return new ApiResultPage(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg(), pageSize, pageIndex, totelCount);
    }

    /**
     * 服务器异常
     *
     * @return
     */
    public static ApiResultPage ServerErrorInstance(int totelCount, int pageIndex, int pageSize) {
        return new ApiResultPage(ErrorCode.SERVER_ERROR.getCode(), ErrorCode.SERVER_ERROR.getMsg(), pageSize, pageIndex, totelCount);
    }

    /**
     * 认证失败
     *
     * @return
     */
    public static ApiResultPage AuthFailureInstance(int totelCount, int pageIndex, int pageSize) {
        return new ApiResultPage(ErrorCode.AUTH_ERROR.getCode(), ErrorCode.AUTH_ERROR.getMsg(), pageSize, pageIndex, totelCount);
    }

    /**
     * 参数错误
     *
     * @return
     */
    public static ApiResultPage ParamsInstance(int totelCount, int pageIndex, int pageSize) {
        return new ApiResultPage(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg(), pageSize, pageIndex, totelCount);
    }

    /**
     * json解析错误
     *
     * @return
     */
    public static ApiResultPage JsonParseInstance(int totelCount, int pageIndex, int pageSize) {
        return new ApiResultPage(ErrorCode.JSON_PARSE_ERROR.getCode(), ErrorCode.JSON_PARSE_ERROR.getMsg(), pageSize, pageIndex, totelCount);
    }

    /**
     * 非法字符串
     *
     * @return
     */
    public static ApiResultPage IlleagalStringInstance(int totelCount, int pageIndex, int pageSize) {
        return new ApiResultPage(ErrorCode.ILLEAGAL_STRING.getCode(), ErrorCode.ILLEAGAL_STRING.getMsg(), pageSize, pageIndex, totelCount);
    }

    /**
     * 未知错误
     *
     * @return
     */
    public static ApiResultPage UnknowInstance(int totelCount, int pageIndex, int pageSize) {
        return new ApiResultPage(ErrorCode.UNKNOW_ERROR.getCode(), ErrorCode.UNKNOW_ERROR.getMsg(), pageSize, pageIndex, totelCount);
    }


    /**
     * 地址错误
     *
     * @return
     */
    public static ApiResultPage AddressInstance(int totelCount, int pageIndex, int pageSize) {
        return new ApiResultPage(ErrorCode.ADDRESS_ERROR.getCode(), ErrorCode.ADDRESS_ERROR.getMsg(), pageSize, pageIndex, totelCount);
    }

    /**
     * 自定义错误
     *
     * @param code
     * @param altMessage
     * @return
     */
    public static ApiResultPage CustomErrorInstance(int code, String altMessage) {
        return new ApiResultPage(code, altMessage, 0, 0, 0);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getTotelCount() {
        return totelCount;
    }

    public void setTotelCount(int totelCount) {
        this.totelCount = totelCount;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public Object getData() {
        return data;
    }

    public ApiResultPage setData(Object data) {
        this.data = data;
        return this;
    }

    public ApiResultPage createEmptyData() {
        return this.setData(new HashMap());
    }

    public ApiResultPage createEmptyList() {
        return this.setData(new ArrayList());
    }

    @Override
    public String toString() {
        return JSONArray.toJSONString(this);
    }
}
