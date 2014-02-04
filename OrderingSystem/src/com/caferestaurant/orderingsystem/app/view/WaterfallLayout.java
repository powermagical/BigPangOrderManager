package com.caferestaurant.orderingsystem.app.view;

import java.util.ArrayList;
import java.util.List;

import com.caferestaurant.orderingsystem.app.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 瀑布流的基础布局
 */
public class WaterfallLayout extends ScrollView {

	private static final int DEFAULT_WATERFALL_NUM = 2;
	
	private List<LinearLayout> flowLayout = new ArrayList<LinearLayout>();
	private int[] flowLength = null;
	
	private boolean isFirstDraw = true;
	
	private TextView messageView = null;
	
	private LinearLayout root = null;
	
	private LinearLayout picturesLayout = null;
	
	/**
	 * 第一次绘制开始时的回调
	 * 在第一次绘制开始时，可以认为瀑布流内部的各个LinearLayout都已经布局完成
	 * 可以取得其中各个布局的长宽（即列宽已经被确定）
	 */
	public interface FirstTimeDrawListener
	{
		void onFirstTimeDraw(View target);
	}
	
	private Handler firstTimeDrawHandler;

	public void setFirstTimeDrawHandler(FirstTimeDrawListener listener) {
		
		// 获取调用者线程的looper对象
		Looper looper = Looper.myLooper();
		if (looper == null)
		{
			looper = Looper.getMainLooper();
		}
	
		// 构造针对调用者线程looper中处理的处理Handler
		this.firstTimeDrawHandler = new FirstTimeDrawHandler(looper, this, listener);
	}
	
	private static class FirstTimeDrawHandler extends Handler
	{
		private WaterfallLayout outer;
		private FirstTimeDrawListener listener;
		
		public FirstTimeDrawHandler(Looper looper, WaterfallLayout layout, FirstTimeDrawListener listener)
		{
			super(looper);
			this.outer = layout;
			this.listener = listener;
		}
		
		//处理消息
		@Override
		public void handleMessage(Message msg) {
			this.listener.onFirstTimeDraw(this.outer);
		}
	};
	
	
	/**
	 * 滑动到底部的监听器接口
	 * 在滚动到底部时触发此监听器
	 * 本监听器仅仅被触发一次
	 * 本监听器必然在主线程（界面更新线程）中被调用
	 * 是否需要改？？？
	 */
	public interface ScrollToBottomListener
	{
		void onScrollToBottom(View target);
	}
	
	private ScrollToBottomListener scrollToBottomListener;
	
	public void setScrollToBottomHandler(ScrollToBottomListener listener) {
		this.scrollToBottomListener = listener;
	}
	
	/**
	 * 是否界面的“所有”数据已经加载完毕
	 */
	private boolean isLoadingFinished = false;
	
	public boolean isLoadingFinished() {
		return isLoadingFinished;
	}

	public void setLoadingFinished(boolean isLoadingFinished) {
		this.isLoadingFinished = isLoadingFinished;
		if (isLoadingFinished)
		{
			this.setLoadingProcess(false);
		}
	}
	
	/**
	 * 是否界面的数据正在加载中
	 * 如果正在加载中
	 * 1.再一次滚动到底部的事件不会被触发
	 * 2.界面底部的“正在更新”字幕被显示
	 */
	private boolean isLoadingProcess = false;
	
	public boolean isLoadingProcess() {
		return isLoadingProcess;
	}

	public void setLoadingProcess(boolean isLoadingProcess) {
		this.isLoadingProcess = isLoadingProcess;
		
		if (isLoadingProcess == false)
		{
			this.messageView.setVisibility(View.INVISIBLE);
		}
		
	}
	
	public void setMessageViewGone()
	{
		this.messageView.setVisibility(View.GONE);
		
		// 需要再做一次全体的布局调整
		this.measure(MeasureSpec.makeMeasureSpec(this.getMeasuredWidth(), MeasureSpec.EXACTLY), -1);
		this.layout(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
	}
	
	protected int getDefaultColumnNum()
	{
		return WaterfallLayout.DEFAULT_WATERFALL_NUM;
	}

	/**
	 * 基本构造函数
	 * 缺省的列数为2
	 */
	public WaterfallLayout(Context context) {
		super(context);
		
		this.createInnerViews(this.getDefaultColumnNum(), context);
	}
	
	public WaterfallLayout(Context context, int fallNum) {
		super(context);
		
		this.createInnerViews(fallNum, context);
	}

	public WaterfallLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public WaterfallLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		// 自定义属性，取得列数
		// (如果仅仅)
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.WaterfallLayout); 
		int flowNum = a.getInt(R.styleable.WaterfallLayout_flowNumber, this.getDefaultColumnNum()); 
		a.recycle();

