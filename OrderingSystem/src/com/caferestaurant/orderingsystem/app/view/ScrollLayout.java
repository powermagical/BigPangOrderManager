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
 * ��Launcher�е�WorkSapce���������һ����л���Ļ����
 */
public class ScrollLayout extends ViewGroup {
	
	/**
	 * �ڲ��࣬��������ĳһ�κ���Ļ����Ѿ�����
	 */
	public interface ScrollEndListener
	{
		void onScrollEnd(int curScreen);
	}
	
	/**
	 * ���log��
	 */
	private static final String TAG = "ScrollLayout";
	
	/**
	 * touch�¼�����״̬������
	 */
	private static final int TOUCH_STATE_REST = 0;
	
	/**
	 * touch�¼�����״̬��������
	 */
	private static final int TOUCH_STATE_SCROLLING = 1;

	/**
	 * �����жϷ�ҳ��X����ٶ���ֵ
	 */
	private static final int SNAP_VELOCITY = 600;
	
	/**
	 * ��������
	 */
	private Scroller mScroller;
	
	/**
	 * touch�¼�������
	 */
	private VelocityTracker mVelocityTracker;

	/**
	 * ��ǰ��ʾ��view�±꣬��0��ʼ
	 */
	private int mCurScreen;
	
	/**
	 * ȱʡ��ʾ��view�±�
	 */
	private int mDefaultScreen = 0;

	/**
	 * ��ǰtouch�¼�����״̬
	 */
	private int mTouchState = TOUCH_STATE_REST;
	/**
	 * ������ʼ���жϵ���ֵ
	 */
	private int mTouchSlop;
	/**
	 * ǰһ��touch�¼���X����
	 */
	private float mLastMotionX;
	
