package com.caferestaurant.orderingsystem.app.view;

import android.content.Context;
import android.util.AttributeSet;

public class RecommandLayout extends WaterfallLayout{

	private static final int DEFAULT_RECOMMAND_COLOMN_NUM = 1;
	
	@Override
	protected int getDefaultColumnNum()
	{
		return RecommandLayout.DEFAULT_RECOMMAND_COLOMN_NUM;
	}
	
	public RecommandLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setLoadingFinished(true);
		this.setLoadingProcess(false);
		this.setMessageViewGone();
	}
	
	public RecommandLayout(Context context) {
		super(context);
		this.setLoadingFinished(true);
		this.setLoadingProcess(false);
		this.setMessageViewGone();
	}
	
	public int getLayoutWidth()
	{
		return super.getFlowWidth(0);
	}

}