		this.createInnerViews(flowNum, context);
	}
	
	/**
	 * 取得某一列的流布局
	 * 流布局的下表为0~N-1
	 */
	public LinearLayout getFlow(int idx)
	{
		return this.flowLayout.get(idx);
	}
	
	/**
	 * 取得流的总数
	 */
	public int getFlowCount()
	{
		return this.flowLayout.size();
	}

	/**
	 * 内部构造
	 * 1.构造出N个数量的流
	 * 2.构造出底部的更新字幕
	 * 3.
	 */
	private void createInnerViews(int flowNumber, Context context)
	{
		this.flowLength = new int[flowNumber];
		for(int i = 0; i < this.flowLength.length; ++i)
		{
			this.flowLength[i] = 0;
		}
		
		LinearLayout llHorizontal = new LinearLayout(context);
		llHorizontal.setOrientation(LinearLayout.HORIZONTAL);
		llHorizontal.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		
		for(int i = 0; i < flowNumber; ++i)
		{
			LinearLayout inVertical = new LinearLayout(context);
			inVertical.setOrientation(LinearLayout.VERTICAL);
			inVertical.setLayoutParams(new LinearLayout.LayoutParams(1, LayoutParams.WRAP_CONTENT, 1.0f));
			
			// dummy view
			DummyView dv = new DummyView(context);
			dv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1));
			dv.setBackgroundColor(Color.BLACK);
			inVertical.addView(dv);
			
			this.flowLayout.add(inVertical);
			llHorizontal.addView(inVertical);
		}
		TextView tv = new TextView(context);
		tv.setText(R.string.waterfall_tail);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		tv.setTextColor(Color.BLACK);
		tv.setGravity(Gravity.CENTER);
		tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		tv.setShadowLayer(2.0f, 2.0f, -2.0f, Color.parseColor("#73804055"));
		tv.setCompoundDrawablesWithIntrinsicBounds(null, this.getResources().getDrawable(R.drawable.loading_small), null, null);
		
		this.messageView = tv;
		
		LinearLayout llVertical = new LinearLayout(context);
		llVertical.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		llVertical.setOrientation(LinearLayout.VERTICAL);
		llVertical.addView(llHorizontal);
		llVertical.addView(tv);
		
		// 一开始的时候消息框不可见
		tv.setVisibility(View.INVISIBLE);
		
		this.picturesLayout = llHorizontal;
		
		// 构造视图
		this.addView(llVertical);
		
		this.root = llVertical;
	}

	/**
	 * 重绘函数
	 * 如果是第一次绘制的话，需要将流布局的横方向LayoutParams指定为固定值（防止加入View的时候改变其列宽）
	 * 并调用首次绘制的回调方法
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if (this.isFirstDraw)
		{
			this.isFirstDraw = false;
			
			for(int i = 0; i < this.flowLayout.size(); ++i)
			{
				this.flowLayout.get(i).setLayoutParams(
						new LinearLayout.LayoutParams(this.flowLayout.get(i).getMeasuredWidth(), LayoutParams.WRAP_CONTENT, 1.0f)
						);
			}
			
			if (this.firstTimeDrawHandler != null)
			{
				this.firstTimeDrawHandler.sendEmptyMessage(0);
			}
		}
	}
	
	protected int getFlowWidth(int idx)
	{
		return this.flowLayout.get(idx).getMeasuredWidth();
	}

	/**
	 * 设定更新消息的可见性
	 * @param v
	 */
	public void setMessageViewVisible(boolean v)
	{
		this.messageView.setVisibility((v == true) ? View.VISIBLE : View.INVISIBLE);
	}
	
	/**
	 * 取得最短的流下标
	 */
	public int getShortestFlowIndex()
	{	
		int shortestIdx = 0;
		for(int i = 1; i < this.flowLayout.size(); ++i)
		{
			if (this.flowLength[shortestIdx] > this.flowLength[i])
			{
				shortestIdx = i;
			}
		}
		
		return shortestIdx;
	}
	
	/**
	 * 取得最长的流下标
	 */
	public int getLongestFlowIndex()
	{	
		int longestIdx = 0;
		for(int i = 1; i < this.flowLayout.size(); ++i)
		{
			if (this.flowLength[longestIdx] < this.flowLength[i])
			{
				longestIdx = i;
			}
		}
		
		return longestIdx;
	}
	
	
	/**
	 * 大小衡量
	 * 由于从ScrollView派生，所以无需自己调用各个子视图的onMeasure
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		for(int i = 0; i < this.flowLayout.size(); ++i)
		{
			this.flowLength[i] = this.flowLayout.get(i).getMeasuredHeight();
		}
	}

	/**
	 * 将v添加到下标为i的流布局中
	 * 不管v的大小参数如何，流布局的宽度不会改变
	 * 在将v添加到布局后，框架将调用measure和layout以使得视图宽高自动计算
	 * 调用方不需要自己调用任何调整
	 */
	public void addViewToIndexFlow(View v, int i)
	{
		if (i > this.flowLayout.size())
		{
			throw new IllegalArgumentException("Invalid index in addViewToIndexFlow! Index:" + i);
		}
		
		LinearLayout.LayoutParams addParams = 
				new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		addParams.rightMargin = getResources().getDimensionPixelSize(R.dimen.waterfall_right_margin);
		addParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.waterfall_bottom_margin);
		
		
		this.flowLayout.get(i).addView(v, addParams);
		this.measure(MeasureSpec.makeMeasureSpec(this.getMeasuredWidth(), MeasureSpec.EXACTLY), -1);
		this.flowLength[i] = this.flowLayout.get(i).getMeasuredHeight();
		this.layout(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
	}

	/**
	 * 监听scroll事件
	 * 下列条件同时满足的话将触发scroll到底部事件
	 * 1.没有把所有数据Load完（isLoadingFinished）
	 * 2.当前不处于load阶段（isLoadingProcess）
	 * 3.滚动到底部（不包括加载字幕）
	 */
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		
		super.onScrollChanged(l, t, oldl, oldt);
		if (t + WaterfallLayout.this.getHeight()
				>= WaterfallLayout.this.picturesLayout.getMeasuredHeight())
		{
			// 若Load没有完全完成且尚未开始再次load，则显示Loading的图标并触发监听器，同时置load中flag为true
			if (!this.isLoadingFinished)
			{
				if (!this.isLoadingProcess)
				{
					Log.e("onScrollChanged", "entering update message");
					this.messageView.setVisibility(View.VISIBLE);
					this.isLoadingProcess = true;
					
					if (this.scrollToBottomListener != null)
					{
						Log.e("onScrollChanged", "calling onScrollToBottom");
						this.scrollToBottomListener.onScrollToBottom(this);
					}
				}
			}
		}
	}
}
