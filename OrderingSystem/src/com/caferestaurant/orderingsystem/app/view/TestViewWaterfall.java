package com.caferestaurant.orderingsystem.app.view;

import com.caferestaurant.orderingsystem.app.R;
import com.caferestaurant.orderingsystem.app.util.ViewUtility;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class TestViewWaterfall extends ViewWaterfallBase{

	int addedCount = 0;
	
	public TestViewWaterfall(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public TestViewWaterfall(Context context) {
		super(context);
	}

	@Override
	protected void onDrawStart(View t) {
		this.addSomeImages();
	}

	@Override
	protected void onGotoBottom(View t) {
		if (this.addedCount <= 1)
		{
			this.addSomeImages();
			this.setLoadingFinished();
			++this.addedCount;
		}
		else
		{
			this.setDataFinished();
		}
	}
	
	private void addSomeImages()
	{
		LayoutInflater inf = this.getInflater();

		for(int i = 0; i < 5; ++i)
		{
			View inner = inf.inflate(R.layout.tile_view, null, false);
			//inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			ImageView iv = (ImageView)inner.findViewById(R.id.img);
			iv.setImageResource(R.drawable.weng1);
			this.addViewToWaterfall(inner);
			
			inner = inf.inflate(R.layout.tile_view, null, false);
			//inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			iv = (ImageView)inner.findViewById(R.id.img);
			iv.setImageResource(R.drawable.weng2);
			this.addViewToWaterfall(inner);
			
			inner = inf.inflate(R.layout.tile_view, null, false);
			//inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			iv = (ImageView)inner.findViewById(R.id.img);
			iv.setImageResource(R.drawable.weng4);
			this.addViewToWaterfall(inner);
			
			inner = ViewUtility.getViewForWaterfallFromResource(R.layout.tile_view, inf);
			iv = (ImageView)inner.findViewById(R.id.img);
			iv.setImageResource(R.drawable.weng6);
			this.addViewToWaterfall(inner);
		}
	}

	@Override
	public String getViewCaption() {
		return "ÏàÇ×¶ÔÏóABC";
	}
}
