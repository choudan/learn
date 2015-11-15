package com.example.yijia.third.uploadphoto;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.yijia.third.tool.PictureUtil;

public class LoadBitAsynk extends AsyncTask<String, Integer, Bitmap> {
	public ImageView imageView;

	public LoadBitAsynk(ImageView imageView) {
		super();
		this.imageView = imageView;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		Bitmap bitmap = null;
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				bitmap = PictureUtil.getBitmap(params[i]);
			}
		}
		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (result != null) {
			imageView.setImageBitmap(result);
		}
	}

	public void imgExcute(String... params) {
		LoadBitAsynk loadBitAsynk = new LoadBitAsynk(imageView);
		loadBitAsynk.execute(params);
	}

	@Override
	protected void onCancelled(Bitmap result) {
		// TODO Auto-generated method stub
		super.onCancelled(result);
		PictureUtil.recyleBitmap(result);
	}
}
