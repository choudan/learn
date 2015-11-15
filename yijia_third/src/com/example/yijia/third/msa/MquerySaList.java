package com.example.yijia.third.msa;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.sa.Sa;
import com.example.yijia.third.sa.Asa;
import com.example.yijia.third.sa.MdeleteSa;
import com.example.yijia.third.tool.Constant;
import com.example.yijia.wiget.swipelist.OnDeleteListioner;
import com.example.yijia.wiget.swipelist.SwipeListView;
import com.example.yijia_third.R;

//onResume()方法，刷新

public class MquerySaList extends BaseActivity implements OnItemClickListener,
		OnDeleteListioner {
	private ArrayList<Sa> list;
	private MquerySaListAdapter adapter;
	private SwipeListView swipeListView;
	private int index;

	// private int width;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.m_assign_sa_list_swipe);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		// super.init();
		setTittleText(this.getString(R.string.doc));

		// DisplayMetrics dm = new DisplayMetrics();
		// this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		// width=dm.widthPixels;
		// Log.e(TAG, "width:  "+width);

		list = getData();

		swipeListView = (SwipeListView) findViewById(R.id.id_swipelistview);
		adapter = new MquerySaListAdapter(this, list);

		swipeListView.setDeleteListioner(this);
		adapter.setOnDeleteListioner(this);
		swipeListView.setAdapter(adapter);
		swipeListView.setOnItemClickListener(this);
		setBtnVisiable(false);
	}

	@Override
	protected void hanlderUi() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void interactive() {
		// TODO Auto-generated method stub
	}

	protected ArrayList<Sa> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<Sa>();
		for (int i = 0; i < 20; i++) {
			Sa user = new Sa();
			user.setRealName("张仲景第" + i);
			user.setId(10 * i);
			user.setPresentNum(2);
			list.add(user);
		}
		return list;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.e(TAG, "index:  " + index);
		if (requestCode == Constant.DELETE_RESQ) {
			if (resultCode == Constant.DELETE_RESP_CONFIRM) {
				list.remove(index);
				swipeListView.deleteItem();
				adapter.notifyDataSetChanged();
				Log.e(TAG, "list.size()..." + list.size());
			} else if (resultCode == Constant.DELETE_RESP_CANSEL) {
				Log.e(TAG, "取消删除...  ");
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterview, View view, int i, long l) {
		// TODO Auto-generated method stub
		Log.e(TAG, "点击的是第  " + i + "  个医生");
		Intent intent = new Intent(MquerySaList.this, Asa.class);
		Bundle mBundle = new Bundle();
		mBundle.putString(Constant.SA_NAME, list.get(i).getRealName());
		mBundle.putLong(Constant.SA_ID, list.get(i).getId());
		intent.putExtras(mBundle);
		startActivity(intent);
	}

	@Override
	public void onDelete(int ID) {
		// TODO Auto-generated method stub
		index = ID;
		Intent intent = new Intent(MquerySaList.this, MdeleteSa.class);
		intent.putExtra(Constant.SA_NAME, list.get(ID).getRealName());
		startActivityForResult(intent, Constant.DELETE_RESQ);
	}

	@Override
	public boolean isCandelete(int position) {
		// TODO Auto-generated method stub
		return true;
	}

}
