package com.caferestaurant.orderingsystem.app.view;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.caferestaurant.orderingsystem.app.R;
import com.caferestaurant.orderingsystem.app.activity.ShoppingDetailActivity;
import com.caferestaurant.orderingsystem.app.util.ViewUtility;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

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
		addSomeImages();
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
	
	/**
	 * universal loading image ���ͼ����ش�������ļ�����
	 * ���������universal image loader��
	 */
	private ImageLoadingListener lsn = new ImageLoadingListener() {
		
		private ColorDrawable colorBase= new ColorDrawable(android.R.color.transparent);
		@Override
		public void onLoadingCancelled(String arg0,
				View arg1) 
		{}

		@Override
		public void onLoadingComplete(String arg0,
				View arg1, Bitmap arg2) {
			
			ImageView iv = (ImageView)arg1;
			iv.setScaleType(ScaleType.FIT_XY);
			// ���ö���
			TransitionDrawable td = new TransitionDrawable(
				new Drawable[] {colorBase, new BitmapDrawable(iv.getResources(), arg2)});
			//td.setCrossFadeEnabled(true);
			iv.setImageDrawable(td);
			td.startTransition(500);
		}

		@Override
		public void onLoadingFailed(String arg0, View arg1,
				FailReason arg2) 
		{}

		@Override
		public void onLoadingStarted(String arg0, View arg1) 
		{}
		
	};
	
	
	private void addSomeImages()
	{
		LayoutInflater inf = this.getLayoutInflater();
		
		
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
			for(int i = 0; i < 2; ++i)
			{
				// ����ĵ���˳���ܹ�Ū��
				// ���򽫻������
				View inner = inf.inflate(R.layout.tile_view, null, false);
				//inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				ImageView iv = (ImageView)inner.findViewById(R.id.img);
				iv.setImageDrawable(
						ViewUtility.getLoadDummyDrawableWithFixedWidthHeight(380, 640, this, 750));
				this.addViewToWaterfall(inner);
				this.adjustImageViewAfterAdding(inner);
				ImageLoader.getInstance().displayImage(
						"http://imgsrc.baidu.com/forum/pic/item/655e4590f603738dc00b8a62b31bb051f919ec89.jpg", iv, this.lsn);
				iv.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {							
							Intent intent = new Intent(TestViewWaterfall.this.getContext(), ShoppingDetailActivity.class);
							TestViewWaterfall.this.getContext().startActivity(intent);							
						}
					}
				);
				
				
				inner = inf.inflate(R.layout.tile_view, null, false);
				//inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				iv = (ImageView)inner.findViewById(R.id.img);
				iv.setImageDrawable(
						ViewUtility.getLoadDummyDrawableWithFixedWidthHeight(640, 480, this, 750));
				//iv.setImageResource(R.drawable.weng2);
				this.addViewToWaterfall(inner);
				this.adjustImageViewAfterAdding(inner);
				ImageLoader.getInstance().displayImage(
						"http://imgsrc.baidu.com/forum/pic/item/b25912950a7b0208c8428fa162d9f2d3562cc843.jpg", iv, this.lsn);
				
				
				inner = inf.inflate(R.layout.tile_view, null, false);
				//inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				iv = (ImageView)inner.findViewById(R.id.img);
				//iv.setImageResource(R.drawable.weng4);
				iv.setImageDrawable(
						ViewUtility.getLoadDummyDrawableWithFixedWidthHeight(434, 651, this, 750));
				this.addViewToWaterfall(inner);
				this.adjustImageViewAfterAdding(inner);
				ImageLoader.getInstance().displayImage(
						"http://imgsrc.baidu.com/forum/pic/item/d4a55e3d269759ee7baa563db2fb43166c22df29.jpg", iv, this.lsn);
				
				
				inner = inf.inflate(R.layout.tile_view, null, false);
				//inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				iv = (ImageView)inner.findViewById(R.id.img);
				//iv.setImageResource(R.drawable.weng4);
				iv.setImageDrawable(
						ViewUtility.getLoadDummyDrawableWithFixedWidthHeight(977, 651, this, 750));
				this.addViewToWaterfall(inner);
				this.adjustImageViewAfterAdding(inner);
				ImageLoader.getInstance().displayImage(
						"http://imgsrc.baidu.com/forum/pic/item/3ca19625bc315c609a485d198db1cb134b5477c2.jpg", iv, this.lsn);
				
				
				inner = inf.inflate(R.layout.tile_view, null, false);
				//inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				iv = (ImageView)inner.findViewById(R.id.img);
				//iv.setImageResource(R.drawable.weng4);
				iv.setImageDrawable(
						ViewUtility.getLoadDummyDrawableWithFixedWidthHeight(434, 651, this, 750));
				this.addViewToWaterfall(inner);
				this.adjustImageViewAfterAdding(inner);
				ImageLoader.getInstance().displayImage(
						"http://imgsrc.baidu.com/forum/pic/item/93152fa4462309f7df2c9be4720e0cf3d6cad660.jpg",
						iv, this.lsn);
				
			
			}
	}

	/**
	 * Ϊ�˷�ֹ��ͼ���ٴε�����С��onmeasure�Ľ������������ǿ�ƽ�imageview�Ŀ�Ⱥ͸߶ȶ���
	 * @param v
	 */
	private void adjustImageViewAfterAdding(View v)
	{
		ImageView iv = (ImageView)v.findViewById(R.id.img);
		
		LayoutParams lp = iv.getLayoutParams();
		lp.width = iv.getMeasuredWidth();
		lp.height = iv.getMeasuredHeight();
		iv.setLayoutParams(lp);
		iv.setScaleType(ScaleType.CENTER);
		iv.setImageResource(R.drawable.load_flower);
		
	}
	
	@Override
	public String getViewCaption() {
		return "����A";
	}

	/**
	 * �ȴ����汻��ʾ���¼�����
	 * Ϊ��ģ���ʼ���¼�����3���رյȴ�����
	 */
	@Override
	protected void onInitFirstDraw(View t) {
		this.postDelayed(new Runnable() {

			@Override
			public void run() {
				TestViewWaterfall.this.finishInit();
			}
		}, 
		3000);
		
	}
}
