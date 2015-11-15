/**
 * 
 */
package com.example.yijia.third.user;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yijia.third.bean.common.Communication;
import com.example.yijia.third.picture.GridViewAdapter;
import com.example.yijia.third.picture.ImagePager;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author Administrator
 *
 */
public class CommunicationAdapter extends BaseAdapter {
	private ArrayList<Communication> list;
	private LayoutInflater inflater;
	private Context mContext;
	
	public CommunicationAdapter(Context mContext,ArrayList<Communication> list) {
		super();
		this.list = list;
		this.mContext=mContext;
		inflater=LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	//Ҫʵ������������
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 4;
	}
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		Communication entity = list.get(position);
	    int type=-1;
	    switch(entity.getType()){
	    case 0:
	    	if(entity.getPicPath()==null)
	    		type=0;     //��ͼƬ���û�0
	    	else
	    		type= 1;  	//��ͼƬ���û�1		    	
	    	break;
		case 1:
			if(entity.getPicPath()==null)
				type=2;  	//��ͼƬ�ķ����2	
			else
				type=3;     //��ͼƬ�ķ����3
			break;
			default:
				break;
	    }
	    return type;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub	
		ViewHolder view0, view1, view2, view3;
		Communication entity = list.get(position);
		final ArrayList<String> list = entity.getPicPath();
		switch (getItemViewType(position)) {
		case 0:
			if (convertView == null) {
				view0 = new ViewHolder();
				convertView = inflater.inflate(R.layout.a_communication_user_item, null);
				view0.date = (TextView) convertView.findViewById(R.id.date);
				view0.content = (TextView) convertView.findViewById(R.id.content);
				view0.iv_userhead = (ImageView) convertView.findViewById(R.id.iv_userhead);
				convertView.setTag(view0);
			} else {
				view0 = (ViewHolder) convertView.getTag();
			}
			view0.date.setText(entity.getDate());
			view0.content.setText(entity.getText());
//			imageHeadImg(entity, view0);
			break;
		case 1:
			if (convertView == null) {
				view1 = new ViewHolder();
				convertView = inflater.inflate(R.layout.a_communication_user_item_pic, null);
				view1.date = (TextView) convertView.findViewById(R.id.date);
				view1.content = (TextView) convertView.findViewById(R.id.content);
				view1.iv_userhead = (ImageView) convertView.findViewById(R.id.iv_userhead);
				view1.gridview = (GridView) convertView.findViewById(R.id.gridview);
				convertView.setTag(view1);
			} else {
				view1 = (ViewHolder) convertView.getTag();
			}
			view1.date.setText(entity.getDate());
			view1.content.setText(entity.getText());
//			imageHeadImg(entity, view1);
			view1.gridview.setAdapter(new GridViewAdapter(mContext, list));
			view1.gridview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					imageBrower(arg2, list);
				}
			});

			break;
		case 2:
			if (convertView == null) {
				view2 = new ViewHolder();
				convertView = inflater.inflate(R.layout.a_communication_sa_item, null);
				view2.date = (TextView) convertView.findViewById(R.id.date);
				view2.content = (TextView) convertView.findViewById(R.id.content);
				view2.iv_userhead = (ImageView) convertView.findViewById(R.id.iv_userhead);

				convertView.setTag(view2);
			} else {
				view2 = (ViewHolder) convertView.getTag();

			}
			view2.date.setText(entity.getDate());
			view2.content.setText(entity.getText());
//			imageHeadImg(entity, view2);
			break;
		case 3:
			if (convertView == null) {
				view3 = new ViewHolder();
				convertView = inflater.inflate(R.layout.a_communication_sa_item_pic, null);
				view3.date = (TextView) convertView.findViewById(R.id.date);
				view3.content = (TextView) convertView.findViewById(R.id.content);
				view3.iv_userhead = (ImageView) convertView.findViewById(R.id.iv_userhead);
				view3.gridview = (GridView) convertView.findViewById(R.id.gridview);

				convertView.setTag(view3);

			} else {
				view3 = (ViewHolder) convertView.getTag();

			}
			view3.date.setText(entity.getDate());
			view3.content.setText(entity.getText());
