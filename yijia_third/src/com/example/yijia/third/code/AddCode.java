/**
 * 
 */
package com.example.yijia.third.code;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.tool.CalendarUtils;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 *
 */
public class AddCode extends BaseActivity implements OnClickListener{
	private ListView list_code_generate;
	private Button copy,generate,minus,plus;
	private TextView date;
	private ArrayList<String> list;
	private int codeType;
	private EditText code_num;
	private int num;//兑换码个数
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.a_add_code);
		codeType=getIntent().getIntExtra(Constant.CODE_TYPE, -1);
		Log.e(TAG, "传过来的codeType是：  "+codeType);
		init();
	}
	
	protected void init(){
		setTittleText(this.getString(R.string.add_code));
		setBtnVisiable(false);
		
		list_code_generate=(ListView)findViewById(R.id.list_code_generate);
		
		code_num=(EditText)findViewById(R.id.code_num);
		code_num.setOnClickListener(this);
		setEditable(code_num, false);
		
		copy=(Button)findViewById(R.id.copy);
		copy.setOnClickListener(this);
		
		generate=(Button)findViewById(R.id.generate);
		generate.setOnClickListener(this);
		
		minus=(Button)findViewById(R.id.minus);
		minus.setOnClickListener(this);
		
		plus=(Button)findViewById(R.id.plus);
		plus.setOnClickListener(this);
				
		date=(TextView)findViewById(R.id.date);
		
		setVisiable(View.GONE);
		initData();
	}
	
	private void initData(){	
		date.setText(CalendarUtils.dateTime().substring(0, 10));
		list=new ArrayList<String>();		
		list.add("12345");
		list.add("6789");	
		ArrayAdapter<String> mAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
		list_code_generate.setAdapter(mAdapter);		
	}
	
	private void setVisiable(int flag){
		copy.setVisibility(flag);
		list_code_generate.setVisibility(flag);
	}
	
	/**
	 * 生成兑换码
	 * 复制到剪贴板
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.code_num:
			setEditable(code_num, true);
			break;
		case R.id.copy:
			
			break;
		case R.id.generate:
			//生成以后
			if(!code_num.getText().toString().equals("")&&
					code_num.getText().toString()!=null&&
					Integer.parseInt(code_num.getText().toString())!=0){
				setVisiable(View.VISIBLE);
				code_num.setEnabled(false);
				minus.setClickable(false);
				plus.setClickable(false);
			}
			break;
		case R.id.minus:
			if(!(code_num.getText().toString()).equals("")&&
					code_num.getText().toString()!=null
					&&Integer.parseInt(code_num.getText().toString())!=0){
				num=Integer.parseInt(code_num.getText().toString());
				code_num.setText(""+(num-1));			
			}
			break;
		case R.id.plus:
			if(!(code_num.getText().toString()).equals("")
					&&code_num.getText().toString()!=null
			&&Integer.parseInt(code_num.getText().toString())!=0){
				num=Integer.parseInt(code_num.getText().toString());
				code_num.setText(""+(num+1));			
			}
			break;
		case R.id.back:
			finish();
			break;
			default:
				break;
		}
	}

	@Override
	protected void hanlderUi() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interactive() {
		// TODO Auto-generated method stub
		
	}
}
