package com.caferestaurant.orderingsystem.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public abstract class ViewBase extends ViewGroup {

	public ViewBase(Context context) {
		super(context);
	}
	
	public ViewBase(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	abstract public String getViewCaption();
}
