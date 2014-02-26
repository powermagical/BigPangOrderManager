package com.caferestaurant.orderingsystem.app.view;

import com.caferestaurant.orderingsystem.app.R;
import com.caferestaurant.orderingsystem.app.activity.MainActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ViewShoppingDetail extends ViewBase {

	private View innerView;
	private TextView countTv;

	public ViewShoppingDetail(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init(context);
	}
	
	public ViewShoppingDetail(Context context) {
		super(context);
		this.init(context);
	}
	
	private void init(Context context)
	{
		this.innerView = this.getLayoutInflater().inflate(R.layout.shopping_detail_layout, null, false);
		this.addView(this.innerView);
		ImageButton minusBtn = (ImageButton)this.innerView.findViewById(R.id.count_adjust_left);
		ImageButton plusBtn = (ImageButton)this.innerView.findViewById(R.id.count_adjust_right);
		countTv = (TextView)this.innerView.findViewById(R.id.count_adjust_count);
		
		minusBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int count = 0;
				if (countTv.getText() != null && countTv.getTextSize() > 0) {
					count = Integer.parseInt(countTv.getText().toString());
					
					if (count > 0) {
						count--;
					}					
				}
				countTv.setText(String.valueOf(count));
			}
		});
		
		plusBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int count = 0;
				if (countTv.getText() != null && countTv.getTextSize() > 0) {
					count = Integer.parseInt(countTv.getText().toString());					
					count++;				
				}
				countTv.setText(String.valueOf(count));
			}
		});
	}
	
	@Override
	public String getViewCaption() {
		// TODO Auto-generated method stub
		return "ÏêÏ¸";
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
}
