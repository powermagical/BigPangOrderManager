package com.caferestaurant.orderingsystem.app.view;

import com.caferestaurant.orderingsystem.app.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class ViewTops extends ViewBase {

	private View innerView;
	
	public ViewTops(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init(context);
	}
	
	public ViewTops(Context context) {
		super(context);
		this.init(context);
	}
	
	private void init(Context context)
	{

		this.innerView = this.getLayoutInflater().inflate(R.layout.tops_layout, null, false);
		this.addView(this.innerView);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed)
		{
			this.innerView.layout(l - l, t - t, r - l, b - t);
			this.layout(l, t, r, b);
		}
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.innerView.measure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public String getViewCaption() {
		return "≈≈√˚";
	}
}
