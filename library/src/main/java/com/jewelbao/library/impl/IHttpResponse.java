package com.jewelbao.library.impl;

import com.litesuits.http.exception.HttpException;

/**
 * Created by Administrator on 2016/2/16 0016.
 * com.jewelbao.library.impl 
 * LiteHttp请求回调
 */
public interface IHttpResponse {
    void onSuccess(int requestID, String result);
    void onFailure(int requestID, HttpException e, int code);
}
