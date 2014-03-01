package com.caferestaurant.orderingsystem.app.communication;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class JSONOnlyForSampleCommunication extends JSONCommunicationBase{

	
	// 响应体常量
	// 计数
	private static String COUNT = "count";
	
	// 婚姻状况全体
	private static String MARRIAGE = "marriage";
	
	// 婚姻状况显示名
	private static String MARRIAGE_NAME = "marriage_name";
	
	// 婚姻状况code
	private static String MARRIAGE_CODE = "marriage_code";
	
	public JSONOnlyForSampleCommunication(Context ctx) {
		super(ctx);
	}
	
	public JSONOnlyForSampleCommunication(Context ctx, String url) {
		super(ctx, url);
	}

	@Override
	protected Object parseResponseBody(JSONObject responseObj) throws Exception {
		
		// 返回值中应该讲解析出来的数据放到某个数据结构中保存
		// 但这里只是示例所以只返回了null
			if (responseObj.getInt(COUNT) > 0)
			{
				JSONArray marriageArray = responseObj.getJSONArray(MARRIAGE);
				for(int i = 0; i < responseObj.getInt(COUNT); ++i)
				{
					String cName = marriageArray.getJSONObject(i).getString(MARRIAGE_NAME);
					String cID = marriageArray.getJSONObject(i).getString(MARRIAGE_CODE);
				}
			}
		
		
		return null;
	}

	@Override
	protected JSONObject getBody() throws JSONException {
		// 本处理没有体
		return null;
	}

	@Override
	protected String getModuleName()
	{
		return "common";
	}
	
	@Override
	protected String getScreenName()
	{
		return "marriage";
	}
	
	@Override
	protected String getFunctionName()
	{
		return "list";
	}
	
	

}
