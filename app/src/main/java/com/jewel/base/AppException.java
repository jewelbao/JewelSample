package com.jewel.base;

import android.text.TextUtils;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2016/2/15 0015.
 * com.jewel.base 
 * 异常消息
 */
@SuppressWarnings("unused")
public class AppException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 异常消息
     */
    private String msg = null;

    @SuppressWarnings("deprecation")
    public AppException(Exception e) {
        super();

        try {
            if (e instanceof ConnectException) {
                msg = Constant.CONNECT_EXCEPTION;
            } else if (e instanceof ConnectTimeoutException) {
                msg = Constant.CONNECT_EXCEPTION;
            } else if (e instanceof UnknownHostException) {
                msg = Constant.UNKNOWN_HOST_EXCEPTION;
            } else if (e instanceof SocketException) {
                msg = Constant.SOCKET_EXCEPTION;
            } else if (e instanceof SocketTimeoutException) {
                msg = Constant.SOCKET_TIMEOUT_EXCEPTION;
            } else if (e instanceof NullPointerException) {
                msg = Constant.NULL_POINTER_EXCEPTION;
            } else {
                if (e == null || TextUtils.isEmpty(e.getMessage())) {
                    msg = Constant.NULL_MESSAGE_EXCEPTION;
                } else {
                    msg = e.getMessage();
                }
            }
        } catch (Exception e2) {
            msg = Constant.UNTREATED_EXCEPTION;
        }
    }

    /**
     * 用一个消息构造异常类
     *
     * @param message 异常消息
     */
    public AppException(String message) {
        super(message);
        msg = message;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
