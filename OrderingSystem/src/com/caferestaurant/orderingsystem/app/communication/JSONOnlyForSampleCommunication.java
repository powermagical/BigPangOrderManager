package com.caferestaurant.orderingsystem.app.communication;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class JSONOnlyForSampleCommunication extends JSONCommunicationBase{

	
	// ��Ӧ�峣��
	// ����
	private static String COUNT = "count";
	
	// ����״��ȫ��
	private static String MARRIAGE = "marriage";
	
	// ����״����ʾ��
	private static String MARRIAGE_NAME = "marriage_name";
	
	// ����״��code
	private static String MARRIAGE_CODE = "marriage_code";
	
	public JSONOnlyForSampleCommunication(Context ctx) {
		super(ctx);
	}
	
	public JSONOnlyForSampleCommunication(Context ctx, String url) {
		super(ctx, url);
	}

	@Override
	protected Object parseResponseBody(JSONObject responseObj) throws Exception {
		
		// ����ֵ��Ӧ�ý��������������ݷŵ�ĳ�����ݽṹ�б���
		// ������ֻ��ʾ������ֻ������null
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
		// ������û����
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
