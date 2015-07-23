package com.su.adapter;

import java.util.ArrayList;

import com.su.model.MenuItem;
import com.waternie.su.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {
	private ArrayList<MenuItem> dataSrc;
	private Context mContext;
	public MenuAdapter(Context ctx, ArrayList<MenuItem> src)
	{
		dataSrc = src;
		mContext = ctx;
	}
	

	public int getCount() {
		return dataSrc.size();
	}

	public Object getItem(int position) {
		return dataSrc.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {	
		MenuItem mi = dataSrc.get(position);
		if(convertView == null)
		{
			convertView = LayoutInflater.from(mContext).inflate(R.layout.drawer_menu_listitem,null);
		
			TextView tv = (TextView) convertView.findViewById(R.id.menu_text);
			tv.setText(mi.getText());
			
			ImageView iv = (ImageView) convertView.findViewById(R.id.menu_icon);
			iv.setImageResource(mi.getImgId());
		}
		return convertView;
		
	}

}
