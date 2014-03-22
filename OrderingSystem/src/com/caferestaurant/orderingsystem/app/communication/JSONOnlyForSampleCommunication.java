package com.caferestaurant.orderingsystem.app.communication;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class JSONOnlyForSampleCommunication extends JSONCommunicationBase{

	
	// ��Ӧ�峣��
	// �Ƽ�һ������
	private static String RECOMMEND = "recommend";
	
	// ĳ���Ƽ��ı���
	private static String RECOMMEND_TITLE = "recommend_title";
	
	// ĳ���Ƽ�������
	private static String RECOMMEND_DESCRIPTION = "recommend_description";
	
	// ĳ���Ƽ�������
	private static String SORT = "sort";
	
	// ĳ���Ƽ���������Ʒ��ϵ�ȫ��
	private static String ITEMS = "items";
	
	// ��Ʒ����˵��
	private static String ITEM_DESCRIPTION = "item_description";
	
	// ��ƷͼƬURL
	private static String ITEM_PICTURE = "item_picture";
	
	// ��Ʒ��ţ�1-5��
	private static String ITEM_NO = "item_no";
	
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
		JSONArray recommendArray = responseObj.optJSONArray(RECOMMEND);
		
		if (recommendArray != null)
		{
			for(int i = 0; i < recommendArray.length(); ++i)
			{
				JSONObject oneRecommend = recommendArray.getJSONObject(i);
				oneRecommend.getString(RECOMMEND_TITLE);
				oneRecommend.getString(RECOMMEND_DESCRIPTION);
				oneRecommend.getInt(SORT);
				JSONArray itemArray = oneRecommend.getJSONArray(ITEMS);
				for(int j = 0; j < itemArray.length(); ++j)
				{
					JSONObject item = itemArray.getJSONObject(j);
					item.getString(ITEM_DESCRIPTION);
					item.getString(ITEM_PICTURE);
					item.getInt(ITEM_NO);
				}
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
		return "recommend";
	}
	
	@Override
	protected String getScreenName()
	{
		return "recommend_list";
	}
	
	@Override
	protected String getFunctionName()
	{
		return "list";
	}
	
	

}
