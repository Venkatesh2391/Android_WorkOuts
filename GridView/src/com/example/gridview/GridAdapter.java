package com.example.gridview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class GridAdapter extends BaseAdapter{
	
	int []items={R.drawable.item_1,R.drawable.item_2,R.drawable.item_3,R.drawable.item_4,R.drawable.item_5,R.drawable.item_6,R.drawable.item_7};
	
	Context ct;
	
	public GridAdapter(Context c){
		ct=c;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Button b=new Button(ct);
		b.setText("image-"+position);
		b.setBackgroundResource(items[position]);
		
		return b;
	}

}
