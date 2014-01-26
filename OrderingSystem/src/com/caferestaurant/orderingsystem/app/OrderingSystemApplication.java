package com.caferestaurant.orderingsystem.app;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.os.Looper;
import android.util.Log;

public class OrderingSystemApplication extends Application{
	private static final String TAG = OrderingSystemApplication.class
			.getName();

	// 启动的Activity集合
	public List<Activity> mActivityList = new ArrayList<Activity>();

	/** 程序启动时的处理 */
	@Override
	public void onCreate() {
		super.onCreate();
		// 出现应用级异常时的处理
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread thread, Throwable throwable) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						// 弹出报错并强制退出的对话框
						if (mActivityList.size() > 0) {
							Looper.prepare();
							new AlertDialog.Builder(getCurrentActivity())
									.setTitle(R.string.app_name)
									.setMessage(R.string.err_fatal)
									.setPositiveButton(
											R.string.confirm,
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// 强制退出程序
													finish();
												}
											}).show();
							Looper.loop();
						}
					}
				}).start();

				// 错误LOG
				Log.e(TAG, throwable.getMessage(), throwable);
			}
		});
		init();
	}

	private void init() {
		try {
			Class.forName("TestDataUtil");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭程序所有的Activity
	 */
	public void clearActivityList() {
		for (int i = 0; i < mActivityList.size(); i++) {
			mActivityList.get(i).finish();
		}
		mActivityList.clear();
	}

	/**
	 * 关闭时的处理
	 */
	public void finish() {
		// 关闭程序所有的Activity
		clearActivityList();

		// 退出
		System.exit(0);
	}

	/**
	 * 获得当前最前端的Activity
	 */
	public Activity getCurrentActivity() {
		if (mActivityList.size() > 0) {
			return mActivityList.get(mActivityList.size() - 1);
		}
		return null;
	}
}
