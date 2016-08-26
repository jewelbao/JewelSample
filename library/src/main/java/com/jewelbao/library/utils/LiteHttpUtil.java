package com.jewelbao.library.utils;

import android.content.Context;

import com.jewelbao.library.impl.IHttpResponse;
import com.litesuits.http.HttpConfig;
import com.litesuits.http.LiteHttp;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.AbstractRequest;
import com.litesuits.http.request.FileRequest;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.FileBody;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.request.param.HttpParamModel;
import com.litesuits.http.response.Response;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 Created by Administrator on 2016/2/16 0016. com.jewelbao.library.utils  LiteHttp工具
 */
public class LiteHttpUtil
{

	private static final int ConnectTimeOut = 10 * 1000;
	private static final int SocketTimeOut = 10 * 1000;

	private static LiteHttpUtil liteHttpUtil;
	private LiteHttp liteHttp;

	private LiteHttpUtil()
	{
		//        throw new Error("Do not need instantiate!");
	}

	public static LiteHttpUtil getInstance()
	{
		if(liteHttpUtil == null)
		{
			liteHttpUtil = new LiteHttpUtil();
		}
		return liteHttpUtil;
	}

	/**
	 必须先初始化LiteHttp再操作网络请求

	 @param context
	 */
	public LiteHttpUtil setLiteHttp(Context context)
	{
		HttpConfig config = new HttpConfig(context).setDebugged(true).setDetectNetwork(true)
												   .setTimeOut(ConnectTimeOut, SocketTimeOut);
		liteHttp = LiteHttp.newApacheHttpClient(config);

		return liteHttpUtil;
	}

	/**
	 必须先初始化LiteHttp再操作网络请求

	 @param context
	 @param header  自定义HTTP header
	 */
	public LiteHttpUtil setLiteHttp(Context context, List<NameValuePair> header)
	{
		HttpConfig config = new HttpConfig(context).setDebugged(true).setDetectNetwork(true)
												   .setTimeOut(ConnectTimeOut, SocketTimeOut)
												   .setCommonHeaders(header);
		liteHttp = LiteHttp.newApacheHttpClient(config);
		return liteHttpUtil;
	}

	/**
	 异步请求（无参数）

	 @param url                  请求url
	 @param httpResponseListener 请求回调
	 */
	public void executeAsync(String url, final int requestID,
							 final IHttpResponse httpResponseListener)
	{

		if(liteHttp == null)
		{
			throw new Error("LiteHttp is not initialization");
		}

		liteHttp.executeAsync(new StringRequest(url).setHttpListener(new HttpListener<String>()
		{
			@Override
			public void onSuccess(String s, Response<String> response)
			{
				httpResponseListener.onSuccess(requestID, s);
			}

			@Override
			public void onFailure(HttpException e, Response<String> response)
			{
				if(response.getHttpStatus() != null)
				{
					httpResponseListener
							.onFailure(requestID, e, response.getHttpStatus().getCode());
				}
			}
		}));
	}

	/**
	 异步请求（有参数）

	 @param url                  请求url
	 @param paramModel           请求参数对象
	 @param httpResponseListener 请求回调
	 */
	public void executeAsync(String url, HttpParamModel paramModel, final int requestID,
							 final IHttpResponse httpResponseListener)
	{

		if(liteHttp == null)
		{
			throw new Error("LiteHttp is not initialization");
		}

		liteHttp.executeAsync(
				new StringRequest(url, paramModel).setHttpListener(new HttpListener<String>()
				{
					@Override
					public void onSuccess(String s, Response<String> response)
					{
						httpResponseListener.onSuccess(requestID, s);
					}

					@Override
					public void onFailure(HttpException e, Response<String> response)
					{
						if(response != null && response.getHttpStatus() != null)
						{
							httpResponseListener
									.onFailure(requestID, e, response.getHttpStatus().getCode());
						} else
						{
							httpResponseListener.onFailure(requestID, e, -1);
						}
					}
				}));
	}

