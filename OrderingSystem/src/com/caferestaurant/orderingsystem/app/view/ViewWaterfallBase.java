package com.caferestaurant.orderingsystem.app.view;

import com.caferestaurant.orderingsystem.app.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

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
	
	public ViewWaterfallBase(Context context) {
		super(context);
		this.init(context);
	}
	
	public ViewWaterfallBase(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init(context);
	}

	// ʵ�ʵ��ٲ�����ͼ
	private WaterfallLayout waterfall;
	
	// ע�⣡�������е��ٲ�����ͼ���ں��ȴ�����
	private ViewBaseReadyWait base_view;
	
	/**
	 * 1.�����ڲ���waterfall layout�Լ��ȴ����棬waterfall layout�ǵȴ������һ������ͼ���ȴ����滹����һ���ȴ���ͼ
	 * 2.���ڲ�layout����Ϊ��������Ϊ����ͼ�ĸ��ؼ�
	 * 3.�趨��һ�θ��µ��¼������Լ��������ײ����¼�����
	 * ע�⣡����
	 * �������״���ʾ���¼���
	 * �����ȴ��������ʾ�¼���־�Ž��湹����ɣ����Խ��г�ʼ������
	 * �ٲ������ֵ���ʾ�¼���־���ٲ����Ľ��湹����ɣ��������ٲ�����������Ӹ�����ͼ
	 * @param context
	 */
	private void init(Context context)
	{
		
		this.waterfall = new WaterfallLayout(context);
		this.waterfall.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		this.base_view = new ViewBaseReadyWait(context);
		this.base_view.setInnerView(this.waterfall);
		
		// ��ʼ��������Կ�ʼ�ļ�����
		this.base_view.setFirstDrawListener(new ViewBaseReadyWait.ViewBaseReadyWaitFirstDrawListener() {
			@Override
			public void onFirstDraw(View v) {
				ViewWaterfallBase.this.onInitFirstDraw(v);
			}
		});
		
		this.addView(this.base_view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		// �������ٲ����а�ȫ�����ͼ�ļ�����
		this.waterfall.setFirstTimeDrawHandler(new WaterfallLayout.FirstTimeDrawListener() {
			
			@Override
			public void onFirstTimeDraw(View target) {
				onDrawStart(target);
			}
		});
		
		// �ٲ�����ͼ�������ײ��ļ�����
		this.waterfall.setScrollToBottomHandler(new WaterfallLayout.ScrollToBottomListener() {
			
			@Override
			public void onScrollToBottom(View target) {
				onGotoBottom(target);
			}
		});

	}
	
	
	/**
	 * ������̣����ٲ����������ͼ
	 * @param v
	 */
	protected void addViewToWaterfall(View v)
	{
		this.waterfall.addViewToIndexFlow(v, this.waterfall.getShortestFlowIndex());
	}
	
	/**
	 * �״���Ⱦ��ʼ�¼�����
	 */
	protected abstract void onDrawStart(View t);
	
	/**
	 * ��ʼ�����濪ʼ��ʾ���������Կ�ʼҵ��ĳ�ʼ��������
	 * @param t
	 */
	protected abstract void onInitFirstDraw(View t);
	
	/*
	 * �������ײ����¼�����
	 */
	protected abstract void onGotoBottom(View t);
	
	/**
	 * ����������
	 * �Ժ�������ײ�ʱ�����ᴥ���¼�
	 */
	protected void setDataFinished()
	{
		this.waterfall.setLoadingFinished(true);
		this.waterfall.setMessageViewGone();
		
	}
	
	/**
	 * �趨����load�����������ж��load��һֱ��setDataFinished������Ϊֹ��
	 */
	protected void setLoadingFinished()
	{
		this.waterfall.setLoadingProcess(false);
	}
	
	/**
	 * �رյȴ�����
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

			// ����l t r b�Ǳ�view�����parent�����꣬������Ҫ������ԭ��(l,t)
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
