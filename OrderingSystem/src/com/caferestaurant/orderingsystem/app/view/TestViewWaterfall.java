package com.caferestaurant.orderingsystem.app.view;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.caferestaurant.orderingsystem.app.R;
import com.caferestaurant.orderingsystem.app.util.ViewUtility;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
	
	private int count = 0;
	
	private void addSomeImages()
	{
		LayoutInflater inf = this.getInflater();
		
		
			/*
			 * ����һ����̬���ص�����
			 * ����ȡ��ͼƬ�Ŀ�͸ߣ����أ�
			 * Ȼ�����ViewUtility.getLoadDummyDrawableWithFixedWidthHeight
			 * ��������������ص�Drawable��ΪImageView��ͼƬ�����趨��setImageDrawable��
			 * ��󽫲���View����ĳ�����������������һ��measure��layout
			 * Ȼ������diaplayImage��һ��
			 * ���ͼƬ�Ĵ�С��getLoadDummyDrawableWithFixedWidthHeightʱ�Ĵ�С��һ�£���������հ�
			 * ��ͼƬ�ı�����Ȼ���֣���XML�ļ��ж��壩.
			 * ע�⣡�����հ׽��ڵڶ������ͼƬ��ʱ���Զ�ȥ��������������Ϊ������һ��layout��
			 * */
			for(int i = 0; i < 5; ++i)
			{
				View inner = inf.inflate(R.layout.tile_view, null, false);
				//inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				ImageView iv = (ImageView)inner.findViewById(R.id.img);
				iv.setImageDrawable(
						ViewUtility.getLoadDummyDrawableWithFixedWidthHeight(380, 640, this, 750));
				this.addViewToWaterfall(inner);
				ImageLoader.getInstance().displayImage(
						"http://imgsrc.baidu.com/forum/pic/item/655e4590f603738dc00b8a62b31bb051f919ec89.jpg", iv);
				this.adjustImageViewAfterAdding(inner);
				
				
				inner = inf.inflate(R.layout.tile_view, null, false);
				//inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				iv = (ImageView)inner.findViewById(R.id.img);
				iv.setImageDrawable(
						ViewUtility.getLoadDummyDrawableWithFixedWidthHeight(640, 480, this, 750));
				//iv.setImageResource(R.drawable.weng2);
				this.addViewToWaterfall(inner);
				ImageLoader.getInstance().displayImage(
						"http://imgsrc.baidu.com/forum/pic/item/b25912950a7b0208c8428fa162d9f2d3562cc843.jpg", iv);
				this.adjustImageViewAfterAdding(inner);
				
				inner = inf.inflate(R.layout.tile_view, null, false);
				//inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				iv = (ImageView)inner.findViewById(R.id.img);
				//iv.setImageResource(R.drawable.weng4);
				iv.setImageDrawable(
						ViewUtility.getLoadDummyDrawableWithFixedWidthHeight(434, 651, this, 750));
				this.addViewToWaterfall(inner);
				ImageLoader.getInstance().displayImage(
						"http://imgsrc.baidu.com/forum/pic/item/d4a55e3d269759ee7baa563db2fb43166c22df29.jpg", iv);
				this.adjustImageViewAfterAdding(inner);
				
				inner = inf.inflate(R.layout.tile_view, null, false);
				//inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				iv = (ImageView)inner.findViewById(R.id.img);
				//iv.setImageResource(R.drawable.weng4);
				iv.setImageDrawable(
						ViewUtility.getLoadDummyDrawableWithFixedWidthHeight(977, 651, this, 750));
				this.addViewToWaterfall(inner);
				ImageLoader.getInstance().displayImage(
						"http://imgsrc.baidu.com/forum/pic/item/3ca19625bc315c609a485d198db1cb134b5477c2.jpg", iv);
				this.adjustImageViewAfterAdding(inner);
				
				
				inner = inf.inflate(R.layout.tile_view, null, false);
				//inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				iv = (ImageView)inner.findViewById(R.id.img);
				//iv.setImageResource(R.drawable.weng4);
				iv.setImageDrawable(
						ViewUtility.getLoadDummyDrawableWithFixedWidthHeight(434, 651, this, 750));
				this.addViewToWaterfall(inner);
				ImageLoader.getInstance().displayImage(
						"http://imgsrc.baidu.com/forum/pic/item/93152fa4462309f7df2c9be4720e0cf3d6cad660.jpg", iv);
				this.adjustImageViewAfterAdding(inner);
			
			}
	}

	private void adjustImageViewAfterAdding(View v)
	{
		ImageView iv = (ImageView)v.findViewById(R.id.img);
		
		LayoutParams lp = iv.getLayoutParams();
		lp.width = iv.getMeasuredWidth();
		lp.height = iv.getMeasuredHeight();
		iv.setLayoutParams(lp);
		
	}
	
	@Override
	public String getViewCaption() {
		return "����A";
	}
}
