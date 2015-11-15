package com.example.yijia.third.myinterface;

import android.view.View;

public interface OnDeleteListioner {
	public void show(View v,int position);
	public void confirm();
	public void cancel();
	public void setTittle(String str);
	public void setLeftBtnContent(String str);
	public void setRightBtnContent(String str);
}