	/**
	 * ���������ļ�����
	 */
	private List<ScrollEndListener> mScrollEndListeners = new ArrayList<ScrollEndListener>();

	
	public ScrollLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public ScrollLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mScroller = new Scroller(context);
		this.mCurScreen = this.mDefaultScreen;
		// ������ֵ�ĳ�ʼֵ����onMeasure�л���Ҫ�ж�
		this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}
	
	/**
	 * ���ӹ�����ɵļ�����
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
	 * �Ƴ�������ɵļ�����
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
	 * ��ĳһ�ι�������ʱ���ñ�����
	 * ����������override������override��������super.onScrollEnd
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
	 * ���ַ���
	 * ����������ͼ�����������κ���ƽ������
	 * ��һ������ͼ�����Ͻ�����Ϊ��0,0��
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
	 * ������С
	 * �������ɿ�ܵ���
	 * ��ÿһ������ͼ����Ϊͬ��layoutһ����С
	 */
	@Override      
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		//Log.e(TAG, "onMeasure");
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		// �ٴβ���������ʼ����ֵ����������Ļ���/20���ж�
		this.mTouchSlop = Math.max(this.mTouchSlop, this.getMeasuredWidth() / 20);

		final int width = MeasureSpec.getSize(widthMeasureSpec);
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		
		// ���ҽ�������ͼ�Ŀ��ΪFILL_PARENT/MATCH_PARENTʱ�ſ���ʹ��
		if (widthMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException("ScrollLayout only canmCurScreen run at EXACTLY mode!");
		}       

		// ���ҽ�������ͼ�ĸ߶�ΪFILL_PARENT/MATCH_PARENTʱ�ſ���ʹ��
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if (heightMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException("ScrollLayout only can run at EXACTLY mode!");
		}       

		// ��������ͼ�����źͱ�ScrollLayoutһ���Ŀ��     
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
		
		// �Ȼ�����ȱʡ����ͼ
		this.scrollTo(this.mCurScreen * width, 0);         
	}

	/**
	 * ���ݵ�ǰ��scroll Xֵ������������������ͼ
	 * �Ժ����ȵ�һ��Ϊ��ֵ
	 */
	public void snapToDestination() {
		final int screenWidth = this.getWidth();
		final int destScreen = (this.getScrollX() + screenWidth / 2) / screenWidth;
		this.snapToScreen(destScreen);
	}

	/**  
	 * ������ָ������ͼ
	 */ 
	public void snapToScreen(int whichScreen) {
		
		// �ж���ͼ�ķ�Χ�Ϸ���0~��ͼ����-1
		whichScreen = Math.max(0, Math.min(whichScreen, this.getChildCount() - 1));
		
		if (this.getScrollX() != (whichScreen * this.getWidth())) {

			// ������Ҫ�����ľ���
			final int delta = whichScreen * this.getWidth() - this.getScrollX();
			// ��������ʼ
			this.mScroller.startScroll(this.getScrollX(), 0, delta, 0, 250);  // ȱʡ�Ļ���ʱ��Ϊ250ms��ò��Ч�����Խ���
			this.mCurScreen = whichScreen;
			// ��ʼdraw��ѭ��
			this.invalidate();
		}
	}

	/**  
	 * ������ָ������ͼ
	 * ˲����ɣ�û�л����Ķ�������
	 */ 
	public void setToScreen(int whichScreen) {
		whichScreen = Math.max(0, Math.min(whichScreen, this.getChildCount()-1));
		this.mCurScreen = whichScreen;
		this.scrollTo(whichScreen * this.getWidth(), 0);
	}

	/**
	 * �õ���ǰ��ʾ������ͼ
	 */
	public int getCurScreen() {
		return this.mCurScreen;
	}
	

	/**
	 * �������
	 * ��������ViewGroup�ڲ���draw��������
	 * ��������������ʺϻ�����������ʾ
	 */
	@Override
	public void computeScroll() {
		if (this.mScroller.computeScrollOffset()) {
			this.scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
			// �������ĵ��÷ǳ���Ҫ����
			// ����δ������һ֡��������
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
	 * ScrollLayout�Ĵ����¼�
	 * ���������ҽ���ĳ��onInterceptTouchEvent�ص�����true�󱻵��ã������ӱ���һϵ�еĴ����¼���ֱ����ָ�뿪��Ļλ�ã�
	 * ������Ϊ���й������������Դ
	 */
	@Override    
	public boolean onTouchEvent(MotionEvent event) {

		if (this.mVelocityTracker == null) {
			this.mVelocityTracker = VelocityTracker.obtain();
		}
		// ���������ƶ��ٶ�
		this.mVelocityTracker.addMovement(event);

		final int action = event.getAction();
		final float x = event.getX();

		switch (action) {
		// ��ָ����
		case MotionEvent.ACTION_DOWN:
			Log.e(TAG, "event down!");
			// ��ǰһ�λ�����δ�����������Ƚ���ǰһ�λ���
			// ����ָ����ʱ��Ļ���ڻ����е�������ڴ˴���Χ
			if (!this.mScroller.isFinished()){  
				this.mScroller.abortAnimation();
			}
			// ���䱾�δ�����X����
			this.mLastMotionX = x;    
			break;    

		// ��ָ�ƶ�
		case MotionEvent.ACTION_MOVE:
			Log.e(TAG, "event move!");
			// ��������ͼ��Ҫ�����ľ���
			int deltaX = (int)(this.mLastMotionX - x);    
			this.mLastMotionX = x;
			
			// ����Ļλ�ڵ�һ������ͼ��û���κλ�������ʱ���������ƾ���������
			if (this.mCurScreen == 0 && deltaX < 0 && this.getScrollX() == 0)
			{
				return true;
			}
			
			// ����Ļλ�����һ������ͼ��û���κλ�������ʱ���������ƾ���������
			if (this.mCurScreen == this.getChildCount() - 1  && deltaX > 0 && this.getScrollX() == (this.getChildCount() - 1) * this.getWidth())
			{
				return true;
			}

			this.scrollBy(deltaX, 0);
			break;

			// ��ָ̧��
		case MotionEvent.ACTION_UP:
			Log.e(TAG, "event : up");

			// ������ָ�ƶ����ٶ�
			final VelocityTracker velocityTracker = mVelocityTracker;
			velocityTracker.computeCurrentVelocity(1000);
			int velocityX = (int) velocityTracker.getXVelocity();
			Log.e(TAG, "velocityX:"+velocityX);

			// ���ٶȴ�����ֵ����ֱ�ӷ�����ָ�ƶ��������һ������ͼ
			// ��������ָ�ſ�ʱ��ͼ�ƶ���λ�þ���
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
			
			// �ͷ��ٶȼ�����
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
	 * �����¼��ļ�����
	 * �������ķ���ֵ��onTouchEvent�Ĺ������ڸ��ӣ������Android�ĵ�
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.e(TAG, "onInterceptTouchEvent-slop:" + mTouchSlop);
		
		final int action = ev.getAction();
		// ����ǰ�Ѿ�����ͼ����ģʽʱ�������е�move������������true�����ɱ����onTouchEvent����
		if ((action == MotionEvent.ACTION_MOVE) && (this.mTouchState != TOUCH_STATE_REST)) {
			return true;
		}

		final float x = ev.getX();

		switch (action) {
		case MotionEvent.ACTION_MOVE:
			// ��ĳһ�����ľ��������ֵ�����ж�Ϊ��layout�Ĺ�����ʼ���������ģʽ
			// �ڹ���ģʽ�£����еĴ����¼����ɱ�class�������������ͼ�������¼�
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
				// ����������
				captions[i] = "����һ��BUG";
			}
		}
		return captions;
	}

}    