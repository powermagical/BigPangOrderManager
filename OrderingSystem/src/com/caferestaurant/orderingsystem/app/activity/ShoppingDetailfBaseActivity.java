package com.caferestaurant.orderingsystem.app.activity;

import com.caferestaurant.orderingsystem.app.OrderingSystemApplication;
import com.caferestaurant.orderingsystem.app.R;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class ShoppingDetailfBaseActivity extends FragmentActivity{

	// Ӧ�ù�ͨ
	protected OrderingSystemApplication mApplication;

	protected LayoutInflater mInflater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.mApplication = (OrderingSystemApplication) getApplication();
		this.mInflater = this.getLayoutInflater();
		
		// ֻ��������
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		// ���activity
		this.mApplication.mActivityList.add(this);
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.shopping_detail_layout);
		
		
	}

	// ���û�������
	@Override
	public void setContentView(int layoutResID) {
		getLayoutInflater().inflate(layoutResID,
				(ViewGroup) findViewById(R.id.body), true);
	}

	// �ͷ��ڴ�
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ɾ��activity
		mApplication.mActivityList.remove(this);
	}

}
