package com.caferestaurant.orderingsystem.app.view;

import com.caferestaurant.orderingsystem.app.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * 具备首次显示时先显示等待界面，然后关闭等待界面，显示innerView的视图
 * @author Administrator
 *
 */
public class ViewBaseReadyWait extends ViewBase {

	// 本View第一次被显示的监听器接口
	public interface ViewBaseReadyWaitFirstDrawListener
	{
		void onFirstDraw(View v);
	}
	
	// 监听器
	private ViewBaseReadyWaitFirstDrawListener firstDrawListener;
	
	// 基础View，其中包含了等待界面以及后来加进去的实际需要显示的界面
	private RelativeLayout baseView;
	
	// 实际需要显示的界面，由外部设定
	private View innerView;
	
	// 等待界面
	private View waitView;
	
	// 用来实际监听首次描绘的dummy视图，位于左上角，1像素见方的白色视图
	private DummyView dummyView;
	
	
	public ViewBaseReadyWait(Context context) {
		super(context);
		this.initLayout(context);
	}
	
	public ViewBaseReadyWait(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initLayout(context);
	}
	
	private void initLayout(Context context)
	{
		// 构造基本视图（具备等待界面的RelativeLayout）
		this.baseView = (RelativeLayout)
				this.getLayoutInflater().inflate(R.layout.base_ready_wait_layout, null, false);
		
		// 抽取等待界面的View并把基本视图加为子视图
		this.waitView = this.baseView.findViewById(R.id.progressbar_pink);
		this.addView(baseView);
		
		// 为了监视首次描绘事件，构造dummy视图
		this.dummyView = new DummyView(context);
		this.addView(this.dummyView, new LayoutParams(1, 1));
		this.dummyView.setFirstDrawListener(new DummyView.FirstDrawListener() {
			
			// dummyView的首次描绘事件的监听器，直接调用外部Class的firstDrawListener
			@Override
			public void onFirstDrawStart(View v) {
				if (ViewBaseReadyWait.this.firstDrawListener != null)
				{
					ViewBaseReadyWait.this.firstDrawListener.onFirstDraw(ViewBaseReadyWait.this);
				}
				
			}
		});
	}
	
	// 设定内部View。内部View一开始不显示
	public void setInnerView(View v)
	{
		this.innerView = v;
		
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		
		this.baseView.addView(this.innerView, lp);
		v.setVisibility(INVISIBLE);
	}
	
	// 获取内部View
	public View getInnerView()
	{
		return this.innerView;
	}

	// layout方法。在将基本视图定位后，将dummyview定位为一个像素见方
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed)
		{
			this.baseView.layout(l - l, t - t, r - 1, b - t);
			this.dummyView.layout(0, 0, 1, 1);
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.baseView.measure(widthMeasureSpec, heightMeasureSpec);
		//this.setMeasuredDimension(this.baseView.getMeasuredWidth(), this.baseView.getMeasuredHeight());
	}

	// 这个没啥用，纯属为了实例化
	@Override
	public String getViewCaption() {
		return null;
	}
	
	// 设置初始化结束
	// 本方法中将隐藏等待界面，并显示内部实际视图
	public void setWaitReady()
	{
		this.innerView.setVisibility(VISIBLE);
		this.waitView.setVisibility(INVISIBLE);
	}

	// 设定首次描绘的监听器
	public void setFirstDrawListener(ViewBaseReadyWaitFirstDrawListener firstDrawListener) {
		this.firstDrawListener = firstDrawListener;
	}
	
}
