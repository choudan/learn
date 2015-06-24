package com.yijia_use;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.download.doctorback.MyApplication;
import com.example.yijia.R;
import com.http.tool.HttpUrl;
import com.http.tool.NetTool;
import com.yijia_login.TestStart;

public class Waveform extends Activity {
    private RelativeLayout pillars;
    private PopMenu popmenu1,popmenu2;
    private Column column;  
	private TextView imageyear,imagemonth,username;
//	private ArrayList<String> datalist;
	private String JMdate="";
	private ArrayList<Float> daylist,sumlist;
	private String month; 
	private String inquiryday="1";//注意修改
	private ProgressDialog progressDialog = null;
	private Button day1,day2,day3,day4,day5,day6,day7,day8,day9,day10,
									day11,day12,day13,day14,day15,day16,day17,day18,day19,day20,
									day21,day22,day23,day24,day25,day26,day27,day28,day29,day30,day31;    
    private ImageButton setyear,setmonth;
    private String[] mmonth={"12","11","10","9","8","7","6","5","4","3","2","1"};
        
    private MyApplication app;    
    
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {  
	    public void handleMessage(Message msg){ //此方法在UI线程中运行  
	    	progressDialog.dismiss();
//        	removeDialog(4); 
	    	switch(msg.what){
	    	case TestStart.FINISH:
	    		Toast.makeText(getApplicationContext(), "查询成功"+inquiryday+"  "+daylist.size()+sumlist.size(), Toast.LENGTH_SHORT).show();
	    		String[]date=JMdate.split("-");	
	    		imageyear.setText(date[0]+"年");
	    		if(date[1].startsWith("0"))
	    		{	            
	    			imagemonth.setText(date[1].substring(1, 2)+"月");	
	    			dealDate(imageyear,imagemonth);
	    		}
	    		else{
	    			imagemonth.setText(date[1]+"月");
	    			dealDate(imageyear,imagemonth);
	    		}  
	    		for(int i=0;i<daylist.size();i++){
					Log.e("=-=-=-=", "daylist:"+daylist.get(i));
					Log.e("=-=-=-=", "sumlist:"+sumlist.get(i));			
				}
	    		column.updateThisData(daylist);//测试矩形
	    		column.updateLastData(sumlist);//标准矩形	 
	    		
	    		break;
	    	case TestStart.COMPLETED:
	    		Toast.makeText(Waveform.this, "该天暂无数据"+inquiryday+"  ", Toast.LENGTH_SHORT).show();
	    		if(imageyear.getText().equals("")||imagemonth.getText().equals("")){
	    			Calendar c = Calendar.getInstance();
	    			String year = String.valueOf(c.get(Calendar.YEAR));
	    			String month = String.valueOf(c.get(Calendar.MONTH));
	    			imageyear.setText(year+"年");
	    			imagemonth.setText(month+"月");
	    			dealDate(imageyear,imagemonth);
	    		}
	    		column.updateThisData(daylist);//测试矩形
	    		column.updateLastData(sumlist);//标准矩形	 
	    		
	    		break;
	    	case TestStart.SUCCESS:
	    		Toast.makeText(Waveform.this, "您尚未上传过数据"+inquiryday, Toast.LENGTH_SHORT).show();
	    		if(imageyear.getText().equals("")||imagemonth.getText().equals("")){
	    			Calendar c = Calendar.getInstance();
	    			String year = String.valueOf(c.get(Calendar.YEAR));
	    			String month = String.valueOf(c.get(Calendar.MONTH));
	    			imageyear.setText(year+"年");
	    			imagemonth.setText(month+"月");
	    			dealDate(imageyear,imagemonth);
	    		}
	    		column.updateThisData(daylist);//测试矩形
	    		column.updateLastData(sumlist);//标准矩形	 
	    	
	    		break;
	    	default:
	    		break;
	    	}	       
	    } 		        	   
	};  
        
    protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);            
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);	
		}	
		setContentView(R.layout.column);       
        
		init();
		new Thread(runnableDownloadData).start();
				
		column=new Column(this,null);
		pillars.addView(column);  //添加柱状图
		
		popmenu1 = new PopMenu(this);
		popmenu2 = new PopMenu(this);
			
		Calendar c = Calendar.getInstance();
	    String year = String.valueOf(c.get(Calendar.YEAR));
		String[]myear={year,String.valueOf(Integer.parseInt(year)-1),String.valueOf(Integer.parseInt(year)-2)};
	    
		popmenu1.addItems(myear);
		popmenu2.addItems(mmonth);
		
		popmenu1.setOnItemClickListener(popmenuItemClickListener1);
		popmenu2.setOnItemClickListener(popmenuItemClickListener2);
     }
	 
	/*
	 * 请求经络数据线程        
	 */
	Runnable runnableDownloadData = new Runnable() {
		private String url = HttpUrl.IP + HttpUrl.DIR + "download_waveform",jsonString;
		private int statusCode;
		@SuppressLint("SimpleDateFormat")
		public void run() {			
			Map<String, String> map = new HashMap<String, String>();
			
			map.put("userid", app.getID());// 注意修改ID
			map.put("inquiryday", inquiryday);
			try {
				jsonString = NetTool.sendGetRequest(url, map, "utf-8");
				Log.e("=-=-=-=", "jsonString:"+jsonString);
				JSONObject json = new JSONObject(jsonString);
				Log.e("=-=-=-=", "json:"+json);
				String result=json.getString("result");
				String data=json.getString("data");
				statusCode = json.getInt("statusCode");
				daylist = getAvg(result);
				sumlist = getAvg(data);
//				for(int i=0;i<daylist.size();i++){
//					Log.e("=-=-=-=", "daylist:"+daylist.get(i));
//					Log.e("=-=-=-=", "sumlist:"+sumlist.get(i));			
//				}
				if(!result.equals(""))
					JMdate = new JSONObject(result).getString("date");
				else{
					JMdate=new SimpleDateFormat("yyyy-MM-dd").format(new Date());					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			Message msg = new Message();
			if(statusCode==-4){
				msg.what = TestStart.SUCCESS;	//未上传过数据			
			}else if(statusCode==-3){
				msg.what = TestStart.COMPLETED;	//该天暂无数据				
			}else if(statusCode==-1){
				msg.what = TestStart.FINISH;	//查询成功																
			}
			Log.e("=-=-=-=", "statusCode:"+statusCode);
			handler.sendMessage(msg);
		}
	};
	
	
	public ArrayList<Float> getAvg(String result){
		ArrayList<Float> list = new ArrayList<Float>();	
		if(!result.equals("")){
			try{
			JSONObject json=new JSONObject(result);
			list.add(Float.parseFloat(json.getString("danjing")));
			list.add(Float.parseFloat(json.getString("ganjing")));
			list.add(Float.parseFloat(json.getString("feijing")));
			list.add(Float.parseFloat(json.getString("dachangjing")));
			list.add(Float.parseFloat(json.getString("weijing")));
			list.add(Float.parseFloat(json.getString("pijing")));
			list.add(Float.parseFloat(json.getString("xinjing")));
			list.add(Float.parseFloat(json.getString("xiaochangjing")));
			list.add(Float.parseFloat(json.getString("pangguangjing")));
			list.add(Float.parseFloat(json.getString("shenjing")));	
			list.add(Float.parseFloat(json.getString("xinbaojing")));
			list.add(Float.parseFloat(json.getString("sanjiaojing")));
			list.add(Float.parseFloat(json.getString("renmaishou")));
			list.add(Float.parseFloat(json.getString("renmaizu")));
			list.add(Float.parseFloat(json.getString("dumaishou")));
			list.add(Float.parseFloat(json.getString("dumaizu")));
			list.add(Float.parseFloat(json.getString("chongmai")));
			list.add(Float.parseFloat(json.getString("chongmaidaimai")));
			list.add(Float.parseFloat(json.getString("geyueshou")));
			list.add(Float.parseFloat(json.getString("geyuezu")));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		}else{
			return null; 		
		}
		return list; 
	}	
	 	 
	 /*
	  * 由最原始的Json字符串解析出ArrayList[Json]
	  */
	public ArrayList<String> getlistString(String key, String jsonString) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				String msg = jsonArray.getString(i);
				list.add(msg);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	OnItemClickListener popmenuItemClickListener1 = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			String year=popmenu1.getitem(position);			
			imageyear.setText(year+"年");
			dealDate(imageyear,imagemonth);			
			popmenu1.dismiss();
		}
	};
	
	OnItemClickListener popmenuItemClickListener2 = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			month=popmenu2.getitem(position);
			imagemonth.setText(month+"月");
			dealDate(imageyear,imagemonth);		
			popmenu2.dismiss();
		}	  
	};
	
	
	private void dealDate(TextView year_text,TextView month_text){
		String month,submonth;
		int i= Integer.parseInt(year_text.getText().toString().substring(0, 4));
		month=month_text.getText().toString();
		if(month.substring(1, 2).equals("月")){
			 submonth=month.substring(0, 1);
		}
		else{
			 submonth=month.substring(0, 2);
		}
		
		if(submonth.equals("2")){
			if((i % 4 == 0 && i % 100 != 0) || i % 400 == 0){
				day29.setVisibility(0);
				day31.setVisibility(8);	
				day30.setVisibility(8);	
			}
			else{
				day29.setVisibility(8);										
				day31.setVisibility(8);	
				day30.setVisibility(8);	
			}		
		}
		else if(submonth.equals("1")||submonth.equals("3")||submonth.equals("5")||submonth.equals("7")
				   ||submonth.equals("8")||submonth.equals("10")||submonth.equals("12")){
				day31.setVisibility(0);	
			    day30.setVisibility(0);	
			    day29.setVisibility(0);
		}else{
			 	day31.setVisibility(8);	
			    day30.setVisibility(0);	
			    day29.setVisibility(0);	
		}						
	}
	
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		return new AlertDialog.Builder(Waveform.this).setTitle("正在加载搜索结果...").setMessage("稍等啦").create();
	 }
	
	private void init(){	
		progressDialog=ProgressDialog.show(this, "正在加载经络数据...", "稍等啦");
//		onCreateDialog(4).show();
	    app=(MyApplication)getApplication();
		
	    daylist=new ArrayList<Float>();
		sumlist=new ArrayList<Float>();
	    
		setyear=(ImageButton)findViewById(R.id.edityear);
		setyear.getBackground().setAlpha(0);  //设置ImageButton或者button的透明度，0为全透明
		setyear.setOnClickListener(listener);
		
		setmonth=(ImageButton)findViewById(R.id.editmonth);
		setmonth.getBackground().setAlpha(0); 
		setmonth.setOnClickListener(listener);		
				
		pillars= (RelativeLayout) findViewById(R.id.pillars);
		
		imageyear=(TextView)findViewById(R.id.year);
		imagemonth=(TextView)findViewById(R.id.month);	
		username=(TextView)findViewById(R.id.username);
		username.setText(app.getNAME());
				
	    day1=(Button)findViewById(R.id.Bt1);
		day1.setOnClickListener(listener);
	    
	    day2=(Button)findViewById(R.id.Bt2);
	    day2.setOnClickListener(listener);
	
	    day3=(Button)findViewById(R.id.Bt3);
	    day3.setOnClickListener(listener);
	
	    day4=(Button)findViewById(R.id.Bt4);
	    day4.setOnClickListener(listener);
		
	    day5=(Button)findViewById(R.id.Bt5);
	    day5.setOnClickListener(listener);
	    
	    day6=(Button)findViewById(R.id.Bt6);
	    day6.setOnClickListener(listener);
	
	    day7=(Button)findViewById(R.id.Bt7);
	    day7.setOnClickListener(listener);
	
	    day8=(Button)findViewById(R.id.Bt8);
	    day8.setOnClickListener(listener);
	    
	    day9=(Button)findViewById(R.id.Bt9);
	    day9.setOnClickListener(listener);
	    
	    day10=(Button)findViewById(R.id.Bt10);
	    day10.setOnClickListener(listener);
	
	    day11=(Button)findViewById(R.id.Bt11);
		day11.setOnClickListener(listener);
	    
	    day12=(Button)findViewById(R.id.Bt12);
	    day12.setOnClickListener(listener);
	
	    day13=(Button)findViewById(R.id.Bt13);
	    day13.setOnClickListener(listener);
	
	    day14=(Button)findViewById(R.id.Bt14);
	    day14.setOnClickListener(listener);
		
	    day15=(Button)findViewById(R.id.Bt15);
	    day15.setOnClickListener(listener);
	    
	    day16=(Button)findViewById(R.id.Bt16);
	    day16.setOnClickListener(listener);
	
	    day17=(Button)findViewById(R.id.Bt17);
	    day17.setOnClickListener(listener);
	
	    day18=(Button)findViewById(R.id.Bt18);
	    day18.setOnClickListener(listener);
	    
	    day19=(Button)findViewById(R.id.Bt19);
	    day19.setOnClickListener(listener);
	    
	    day20=(Button)findViewById(R.id.Bt20);
	    day20.setOnClickListener(listener);
	    
	    day21=(Button)findViewById(R.id.Bt21);
		day21.setOnClickListener(listener);
	    
	    day22=(Button)findViewById(R.id.Bt22);
	    day22.setOnClickListener(listener);
	
	    day23=(Button)findViewById(R.id.Bt23);
	    day23.setOnClickListener(listener);
	
	    day24=(Button)findViewById(R.id.Bt24);
	    day24.setOnClickListener(listener);
		
	    day25=(Button)findViewById(R.id.Bt25);
	    day25.setOnClickListener(listener);
	    
	    day26=(Button)findViewById(R.id.Bt26);
	    day26.setOnClickListener(listener);
	
	    day27=(Button)findViewById(R.id.Bt27);
	    day27.setOnClickListener(listener);
	
	    day28=(Button)findViewById(R.id.Bt28);
	    day28.setOnClickListener(listener);
	    
	    day29=(Button)findViewById(R.id.Bt29);
	    day29.setOnClickListener(listener);
	    
	    day30=(Button)findViewById(R.id.Bt30);
	    day30.setOnClickListener(listener);
	    
	    day31=(Button)findViewById(R.id.Bt31);
	    day31.setOnClickListener(listener);		   
	}  
		 
    private OnClickListener listener = new OnClickListener(){
    	public void onClick(View v){
    		switch(v.getId()){
    		case R.id.editmonth:
    			popmenu2.showAsDropDown(v);
    			break;
    		case R.id.edityear:
    			popmenu1.showAsDropDown(v);
    			break;
    		case R.id.Bt1:  
    			//     
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"01";
    			}else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"01";
    			}       			
    			new Thread(runnableDownloadData).start();   			
   			   break;  
    		case R.id.Bt2:
    		
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"02";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"02";
    			}       		
    			new Thread(runnableDownloadData).start();
 			    break;  
    		case R.id.Bt3:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"03";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"03";
    			}
    			new Thread(runnableDownloadData).start();
 			    break;  
    		case R.id.Bt4:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"04";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"04";
    			}
    			new Thread(runnableDownloadData).start();
 			    break; 
    		case R.id.Bt5:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"05";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"05";
    			}
    			new Thread(runnableDownloadData).start();
    			Log.e("______", inquiryday);
			    break;    
    		case R.id.Bt6:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"06";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"06";
    			}
    			new Thread(runnableDownloadData).start();
			    break;    
    		case R.id.Bt7:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"07";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"07";
    			}
    			new Thread(runnableDownloadData).start();
			    break;    
    		case R.id.Bt8:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"08";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"08";
    			}
    			new Thread(runnableDownloadData).start();
			    break;    
    		case R.id.Bt9:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"09";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"09";
    			}
    			new Thread(runnableDownloadData).start();
			    break;    
    		case R.id.Bt10:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"10";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"10";
    			}
    			new Thread(runnableDownloadData).start();
		     	break;  
    		case R.id.Bt11: 
    			
    			//      			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"11";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"11";
    			}    			  
    			new Thread(runnableDownloadData).start();
    			break;  
    		case R.id.Bt12:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"12";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"12";
    			}
    			new Thread(runnableDownloadData).start();
 			    break;  
    		case R.id.Bt13:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"13";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"13";
    			}
    			new Thread(runnableDownloadData).start();
 			    break;  
    		case R.id.Bt14:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"14";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"14";
    			}
    			new Thread(runnableDownloadData).start();
 			    break; 
    		case R.id.Bt15:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"15";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"15";
    			}
    			new Thread(runnableDownloadData).start();
			    break;    
    		case R.id.Bt16:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"16";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"16";
    			}
    			new Thread(runnableDownloadData).start();
			    break;    
    		case R.id.Bt17:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"17";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"17";
    			}
    			new Thread(runnableDownloadData).start();
			    break;    
    		case R.id.Bt18:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"18";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"18";
    			}
    			new Thread(runnableDownloadData).start();
			    break;    
    		case R.id.Bt19:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"19";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"19";
    			}
    			new Thread(runnableDownloadData).start();
			    break;    
    		case R.id.Bt20:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"20";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"20";
    			}
    			new Thread(runnableDownloadData).start();
		     	break;  	
    		case R.id.Bt21:  
    			
    			//      			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"21";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"21";
    			}   			  
    			new Thread(runnableDownloadData).start();
    			break;  
    		case R.id.Bt22:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"22";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"22";
    			}   			  
    			new Thread(runnableDownloadData).start();
 			    break;  
    		case R.id.Bt23:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"23";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"23";
    			}   			  
    			new Thread(runnableDownloadData).start();
 			    break;  
    		case R.id.Bt24:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"24";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"24";
    			}   			  
    			new Thread(runnableDownloadData).start();
 			    break; 
    		case R.id.Bt25:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"25";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"25";
    			}   			  
    			new Thread(runnableDownloadData).start();
			    break;    
    		case R.id.Bt26:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"26";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"26";
    			}   			  
    			new Thread(runnableDownloadData).start();
			    break;    
    		case R.id.Bt27:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"27";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"27";
    			}   			  
    			new Thread(runnableDownloadData).start();
			    break;    
    		case R.id.Bt28:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"28";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"28";
    			}   			  
    			new Thread(runnableDownloadData).start();
			    break;    
    		case R.id.Bt29:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"29";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"29";
    			}   			  
    			new Thread(runnableDownloadData).start();
			    break;    
    		case R.id.Bt30:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"30";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"30";
    			}   			  
    			new Thread(runnableDownloadData).start();
		     	break;   	
    		case R.id.Bt31:
    			
    			if(imagemonth.getText().subSequence(1, 2).equals("月"))
    			{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+"0"+imagemonth.getText().subSequence(0, 1)+"-"+"31";
    			}
    			else{
    				inquiryday=imageyear.getText().subSequence(0, 4).toString()+"-"+imagemonth.getText().subSequence(0, 2)+"-"+"31";
    			}   			  
    			new Thread(runnableDownloadData).start();
		     	break; 
	        }  
		  }
	    };
	  }
