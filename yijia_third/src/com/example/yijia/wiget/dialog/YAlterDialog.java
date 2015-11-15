package com.example.yijia.wiget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.yijia_third.R;

/**
 * @author  丑旦 
 * @date 创建时间：2015-10-1 下午10:38:55
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 *
 */
public class YAlterDialog extends Dialog implements android.view.View.OnClickListener{
	private TextView tittle,content;
	private OnDialogListener mOnDialogListener;
	private boolean mDismiss = true;
	private boolean mCancelable = true;
	private boolean mCanceledOnTouchOutside = true;
	private Button confirm,cancel,prompt;
	
	//dialog的context必须是Activity，只有Activity才能添加窗体。否则会报WindowManager$BadTokenException
	//type表示按钮的个数
	public YAlterDialog(Context context,int type) {	
		super(context, R.style.MyDialogStyle);
		setContentView(R.layout.alert_dialog);
		setViewType(type);		
		setmOnDialogListener(mOnDialogListener);
		init();
	}
	
	public YAlterDialog(Context context,int type,CharSequence tittle,String leftstr,String rightstr) {	
		this(context, type);
		setTittle(tittle);		
		setLeftBtn(leftstr);
		setRightBtn(rightstr);
	}
	
	public YAlterDialog(Context context,int type,CharSequence tittle,String leftstr,String rightstr,CharSequence content) {	
		this(context, type, tittle, leftstr, rightstr);
		setContent(content);
	}

	public void setmOnDialogListener(OnDialogListener mOnDialogListener) {
		this.mOnDialogListener = mOnDialogListener;
	}

	private void setViewType(int type){
		if(type==1){
			prompt.setVisibility(View.VISIBLE);
		}else if(type==2){
			prompt.setVisibility(View.INVISIBLE);			
		}
	}
	
	private void init() {
		// TODO Auto-generated method stub
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.width = WindowManager.LayoutParams.MATCH_PARENT;
		params.height = WindowManager.LayoutParams.MATCH_PARENT;
		getWindow().setAttributes(params);
		
		confirm = (Button)findViewById(R.id.confirm);
		confirm.setOnClickListener(this);
		
		cancel = (Button)findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		
		prompt = (Button)findViewById(R.id.prompt);
		prompt.setOnClickListener(this);
		
		tittle = (TextView)findViewById(R.id.tittle);
		content = (TextView)findViewById(R.id.content);	
	}

	public void setTittle(CharSequence text) {
		if ((text == null) || (TextUtils.isEmpty(text.toString()))) {
			tittle.setText(text);
			return;
		}
	}
	
	public void setLeftBtn(String str){
		if ((str == null) || (TextUtils.isEmpty(str))) {
			confirm.setText(str);
			return;
		}		
	}
	
	public void setRightBtn(String str){
		if ((str == null) || (TextUtils.isEmpty(str))) {
			cancel.setText(str);
			return;
		}		
	}
	
	public void setMiddleBtn(String str){
		if ((str == null) || (TextUtils.isEmpty(str))) {
			prompt.setText(str);
			return;
		}		
	}
	
	public void setTittle(int text){
		setTittle(this.getContext().getString(text));
	}
	
	public void setContent(CharSequence text) {
		if ((text == null) || (TextUtils.isEmpty(text.toString()))) {
			content.setText(text);
			return;
		}
	}
	
	public void setContent(int text) {
		setContent(this.getContext().getString(text));
	}

	public void setCancelable(boolean flag) {
		super.setCancelable(flag);
		mCancelable = flag;
	}
	
	public void setCanceledOnTouchOutside(boolean cancel) {
		super.setCanceledOnTouchOutside(cancel);
		mCanceledOnTouchOutside = cancel;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mCancelable && mCanceledOnTouchOutside&& event.getAction() == MotionEvent.ACTION_DOWN) {
			if (mDismiss) 
				dismiss();
			return true;
		}
		return super.onTouchEvent(event);
	}
	
	/**
     * 点击按钮不销毁对话框
     */
    public void setDismissFalse() {
        mDismiss = false;
    }
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(mOnDialogListener!=null){		
			switch(v.getId()){
			case R.id.confirm:
				mOnDialogListener.confirm();
				break;
			case R.id.cancel:
				mOnDialogListener.cancel();
				break;
			case R.id.prompt:
				mOnDialogListener.prompt();
				break;
			default:
				break;
			}
		}
		if (mDismiss) {
			dismiss();
			return;
		}
	}
	
	public interface OnDialogListener{
		public void confirm();
		public void cancel();
		public void prompt();		
	}
}
