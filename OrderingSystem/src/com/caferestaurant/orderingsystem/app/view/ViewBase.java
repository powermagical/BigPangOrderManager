package com.caferestaurant.orderingsystem.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public abstract class ViewBase extends ViewGroup {

	private LayoutInflater layoutInflater;
	
	public ViewBase(Context context) {
		super(context);
		this.setLayoutInflater(LayoutInflater.from(context));
	}
	
	public ViewBase(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setLayoutInflater(LayoutInflater.from(context));
	}
	
	abstract public String getViewCaption();

	protected LayoutInflater getLayoutInflater() {
		return layoutInflater;
	}

	private void setLayoutInflater(LayoutInflater layoutInflater) {
		this.layoutInflater = layoutInflater;
	}
}
