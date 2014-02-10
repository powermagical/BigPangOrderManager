package com.caferestaurant.orderingsystem.app.view;

import com.caferestaurant.orderingsystem.app.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewTopItem extends ViewBase{

	private View innerView;
	
	public ViewTopItem(Context context) {
		super(context);
		this.init(context);
	}
	
	public ViewTopItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init(context);
	}
	
	private void init(Context context)
	{
		this.innerView = this.getLayoutInflater().inflate(R.layout.top_item, null, false);
		this.addView(this.innerView);
	}

	@Override
	public String getViewCaption() {
		return null;
	}

	@Override
	protected void onLayout(boolean arg0, int l, int t, int r, int b) {
		if (arg0)
		{
			this.innerView.layout(l - l, t - t, r - l, b - t);
			super.layout(l, t, r, b);
		}
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		this.innerView.measure(widthMeasureSpec, heightMeasureSpec);
		ImageView iv1 = (ImageView)this.innerView.findViewById(R.id.top_item_1);
		LayoutParams lp = iv1.getLayoutParams();
		lp.height = iv1.getMeasuredWidth();
		iv1.setLayoutParams(lp);
		
		ImageView iv2 = (ImageView)this.innerView.findViewById(R.id.top_item_2);
		lp = iv2.getLayoutParams();
		lp.height = iv2.getMeasuredWidth();
		iv2.setLayoutParams(lp);
		
		ImageView iv3 = (ImageView)this.innerView.findViewById(R.id.top_item_3);
		lp = iv3.getLayoutParams();
		lp.height = iv3.getMeasuredWidth();
		iv3.setLayoutParams(lp);
		
		TextView tv = (TextView)this.innerView.findViewById(R.id.top_item_others);
		lp = tv.getLayoutParams();
		lp.height = iv3.getMeasuredWidth();
		tv.setLayoutParams(lp);
		
		this.innerView.measure(widthMeasureSpec, heightMeasureSpec);
		
		this.setMeasuredDimension(this.innerView.getMeasuredWidth(), this.innerView.getMeasuredHeight());
	}
	
}
