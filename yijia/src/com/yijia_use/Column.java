package com.yijia_use;
/*
 * Y轴方向向下，X轴向右，以整个View的左上角为坐标原点，确定划分高度，规划坐标值，然后谱线。
 * 十六进制8位颜色代码和十六进制6位颜色代码（RGB）的区别只在前两位。8位的前两位代表透明度，后6位一样。
 */
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.yijia.R.color;

public class Column extends View {

	public Column(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		init(context, null);
	}

	public Column(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

//	坐标轴 轴线 画笔：
	private Paint axisLinePaint;
//	坐标轴水平内部 虚线画笔
	private Paint hLinePaint;
//	绘制文本的画笔
	private Paint titlePaint;
//  绘制矩形顶部的红线
	private Paint redPaint;
//	矩形画笔 柱状图的样式信息
	private Paint recPaint;
	private void init(Context context, AttributeSet attrs)
	{	
		axisLinePaint = new Paint();
		hLinePaint = new Paint();
		titlePaint = new Paint();
		recPaint = new Paint();
		
		axisLinePaint.setColor(Color.DKGRAY);
		hLinePaint.setColor(Color.LTGRAY);
		titlePaint.setColor(Color.BLACK);	
	}
	
	//7 条
	private ArrayList<Float> criterion;
	
	//7 条
	private ArrayList<Float> test;
	

	/**
	 * 跟新自身的数据 需要View子类重绘。
	 * 
	 * 主线程 刷新控件的时候调用：
	 * this.postInvalidate();  可以子线程 更新视图的方法调用。
	 * 
	 * */
	//updata this year data
	public void updateThisData(ArrayList<Float> s)
	{
		test = s;
		this.postInvalidate();  //可以子线程 更新视图的方法调用。
	}
	
	//updata last year data
	public void updateLastData(ArrayList<Float> criterion2)
	{
		criterion = criterion2;
		this.postInvalidate();  //可以子线程 更新视图的方法调用。
	}
	
	
	private String[] yTitlesStrings = 
			new String[]{};
	
	private String[] xTitles = 
			new String[]{"胆经", "肝经", "肺经", "大肠经", "胃经", "脾经", "心经", "小肠经",
			"膀胱经", "肾经", "心包经", "三焦经", "任脉（手）", "任脉（足）", "督脉（手）", "督脉（足）",
			"冲脉（手）", "冲脉带脉（足）", "膈腧（手）", "膈腧（足）"};
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
				
		int width = getWidth();
		int height = getHeight();		
		
		// 1 绘制坐标轴：
		canvas.drawLine(30, 20, 30, height-50, axisLinePaint);//绘制Y轴
		
		canvas.drawLine(30, height-50, width-50 , height-50, axisLinePaint);//绘制X轴
		
		
		
		// 2 绘制坐标内部的水平线
		
		int leftHeight = height-100;// 左侧外周的 需要划分的高度：
		
		int hPerHeight = leftHeight/5;
									
		hLinePaint.setTextAlign(Align.CENTER);
		for(int i=1;i<6;i++)
		{
			canvas.drawLine(30, height-50-i*hPerHeight, width-50, height-50-i*hPerHeight, hLinePaint);
		}
		
		
		// 3 绘制 Y 坐标刻度
		
		FontMetrics metrics =titlePaint.getFontMetrics();
		int descent = (int)metrics.descent;
		titlePaint.setTextAlign(Align.RIGHT);
		for(int i=0;i<yTitlesStrings.length;i++)
		{
			canvas.drawText(yTitlesStrings[i], 20, 50+i*hPerHeight+descent, titlePaint);
		}
		
		// 4  绘制 X 坐标刻度
		
		int xAxisLength = width-10;		
		int columCount = xTitles.length+1;
		int step = xAxisLength/columCount;
		for(int i=0;i<columCount-1;i++)
		{
			titlePaint.setTextAlign(Paint.Align.CENTER);
			titlePaint.setTextSize(20); 
			titlePaint.setColor(0xFF008B8B);  
			
			canvas.drawText(xTitles[i], 0+step*(i+1), 40 , titlePaint);
		}
		
		// 5 绘制测试矩形
		
		if(test != null && test.size() >0)
		{
			int thisCount = test.size();
			
			
			for(int i=0;i<thisCount;i++)
			{
				double value = test.get(i);
				
//				float num = 10 - value / 10;
				recPaint.setColor(0xFFC8E7FF);  //淡蓝色
								
				Rect rect = new Rect();
				
				rect.left  = 0+ step * (i+1)  - 30;
 				rect.right = 0+ step * (i+1)  + 30;
 				//当前的相对高度：
//				float rh = (leftHeight * num) / 5;//测试
// 				rect.top = (int) (rh + 20);				
 				int rh = (int)(leftHeight-(value/255)*leftHeight);		
				
				rect.top = (int) (rh);				
				rect.bottom = getHeight()-50 ;
				
				canvas.drawRect(rect, recPaint);
			}
		}
		
		/*
		 * 绘制标准矩形左边线
		 */
		if(criterion != null && criterion.size() >0)
		{
			int thisCount = criterion.size();
						
			for(int i=0;i<thisCount;i++)
			{
				double value = criterion.get(i);
				
				int num = (int) (10 - value / 10) ;
				
				recPaint.setColor(color.bule_overlay); 
				
 				int rh = (int)(leftHeight-(value/255)*leftHeight);		
				canvas.drawLine(step * (i+1)  - 30, getHeight()-50, step * (i+1)  - 30, rh , recPaint);
			}  
		}
		
		/*
		 * 绘制标准矩形右边线
		 */
		if(criterion != null && criterion.size() >0)
		{
			int thisCount = criterion.size();
						
			for(int i=0;i<thisCount;i++)
			{
				double value = criterion.get(i);
				
				int num = (int) (10 - value / 10) ;
				
				recPaint.setColor(color.bule_overlay); 
				  			    
 				int rh = (int)(leftHeight-(value/255)*leftHeight);		
				canvas.drawLine(step * (i+1)  + 30,rh,step * (i+1)  + 30,getHeight()-50,recPaint);
			}
		}
	 
		//画红线	
		if(criterion != null && criterion.size() >0)
		{
			int thisCount = criterion.size();
						
			for(int i=0;i<thisCount;i++)
			{
				double value = criterion.get(i);
				
				int num = (int) (10 - value / 10) ;
				
				recPaint.setColor(0xffff0000); 
				
 				int rh = (int)(leftHeight-(value/255)*leftHeight);		
				canvas.drawLine(step * (i+1)  - 30, rh, step * (i+1)  + 30, rh, recPaint);
			}
		}
	}	
}