package com.caferestaurant.orderingsystem.app.view;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.caferestaurant.orderingsystem.app.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ViewShoppingCart extends ViewBase{

	// �༭ģʽ
	// ��Ϊδ�༭�����ڱ༭���༭������״̬δʹ�ã�
	public enum EDIT_MODE {NO_EDIT, EDITING, EDITED};
	
	// ��ǰ�ı༭ģʽ
	private EDIT_MODE editMode = EDIT_MODE.NO_EDIT;
	
	// �ڲ���ͼ
	private View innerView;
	
	// ���ﳵ����Ʒ�б�
	private ListView listView;
	
	// �����ﳵΪ��ʱ����ʾ����ͼ
	private LinearLayout emptyLay;
	
	// �����ı༭��ť
	private Button editButton;
	
	// ��Ʒ�б�listview����ͼӳ��
	private Map<Integer, View> shoppingCartMapView;
	
	// ֧��ȷ����ť/ɾ����ť
	private TextView payButton;
	
	
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
	
	private void initControls(final Context context)
	{
		this.getControls(context);
		final int[] types = {0, 1, 1, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 1, 2};
		this.shoppingCartMapView = new LinkedHashMap<Integer, View>();
		this.listView.setAdapter(new ShoppingCartListViewAdapter(
				context, null, types));
		
		this.editButton.setOnClickListener(new View.OnClickListener() {
			
			private void adjustCountEditLay(EDIT_MODE currentEditMode)
			{
				for(int i = 0; i < types.length; ++i)
				{
					if (types[i] == 1)
					{
						View w = ViewShoppingCart.this.shoppingCartMapView.get(Integer.valueOf(i));
						if (w != null)
						{
							w = w.findViewById(R.id.count_edit_lay);
							if (currentEditMode == EDIT_MODE.NO_EDIT)
							{
								w.setVisibility(View.VISIBLE);
								w.setAnimation(
									AnimationUtils.loadAnimation(context, R.anim.exchange_slide_in_from_right));
							}
							else
							{
								w.setVisibility(GONE);
							}
						}
					}
				}
			}
			
			private void adjustCurrentMode(EDIT_MODE currentEditMode)
			{
				if (currentEditMode == EDIT_MODE.NO_EDIT)
				{
					ViewShoppingCart.this.setEditMode(EDIT_MODE.EDITING);
				}
				else
				{
					ViewShoppingCart.this.setEditMode(EDIT_MODE.NO_EDIT);
				}
			}
			
			private void adjustView(EDIT_MODE currentEditMode)
			{
				if (currentEditMode == EDIT_MODE.NO_EDIT)
				{
					ViewShoppingCart.this.payButton.setBackgroundResource(R.drawable.del_shopcart_btn);
					ViewShoppingCart.this.payButton.setText(R.string.btn_delete);
				}
				else
				{
					ViewShoppingCart.this.payButton.setBackgroundResource(R.drawable.round_red_btn_bg);
					ViewShoppingCart.this.payButton.setText(R.string.btn_to_settle);
				}
			}
			
			@Override
			public void onClick(View v) {
				
				// �õ���ǰ��ģʽ
				EDIT_MODE currentMode = ViewShoppingCart.this.getEditMode();
				
				// �趨����������ť
				this.adjustCountEditLay(currentMode);
				
				// �趨�������಼�ֿؼ�
				this.adjustView(currentMode);
				
				// �趨ģʽ
				this.adjustCurrentMode(currentMode);
				
				
			}
		});
	}
	
	private void getControls(Context context)
	{
		this.listView = (ListView)this.innerView.findViewById(R.id.list_view);
		this.emptyLay = (LinearLayout)this.innerView.findViewById(R.id.empty_lay);
		
		this.editButton = (Button)this.innerView.findViewById(R.id.btn_right);
		
		this.payButton = (TextView)this.innerView.findViewById(R.id.pay);
	}
	
	private void init(Context context)
	{
		this.innerView = this.getLayoutInflater().inflate(R.layout.shopping_cart_layout, this, false);
		this.addView(this.innerView);
	}

	@Override
	public String getViewCaption() {
		return "���ﳵ";
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
	

	public EDIT_MODE getEditMode() {
		return editMode;
	}

	public void setEditMode(EDIT_MODE editMode) {
		this.editMode = editMode;
	}


	public class ShoppingCartListViewAdapter extends BaseAdapter{
		
		//����Դ
		private List<HashMap<String,String>> list;
		private Context context;
		private int []type;
	 
		//���캯��
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
			//����һ��View
			View view = null;
			//����type��ͬ���������͹��첻ͬ��View
			if (type[position]==1)
			{
				view = mInflater.inflate(R.layout.shopping_cart_goods_item, null);
				ViewShoppingCartEdit editCtrl = (ViewShoppingCartEdit)view.findViewById(R.id.count_edit_controls);
				if (ViewShoppingCart.this.getEditMode() == EDIT_MODE.EDITING)
				{
					editCtrl.setInitialShow(true);
				}
				else
				{
					editCtrl.setInitialShow(false);
				}
				
			}
			else if (type[position]==0)
			{
				view = mInflater.inflate(R.layout.shopping_cart_store_item, null);
			}
			else if (type[position]==2)
			{
				view = mInflater.inflate(R.layout.shopping_cart_oneend_item, null);
			}
			
			ViewShoppingCart.this.shoppingCartMapView.put(position, view);
			 
			return view;
		}
		
	}
	
}
