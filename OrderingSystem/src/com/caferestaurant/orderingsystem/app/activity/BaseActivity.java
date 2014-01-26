package com.caferestaurant.orderingsystem.app.activity;

import com.caferestaurant.orderingsystem.app.OrderingSystemApplication;
import com.caferestaurant.orderingsystem.app.R;
import com.caferestaurant.orderingsystem.app.view.TestViewWaterfall;
import com.caferestaurant.orderingsystem.app.view.WaterfallLayout;

import android.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class BaseActivity extends FragmentActivity{

	// 应用共通
	protected OrderingSystemApplication mApplication;

	protected FragmentManager mFragmentManager;

	protected LayoutInflater mInflater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.mApplication = (OrderingSystemApplication) getApplication();
		this.mFragmentManager = this.getFragmentManager();
		this.mInflater = this.getLayoutInflater();
		
		// 只允许竖屏
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		// 添加activity
		this.mApplication.mActivityList.add(this);
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.base_layout);
		
		
		//final TestViewWaterfall test = (TestViewWaterfall)this.findViewById(R.id.testtest);
		/*test.setFirstTimeDrawHandler(new WaterfallLayout.FirstTimeDrawListener() {

			@Override
			public void onFirstTimeDraw(View target) {
				
				for(int i = 0; i < 5; ++i)
				{
					View inner = BaseActivity.this.mInflater.inflate(R.layout.tile_view, null, false);
					inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
					ImageView iv = (ImageView)inner.findViewById(R.id.img);
					iv.setImageResource(R.drawable.weng1);
					test.addViewToIndexFlow(inner, test.getShortestFlowIndex());
					
					inner = BaseActivity.this.mInflater.inflate(R.layout.tile_view, null, false);
					inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
					iv = (ImageView)inner.findViewById(R.id.img);
					iv.setImageResource(R.drawable.weng2);
					test.addViewToIndexFlow(inner, test.getShortestFlowIndex());
					
					inner = BaseActivity.this.mInflater.inflate(R.layout.tile_view, null, false);
					inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
					iv = (ImageView)inner.findViewById(R.id.img);
					iv.setImageResource(R.drawable.weng4);
					test.addViewToIndexFlow(inner, test.getShortestFlowIndex());
				}
			}
			
		});*/
		
	}

	// 设置基本布局
	@Override
	public void setContentView(int layoutResID) {
		getLayoutInflater().inflate(layoutResID,
				(ViewGroup) findViewById(R.id.body), true);
	}

	// 释放内存
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 删除activity
		mApplication.mActivityList.remove(this);
	}

	
	
	
	
	// protected Handler mHandler = new BaseHandler(this);
	//
	// protected static class BaseHandler extends Handler {
	// private WeakReference<BaseActivity> mWeakActivity;
	//
	// public BaseHandler(BaseActivity baseActivity) {
	// mWeakActivity = new WeakReference<BaseActivity>(baseActivity);
	// }
	//
	// @Override
	// public void handleMessage(Message msg) {
	// // TODO Auto-generated method stub
	// super.handleMessage(msg);
	// }
	// }
}
