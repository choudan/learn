/**
 * 
 */
package com.example.yijia.third.settlement.column;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

/**
 * @author Administrator
 * 
 */
public class MsettlementColumn extends View {

	public MsettlementColumn(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	// 坐标轴 轴线 画笔：
	private Paint axisLinePaint;
	// 坐标轴水平内部 虚线画笔
	private Paint hLinePaint;
	// 绘制文本的画笔
	private Paint titlePaint;
	// 矩形画笔 柱状图的样式信息
	private Paint recPaint;
	// 数据源
	private String[] xTittle, data;

	private int width, height;

	private void init() {
		axisLinePaint = new Paint();
		hLinePaint = new Paint();
		titlePaint = new Paint();
		recPaint = new Paint();

		axisLinePaint.setColor(Color.DKGRAY);
		hLinePaint.setColor(Color.LTGRAY);
		titlePaint.setColor(Color.BLACK);
		recPaint.setColor(Color.LTGRAY);
	}

	// 可以子线程 更新视图的方法调用
	public void updateData(String[] data) {
		this.data = data;
		this.postInvalidate();
	}

	// 可以子线程 更新视图的方法调用
	public void updateXtittle(String[] xTittle) {
		this.xTittle = xTittle;
		this.postInvalidate();
	}

//	// 可以子线程 更新视图的方法调用
//	public void updateYtittle(String[] yTittle) {
//		this.yTittle = yTittle;
//		this.postInvalidate();
//	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		this.width = getWidth();
		this.height = getHeight();

		canvas.drawLine(40, 100, 40, height - 50, axisLinePaint);//y
		canvas.drawLine(40, height - 50, width - 50, height - 50, axisLinePaint);//x

		hLinePaint.setTextAlign(Align.CENTER);

		// 4 绘制 X 坐标刻度

		int xAxisLength = width - 10;
		int columCount = xTittle.length+1 ;
//		int leftHeight = height-150;// 左侧外周的 需要划分的高度：
		int step = xAxisLength / columCount;
		for (int i = 0; i < columCount - 1; i++) {
			titlePaint.setTextAlign(Paint.Align.CENTER);
			titlePaint.setTextSize(30);
			titlePaint.setColor(0xFF008B8B);

			canvas.drawText(xTittle[i], 40 + step * i , height - 10,titlePaint);
		}

		// 5 绘制矩形
		if (data != null && data.length > 0) {
			FontMetrics metrics =titlePaint.getFontMetrics();
			int descent = (int)metrics.descent;
			titlePaint.setTextAlign(Align.CENTER);
			titlePaint.setTextSize(30);
			titlePaint.setColor(0xFF008B8B);		
			int thisCount = data.length;
			
			for (int i = 0; i < thisCount; i++) {				
				
				int value = Integer.parseInt(data[i]);	
				Log.e("=-=-= value :", ""+value);
 				recPaint.setColor(0xFFC8E7FF); // 淡蓝色
				Rect rect = new Rect();
				rect.left = step * (i + 1);
				rect.right = step * (i + 1) + 80;		
				float rh = getHeight()-value/10;
				rect.top = (int) (rh);
				rect.bottom = height - 50;
				
				canvas.drawText(data[i], step * (i + 1)+40, rh-80+descent, titlePaint);//柱子顶端坐标
				canvas.drawRect(rect, recPaint);
			}
		}
	}
}
