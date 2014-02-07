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
 * �߱��״���ʾʱ����ʾ�ȴ����棬Ȼ��رյȴ����棬��ʾinnerView����ͼ
 * @author Administrator
 *
 */
public class ViewBaseReadyWait extends ViewBase {

	// ��View��һ�α���ʾ�ļ������ӿ�
	public interface ViewBaseReadyWaitFirstDrawListener
	{
		void onFirstDraw(View v);
	}
	
	// ������
	private ViewBaseReadyWaitFirstDrawListener firstDrawListener;
	
	// ����View�����а����˵ȴ������Լ������ӽ�ȥ��ʵ����Ҫ��ʾ�Ľ���
	private RelativeLayout baseView;
	
	// ʵ����Ҫ��ʾ�Ľ��棬���ⲿ�趨
	private View innerView;
	
	// �ȴ�����
	private View waitView;
	
	// ����ʵ�ʼ����״�����dummy��ͼ��λ�����Ͻǣ�1���ؼ����İ�ɫ��ͼ
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
		// ���������ͼ���߱��ȴ������RelativeLayout��
		this.baseView = (RelativeLayout)
				this.getLayoutInflater().inflate(R.layout.base_ready_wait_layout, null, false);
		
		// ��ȡ�ȴ������View���ѻ�����ͼ��Ϊ����ͼ
		this.waitView = this.baseView.findViewById(R.id.progressbar_pink);
		this.addView(baseView);
		
		// Ϊ�˼����״�����¼�������dummy��ͼ
		this.dummyView = new DummyView(context);
		this.addView(this.dummyView, new LayoutParams(1, 1));
		this.dummyView.setFirstDrawListener(new DummyView.FirstDrawListener() {
			
			// dummyView���״�����¼��ļ�������ֱ�ӵ����ⲿClass��firstDrawListener
			@Override
			public void onFirstDrawStart(View v) {
				if (ViewBaseReadyWait.this.firstDrawListener != null)
				{
					ViewBaseReadyWait.this.firstDrawListener.onFirstDraw(ViewBaseReadyWait.this);
				}
				
			}
		});
	}
	
	// �趨�ڲ�View���ڲ�Viewһ��ʼ����ʾ
	public void setInnerView(View v)
	{
		this.innerView = v;
		
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		
		this.baseView.addView(this.innerView, lp);
		v.setVisibility(INVISIBLE);
	}
	
	// ��ȡ�ڲ�View
	public View getInnerView()
	{
		return this.innerView;
	}

	// layout�������ڽ�������ͼ��λ�󣬽�dummyview��λΪһ�����ؼ���
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

	// ���ûɶ�ã�����Ϊ��ʵ����
	@Override
	public String getViewCaption() {
		return null;
	}
	
	// ���ó�ʼ������
	// �������н����صȴ����棬����ʾ�ڲ�ʵ����ͼ
	public void setWaitReady()
	{
		this.innerView.setVisibility(VISIBLE);
		this.waitView.setVisibility(INVISIBLE);
	}

	// �趨�״����ļ�����
	public void setFirstDrawListener(ViewBaseReadyWaitFirstDrawListener firstDrawListener) {
		this.firstDrawListener = firstDrawListener;
	}
	
}
