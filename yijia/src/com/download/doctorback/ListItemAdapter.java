package com.download.doctorback;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yijia.R;
import com.http.tool.HttpUrl;

/** 
 * ��ҳListView������������ 
 *  
 * @author luojing
 * �м��ֲ��֣���Ҫ��������ViewHolder,���򣬸��û��ƻ�������ݴ��ҡ��������������������ǹؼ�
 * ʵ��getViewType()��getViewTypeCount()���� �������������������������������������ǹؼ�
 * (1)�������ViewHolder��ÿ��ViewHolder��inflateһ����һ�޶������Լ�layout�����ļ�
 * (2)����һ��ViewHolder,inflateһ�ֹ��õ�layout�ļ���ʵ�������viewholder,ͨ���ؼ�����ʾ��������ʵ�ֶ��ֲ���
 * (3)����һ��ViewHolder,inflate���ֲ����Լ���layout�ļ���ʵ�������viewholder,ʵ�ֶ��ֲ���
 * ������õ��ǵ�3�֣��ϼ�
 *  
 */  

/*    public View getView(int position, View convertView, ViewGroup parent) {  
		ViewHolder0  holder0=null;
		ViewHolder1  holder1=null;
		ViewHolder2  holder2=null;
		
        ItemEntity itementity = items.get(position);//if����ֻ��һ��ִ�����ʱ��{}����ʡ�ԡ�
        int isComMsg = itementity.getType();
        final ArrayList<String> imageUrls = itementity.getImageUrls();
        		
        if (convertView == null) //��ʼ����û��ʼ���ã������Զ����view������ͬһ�����
        {  
        	 switch(isComMsg){
             case 0:
             	if(itementity.getImageUrls()==null){//��ͼ
             		
             		convertView = View.inflate(mContext,R.layout.chatting_item_user, null);  
            		holder0 = new ViewHolder0();    
            		holder0.tv_content = (TextView) convertView.findViewById(R.id.content);
            		holder0.date=(TextView) convertView.findViewById(R.id.date);  		          
            		
            		convertView.setTag(holder0);  
            		
             	}else{
             		convertView = View.inflate(mContext,R.layout.chatting_item_user_pic, null);  
             		holder1 = new ViewHolder1();    
             		holder1.tv_content = (TextView) convertView.findViewById(R.id.content);
             		holder1.gridview = (NoScrollGridView) convertView.findViewById(R.id.gridview);
             		holder1.date=(TextView) convertView.findViewById(R.id.date);
             		
            		convertView.setTag(holder1); 
             	}
             	break;
     		case 1:
	
     			convertView = View.inflate(mContext,R.layout.chatting_item_doctor, null);  
     			holder2 = new ViewHolder2();    
     			holder2.tv_content = (TextView) convertView.findViewById(R.id.content);
     			
        		convertView.setTag(holder2);  
     			break;
             }
            		                
       }else{//����Tag,��ʼ����
	    	   switch(isComMsg){
	           case 0:
	           	if(itementity.getImageUrls()==null){
	           		holder0 = (ViewHolder0) convertView.getTag(); 
    	   			holder0.date.setText(itementity.getDate());
    	   			holder0.tv_content.setText(itementity.getUserWords()); 
	           	}else{
	           		holder1 = (ViewHolder1) convertView.getTag(); 
	           		holder1.date.setText(itementity.getDate());
	           		holder1.tv_content.setText(itementity.getUserWords()); 
	           		holder1.gridview.setAdapter(new NoScrollGridAdapter(mContext,imageUrls)); 
	           		holder1.gridview.setOnItemClickListener(new OnItemClickListener() {  
	    	         	
	    	         	@Override  
	    	         	public void onItemClick(AdapterView<?> parent, View view,  
	    	         			int position, long id) {  
	    	         		// TODO Auto-generated method stub  
	    	         		imageBrower(position, imageUrls);  
	    	         	}  
	    	         });  
	           	}
	           	break;
	   		case 1:
	   			holder2 = (ViewHolder2) convertView.getTag();  
	   			holder2.tv_content.setText(itementity.getDoc_time());
	   			break;
	           }
	   	}
        return convertView;  
    } 
	
	
    *//** 
     * ��ͼƬ�鿴�� 
     *  
     * @param position 
     * @param urls2 
     *//*  
    protected void imageBrower(int position, ArrayList<String> urls2) {  
        Intent intent = new Intent(mContext, ImagePagerActivity.class);  
        // ͼƬurl,Ϊ����ʾ����ʹ�ó�����һ������ݿ��л������л�ȡ  
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);  
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);  
        mContext.startActivity(intent);  
    }  
  
    *//** 
     * listview������ã���ֹ�����١� 
     *  
     * @author Administrator 
     *  
     *//*  
    class ViewHolder0 {  //��ͼ
        private TextView date;  
        private TextView tv_content;  

        } 
    
    class ViewHolder1 {  //��ͼ
    	 private TextView date;  
         private TextView tv_content;  
         private NoScrollGridView gridview; 
    } 
    
    class ViewHolder2 {  //ҽ��
    	private TextView tv_content;  
    } 
} */ 


