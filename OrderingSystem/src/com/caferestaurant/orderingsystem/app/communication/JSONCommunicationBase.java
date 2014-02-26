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
	 * JSONȱʡͨ�ŵ�ַ
	 */
	private static String JSON_DEFAULT_URL = 
			"http://111.1.58.76:8080/communityhz/command/queryContent.html";
	
	/** ��Ϣͷ�����ֶ� */
	private static String HEADER_PART_NAME = "head";
	/** ��Ϣ�岿���ֶ� */
	private static String BODY_PART_NAME = "body";
	
	/**
	 * ͨ�������ģ���������async http��
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
	 * ��ͨ�ŷ���
	 * @param cb ͨ�Ž�����Ļص��ӿ�
	 * @param cookie �ص��ӿڵ����һ������
	 */
	@Override
	public void communicate(final CommunicationCallBack cb, final Object cookie) {

		// ����ͷ�����岿������getBody������������ʵ��
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
					public void onSuccess(JSONObject arg0) {   //���ص���JSONObject�����������
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
