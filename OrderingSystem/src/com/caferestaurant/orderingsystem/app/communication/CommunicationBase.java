package com.caferestaurant.orderingsystem.app.communication;

/**
 * ͨ�Ż���
 * �����౻����ͨ����̳�
 *
 */
public abstract class CommunicationBase {

	/** ͨ�ŵ�ַ */
	private String communicateURI;

	/** ��ȡͨ��URI */
	protected String getCommunicateURI() {
		return communicateURI;
	}

	/** ����ͨ��URI */
	protected void setCommunicateURI(String communicateURI) {
		this.communicateURI = communicateURI;
	}
	
	/** ��ͨ�ŷ��� */
	public abstract void communicate(final CommunicationCallBack cb, final Object cookie);
	
	/** �ص��ӿڶ��� */
	public static interface CommunicationCallBack
	{
		public void response(int result, Object resData, Object cookie); 
	}
	
}
