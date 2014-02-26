package com.caferestaurant.orderingsystem.app.util;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 异步通信的基础方法，借用了async-http-client库
 * @author Administrator
 *
 */
public class HttpUtility {

	private static AsyncHttpClient client = new AsyncHttpClient(); // 实例化对象
	static {
		client.setTimeout(10000); // 设置链接超时，如果不设置，默认为10s
	}

	// 用一个完整url获取一个string对象
	public static void get(String urlString, AsyncHttpResponseHandler res)
	{
		client.get(urlString, res);
	}
	
	// url里面带参数
	public static void get(String urlString, RequestParams params, AsyncHttpResponseHandler res)
	{
		client.get(urlString, params, res);
	}
	
	// 不带参数，获取json对象或者数组
	public static void get(String urlString, JsonHttpResponseHandler res) 
	{
		client.get(urlString, res);
	}
	
	// 不带参数，获取json对象或者数组
	public static void post(String urlString, JsonHttpResponseHandler res) 
	{
		client.post(urlString, res);
	}
	
	// 带参数，获取json对象或者数组
	public static void get(String urlString, RequestParams params, JsonHttpResponseHandler res) {
		client.get(urlString, params, res);
	}
	
	// 带参数，获取json对象或者数组
	public static void post(String urlString, RequestParams params, JsonHttpResponseHandler res) {
		client.post(urlString, params, res);
	}
	
	
	public static void post(Context ctx, String urlString, JSONObject sendObj, JsonHttpResponseHandler res) 
			throws UnsupportedEncodingException
	{
		StringEntity s = new StringEntity(sendObj.toString(), "UTF-8");
		s.setContentEncoding("UTF-8");
		s.setContentType("application/json");
		client.post(ctx, urlString, s, "application/json", res);
	}
	
	// 下载数据使用，会返回byte数据
	public static void get(String uString, BinaryHttpResponseHandler bHandler) {
		client.get(uString, bHandler);
	}

	public static AsyncHttpClient getClient()
	{
		return client;
	}

}
