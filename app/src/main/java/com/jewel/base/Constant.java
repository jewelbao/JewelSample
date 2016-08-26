package com.jewel.base;

/**
 * Created by Administrator on 2016/2/15 0015.
 * com.jewel.base 
 * 通用常量
 */
public class Constant {
    /**
     * 方向--左
     */
    public static final int LEFT = 1;
    /**
     * 方向--右
     */
    public static final int RIGHT = 2;
    /**
     * 方向--上
     */
    public static final int TOP = 3;
    /**
     * 方向--下
     */
    public static final int BOTTOM = 4;

    /** The Constant CONNECTEXCEPTION. */
    public static String CONNECT_EXCEPTION = "无法连接到网络";

    /** The Constant UNKNOWNHOSTEXCEPTION. */
    public static String UNKNOWN_HOST_EXCEPTION = "连接远程地址失败";

    /** The Constant SOCKETEXCEPTION. */
    public static String SOCKET_EXCEPTION = "网络连接出错，请重试";

    /** The Constant SOCKETTIMEOUTEXCEPTION. */
    public static String SOCKET_TIMEOUT_EXCEPTION = "连接超时，请重试";

    /** The Constant NULLPOINTEREXCEPTION. */
    public static String NULL_POINTER_EXCEPTION = "抱歉，远程服务出错了";

    /** The Constant NULLMESSAGEEXCEPTION. */
    public static String NULL_MESSAGE_EXCEPTION = "抱歉，程序出错了";

    /** The Constant CLIENTPROTOCOLEXCEPTION. */
    @SuppressWarnings("unused")
    public static String CLIENT_PROTOCOL_EXCEPTION = "Http请求参数错误";

    /** 参数个数不够. */
    @SuppressWarnings("unused")
    public static String MISSING_PARAMETERS = "参数没有包含足够的值";

    /** The Constant REMOTESERVICEEXCEPTION. */
    @SuppressWarnings("unused")
    public static String REMOTE_SERVICE_EXCEPTION = "抱歉，远程服务出错了";

    /** 页面未找到. */
    @SuppressWarnings("unused")
    public static String NOT_FOUND_EXCEPTION = "页面未找到";

    /** 其他异常. */
    public static String UNTREATED_EXCEPTION = "未处理的异常";
}
