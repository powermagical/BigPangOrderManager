package com.caferestaurant.orderingsystem.app.view;

import java.util.HashMap;
import java.util.List;

import com.caferestaurant.orderingsystem.app.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ViewShoppingCart extends ViewBase{

	private View innerView;
	
	private ListView listView;
	private LinearLayout emptyLay;
	
	
	public ViewShoppingCart(Context context) {
		super(context);
		this.init(context);
		this.initControls(context);
	}
	
	public ViewShoppingCart(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init(context);
		this.initControls(context);
	}
	
	private void initControls(Context context)
	{
		this.getControls(context);
		this.listView.setAdapter(new ShoppingCartListViewAdapter(
				context, null, new int[] {0, 1, 1, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 1, 2}));
	}
	
	private void getControls(Context context)
	{
		this.listView = (ListView)this.innerView.findViewById(R.id.list_view);
		this.emptyLay = (LinearLayout)this.innerView.findViewById(R.id.empty_lay);
	}
	
	private void init(Context context)
	{
		this.innerView = this.getLayoutInflater().inflate(R.layout.shopping_cart_layout, this, false);
		this.addView(this.innerView);
	}

	@Override
	public String getViewCaption() {
		return "购物车";
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
	

	public class ShoppingCartListViewAdapter extends BaseAdapter{
		
		//数据源
		private List<HashMap<String,String>> list;
		private Context context;
		private int []type;
	 
		//构造函数
		public ShoppingCartListViewAdapter(Context context, List<HashMap<String,String>> list, int[] typeArray){
			
			this.context = context;
			this.list = list;
			this.type = typeArray;
			
		}
		 
		@Override
		public int getCount() {
			//return list.size();
			return this.type.length;
		}
		 
		@Override
		public Object getItem(int position) {
			//return list.get(position);
			return null;
		}
		 
		@Override
		public long getItemId(int position) {
			return position;
		}
	 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater mInflater = LayoutInflater.from(context);
		//产生一个View
		View view = null;
		//根据type不同的数据类型构造不同的View,也可以根据1,2,3天数构造不同的样式
		if (type[position]==1)
		{
			view = mInflater.inflate(R.layout.shopping_cart_goods_item, null);
		}
		else if (type[position]==0)
		{
			view = mInflater.inflate(R.layout.shopping_cart_store_item, null);
		}
		else if (type[position]==2)
		{
			view = mInflater.inflate(R.layout.shopping_cart_oneend_item, null);
		}
		/*else{
			view = mInflater.inflate(R.layout.content_item, null);
		 
			//获取数据
			 
			String content=list.get(position).get("data");
			 
			//分离数据
			 
			String []items=content.split(",");
			 
		
			TextView weather=(TextView)view.findViewById(R.id.content);
			 
			weather.setText(items[0]+"天气: "+items[1]+";温度:  "+items[2]);
			 
			TextView date=(TextView)view.findViewById(R.id.date);
			 
			date.setText(items[3]);
		 
	
		}*/
			return view;
		}
	
	}
	 
	
	
}
