package com.caferestaurant.orderingsystem.app.communication;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSON通信用的辅助方法类
 * 貌似到目前为止还用不到
 * @author wangji
 *
 */
public class JSONCommunicationUtil {
	private static int currentInvokeID = 1;
	
	public static int getInvokeID()
	{
		synchronized(JSONCommunicationUtil.class)
		{
			return currentInvokeID++;
		}
	}
}
