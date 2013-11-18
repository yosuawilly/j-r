package com.LearningKimia.adapter;

import java.util.List;

import com.LearningKimia.R;
import com.LearningKimia.model.Menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuListAdapter extends ArrayAdapter<Menu>{
	public List<Menu> menus;
	public Menu menu;
	int resourceId;
	Context context ;
	LayoutInflater viewInflater;
	TextView menuTextTop, menuTextBottom, menuTextRight;
	LinearLayout nextIcon;

	public MenuListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.resourceId = textViewResourceId;
		this.context = context;
	}
	
	public MenuListAdapter(Context context, int textViewResourceId, List<Menu> menus) {
		super(context, textViewResourceId);
		this.resourceId = textViewResourceId;
		this.context = context;
		this.menus = menus;
	}
	
	public void setMainMenus(List<Menu> menus) {
		this.menus = menus;
	}
	
	public List<Menu> getMenus() {
		return menus;
	}
	
	@Override
	public int getCount() {
		return menus.size();
	}

	@Override
	public Menu getItem(int position) {
		return this.menus.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return Long.valueOf(position);
	}
	
	@Override
	public View getView(int location, View view, ViewGroup viewGroup) {
		viewInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = viewInflater.inflate(R.layout.list_layout, viewGroup, false);
		menuTextTop = (TextView) view.findViewById(R.id.menuTextTop);
		menuTextBottom = (TextView) view.findViewById(R.id.menuTextBottom);
		if(!(getMenus().get(location).getMenuTextRight()==null)){
			menuTextRight = (TextView)view.findViewById(R.id.menuTextRight);
			nextIcon = (LinearLayout)view.findViewById(R.id.nextIcon);
			menuTextRight.setVisibility(TextView.VISIBLE);
			nextIcon.setVisibility(LinearLayout.GONE);
			menuTextTop.setText(getMenus().get(location).getMenuTextTop());
			menuTextBottom.setText(getMenus().get(location).getMenuTextBottom());
			menuTextRight.setText(getMenus().get(location).getMenuTextRight());
		}else {
			menuTextTop.setText(getMenus().get(location).getMenuTextTop());
			if(getMenus().get(location).getMenuTextBottom()==null)menuTextBottom.setVisibility(TextView.GONE);
			menuTextBottom.setText(getMenus().get(location).getMenuTextBottom());	
		}
		return view;
	}

}
