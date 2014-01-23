package com.LearningKimia.adapter;

import java.util.List;

import com.LearningKimia.R;
import com.LearningKimia.model.Option;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class FileArrayAdapter extends ArrayAdapter<Option>{

	private Context c;
	private int id;
	private List<Option>items;
	
	public FileArrayAdapter(Context context, int textViewResourceId,
			List<Option> objects) {
		super(context, textViewResourceId, objects);
		c = context;
		id = textViewResourceId;
		items = objects;
	}
	public Option getItem(int i)
	 {
		 return items.get(i);
	 }
	 @Override
       public View getView(int position, View convertView, ViewGroup parent) {
               View v = convertView;
               if (v == null) {
                   LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                   v = vi.inflate(id, null);
               }
               final Option o = items.get(position);
               if (o != null) {
            	   ImageView icon = (ImageView)v.findViewById(R.id.icon);
                       TextView t1 = (TextView) v.findViewById(R.id.menuTextTop);
                       TextView t2 = (TextView) v.findViewById(R.id.menuTextBottom);
                       
                       if(o.getData().equalsIgnoreCase("Folder") || o.getData().equalsIgnoreCase("Parent Directory"))
                    	   icon.setBackgroundResource(R.drawable.folder);
                       else icon.setBackgroundResource(R.drawable.audio_icon);
                       if(t1!=null)
                       	t1.setText(o.getName());
                       if(t2!=null)
                       	t2.setText(o.getData());
                       
               }
               return v;
       }

}
