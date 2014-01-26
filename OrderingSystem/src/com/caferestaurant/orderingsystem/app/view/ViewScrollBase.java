package com.caferestaurant.orderingsystem.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * ������������ͼ����
 * ��ʵ����ֱ��ʹ�á�����
 * �����ṩ��ĳ���л���ɵ��¼�����
 * @author wangji
 *
 */
public class ViewScrollBase extends ViewBase {

	private ScrollLayout scrollLayout;
	
	private boolean needRebuildChilds = true;
	
	private int currentScreen = -1;
	
	public ViewScrollBase(Context context) {
		super(context);
		this.initLayout(context);
	}
	
	public ViewScrollBase(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initLayout(context);
	}

	public int getCurrentScreen()
	{
		return this.currentScreen;
	}
	
	private void initLayout(Context context)
	{
		this.scrollLayout = new ScrollLayout(context, null);
		this.scrollLayout.setLayoutParams(
				new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		this.scrollLayout.addScrollEndListener(new ScrollLayout.ScrollEndListener() {
			
			@Override
			public void onScrollEnd(int curScreen) {
				if (ViewScrollBase.this.currentScreen != curScreen)
				{
					ViewScrollBase.this.currentScreen = curScreen;
					ViewScrollBase.this.onSlideEnd(curScreen);
				}
			}
		});
	}
	
	@Override
	public String getViewCaption() {
		return null;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed)
		{
			this.scrollLayout.layout(l - l, t - t, r - l, b - t);
		};
	}
	
	/**
	 * �ڵ�һ�ε���measureʱ������ǰ��ͼ�µ���������ͼȫ��ת�Ƶ�scrollLayout�У�
	 * ����scrollLayout���Ϊ��ǰ��ͼ������ͼ
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int childNum = this.getChildCount();
		
		if (this.needRebuildChilds)
		{
			for(int i = 0; i < childNum; ++i)
			{
				View cld = this.getChildAt(0);
				this.removeView(cld);
				this.scrollLayout.addView(cld);
			}
			this.addView(this.scrollLayout);
			
			this.needRebuildChilds = false;
		}
		
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.scrollLayout.measure(widthMeasureSpec, heightMeasureSpec);
	}

	protected void onSlideEnd(int curScreen)
	{
		Log.e("ViewScrollBase", "in onSlideEnd");
	}
	
	
}
