package com.caferestaurant.orderingsystem.app.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class ViewUtility {
	public static View getViewForWaterfallFromResource(int resID, Context context)
	{
		View inner = LayoutInflater.from(context).inflate(resID, null, false);
		inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		return inner;
	}
	
	public static View getViewForWaterfallFromResource(int resID, LayoutInflater inflater)
	{
		View inner = inflater.inflate(resID, null, false);
		inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		return inner;
	}
	
	public static View getViewForWaterfallFromResourceWithSettingImage(
			int layoutResID, LayoutInflater inflater, int imageControlID, int imgResID)
	{
		View v = ViewUtility.getViewForWaterfallFromResource(layoutResID, inflater);
		ImageView iv = (ImageView) v.findViewById(imageControlID);
		iv.setImageResource(imgResID);
		
		return v;
	}
}