	/**
	 post请求

	 @param uploadUrl            post url
	 @param keys                 参数key
	 @param params               参数值
	 @param requestID            请求ID
	 @param httpResponseListener 回调方法
	 */
	public void executeAsyncPost(String uploadUrl, String[] keys, final String[] params,
								 final int requestID, final IHttpResponse httpResponseListener)
	{
		final StringRequest postRequest = new StringRequest(uploadUrl).setMethod(HttpMethods.Post)
																	  .setHttpListener(
																			  new HttpListener<String>(
																					  true, false,
																					  true)
																			  {
																				  @Override
																				  public void onStart(
																						  AbstractRequest<String> request)
																				  {
																					  super.onStart(
																							  request);
																				  }

																				  @Override
																				  public void onUploading(
																						  AbstractRequest<String> request,
																						  long total,
																						  long len)
																				  {
																					  double progress = len / total;
																					  JLog.e("上传进度:" + Double
																							  .toString(
																									  progress));
																				  }

																				  @Override
																				  public void onEnd(
																						  Response<String> response)
																				  {
																					  if(response
																							  .isConnectSuccess())
																					  {
																						  httpResponseListener
																								  .onSuccess(
																										  requestID,
																										  response.getResult());
																						  JLog.e("上传成功" + response
																								  .getResult());
																					  } else
																					  {
																						  httpResponseListener
																								  .onFailure(
																										  requestID,
																										  response.getException(),
																										  -1);
																						  JLog.e("上传失败" + response
																								  .getException());
																					  }
																					  response.printInfo();
																				  }
																			  });
		LinkedList<NameValuePair> pList = new LinkedList<NameValuePair>();
		for(int i = 0; i < keys.length; i++)
		{
			pList.add(new NameValuePair(keys[i], params[i])); // url?info=deviceInfo
		}
		postRequest.setHttpBody(new UrlEncodedFormBody(pList));
		liteHttp.executeAsync(postRequest);
	}

	/**
	 post请求

	 @param uploadUrl            post url
	 @param requestID            请求ID
	 @param path                 文件路径
	 @param httpResponseListener 回调方法
	 */
	public void executeAsyncPostFile(String uploadUrl, final String path, final int requestID,
									 final IHttpResponse httpResponseListener)
	{
		final StringRequest postRequest = new StringRequest(uploadUrl).setMethod(HttpMethods.Post)
																	  .setHttpListener(
																			  new HttpListener<String>(
																					  true, false,
																					  true)
																			  {
																				  @Override
																				  public void onStart(
																						  AbstractRequest<String> request)
																				  {
																					  super.onStart(
																							  request);
																				  }

																				  @Override
																				  public void onUploading(
																						  AbstractRequest<String> request,
																						  long total,
																						  long len)
																				  {
																					  float progress = (len / total) * 100;
																					  JLog.e("上传进度:" + progress);
																				  }

																				  @Override
																				  public void onEnd(
																						  Response<String> response)
																				  {
																					  if(response
																							  .isConnectSuccess())
																					  {
																						  httpResponseListener
																								  .onSuccess(
																										  requestID,
																										  response.getResult());
																						  //                            JLog.e("上传成功" +  response.getResult());
																					  } else
																					  {
																						  httpResponseListener
																								  .onFailure(
																										  requestID,
																										  response.getException(),
																										  -1);
																						  JLog.e("上传失败" + response
																								  .getException());
																					  }
																					  response.printInfo();
																				  }
																			  });
		postRequest.setHttpBody(new FileBody(new File(path)));
		liteHttp.executeAsync(postRequest);
	}


	/**
	 下载文件

	 @param url        下载地址
	 @param saveToPath 保存路径
	 */
	public void downFileAsync(String url, String saveToPath)
	{
		liteHttp.executeAsync(new FileRequest(url, saveToPath));
	}

}
