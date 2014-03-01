package com.caferestaurant.orderingsystem.app.communication;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSONͨ���õĸ���������
 * ò�Ƶ�ĿǰΪֹ���ò���
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