//public View getView(int position, View convertView, ViewGroup parent) {  
//ViewHolder1  holder0,holder1,holder2;
//
//ItemEntity itementity = items.get(position);//if����ֻ��һ��ִ�����ʱ��{}����ʡ�ԡ�
//int isComMsg = itementity.getType();
//final ArrayList<String> imageUrls = itementity.getImageUrls();
//		
//if (convertView == null) //��ʼ����û��ʼ���ã������Զ����view������ͬһ�����
//{  
//	 convertView = View.inflate(mContext,R.layout.chatting_item_user_pic, null);  
//	
//	 switch(isComMsg){
//     case 0:
//     	if(itementity.getImageUrls()==null){//��ͼ
//     		
//    		holder0 = new ViewHolder1();    
//    		holder0.tv_content = (TextView) convertView.findViewById(R.id.content);
//    		holder0.date=(TextView) convertView.findViewById(R.id.date); 
//    		holder0.gridview = (NoScrollGridView) convertView.findViewById(R.id.gridview);
//    		
//    		
//    		convertView.setTag(holder0);  
//    		
//     	}else{
//     		holder1 = new ViewHolder1();    
//     		holder1.tv_content = (TextView) convertView.findViewById(R.id.content);
//     		holder1.gridview = (NoScrollGridView) convertView.findViewById(R.id.gridview);
//     		holder1.date=(TextView) convertView.findViewById(R.id.date);
//     		
//    		convertView.setTag(holder1); 
//     	}
//     	break;
//		case 1:
//
//			holder2 = new ViewHolder1();    
//			holder2.tv_content = (TextView) convertView.findViewById(R.id.content);
// 		holder2.gridview = (NoScrollGridView) convertView.findViewById(R.id.gridview);
// 		holder2.date=(TextView) convertView.findViewById(R.id.date);
// 		
//		convertView.setTag(holder2);  
//			break;
//     }
//    		                
//}else{//����Tag,��ʼ����
//	   switch(isComMsg){
//       case 0:
//       	if(itementity.getImageUrls()==null){
//       		holder0 = (ViewHolder1) convertView.getTag(); 
//   			holder0.date.setText(itementity.getDate());
//   			holder0.tv_content.setText(itementity.getUserWords()); 
////   			holder0.gridview.setVisibility(View.GONE);
//       	}else{
//       		holder1 = (ViewHolder1) convertView.getTag(); 
//       		holder1.date.setText(itementity.getDate());
//       		holder1.tv_content.setText(itementity.getUserWords()); 
//       		holder1.gridview.setAdapter(new NoScrollGridAdapter(mContext,imageUrls)); 
//       		holder1.gridview.setOnItemClickListener(new OnItemClickListener() {  
//	         	
//	         	@Override  
//	         	public void onItemClick(AdapterView<?> parent, View view,  
//	         			int position, long id) {  
//	         		// TODO Auto-generated method stub  
//	         		imageBrower(position, imageUrls);  
//	         	}  
//	         });  
//       	}
//       	break;
//		case 1:
//			holder2 = (ViewHolder1) convertView.getTag();  
//			holder2.tv_content.setText(itementity.getDoc_time());
//			holder2.gridview.setVisibility(View.GONE);
//			holder2.date.setVisibility(View.GONE);
//
//			break;
//       }
//	}
//return convertView;  
//} 
//class ViewHolder1 {  //��ͼ
// private TextView date;  
//private TextView tv_content;  
//private NoScrollGridView gridview; 
//} 


//�м�����ͼ������Ҫ���弸�������������������״���
public class ListItemAdapter extends BaseAdapter {  

	private Context mContext;  
	private ArrayList<ItemEntity> items;  
	//private LayoutInflater mInflater;
	private int USER =0;
	private int USER_PIC=1;
	private int DOCTOR=2;
	
	public ListItemAdapter(Context ctx, ArrayList<ItemEntity> items) {  
	    this.mContext = ctx;  
	    this.items = items; 
	//    mInflater = LayoutInflater.from(ctx);
	}  
	
	@Override  
	public int getCount() {
	    return items.size();
	}
	
	@Override  
	public Object getItem(int position) {  
	    return items.get(position);  
	}  
	
