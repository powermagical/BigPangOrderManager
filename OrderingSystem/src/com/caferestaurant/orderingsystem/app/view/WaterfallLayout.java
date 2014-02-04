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
 * �ٲ����Ļ�������
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
	 * ��һ�λ��ƿ�ʼʱ�Ļص�
	 * �ڵ�һ�λ��ƿ�ʼʱ��������Ϊ�ٲ����ڲ��ĸ���LinearLayout���Ѿ��������
	 * ����ȡ�����и������ֵĳ������п��Ѿ���ȷ����
	 */
	public interface FirstTimeDrawListener
	{
		void onFirstTimeDraw(View target);
	}
	
	private Handler firstTimeDrawHandler;

	public void setFirstTimeDrawHandler(FirstTimeDrawListener listener) {
		
		// ��ȡ�������̵߳�looper����
		Looper looper = Looper.myLooper();
		if (looper == null)
		{
			looper = Looper.getMainLooper();
		}
	
		// ������Ե������߳�looper�д���Ĵ���Handler
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
		
		//������Ϣ
		@Override
		public void handleMessage(Message msg) {
			this.listener.onFirstTimeDraw(this.outer);
		}
	};
	
	
	/**
	 * �������ײ��ļ������ӿ�
	 * �ڹ������ײ�ʱ�����˼�����
	 * ������������������һ��
	 * ����������Ȼ�����̣߳���������̣߳��б�����
	 * �Ƿ���Ҫ�ģ�����
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
	 * �Ƿ����ġ����С������Ѿ��������
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
	 * �Ƿ������������ڼ�����
	 * ������ڼ�����
	 * 1.��һ�ι������ײ����¼����ᱻ����
	 * 2.����ײ��ġ����ڸ��¡���Ļ����ʾ
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
		
		// ��Ҫ����һ��ȫ��Ĳ��ֵ���
		this.measure(MeasureSpec.makeMeasureSpec(this.getMeasuredWidth(), MeasureSpec.EXACTLY), -1);
		this.layout(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
	}
	
	protected int getDefaultColumnNum()
	{
		return WaterfallLayout.DEFAULT_WATERFALL_NUM;
	}

	/**
	 * �������캯��
	 * ȱʡ������Ϊ2
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
		
		// �Զ������ԣ�ȡ������
		// (�������)
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.WaterfallLayout); 
		int flowNum = a.getInt(R.styleable.WaterfallLayout_flowNumber, this.getDefaultColumnNum()); 
		a.recycle();

		this.createInnerViews(flowNum, context);
	}
	
	/**
	 * ȡ��ĳһ�е�������
	 * �����ֵ��±�Ϊ0~N-1
	 */
	public LinearLayout getFlow(int idx)
	{
		return this.flowLayout.get(idx);
	}
	
	/**
	 * ȡ����������
	 */
	public int getFlowCount()
	{
		return this.flowLayout.size();
	}

	/**
	 * �ڲ�����
	 * 1.�����N����������
	 * 2.������ײ��ĸ�����Ļ
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
		
		// һ��ʼ��ʱ����Ϣ�򲻿ɼ�
		tv.setVisibility(View.INVISIBLE);
		
		this.picturesLayout = llHorizontal;
		
		// ������ͼ
		this.addView(llVertical);
		
		this.root = llVertical;
	}

	/**
	 * �ػ溯��
	 * ����ǵ�һ�λ��ƵĻ�����Ҫ�������ֵĺ᷽��LayoutParamsָ��Ϊ�̶�ֵ����ֹ����View��ʱ��ı����п�
	 * �������״λ��ƵĻص�����
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
	 * �趨������Ϣ�Ŀɼ���
	 * @param v
	 */
	public void setMessageViewVisible(boolean v)
	{
		this.messageView.setVisibility((v == true) ? View.VISIBLE : View.INVISIBLE);
	}
	
	/**
	 * ȡ����̵����±�
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
	 * ȡ��������±�
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
	 * ��С����
	 * ���ڴ�ScrollView���������������Լ����ø�������ͼ��onMeasure
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
	 * ��v��ӵ��±�Ϊi����������
	 * ����v�Ĵ�С������Σ������ֵĿ�Ȳ���ı�
	 * �ڽ�v��ӵ����ֺ󣬿�ܽ�����measure��layout��ʹ����ͼ����Զ�����
	 * ���÷�����Ҫ�Լ������κε���
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
	 * ����scroll�¼�
	 * ��������ͬʱ����Ļ�������scroll���ײ��¼�
	 * 1.û�а���������Load�꣨isLoadingFinished��
	 * 2.��ǰ������load�׶Σ�isLoadingProcess��
	 * 3.�������ײ���������������Ļ��
	 */
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		
		super.onScrollChanged(l, t, oldl, oldt);
		if (t + WaterfallLayout.this.getHeight()
				>= WaterfallLayout.this.picturesLayout.getMeasuredHeight())
		{
			// ��Loadû����ȫ�������δ��ʼ�ٴ�load������ʾLoading��ͼ�겢������������ͬʱ��load��flagΪtrue
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
