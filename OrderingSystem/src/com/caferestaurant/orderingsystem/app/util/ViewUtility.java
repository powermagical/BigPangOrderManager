package com.caferestaurant.orderingsystem.app.util;

import com.caferestaurant.orderingsystem.app.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class ViewUtility {
	public static View getViewForWaterfallFromResource(int resID, Context context)
	{
		View inner = LayoutInflater.from(context).inflate(resID, null, false);
		//inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		return inner;
	}
	
	public static View getViewForWaterfallFromResource(int resID, LayoutInflater inflater)
	{
		View inner = inflater.inflate(resID, null, false);
		//inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
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
	
	public static View getRecommandItemLayout(LayoutInflater inf, int totalWidth)
	{
		View recommandItem = inf.inflate(R.layout.recommand_item, null, false);
		
		
		View layout1 = recommandItem.findViewById(R.id.layout_01);
		LayoutParams lp1 = layout1.getLayoutParams();
		lp1.width = totalWidth * 2 / 3;
		lp1.height = totalWidth / 3;
		layout1.setLayoutParams(lp1);
		
		View layout2 = recommandItem.findViewById(R.id.layout_02);
		LayoutParams lp2 = layout2.getLayoutParams();
		lp2.width = totalWidth / 3;
		lp2.height = totalWidth * 2 / 3;
		layout2.setLayoutParams(lp2);
		
		View layout3 = recommandItem.findViewById(R.id.layout_03);
		LayoutParams lp3 = layout3.getLayoutParams();
		lp3.width = totalWidth / 3;
		lp3.height = totalWidth / 3;
		layout3.setLayoutParams(lp3);
		
		View layout4 = recommandItem.findViewById(R.id.layout_04);
		LayoutParams lp4 = layout4.getLayoutParams();
		lp4.width = totalWidth / 3;
		lp4.height = totalWidth / 3;
		layout4.setLayoutParams(lp4);
		
		View layout5 = recommandItem.findViewById(R.id.layout_05);
		LayoutParams lp5 = layout5.getLayoutParams();
		lp5.width = totalWidth / 3;
		lp5.height = totalWidth / 2;
		layout5.setLayoutParams(lp5);
		
		View layout6 = recommandItem.findViewById(R.id.layout_06);
		LayoutParams lp6 = layout6.getLayoutParams();
		lp6.width = totalWidth / 3;
		lp6.height = totalWidth / 2;
		layout6.setLayoutParams(lp6);
		
		
		return recommandItem;
	}
}
