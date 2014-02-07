package com.caferestaurant.orderingsystem.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 没有特别用处的VIEW
 * 一般用来占位或者调试
 *
 */
public class DummyView extends View{

	private Handler mainHandler;
	
	// 本View第一次被显示时的回调接口
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

	// 本处理非常重要！！！
	// 涉及到外层的TOUCH事件拦截
	// 如果不返回TRUE，则返回给外层的父控件的本方法处理
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}

	private boolean isFirstDraw = true;
	
	public void setFirstDrawListener(FirstDrawListener lsner) {
		this.lsner = lsner;
	}
}
