package com.caferestaurant.orderingsystem.app.communication;

/**
 * 通信基类
 * 本基类被所有通信类继承
 *
 */
public abstract class CommunicationBase {

	/** 通信地址 */
	private String communicateURI;

	/** 获取通信URI */
	protected String getCommunicateURI() {
		return communicateURI;
	}

	/** 设置通信URI */
	protected void setCommunicateURI(String communicateURI) {
		this.communicateURI = communicateURI;
	}
	
	/** 主通信方法 */
	public abstract void communicate(final CommunicationCallBack cb, final Object cookie);
	
	/** 回调接口定义 */
	public static interface CommunicationCallBack
	{
		public void response(int result, Object resData, Object cookie); 
	}
	
}
