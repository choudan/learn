package com.choosephoto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.ImageView;

public class CompressUtil {
	
	Context context;
	
	public CompressUtil(Context context) {
		this.context=context;
	}
	
	/**
	 * ��ȡȫ��ͼƬ��ַ
	 * @return
	 */
	public ArrayList<String>  listAlldir(){
    	Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    	Uri uri = intent.getData();
    	ArrayList<String> list = new ArrayList<String>();
    	String[] proj ={MediaStore.Images.Media.DATA};
    	Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);//managedQuery(uri, proj, null, null, null);
    	while(cursor.moveToNext()){
    		String path =cursor.getString(0);
    		list.add(new File(path).getAbsolutePath());
    	}
		return list;
    }
	
	public List<FileTraversal> LocalImgFileList(){
		List<FileTraversal> data=new ArrayList<FileTraversal>();
		String filename="";
		List<String> allimglist=listAlldir();
		List<String> retulist=new ArrayList<String>();
		if (allimglist!=null) {
			Set set = new TreeSet();
			String []str;
			for (int i = 0; i < allimglist.size(); i++) {
				retulist.add(getfileinfo(allimglist.get(i)));
			}
			for (int i = 0; i < retulist.size(); i++) {
				set.add(retulist.get(i));
			}
			str= (String[]) set.toArray(new String[0]);
			for (int i = 0; i < str.length; i++) {
				filename=str[i];
				FileTraversal ftl= new FileTraversal();
				ftl.filename=filename;
				data.add(ftl);
			}
			
			for (int i = 0; i < data.size(); i++) {
				for (int j = 0; j < allimglist.size(); j++) {
					if (data.get(i).filename.equals(getfileinfo(allimglist.get(j)))) {
						data.get(i).filecontent.add(allimglist.get(j));
					}
				}
			}
		}
		return data;
	}
	
	 //* ͼƬ������ѹ��
	 
	
	//��ʾԭ��ͼƬ�ߴ��С
	public Bitmap getPathBitmap(String imageFilePath,int dw,int dh) throws FileNotFoundException{
		//��ȡ��Ļ�Ŀ�͸�  
        /** 
         * Ϊ�˼������ŵı�����������Ҫ��ȡ����ͼƬ�ĳߴ磬������ͼƬ 
         * BitmapFactory.Options������һ�������ͱ���inJustDecodeBounds����������Ϊtrue 
         * ���������ǻ�ȡ���ľ���ͼƬ�ĳߴ磬�����ü���ͼƬ�ˡ� 
         * �������������ֵ��ʱ�����ǽ��žͿ��Դ�BitmapFactory.Options��outWidth��outHeight�л�ȡ��ֵ 
         */  
        BitmapFactory.Options op = new BitmapFactory.Options();  
        op.inJustDecodeBounds = true;  
        //����ʹ����MediaStore�洢���������URI��ȡ����������ʽ    
        
        Bitmap pic=BitmapFactory.decodeFile(imageFilePath,op);
        
        int wRatio = (int) Math.round(op.outWidth / (float) dw); //�����ȱ���  
        int hRatio = (int) Math.round(op.outHeight / (float) dh); //����߶ȱ���
      
        /** 
         * �����������Ǿ���Ҫ�ж��Ƿ���Ҫ�����Լ����׶Կ��Ǹ߽������š� 
         * ����ߺͿ���ȫ����������Ļ����ô�������š� 
         * ����ߺͿ���������Ļ��С�������ѡ�������ء� 
         * ����Ҫ�ж�wRatio��hRatio�Ĵ�С 
         * ���һ���������ţ���Ϊ���Ŵ��ʱ��С��Ӧ���Զ�����ͬ�������š� 
         * ����ʹ�õĻ���inSampleSize���� 
         */  
        if (wRatio > 1 && hRatio > 1) {  
            if (wRatio > hRatio) {  
                op.inSampleSize = hRatio;  
            } else {  
                op.inSampleSize = wRatio;  
            }  
        }  
        op.inJustDecodeBounds = false; //ע�����һ��Ҫ����Ϊfalse����Ϊ�������ǽ�������Ϊtrue����ȡͼƬ�ߴ���  

        pic=BitmapFactory.decodeFile(imageFilePath,op);
                       
        return pic;
	}
	
	// * ͼƬ������ѹ��
	 
	private Bitmap compressImage(Bitmap image) {  		  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        int options = 100;  
        while ( baos.toByteArray().length / 1024>100) {  //ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��         
            baos.reset();//����baos�����baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ��������ݴ�ŵ�baos��  
            options -= 10;//ÿ�ζ�����10  
        }  
        
        boolean flag=image.compress(Bitmap.CompressFormat.JPEG, 85, baos);//����ѹ��options%����ѹ��������ݴ�ŵ�baos��  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//��ѹ���������baos��ŵ�ByteArrayInputStream��  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//��ByteArrayInputStream��������ͼƬ  
        return bitmap;  
    } 
	
	
	private static Bitmap rotateBitmap(Bitmap bitmap, int rotate){  
        if(bitmap == null)  
            return null ;  
          
        int w = bitmap.getWidth();  
        int h = bitmap.getHeight();  
  
        // Setting post rotate to 90  
        Matrix mtx = new Matrix();  
        mtx.postRotate(rotate);  
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);  
    } 
	
	
	// * ������ת��
	 
	private static int readPictureDegree(String path) {    
        int degree  = 0;    
        try {    
                ExifInterface exifInterface = new ExifInterface(path);    
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);    
                switch (orientation) {    
                case ExifInterface.ORIENTATION_ROTATE_90:    
                        degree = 90;    
                        break;    
                case ExifInterface.ORIENTATION_ROTATE_180:    
                        degree = 180;    
                        break;    
                case ExifInterface.ORIENTATION_ROTATE_270:    
                        degree = 270;    
                        break;    
                }    
        } catch (IOException e) {    
                e.printStackTrace();    
        }    
        return degree;    
    }   
	
	
	public String getfileinfo(String data){
		String filename[]= data.split("/");
		if (filename!=null) {
			return filename[filename.length-2];
		}
		return null;
	}
	
	public void imgExcute(ImageView imageView,ImgCallBack icb, String... params){
		LoadBitAsynk loadBitAsynk=new LoadBitAsynk(imageView,icb);
		loadBitAsynk.execute(params);
	}
	
	public class LoadBitAsynk extends AsyncTask<String, Integer, Bitmap>{

		ImageView imageView;
		ImgCallBack icb;
		
		LoadBitAsynk(ImageView imageView,ImgCallBack icb){
			this.imageView=imageView;
			this.icb=icb;
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap=null;
			try {
				if (params!=null) {
					for (int i = 0; i < params.length; i++) {
						bitmap=getPathBitmap(params[i], 480,800);
					}
				} 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if (result!=null) {
				imageView.setImageBitmap(result);
				icb.resultImgCall(imageView, result);
			}
		}				
	}	
}