	@Override  
	public long getItemId(int position) {  
	    return position;  
	}   
	
	//Ҫʵ������������
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 3;
	}
	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
	    ItemEntity itementity = items.get(position);
	    int TYPE=-1;
	    switch(itementity.getType()){
	    case 0:
	    	if(itementity.getImageUrls()==null){
	    		TYPE=USER; //��ͼƬ���û�0
	    	}else{
	    		TYPE= USER_PIC;  	//��ͼ�û�1	
	    	}
	    	break;
		case 1:
			TYPE= DOCTOR;  	//ҽ��2	
			break;
	    }
	    return TYPE;
	}
	
	@Override  
	public View getView(int position, View convertView, ViewGroup parent) {  
		ViewHolder1  holder0,holder1,holder2;
		
	    ItemEntity itementity = items.get(position);//if����ֻ��һ��ִ�����ʱ��{}����ʡ�ԡ�
	    int isComMsg = itementity.getType();
	    final ArrayList<String> imageUrls = itementity.getImageUrls();
	    		
	    if (convertView == null) //��ʼ����û��ʼ���ã������Զ����view������ͬһ�����
	    {  
	    	 switch(isComMsg){
	         case 0:
	         	if(itementity.getImageUrls()==null){//��ͼ
	         		
	         		convertView = View.inflate(mContext,R.layout.chatting_item_user, null);  
	        		holder0 = new ViewHolder1();    
	        		holder0.tv_content = (TextView) convertView.findViewById(R.id.content);
	        		holder0.date=(TextView) convertView.findViewById(R.id.date);  		          
	        		
	        		convertView.setTag(holder0);  
	        		
	         	}else{
	         		convertView = View.inflate(mContext,R.layout.chatting_item_user_pic, null);  
	         		holder1 = new ViewHolder1();    
	         		holder1.tv_content = (TextView) convertView.findViewById(R.id.content);
	         		holder1.gridview = (NoScrollGridView) convertView.findViewById(R.id.gridview);
	         		holder1.date=(TextView) convertView.findViewById(R.id.date);
	         		
	        		convertView.setTag(holder1); 
	         	}
	         	break;
	 		case 1:
	
	 			convertView = View.inflate(mContext,R.layout.chatting_item_doctor, null);  
	 			holder2 = new ViewHolder1();    
	 			holder2.tv_content = (TextView) convertView.findViewById(R.id.content);
	 			
	    		convertView.setTag(holder2);  
	 			break;
	         }
	        		                
	   }else{//����Tag,��ʼ����
	    	   switch(isComMsg){
	           case 0:
	           	if(itementity.getImageUrls()==null){
	           		holder0 = (ViewHolder1) convertView.getTag(); 
		   			holder0.date.setText(itementity.getDate());
		   			holder0.tv_content.setText(itementity.getUserWords()); 
	           	}else{
	           		holder1 = (ViewHolder1) convertView.getTag(); 
	           		holder1.date.setText(itementity.getDate());
	           		holder1.tv_content.setText(itementity.getUserWords()); 
	           		holder1.gridview.setAdapter(new NoScrollGridAdapter(mContext,getName(imageUrls))); 
	           		holder1.gridview.setOnItemClickListener(new OnItemClickListener() {  
	    	         	
	    	         	@Override  
	    	         	public void onItemClick(AdapterView<?> parent, View view,  
	    	         			int position, long id) {  
	    	         		// TODO Auto-generated method stub  
	    	         		imageBrower(position, imageUrls);  
	    	         	}  
	    	         });  
	           	}
	           	break;
	   		case 1:
	   			holder2 = (ViewHolder1) convertView.getTag();  
	   			holder2.tv_content.setText(itementity.getDoc_time());
	   			break;
	           }
	   	}
	    return convertView;  
	} 
	//����ͼ·��	
	private ArrayList<String> getName(ArrayList<String> s){
		 ArrayList<String> listname=new ArrayList<String>();
		 for(int i=0;i<s.size();i++){
			 String[] namearray=s.get(i).split("/");
			 String name=namearray[namearray.length-1];
			 listname.add(HttpUrl.IP+"/yijia/picture/m_"+name);
		 }
		 return listname;
	}
	
	protected void imageBrower(int position, ArrayList<String> urls2) {  
	    Intent intent = new Intent(mContext, ImagePagerActivity.class);  
	    // ͼƬurl 
	    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);  
	    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);  
	    mContext.startActivity(intent);  
	}  
	
	
	class ViewHolder1 {  //��ͼ
		 private TextView date;  
	     private TextView tv_content;  
	     private NoScrollGridView gridview; 
	} 

}
