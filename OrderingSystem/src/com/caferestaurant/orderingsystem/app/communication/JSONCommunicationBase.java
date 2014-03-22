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
	
	/** ��Ϣͷ�������ֶ��� */
	private static String MAGIC_NAME = "magic";
	private static String INVOKE_NAME = "invoke_id";
	private static String VERSION_NAME = "version";
	private static String DIGEST_NAME = "digest";
	private static String MODULE_NAME = "module";
	private static String SCREEN_NAME = "screen";
	private static String FUNCTION_NAME = "function";
	
	/** Ӧ����Ϣͷ�������ֶ��� */
	private static String RESULT_CODE = "result_code";
	
	public static int RESULT_CODE_SUCCESS = 0;
	public static int RESULT_CODE_FAILURE_HEADBODY = 1000;
	public static int RESULT_CODE_HTTP_POST = 2000;
	public static int RESULT_CODE_FAILURE_COMMUNICATE = 3000;
	public static int RESULT_CODE_FAILURE_SERVER_CLOSED = 4000;
	public static int RESULT_CODE_FAILURE_PARSE = 5000;
	
	/** ��ͷMAGIC�ֶ� */
	private static String MAGIC_DEFALUT = "���ֵĳ���";
	/** ��ͷVERSION�ֶ� */
	private static String VERSION_DEFAULT = "1.0";
	
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

		final JSONObject commObj = new JSONObject();
		try
		{
			// ����ͷ�����岿������getBody������������ʵ��
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
			// TODO:��¼�쳣��Ϣ
			cb.response(RESULT_CODE_FAILURE_HEADBODY, null, cookie); // header����body�Ļ�ȡ�������쳣
			return;
		}
		
		try {
			HttpUtility.post(this.context, this.getCommunicateURI(), commObj, 
					new JsonHttpResponseHandler()
				{

					public void onFailure(Throwable arg0) {
						Log.e("JSONCommunicationBase", " onFailure" + arg0.toString());
						// TODO:��¼�쳣��Ϣ
						cb.response(RESULT_CODE_FAILURE_COMMUNICATE, null, cookie);
					};
					public void onFinish() {
						Log.i("JSONCommunicationBase", "onFinish");
					};
					public void onSuccess(JSONObject arg0) {   //���ص���JSONObject�����������
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
							// TODO:��¼�쳣��Ϣ
							cb.response(RESULT_CODE_FAILURE_PARSE, null, cookie);
						}
						cb.response(RESULT_CODE_SUCCESS, res, cookie);
						
					};
				}
			);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			// TODO:��¼�쳣��Ϣ
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
	 * �����������˷��ص�JSON����
	 * @param responseObj �������˷��ص�JSON����
	 * @return ���������Ϣ���������Լ�����
	 */
	abstract protected Object parseResponseBody(JSONObject responseObj) throws Exception;
	
	protected abstract JSONObject getBody() throws JSONException;
	
	/**
	 * ȡ��ͨ�Ű汾��������һ�㲻��Ҫ��д
	 */
	protected String getVersion()
	{
		return JSONCommunicationBase.VERSION_DEFAULT;
	}
	
	/**
	 * ȡ��ͨ��HEAD�е�module�ֶ�
	 */
	abstract protected String getModuleName();
	
	/**
	 * ȡ��ͨ��HEAD�е�screen�ֶ�
	 */
	abstract protected String getScreenName();
	
	/**
	 * ȡ��ͨ��HEAD�е�function�ֶ�
	 */
	abstract protected String getFunctionName();
	
}
