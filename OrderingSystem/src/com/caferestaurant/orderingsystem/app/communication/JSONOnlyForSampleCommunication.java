package com.caferestaurant.orderingsystem.app.communication;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class JSONOnlyForSampleCommunication extends JSONCommunicationBase{

	
	// 响应体常量
	// 推荐一览总体
	private static String RECOMMEND = "recommend";
	
	// 某种推荐的标题
	private static String RECOMMEND_TITLE = "recommend_title";
	
	// 某种推荐的描述
	private static String RECOMMEND_DESCRIPTION = "recommend_description";
	
	// 某种推荐的排序
	private static String SORT = "sort";
	
	// 某种推荐的所有商品组合的全体
	private static String ITEMS = "items";
	
	// 商品介绍说明
	private static String ITEM_DESCRIPTION = "item_description";
	
	// 商品图片URL
	private static String ITEM_PICTURE = "item_picture";
	
	// 商品编号（1-5）
	private static String ITEM_NO = "item_no";
	
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
		// 本处理没有体
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
