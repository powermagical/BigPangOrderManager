package com.caferestaurant.orderingsystem.app.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration; 
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 仿Launcher中的WorkSapce，可以左右滑动切换屏幕的类
 */
public class ScrollLayout extends ViewGroup {
	
	/**
	 * 内部类，用来监听某一次横向的滑动已经结束
	 */
	public interface ScrollEndListener
	{
		void onScrollEnd(int curScreen);
	}
	
	/**
	 * 输出log用
	 */
	private static final String TAG = "ScrollLayout";
	
	/**
	 * touch事件监视状态，空闲
	 */
	private static final int TOUCH_STATE_REST = 0;
	
	/**
	 * touch事件监视状态，滚动中
	 */
	private static final int TOUCH_STATE_SCROLLING = 1;

	/**
	 * 用于判断翻页的X轴的速度阈值
	 */
	private static final int SNAP_VELOCITY = 600;
	
	/**
	 * 滚动动画
	 */
	private Scroller mScroller;
	
	/**
	 * touch事件监视器
	 */
	private VelocityTracker mVelocityTracker;

	/**
	 * 当前显示的view下标，从0开始
	 */
	private int mCurScreen;
	
	/**
	 * 缺省显示的view下标
	 */
	private int mDefaultScreen = 0;

	/**
	 * 当前touch事件监视状态
	 */
	private int mTouchState = TOUCH_STATE_REST;
	/**
	 * 滑动开始的判断的阈值
	 */
	private int mTouchSlop;
	/**
	 * 前一次touch事件的X坐标
	 */
	private float mLastMotionX;
	
