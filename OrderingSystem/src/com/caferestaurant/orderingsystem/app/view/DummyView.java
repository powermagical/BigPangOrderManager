package com.caferestaurant.orderingsystem.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * û���ر��ô���VIEW
 * һ������ռλ���ߵ���
 *
 */
public class DummyView extends View{

	private Handler mainHandler;
	
	// ��View��һ�α���ʾʱ�Ļص��ӿ�
	public interface FirstDrawListener
	{
		void onFirstDrawStart(View v);
	}
	
	private FirstDrawListener lsner;
	
	public DummyView(Context context) {
		super(context);
	}
	
	public DummyView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (this.mainHandler != null)
		{
			this.mainHandler.sendEmptyMessage(0);
		}
		if (this.isFirstDraw)
		{
			this.isFirstDraw = false;
			if (this.lsner != null)
			{
				this.lsner.onFirstDrawStart(this);
			}
		}
	}

	public void setMainHandler(Handler mainHandler) {
		this.mainHandler = mainHandler;
	}

	// ������ǳ���Ҫ������
	// �漰������TOUCH�¼�����
	// ���������TRUE���򷵻ظ����ĸ��ؼ��ı���������
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}

	private boolean isFirstDraw = true;
	
	public void setFirstDrawListener(FirstDrawListener lsner) {
		this.lsner = lsner;
	}
}
