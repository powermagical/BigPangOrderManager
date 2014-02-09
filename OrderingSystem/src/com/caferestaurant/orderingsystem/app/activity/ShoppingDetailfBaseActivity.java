package com.caferestaurant.orderingsystem.app.activity;

import com.caferestaurant.orderingsystem.app.OrderingSystemApplication;
import com.caferestaurant.orderingsystem.app.R;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class ShoppingDetailfBaseActivity extends FragmentActivity{

	// 应用共通
	protected OrderingSystemApplication mApplication;

	protected LayoutInflater mInflater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.mApplication = (OrderingSystemApplication) getApplication();
		this.mInflater = this.getLayoutInflater();
		
		// 只允许竖屏
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		// 添加activity
		this.mApplication.mActivityList.add(this);
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.shopping_detail_layout);
		
		
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

}