//			imageHeadImg(entity, view3);
			view3.gridview.setAdapter(new GridViewAdapter(mContext, list));
			view3.gridview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					imageBrower(arg2, list);
				}
			});
		}
		return convertView;
	}
	
	/**
	 * ��ͼƬ�鿴��
	 * 
	 * @param position
	 * @param urls2
	 */
	protected void imageBrower(int position, ArrayList<String> urls2) {
		Intent intent = new Intent(mContext, ImagePager.class);
		// ͼƬurl
		intent.putExtra(Constant.IMAGE_URLS, urls2);
		intent.putExtra(Constant.IMAGE_INDEX, position);
		mContext.startActivity(intent);
	}
	
	/**
	 * ͷ��
	 * 
	 * @param entity
	 * @param holder
	 */
	protected void imageHeadImg(Communication entity,ViewHolder holder){
		DisplayImageOptions options = new DisplayImageOptions.Builder()//
		.showImageOnLoading(R.drawable.ic_launcher) // ��������ʾ��Ĭ��ͼƬ
		.showImageOnFail(R.drawable.ic_launcher) // ���ü���ʧ�ܵ�Ĭ��ͼƬ
		.cacheInMemory(true) // �ڴ滺��
		.cacheOnDisk(true) // sdcard����
		.bitmapConfig(Config.RGB_565)// �����������
		.build();//
		ImageLoader.getInstance().displayImage(entity.getHeadImgUrl(), holder.iv_userhead, options);
	}
	
	public class ViewHolder{
		public TextView date,content;
		public ImageView iv_userhead;
		public GridView gridview;		
	}

}


/** 
 * ��ҳListView������������ 
 *  
 * @author luojing
 * һ��������ͼ��
 * �м��ֲ��֣���Ҫ��������ViewHolder����������һ��һ����,���򣬸��û��ƻ�������ݴ��ҡ��������������������ǹؼ�
 * ʵ��getViewType()��getViewTypeCount()���� �������������������������������������ǹؼ�
 * (1)�������ViewHolder��ÿ��ViewHolder��inflateһ����һ�޶������Լ�layout�����ļ�
 * (2)����һ��ViewHolder,inflateһ�ֹ��õ�layout�ļ���ʵ�������viewholder,ͨ���ؼ�����ʾ��������ʵ�ֶ��ֲ���
 * (3)����һ��ViewHolder,inflate���ֲ����Լ���layout�ļ���ʵ�������viewholder,ʵ�ֶ��ֲ���
 * ������õ��ǵ�3�֣��ϼ�
 *  
 */  