	/**
	 * 滑动结束的监听器
	 */
	private List<ScrollEndListener> mScrollEndListeners = new ArrayList<ScrollEndListener>();

	
	public ScrollLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public ScrollLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mScroller = new Scroller(context);
		this.mCurScreen = this.mDefaultScreen;
		// 滚动阈值的初始值，在onMeasure中还需要判断
		this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}
	
	/**
	 * 增加滚动完成的监听器
	 */
	public boolean addScrollEndListener(ScrollEndListener listener)
	{
		for(ScrollEndListener l : this.mScrollEndListeners)
		{
			if(l == listener)
			{
				return false;
			}
		}
		this.mScrollEndListeners.add(listener);
		return true;
	}
	
	/**
	 * 移除滚动完成的监听器
	 */
	public boolean removeScrollEndListener(ScrollEndListener listener)
	{
		for(ScrollEndListener l : this.mScrollEndListeners)
		{
			if(l == listener)
			{
				this.mScrollEndListeners.remove(listener);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 当某一次滚动结束时调用本方法
	 * 本方法可以override，但在override后必须调用super.onScrollEnd
	 */
	protected void onScrollEnd()
	{
		if (this.mScrollEndListeners.size() > 0)
		{
			for(ScrollEndListener l : this.mScrollEndListeners)
			{
				l.onScrollEnd(this.mCurScreen);
			}
		}
	}
	
	/**
	 * 布局方法
	 * 将各个子视图从左向右依次横向平铺排列
	 * 第一个子视图的左上角坐标为（0,0）
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		
		if (changed) {
			int childLeft = 0;
			final int childCount = getChildCount();

			for (int i=0; i<childCount; i++) {
				final View childView = getChildAt(i); 
				if (childView.getVisibility() != View.GONE) {
					final int childWidth = childView.getMeasuredWidth();
					childView.layout(childLeft, 0,
							childLeft+childWidth, childView.getMeasuredHeight());
					childLeft += childWidth;
				}
			}
		}
	}
	
	/**
	 * 测量大小
	 * 本方法由框架调用
	 * 将每一个子视图调整为同本layout一样大小
	 */
	@Override      
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		//Log.e(TAG, "onMeasure");
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		// 再次测量滑动开始的阈值，加上了屏幕宽度/20的判断
		this.mTouchSlop = Math.max(this.mTouchSlop, this.getMeasuredWidth() / 20);

		final int width = MeasureSpec.getSize(widthMeasureSpec);
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		
		// 当且仅当子视图的宽度为FILL_PARENT/MATCH_PARENT时才可以使用
		if (widthMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException("ScrollLayout only canmCurScreen run at EXACTLY mode!");
		}       

		// 当且仅当子视图的高度为FILL_PARENT/MATCH_PARENT时才可以使用
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if (heightMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException("ScrollLayout only can run at EXACTLY mode!");
		}       

		// 各个子视图均有着和本ScrollLayout一样的宽高     
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
		
		// 先滑动到缺省子视图
		this.scrollTo(this.mCurScreen * width, 0);         
	}

	/**
	 * 根据当前的scroll X值，滑动到所属的子视图
	 * 以横向宽度的一半为阈值
	 */
	public void snapToDestination() {
		final int screenWidth = this.getWidth();
		final int destScreen = (this.getScrollX() + screenWidth / 2) / screenWidth;
		this.snapToScreen(destScreen);
	}

	/**  
	 * 滑动到指定子视图
	 */ 
	public void snapToScreen(int whichScreen) {
		
		// 判断视图的范围合法性0~视图数量-1
		whichScreen = Math.max(0, Math.min(whichScreen, this.getChildCount() - 1));
		
		if (this.getScrollX() != (whichScreen * this.getWidth())) {

			// 计算需要滑动的距离
			final int delta = whichScreen * this.getWidth() - this.getScrollX();
			// 滑动对象开始
			this.mScroller.startScroll(this.getScrollX(), 0, delta, 0, 250);  // 缺省的滑动时间为250ms，貌似效果可以接受
			this.mCurScreen = whichScreen;
			// 开始draw的循环
			this.invalidate();
		}
	}

	/**  
	 * 滑动到指定子视图
	 * 瞬间完成，没有滑动的动画过程
	 */ 
	public void setToScreen(int whichScreen) {
		whichScreen = Math.max(0, Math.min(whichScreen, this.getChildCount()-1));
		this.mCurScreen = whichScreen;
		this.scrollTo(whichScreen * this.getWidth(), 0);
	}

	/**
	 * 得到当前显示的子视图
	 */
	public int getCurScreen() {
		return this.mCurScreen;
	}
	

	/**
	 * 计算滚动
	 * 本方法由ViewGroup内部的draw方法调用
	 * 这里仅仅重载以适合滑动动画的显示
	 */
	@Override
	public void computeScroll() {
		if (this.mScroller.computeScrollOffset()) {
			this.scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
			// 本方法的调用非常重要！！
			// 否则未必有下一帧滑动动画
			this.postInvalidate();
		}
		
		//Log.e(TAG, "dist is " + Math.abs(this.mScroller.getCurrX() - this.mScroller.getFinalX()));
		if (this.mScroller.getCurrX() == this.mScroller.getFinalX())
		{
			Log.e(TAG, "entering!!!");
			this.onScrollEnd();
		}
	}
	
	/**
	 * ScrollLayout的触碰事件
	 * 本方法当且仅当某次onInterceptTouchEvent回调返回true后被调用，并监视本次一系列的触碰事件（直到手指离开屏幕位置）
	 * 本方法为所有滚动处理的驱动源
	 */
	@Override    
	public boolean onTouchEvent(MotionEvent event) {

		if (this.mVelocityTracker == null) {
			this.mVelocityTracker = VelocityTracker.obtain();
		}
		// 用来计算移动速度
		this.mVelocityTracker.addMovement(event);

		final int action = event.getAction();
		final float x = event.getX();

		switch (action) {
		// 手指按下
		case MotionEvent.ACTION_DOWN:
			Log.e(TAG, "event down!");
			// 若前一次滑动尚未结束，则首先结束前一次滑动
			// 当手指按下时屏幕尚在滑动中的情况属于此处理范围
			if (!this.mScroller.isFinished()){  
				this.mScroller.abortAnimation();
			}
			// 记忆本次触碰的X坐标
			this.mLastMotionX = x;    
			break;    

		// 手指移动
		case MotionEvent.ACTION_MOVE:
			Log.e(TAG, "event move!");
			// 计算子视图需要滑动的距离
			int deltaX = (int)(this.mLastMotionX - x);    
			this.mLastMotionX = x;
			
			// 当屏幕位于第一个子视图且没有任何滑动，此时的右移手势均不作处理
			if (this.mCurScreen == 0 && deltaX < 0 && this.getScrollX() == 0)
			{
				return true;
			}
			
			// 当屏幕位于最后一个子视图且没有任何滑动，此时的左移手势均不作处理
			if (this.mCurScreen == this.getChildCount() - 1  && deltaX > 0 && this.getScrollX() == (this.getChildCount() - 1) * this.getWidth())
			{
				return true;
			}

			this.scrollBy(deltaX, 0);
			break;

			// 手指抬起
		case MotionEvent.ACTION_UP:
			Log.e(TAG, "event : up");

			// 计算手指移动的速度
			final VelocityTracker velocityTracker = mVelocityTracker;
			velocityTracker.computeCurrentVelocity(1000);
			int velocityX = (int) velocityTracker.getXVelocity();
			Log.e(TAG, "velocityX:"+velocityX);

			// 若速度大于阈值，则直接翻到手指移动方向的下一个子视图
			// 否则，视手指放开时视图移动的位置决定
			if (velocityX > SNAP_VELOCITY && mCurScreen > 0) {
				// Fling enough to move left       
				Log.e(TAG, "snap left");
				this.snapToScreen(mCurScreen - 1);       
			} else if (velocityX < -SNAP_VELOCITY && mCurScreen < getChildCount() - 1) {
				// Fling enough to move right
				Log.e(TAG, "snap right");
				this.snapToScreen(mCurScreen + 1);
			} else {
				this.snapToDestination();
			}
			
			// 释放速度监视器
			if (mVelocityTracker != null) {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			
			mTouchState = TOUCH_STATE_REST;       
			break;
			
		case MotionEvent.ACTION_CANCEL:
			mTouchState = TOUCH_STATE_REST;
			break;    
		}    

		return true;
	}
	
	/**
	 * 触碰事件的监视器
	 * 本方法的返回值和onTouchEvent的关联过于复杂，请参阅Android文档
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.e(TAG, "onInterceptTouchEvent-slop:" + mTouchSlop);
		
		final int action = ev.getAction();
		// 若当前已经在视图滚动模式时，则所有的move均无条件返回true（交由本类的onTouchEvent处理）
		if ((action == MotionEvent.ACTION_MOVE) && (this.mTouchState != TOUCH_STATE_REST)) {
			return true;
		}

		final float x = ev.getX();

		switch (action) {
		case MotionEvent.ACTION_MOVE:
			// 若某一滑动的距离大于阈值，则判定为本layout的滚动开始，进入滚动模式
			// 在滚动模式下，所有的触碰事件均由本class处理，下面的子视图不接受事件
			final int xDiff = (int)Math.abs(this.mLastMotionX - x);
			if (xDiff > this.mTouchSlop) {
				Log.e(TAG, "START!!");
				this.mTouchState = TOUCH_STATE_SCROLLING;    
			}
			break;    

		case MotionEvent.ACTION_DOWN:
			this.mLastMotionX = x;
			this.mTouchState = this.mScroller.isFinished()? TOUCH_STATE_REST : TOUCH_STATE_SCROLLING;    
			break;    

		case MotionEvent.ACTION_CANCEL:    
		case MotionEvent.ACTION_UP:    
			this.mTouchState = TOUCH_STATE_REST;    
			break;    
		}    

		boolean res = (this.mTouchState != TOUCH_STATE_REST);
		return res;
	}
	
	
	public String[] getScreenCaptions()
	{
		String[] captions = new String[this.getChildCount()];
		for(int i = 0; i < this.getChildCount(); ++i)
		{
			if (this.getChildAt(i) instanceof ViewBase)
			{
				captions[i] = ((ViewBase)this.getChildAt(i)).getViewCaption();
			}
			else
			{
				// 仅供测试用
				captions[i] = "这是一个BUG";
			}
		}
		return captions;
	}

}    