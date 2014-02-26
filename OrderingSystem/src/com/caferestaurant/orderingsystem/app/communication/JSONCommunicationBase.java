package com.caferestaurant.orderingsystem.app.communication;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.caferestaurant.orderingsystem.app.util.HttpUtility;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.content.Context;
import android.util.Log;

public abstract class JSONCommunicationBase extends CommunicationBase{
	
	/**
	 * JSON缺省通信地址
	 */
	private static String JSON_DEFAULT_URL = 
			"http://111.1.58.76:8080/communityhz/command/queryContent.html";
	
	/** 消息头部总字段 */
	private static String HEADER_PART_NAME = "head";
	/** 消息体部总字段 */
	private static String BODY_PART_NAME = "body";
	
	/**
	 * 通信上下文（用来调用async http）
	 */
	private Context context;
	
	public JSONCommunicationBase(Context ctx)
	{
		this(ctx, JSON_DEFAULT_URL);
	}
	
	public JSONCommunicationBase(Context ctx, String commURI)
	{
		this.context = ctx;
		this.setCommunicateURI(commURI);
	}
	
	/**
	 * 主通信方法
	 * @param cb 通信结束后的回调接口
	 * @param cookie 回调接口的最后一个参数
	 */
	@Override
	public void communicate(final CommunicationCallBack cb, final Object cookie) {

		// 构造头部和体部，其中getBody将在派生类中实现
		JSONObject head = this.getHead();
		JSONObject body = this.getBody();
		final JSONObject commObj = new JSONObject();
		try
		{
			commObj.putOpt(HEADER_PART_NAME, head);
			if (body != null)
			{
				commObj.putOpt(BODY_PART_NAME, body);
			}
		}
		catch(JSONException e)
		{
			e.printStackTrace();
			// TODO
		}
		
		try {
			HttpUtility.post(this.context, this.getCommunicateURI(), commObj, 
					new JsonHttpResponseHandler()
				{

					public void onFailure(Throwable arg0) {
						Log.e("JSONCommunicationBase", " onFailure" + arg0.toString());
					};
					public void onFinish() {
						Log.i("JSONCommunicationBase", "onFinish");
					};
					public void onSuccess(JSONObject arg0) {   //返回的是JSONObject，会调用这里
						Log.i("JSONCommunicationBase", "onSuccess ");
						//TODO: cb.response(result, resultDetail, resData, cookie)
					};
				}
			);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	protected abstract JSONObject getHead();
	
	protected abstract JSONObject getBody();
	
}