////�м�����ͼ������Ҫ���弸�������������������״���
//public class ListItemAdapter extends BaseAdapter {  
//
//	private Context mContext;  
//	private ArrayList<ItemEntity> items;  
//	//private LayoutInflater mInflater;
//	private int USER =0;
//	private int USER_PIC=1;
//	private int DOCTOR=2;
//	
//	public ListItemAdapter(Context ctx, ArrayList<ItemEntity> items) {  
//	    this.mContext = ctx;  
//	    this.items = items; 
//	//    mInflater = LayoutInflater.from(ctx);
//	}  
//	
//	
//	public void update() {
//		// TODO Auto-generated method stub
//		super.notifyDataSetChanged();
//	}
//
//	@Override  
//	public int getCount() {
//	    return items.size();
//	}
//	
//	@Override  
//	public Object getItem(int position) {  
//	    return items.get(position);  
//	}  
//	
//	@Override  
//	public long getItemId(int position) {  
//	    return position;  
//	}   
//	
//	//Ҫʵ������������
//	public int getViewTypeCount() {
//		// TODO Auto-generated method stub
//		return 3;
//	}
//	
//	@Override
//	public int getItemViewType(int position) {
//		// TODO Auto-generated method stub
//	    ItemEntity itementity = items.get(position);
//	    int TYPE=-1;
//	    switch(itementity.getType()){
//	    case 0:
//	    	if(itementity.getImageUrls()==null){
//	    		TYPE=USER; //��ͼƬ���û�0
//	    	}else{
//	    		TYPE= USER_PIC;  	//��ͼ�û�1	
//	    	}
//	    	break;
//		case 1:
//			TYPE= DOCTOR;  	//ҽ��2	
//			break;
//	    }
//	    return TYPE;
//	}
//	
//	@Override  
//	public View getView(int position, View convertView, ViewGroup parent) {  
//		ViewHolder1  holder0,holder1,holder2;
//		
//	    ItemEntity itementity = items.get(position);//if����ֻ��һ��ִ�����ʱ��{}����ʡ�ԡ�
//	    int isComMsg = itementity.getType();
//	    final ArrayList<String> imageUrls = itementity.getImageUrls();
//	    		
//	    if (convertView == null) //��ʼ����û��ʼ���ã������Զ����view������ͬһ�����
//	    {  
//	    	 switch(isComMsg){
//	         case 0:
//	         	if(itementity.getImageUrls()==null){//��ͼ
//	         		
//	         		convertView = View.inflate(mContext,R.layout.chatting_item_user, null);  
//	        		holder0 = new ViewHolder1();    
//	        		holder0.tv_content = (TextView) convertView.findViewById(R.id.content);
//	        		holder0.date=(TextView) convertView.findViewById(R.id.date);  		          
//	        		
//	        		convertView.setTag(holder0);  
//	        		
//	         	}else{
//	         		convertView = View.inflate(mContext,R.layout.chatting_item_user_pic, null);  
//	         		holder1 = new ViewHolder1();    
//	         		holder1.tv_content = (TextView) convertView.findViewById(R.id.content);
//	         		holder1.gridview = (NoScrollGridView) convertView.findViewById(R.id.gridview);
//	         		holder1.date=(TextView) convertView.findViewById(R.id.date);
//	         		
//	        		convertView.setTag(holder1); 
//	         	}
//	         	break;
//	 		case 1:
//	
//	 			convertView = View.inflate(mContext,R.layout.chatting_item_doctor, null);  
//	 			holder2 = new ViewHolder1();    
//	 			holder2.tv_content = (TextView) convertView.findViewById(R.id.content);
//	 			
//	    		convertView.setTag(holder2);  
//	 			break;
//	         }
//	        		                
//	   }else{//����Tag,��ʼ����
//	    	   switch(isComMsg){
//	           case 0:
//	           	if(itementity.getImageUrls()==null){
//	           		holder0 = (ViewHolder1) convertView.getTag(); 
//		   			holder0.date.setText(itementity.getDate());
//		   			holder0.tv_content.setText(itementity.getUserWords()); 
//	           	}else{
//	           		holder1 = (ViewHolder1) convertView.getTag(); 
//	           		holder1.date.setText(itementity.getDate());
//	           		holder1.tv_content.setText(itementity.getUserWords()); 
//	           		holder1.gridview.setAdapter(new NoScrollGridAdapter(mContext,getName(imageUrls))); 
//	           		holder1.gridview.setOnItemClickListener(new OnItemClickListener() {  
//	    	         	
//	    	         	@Override  
//	    	         	public void onItemClick(AdapterView<?> parent, View view,  
//	    	         			int position, long id) {  
//	    	         		// TODO Auto-generated method stub  
//	    	         		imageBrower(position, imageUrls);  
//	    	         	}  
//	    	         });  
//	           	}
//	           	break;
//	   		case 1:
//	   			holder2 = (ViewHolder1) convertView.getTag();  
//	   			holder2.tv_content.setText(itementity.getDoc_time());
//	   			break;
//	           }
//	   	}
//	    return convertView;  
//	} 
//	//����ͼ·��	
//	private ArrayList<String> getName(ArrayList<String> s){
//		 ArrayList<String> listname=new ArrayList<String>();
//		 for(int i=0;i<s.size();i++){
//			 String[] namearray=s.get(i).split("/");
//			 String name=namearray[namearray.length-1];
//			 listname.add(HttpUrl.IP+"/yijia/picture/m_"+name);
//		 }
//		 return listname;
//	}
//	
//	protected void imageBrower(int position, ArrayList<String> urls2) {  
//	    Intent intent = new Intent(mContext, ImagePagerActivity.class);  
//	    // ͼƬurl 
//	    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);  
//	    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);  
//	    mContext.startActivity(intent);  
//	}  
//	
//	
//	class ViewHolder1 {  //��ͼ
//		 private TextView date;  
//	     private TextView tv_content;  
//	     private NoScrollGridView gridview; 
//	} 
//
//}
