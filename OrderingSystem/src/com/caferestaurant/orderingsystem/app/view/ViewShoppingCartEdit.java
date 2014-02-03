package com.caferestaurant.orderingsystem.app.view;

import com.caferestaurant.orderingsystem.app.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class ViewShoppingCartEdit extends ViewBase{

	private View innerView;
	
	private boolean initialShow = false;
	
	public ViewShoppingCartEdit(Context context) {
		super(context);
		this.init(context);
	}
	
	public ViewShoppingCartEdit(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init(context);
	}
	
	private void init(Context context)
	{
		this.innerView = this.getLayoutInflater().inflate(R.layout.shopping_cart_edit_item, this, false);
		this.addView(this.innerView);
	}

	/**
	 * ��Ϊ��һ���б�����ʱ���ֵ��ӿؼ���captionû���õ�
	 */
	@Override
	public String getViewCaption() {
		return null;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed)
		{
			this.innerView.layout(l - l, t - t, r - l, b - t);
			if (!this.isInitialShow())
			{
				((View)this.getParent()).setVisibility(View.GONE);
			}
			else
			{
				((View)this.getParent()).setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.innerView.measure(widthMeasureSpec, heightMeasureSpec);
		// ����������ǳ���Ҫ��������
		this.setMeasuredDimension(this.innerView.getMeasuredWidth(), this.innerView.getMeasuredHeight());
	}

	public boolean isInitialShow() {
		return initialShow;
	}

	public void setInitialShow(boolean initialShow) {
		this.initialShow = initialShow;
	}
}
