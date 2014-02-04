package com.caferestaurant.orderingsystem.app.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 瀑布流的基类视图
 * 所有涉及到瀑布流的视图必须从此派生
 * 参照TestViewWaterfall类的实现
 * @author wangji
 *
 * 注意!!!
 * ViewBase派生的情况下必须重写OnLayout!!!
 * 并自己实现所有功能！！！
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
	 * 1.生成内部的waterfall layout
	 * 2.将内部layout设置为充满并置为本视图的父控件
	 * 3.设定第一次更新的事件处理以及滚动到底部的事件处理
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
	 * 首次渲染开始事件处理
	 */
	protected abstract void onDrawStart(View t);
	
	/*
	 * 滚动到底部的事件处理
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

			// 由于l t r b是本view相对于parent的坐标，所以需要调整到原点(l,t)
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
