package com.caferestaurant.orderingsystem.app.view;

import com.caferestaurant.orderingsystem.app.R;
import com.caferestaurant.orderingsystem.app.util.ViewUtility;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * 推荐栏的View
 * 可以派生，但不推荐。。。。。。
 * @author Administrator
 *
 */
public class ViewRecommandBase extends ViewBase{
	
	public ViewRecommandBase(Context context) {
		super(context);
		this.init(context);
	}
	
	public ViewRecommandBase(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init(context);
	}
	
	private RecommandLayout recommand;
	
	private void init(Context context)
	{
		this.recommand = new RecommandLayout(context);
		this.recommand.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		this.addView(this.recommand);
		
		this.recommand.setFirstTimeDrawHandler(new WaterfallLayout.FirstTimeDrawListener() {
			
			@Override
			public void onFirstTimeDraw(View target) {
				onDrawStart(target);
			}
		});
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.recommand.measure(widthMeasureSpec, heightMeasureSpec);
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
			this.recommand.layout(l - l, t - t, r - l, b - t);
		}
	}

	@Override
	public String getViewCaption() {
		return null;
	}
	
	protected void onDrawStart(View target)
	{
		int w = this.recommand.getFlowWidth(0);
		w -= this.getResources().getDimensionPixelSize(R.dimen.recommand_inner_margin_space);
		View recItem = ViewUtility.getRecommandItemLayout(this.getLayoutInflater(), w);
		this.recommand.addViewToIndexFlow(recItem, 0);
		
		recItem = ViewUtility.getRecommandItemLayout(this.getLayoutInflater(), w);
		this.recommand.addViewToIndexFlow(recItem, 0);
	}
}
