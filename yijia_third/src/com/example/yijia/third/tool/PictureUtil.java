/**
 * 
 */
package com.example.yijia.third.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

/**
 * @author Administrator
 * 
 */
public class PictureUtil {

	/**
	 * ����ͼƬ�ļ���
	 * 
	 * @return
	 */
	public static File getAlbumDir() {
		File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				getAlbumName());
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	/**
	 * ��ȡ���� ��������ͼƬ�ļ�������
	 * 
	 * @return
	 */
	public static String getAlbumName() {
		return "yijia";
	}
	
	/**
	 * ��ȡ���� ��������ͼƬ�ļ�������
	 * 
	 * @return
	 */
	public static String[] getArray(ArrayList<String>list) {
		int size=list.size();
		String[] array = (String[])list.toArray(new String[size]);
		return array;  
	}

	/**
	 * ����ͼƬ �ѳ����������Ƭ�ŵ� SD���� PicturesĿ¼�� yijia �ļ�����
	 * ��Ƭ����������Ϊ��yijia_20130125_173729.jpg
	 * 
	 * @return
	 * @throws IOException
	 */

	@SuppressLint("SimpleDateFormat")
	public static File createImageFile() throws IOException {
		String imageFileName = "yijia_" + getTimeStamp() + ".jpg";
		File image = new File(PictureUtil.getAlbumDir(), imageFileName);
		return image;
	}

	/**
	 * ��ӵ�ͼ��
	 */
	public static void galleryAddPic(Context context, String path) {
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(path);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		context.sendBroadcast(mediaScanIntent);
	}

	/**
	 * ����ʱ���
	 */
	public static String getTimeStamp() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String timeStamp = format.format(new Date());
		System.currentTimeMillis();
		return timeStamp;
	}

	/**
	 * ����·�����ͼƬ��ѹ������bitmap������ʾ
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static Bitmap getFloatBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

		return bitmap;
	}
	/**
	 * ����·�����ͼƬ��ѹ������bitmap������ʾ
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static Bitmap getBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		
		// Calculate inSampleSize
		// options.inSampleSize = calculateInSampleSize(options, 480, 800);
		options.inSampleSize = 10;// ��calculateInSampleSize������ĳߴ�̫���ڴ������
		
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		
		Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
		
		return bitmap;
	}

	/**
	 * ��ȡ���������ļ�
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static File getFile(String filePath) {
		return new File(filePath);
	}

	/**
	 * ����·�����ͼƬ��ѹ������bitmap������ʾ
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static void recyleBitmap(Bitmap bitmap) {
		if (!bitmap.isRecycled()) {
			bitmap.recycle();
			System.gc();
		}
	}

	/**
	 * ����ͼƬ������ֵ
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}
	
	/**
	 * ѹ��ͼƬ
	 * 
	 * @param image
	 * @param name
	 * @param format
	 * @return
	 * @throws IOException
	 */
	public static String compressImage(Bitmap image,String name,String format,int percent) throws IOException {
		String path = PictureUtil.getAlbumDir()+ name + getTimeStamp()+ format;
		System.out.println("=-=-=compressImage "+path);
		File smallFile = getFile(path);
		System.out.println("=-=-=compressImage 0 "+smallFile.length());
		FileOutputStream fos = new FileOutputStream(smallFile);
		System.out.println("=-=-=compressImage 1 "+smallFile.length());
		image.compress(Bitmap.CompressFormat.JPEG, percent, fos);		
		System.out.println("=-=-=compressImage 2 "+smallFile.length());
		image.recycle();// bitmapд���ļ�
		System.out.println("=-=-=compressImage 3 "+smallFile.length());
		fos.flush();
		System.out.println("=-=-=compressImage 4 "+smallFile.length());
		fos.close();
		System.out.println("=-=-=compressImage 5 "+smallFile.length());
		return path;
	}
	
	/**
	 * ����ѹ������
	 * 
	 * @param image
	 * @param name
	 * @param format
	 * @return
	 * @throws IOException
	 */
	public static float compressRate(String imagePath,int size) throws IOException {
		File file = new File(imagePath);
		long sum = file.length();
		if(size*1000<sum)
			return (float)size*1000/sum;
		else
			return 1;
	}
	
}
