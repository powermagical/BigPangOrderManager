package com.caferestaurant.orderingsystem.app.view;

import com.caferestaurant.orderingsystem.app.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

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
	
	public ViewWaterfallBase(Context context) {
		super(context);
		this.init(context);
	}
	
	public ViewWaterfallBase(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init(context);
	}

	// 实际的瀑布流视图
	private WaterfallLayout waterfall;
	
	// 注意！！！所有的瀑布流视图均内含等待界面
	private ViewBaseReadyWait base_view;
	
	/**
	 * 1.生成内部的waterfall layout以及等待界面，waterfall layout是等待界面的一个子视图。等待界面还包括一个等待视图
	 * 2.将内部layout设置为充满并置为本视图的父控件
	 * 3.设定第一次更新的事件处理以及滚动到底部的事件处理
	 * 注意！！！
	 * 有两个首次显示的事件。
	 * 基础等待界面的显示事件标志着界面构造完成，可以进行初始化处理
	 * 瀑布流布局的显示事件标志着瀑布流的界面构造完成，可以往瀑布流布局中添加各种视图
	 * @param context
	 */
	private void init(Context context)
	{
		
		this.waterfall = new WaterfallLayout(context);
		this.waterfall.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		this.base_view = new ViewBaseReadyWait(context);
		this.base_view.setInnerView(this.waterfall);
		
		// 初始化处理可以开始的监听器
		this.base_view.setFirstDrawListener(new ViewBaseReadyWait.ViewBaseReadyWaitFirstDrawListener() {
			@Override
			public void onFirstDraw(View v) {
				ViewWaterfallBase.this.onInitFirstDraw(v);
			}
		});
		
		this.addView(this.base_view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		// 可以往瀑布流中安全添加视图的监听器
		this.waterfall.setFirstTimeDrawHandler(new WaterfallLayout.FirstTimeDrawListener() {
			
			@Override
			public void onFirstTimeDraw(View target) {
				onDrawStart(target);
			}
		});
		
		// 瀑布流视图滚动到底部的监听器
		this.waterfall.setScrollToBottomHandler(new WaterfallLayout.ScrollToBottomListener() {
			
			@Override
			public void onScrollToBottom(View target) {
				onGotoBottom(target);
			}
		});

	}
	
	
	/**
	 * 往（最短）的瀑布流中添加视图
	 * @param v
	 */
	protected void addViewToWaterfall(View v)
	{
		this.waterfall.addViewToIndexFlow(v, this.waterfall.getShortestFlowIndex());
	}
	
	/**
	 * 首次渲染开始事件处理
	 */
	protected abstract void onDrawStart(View t);
	
	/**
	 * 初始化界面开始显示处理（即可以开始业务的初始化操作）
	 * @param t
	 */
	protected abstract void onInitFirstDraw(View t);
	
	/*
	 * 滚动到底部的事件处理
	 */
	protected abstract void onGotoBottom(View t);
	
	/**
	 * 数据添加完成
	 * 以后滚动到底部时将不会触发事件
	 */
	protected void setDataFinished()
	{
		this.waterfall.setLoadingFinished(true);
		this.waterfall.setMessageViewGone();
		
	}
	
	/**
	 * 设定本次load结束（可以有多次load，一直到setDataFinished被调用为止）
	 */
	protected void setLoadingFinished()
	{
		this.waterfall.setLoadingProcess(false);
	}
	
	/**
	 * 关闭等待界面
	 */
	protected void finishInit()
	{
		this.base_view.setWaitReady();
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
			this.base_view.layout(l - l, t - t, r - l, b - t);
		}
	}

	@Override
	public String getViewCaption() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.base_view.measure(widthMeasureSpec, heightMeasureSpec);
	}
	
	
	
}
