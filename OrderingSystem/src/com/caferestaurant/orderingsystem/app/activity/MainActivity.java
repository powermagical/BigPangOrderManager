package com.caferestaurant.orderingsystem.app.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.TextView;

import com.caferestaurant.orderingsystem.app.R;

/**
 * 主界面activity
 * 其中的各个view仅供参考
 * @author Administrator
 *
 */
public class MainActivity extends BaseActivity{
	
	private TextView tvFirstPage;
	private TextView tvRecommand;
	private TextView tvOrder;
	private TextView tvShoppingCart;
	private TextView tvElse;
	
	private TextView tvShoppingCartNum;
	
	
	private TabHost thTabHost;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.main_layout);
		this.initLayout();
	}
	
	
	private void initLayout()
	{
		this.initControls();
		this.initTabs();
		this.initToolbar();
		
		this.thTabHost.setCurrentTab(2);
		this.adjustToolbalIcons(2);
		
	}
	
	private void initControls()
	{
		this.tvFirstPage = (TextView)this.findViewById(R.id.tv_first_page);
		this.tvRecommand = (TextView)this.findViewById(R.id.tv_recommand);
		this.tvOrder = (TextView)this.findViewById(R.id.tv_order);
		this.tvShoppingCart = (TextView)this.findViewById(R.id.tv_shoppingcart);
		this.tvElse = (TextView)this.findViewById(R.id.tv_else);
		
		this.tvShoppingCartNum = (TextView)this.findViewById(R.id.shopping_cart_num);
		
		this.setShoppintCartNum(0);

		this.thTabHost = (TabHost)this.findViewById(android.R.id.tabhost);
	}
	
	private void initTabs()
	{
		this.thTabHost.setup();
		
		this.thTabHost.addTab(
				this.thTabHost.newTabSpec("1").setIndicator("1").setContent(R.id.view1)
				);
		
		this.thTabHost.addTab(
				this.thTabHost.newTabSpec("2").setIndicator("2").setContent(R.id.view2)
				);
		
		this.thTabHost.addTab(
				this.thTabHost.newTabSpec("3").setIndicator("3").setContent(R.id.view3)
				);
		
		this.thTabHost.addTab(
				this.thTabHost.newTabSpec("4").setIndicator("4").setContent(R.id.view4)
				);
		
		this.thTabHost.addTab(
				this.thTabHost.newTabSpec("5").setIndicator("5").setContent(R.id.view1)
				);
	}
	
	private void initToolbar()
	{
		this.tvFirstPage.setClickable(true);
		this.tvRecommand.setClickable(true);
		this.tvOrder.setClickable(true);
		this.tvShoppingCart.setClickable(true);
		this.tvElse.setClickable(true);
		
		this.tvFirstPage.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				MainActivity.this.thTabHost.setCurrentTab(0);
				MainActivity.this.adjustToolbalIcons(0);
			}
		});
		
		this.tvRecommand.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				MainActivity.this.thTabHost.setCurrentTab(1);
				MainActivity.this.adjustToolbalIcons(1);
			}
		});
		
		this.tvOrder.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				MainActivity.this.thTabHost.setCurrentTab(2);
				MainActivity.this.adjustToolbalIcons(2);
			}
		});
		
		this.tvShoppingCart.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				MainActivity.this.thTabHost.setCurrentTab(3);
				MainActivity.this.adjustToolbalIcons(3);
			}
		});
		
		this.tvElse.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				MainActivity.this.thTabHost.setCurrentTab(4);
				MainActivity.this.adjustToolbalIcons(4);
			}
		});
	}
	
	private void adjustToolbalIcons(int currentSel)
	{
		this.tvFirstPage.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.toolbar_community_normal, 0, 0);
		this.tvRecommand.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.toolbar_category_normal, 0, 0);
		this.tvOrder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.toolbar_ico_home_normal, 0, 0);
		this.tvShoppingCart.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.toolbar_shoppingcart_normal, 0, 0);
		this.tvElse.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.toolbar_me_normal, 0, 0);
		
		this.tvFirstPage.setTextColor(this.getResources().getColor(R.color.main_text_color));
		this.tvRecommand.setTextColor(this.getResources().getColor(R.color.main_text_color));
		this.tvOrder.setTextColor(this.getResources().getColor(R.color.main_text_color));
		this.tvShoppingCart.setTextColor(this.getResources().getColor(R.color.main_text_color));
		this.tvElse.setTextColor(this.getResources().getColor(R.color.main_text_color));
		
		
		switch (currentSel){
		case 0:
			this.tvFirstPage.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.toolbar_community_active, 0, 0);
			this.tvFirstPage.setTextColor(this.getResources().getColor(R.color.pink));
			break;
		case 1:
			this.tvRecommand.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.toolbar_category_active, 0, 0);
			this.tvRecommand.setTextColor(this.getResources().getColor(R.color.pink));
			break;
		case 2:
			this.tvOrder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.toolbar_ico_home_active, 0, 0);
			this.tvOrder.setTextColor(this.getResources().getColor(R.color.pink));
			break;
		case 3:
			this.tvShoppingCart.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.toolbar_shoppingcart_active, 0, 0);
			this.tvShoppingCart.setTextColor(this.getResources().getColor(R.color.pink));
			break;
		default:
			this.tvElse.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.toolbar_me_active, 0, 0);
			this.tvElse.setTextColor(this.getResources().getColor(R.color.pink));
			break;
		}
	}
	
	private void setShoppintCartNum(int n)
	{
		this.tvShoppingCartNum.setText(Integer.toString(n));
	}
	
}
