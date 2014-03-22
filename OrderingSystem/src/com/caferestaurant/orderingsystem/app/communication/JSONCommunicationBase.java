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
	
	/** 消息头部各个字段名 */
	private static String MAGIC_NAME = "magic";
	private static String INVOKE_NAME = "invoke_id";
	private static String VERSION_NAME = "version";
	private static String DIGEST_NAME = "digest";
	private static String MODULE_NAME = "module";
	private static String SCREEN_NAME = "screen";
	private static String FUNCTION_NAME = "function";
	
	/** 应答消息头部特有字段名 */
	private static String RESULT_CODE = "result_code";
	
	public static int RESULT_CODE_SUCCESS = 0;
	public static int RESULT_CODE_FAILURE_HEADBODY = 1000;
	public static int RESULT_CODE_HTTP_POST = 2000;
	public static int RESULT_CODE_FAILURE_COMMUNICATE = 3000;
	public static int RESULT_CODE_FAILURE_SERVER_CLOSED = 4000;
	public static int RESULT_CODE_FAILURE_PARSE = 5000;
	
	/** 先头MAGIC字段 */
	private static String MAGIC_DEFALUT = "大胖的厨房";
	/** 先头VERSION字段 */
	private static String VERSION_DEFAULT = "1.0";
	
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

		final JSONObject commObj = new JSONObject();
		try
		{
			// 构造头部和体部，其中getBody将在派生类中实现
			JSONObject head = this.getHead();
			JSONObject body = this.getBody();
			
			commObj.putOpt(HEADER_PART_NAME, head);
			if (body != null)
			{
				commObj.putOpt(BODY_PART_NAME, body);
			}
		}
		catch(JSONException e)
		{
			e.printStackTrace();
			// TODO:记录异常信息
			cb.response(RESULT_CODE_FAILURE_HEADBODY, null, cookie); // header或者body的获取发生了异常
			return;
		}
		
		try {
			HttpUtility.post(this.context, this.getCommunicateURI(), commObj, 
					new JsonHttpResponseHandler()
				{

					public void onFailure(Throwable arg0) {
						Log.e("JSONCommunicationBase", " onFailure" + arg0.toString());
						// TODO:记录异常信息
						cb.response(RESULT_CODE_FAILURE_COMMUNICATE, null, cookie);
					};
					public void onFinish() {
						Log.i("JSONCommunicationBase", "onFinish");
					};
					public void onSuccess(JSONObject arg0) {   //返回的是JSONObject，会调用这里
						Log.i("JSONCommunicationBase", "onSuccess ");
						Object res = null;
						
						try
						{
							JSONObject resHead = arg0.getJSONObject(HEADER_PART_NAME);
							int resCode = resHead.getInt(RESULT_CODE);
							
							if (resCode == 0)
							{
								JSONObject resBody = arg0.optJSONObject(BODY_PART_NAME);
								if (resBody != null)
								{
									res = parseResponseBody(resBody);
								}
							}
							else
							{
								cb.response(resCode, null, cookie);
								return;
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
							// TODO:记录异常信息
							cb.response(RESULT_CODE_FAILURE_PARSE, null, cookie);
						}
						cb.response(RESULT_CODE_SUCCESS, res, cookie);
						
					};
				}
			);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			// TODO:记录异常信息
			cb.response(RESULT_CODE_HTTP_POST, null, cookie);
			return;
		}
		
	}
	
	
	protected JSONObject getHead()
	{
		JSONObject obj = new JSONObject();
		try 
		{
			obj.putOpt(JSONCommunicationBase.MAGIC_NAME, JSONCommunicationBase.MAGIC_DEFALUT);
			obj.putOpt(JSONCommunicationBase.INVOKE_NAME, JSONCommunicationUtil.getInvokeID());
			obj.putOpt(JSONCommunicationBase.VERSION_NAME, this.getVersion());
			obj.putOpt(JSONCommunicationBase.DIGEST_NAME, "");
			obj.putOpt(JSONCommunicationBase.MODULE_NAME, this.getModuleName());
			obj.putOpt(JSONCommunicationBase.SCREEN_NAME, this.getScreenName());
			obj.putOpt(JSONCommunicationBase.FUNCTION_NAME, this.getFunctionName());
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * 解析服务器端返回的JSON数据
	 * @param responseObj 服务器端返回的JSON数据
	 * @return 解析后的信息，由子类自己定义
	 */
	abstract protected Object parseResponseBody(JSONObject responseObj) throws Exception;
	
	protected abstract JSONObject getBody() throws JSONException;
	
	/**
	 * 取得通信版本。本方法一般不需要重写
	 */
	protected String getVersion()
	{
		return JSONCommunicationBase.VERSION_DEFAULT;
	}
	
	/**
	 * 取得通信HEAD中的module字段
	 */
	abstract protected String getModuleName();
	
	/**
	 * 取得通信HEAD中的screen字段
	 */
	abstract protected String getScreenName();
	
	/**
	 * 取得通信HEAD中的function字段
	 */
	abstract protected String getFunctionName();
	
}
