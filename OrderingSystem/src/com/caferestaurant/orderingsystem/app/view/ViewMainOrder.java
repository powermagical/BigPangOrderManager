package com.caferestaurant.orderingsystem.app.view;

import com.caferestaurant.orderingsystem.app.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewMainOrder extends ViewBase{

	private View innerView;
	
	private LinearLayout mainTab;
	
	private ViewScrollBase scrollBase;
	
	private RelativeLayout[] mainTabItems;
	
	private Button scanCode;
	
	public ViewMainOrder(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init(context);
	}
	
	public ViewMainOrder(Context context) {
		super(context);
		this.init(context);
		
		
	}
	
	private void init(Context context)
	{

		this.innerView = this.getLayoutInflater().inflate(R.layout.mainorder_layout, this, false);
		this.addView(this.innerView);

		this.mainTab = (LinearLayout)this.findViewById(R.id.mainorder_tab_container);
		this.scrollBase = (ViewScrollBase)this.findViewById(R.id.mainorder_details);
		
		this.scanCode = (Button)this.findViewById(R.id.btn_scancode);
		
		this.initTabCaptions(context);
		
		this.scrollBase.setScrollEndListener(new ViewScrollBase.ScrollEndListener() {
			
			@Override
			public void onScrollEnd(int curScreen) {
				ViewMainOrder.this.setSelectedTabItem(curScreen);
			}
		});
		
		this.scanCode.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setAction(com.google.zxing.client.android.Intents.Scan.ACTION);
				ViewMainOrder.this.getContext().startActivity(intent);
				
			}
			
		});
		
		
		this.postDelayed(new Runnable(){
			@Override
			public void run() {
				ViewMainOrder.this.findViewById(R.id.main_order_main_layout).setVisibility(VISIBLE);
				ViewMainOrder.this.findViewById(R.id.progressbar_pink).setVisibility(INVISIBLE);
			}
		}, 5000);
	}

	private void initTabCaptions(Context ctx)
	{
		String[] captions = this.scrollBase.getScreenCaptions();
		this.mainTabItems = new RelativeLayout[captions.length];
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		lp.weight = 1.0f;
		
		for(int i = 0; i < captions.length; ++i)
		{
			String s = captions[i];
			RelativeLayout rl = new RelativeLayout(ctx);
			rl.setLayoutParams(lp);
			TextView tv = new TextView(ctx);
			tv.setText(s);
			RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
			tv.setLayoutParams(rlp);
			//if (i == 0)
			//{
			//	tv.setTextAppearance(ctx, R.style.tabItemSelectedStyle);
			//	tv.setBackgroundResource(R.drawable.tabmenu_active);
			//}
			//else
			{
				tv.setTextAppearance(ctx, R.style.tabItemSelectedStyle);
				tv.setBackgroundResource(R.drawable.tabmenu_active);
			}
			rl.addView(tv);
			
			this.mainTab.addView(rl);
			
			this.mainTabItems[i] = rl;
			
		}
	}
	
	private void setSelectedTabItem(int itemIndex)
	{
		for(int i = 0; i < this.mainTabItems.length; ++i)
		{
			RelativeLayout itemLayout = this.mainTabItems[i];
			TextView txtView = (TextView)itemLayout.getChildAt(0);
			if (i == itemIndex)
			{
				txtView.setTextAppearance(this.getContext(), R.style.tabItemSelectedStyle);
				txtView.setBackgroundResource(R.drawable.tabmenu_active);
			}
			else
			{
				txtView.setTextAppearance(this.getContext(), R.style.tabItemUnselectedStyle);
				txtView.setBackgroundColor(Color.TRANSPARENT);
			}
		}
	}
	

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed)
		{
			this.innerView.layout(l - l, t - t, r - l, b - t);
		}
	}
	

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.innerView.measure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public String getViewCaption() {
		return "µã²Ë";
	}
	
}
