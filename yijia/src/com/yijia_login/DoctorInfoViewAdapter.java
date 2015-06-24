
package com.yijia_login;

import java.util.List;

import com.example.yijia.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DoctorInfoViewAdapter extends BaseAdapter {
		
    private static final String TAG = DoctorInfoViewAdapter.class.getSimpleName();

    private List<DoctorInfoEntity> coll;

    private Context ctx;
    
    private LayoutInflater mInflater;

    public DoctorInfoViewAdapter(Context context, List<DoctorInfoEntity> coll) {
        ctx = context;
        this.coll = coll;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return coll.size();
    }

    public Object getItem(int position) {
        return coll.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
	
    public View getView(int position, View convertView, ViewGroup parent) {
    	ViewHolder viewHolder = null;
    	DoctorInfoEntity Entity=coll.get(position);
	    if (convertView == null){	    	  
	    	convertView = mInflater.inflate(R.layout.list_doctor, null);
	    	viewHolder = new ViewHolder();
	    	viewHolder.Name = (TextView) convertView.findViewById(R.id.name);
			  
			convertView.setTag(viewHolder);
	    }else{
	        viewHolder = (ViewHolder) convertView.getTag();
	    } 
	    viewHolder.Name.setText(Entity.getName()+"\n"+Entity.getHospital()+Entity.getDepartment());
	    return convertView;
    }
    

    static class ViewHolder { 
        public ImageView Header;//头像暂时不加上
        public TextView Name;
        public TextView Hospital;
    }
}
