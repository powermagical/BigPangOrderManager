package com.caferestaurant.orderingsystem.app.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * �ٲ����Ļ�����ͼ
 * �����漰���ٲ�������ͼ����Ӵ�����
 * ����TestViewWaterfall���ʵ��
 * @author wangji
 *
 * ע��!!!
 * ViewBase����������±�����дOnLayout!!!
 * ���Լ�ʵ�����й��ܣ�����
 */
public abstract class ViewWaterfallBase extends ViewBase{

	private LayoutInflater inflater;
	
	public ViewWaterfallBase(Context context) {
		super(context);
		this.init(context);
	}
	
	public ViewWaterfallBase(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init(context);
	}

	private WaterfallLayout waterfall;
	
	/**
	 * 1.�����ڲ���waterfall layout
	 * 2.���ڲ�layout����Ϊ��������Ϊ����ͼ�ĸ��ؼ�
	 * 3.�趨��һ�θ��µ��¼������Լ��������ײ����¼�����
	 * @param context
	 */
	private void init(Context context)
	{
		this.setInflater(LayoutInflater.from(context));
		
		this.waterfall = new WaterfallLayout(context);
		this.waterfall.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		this.addView(this.waterfall);
		
		this.waterfall.setFirstTimeDrawHandler(new WaterfallLayout.FirstTimeDrawListener() {
			
			@Override
			public void onFirstTimeDraw(View target) {
				onDrawStart(target);
			}
		});
		
		this.waterfall.setScrollToBottomHandler(new WaterfallLayout.ScrollToBottomListener() {
			
			@Override
			public void onScrollToBottom(View target) {
				onGotoBottom(target);
			}
		});

	}
	
	protected void addViewToWaterfall(View v)
	{
		//int curScrollPos = this.getScrollY();
		this.waterfall.addViewToIndexFlow(v, this.waterfall.getShortestFlowIndex());
		//this.scrollTo(0, curScrollPos);
	}
	
	/**
	 * �״���Ⱦ��ʼ�¼�����
	 */
	protected abstract void onDrawStart(View t);
	
	/*
	 * �������ײ����¼�����
	 */
	protected abstract void onGotoBottom(View t);
	
	protected void setDataFinished()
	{
		this.waterfall.setLoadingFinished(true);
		this.waterfall.setMessageViewGone();
		
	}
	
	protected void setLoadingFinished()
	{
		this.waterfall.setLoadingProcess(false);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed)
		{
			this.setLeft(l);
			this.setTop(t);
			this.setRight(r);
			this.setBottom(b);

			// ����l t r b�Ǳ�view�����parent�����꣬������Ҫ������ԭ��(l,t)
			this.waterfall.layout(l - l, t - t, r - l, b - t);
		}
	}

	public LayoutInflater getInflater() {
		return inflater;
	}

	private void setInflater(LayoutInflater inflater) {
		this.inflater = inflater;
	}

	@Override
	public String getViewCaption() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.waterfall.measure(widthMeasureSpec, heightMeasureSpec);
	}
	
	
	
}
