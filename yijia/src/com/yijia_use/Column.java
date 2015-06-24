package com.yijia_use;
/*
 * Y�᷽�����£�X�����ң�������View�����Ͻ�Ϊ����ԭ�㣬ȷ�����ָ߶ȣ��滮����ֵ��Ȼ�����ߡ�
 * ʮ������8λ��ɫ�����ʮ������6λ��ɫ���루RGB��������ֻ��ǰ��λ��8λ��ǰ��λ����͸���ȣ���6λһ����
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

//	������ ���� ���ʣ�
	private Paint axisLinePaint;
//	������ˮƽ�ڲ� ���߻���
	private Paint hLinePaint;
//	�����ı��Ļ���
	private Paint titlePaint;
//  ���ƾ��ζ����ĺ���
	private Paint redPaint;
//	���λ��� ��״ͼ����ʽ��Ϣ
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
	
	//7 ��
	private ArrayList<Float> criterion;
	
	//7 ��
	private ArrayList<Float> test;
	

	/**
	 * ������������� ��ҪView�����ػ档
	 * 
	 * ���߳� ˢ�¿ؼ���ʱ����ã�
	 * this.postInvalidate();  �������߳� ������ͼ�ķ������á�
	 * 
	 * */
	//updata this year data
	public void updateThisData(ArrayList<Float> s)
	{
		test = s;
		this.postInvalidate();  //�������߳� ������ͼ�ķ������á�
	}
	
	//updata last year data
	public void updateLastData(ArrayList<Float> criterion2)
	{
		criterion = criterion2;
		this.postInvalidate();  //�������߳� ������ͼ�ķ������á�
	}
	
	
	private String[] yTitlesStrings = 
			new String[]{};
	
	private String[] xTitles = 
			new String[]{"����", "�ξ�", "�ξ�", "�󳦾�", "θ��", "Ƣ��", "�ľ�", "С����",
			"���׾�", "����", "�İ���", "������", "�������֣�", "�������㣩", "�������֣�", "�������㣩",
			"�������֣�", "�����������㣩", "�����֣�", "�����㣩"};
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
				
		int width = getWidth();
		int height = getHeight();		
		
		// 1 ���������᣺
		canvas.drawLine(30, 20, 30, height-50, axisLinePaint);//����Y��
		
		canvas.drawLine(30, height-50, width-50 , height-50, axisLinePaint);//����X��
		
		
		
		// 2 ���������ڲ���ˮƽ��
		
		int leftHeight = height-100;// ������ܵ� ��Ҫ���ֵĸ߶ȣ�
		
		int hPerHeight = leftHeight/5;
									
		hLinePaint.setTextAlign(Align.CENTER);
		for(int i=1;i<6;i++)
		{
			canvas.drawLine(30, height-50-i*hPerHeight, width-50, height-50-i*hPerHeight, hLinePaint);
		}
		
		
		// 3 ���� Y ����̶�
		
		FontMetrics metrics =titlePaint.getFontMetrics();
		int descent = (int)metrics.descent;
		titlePaint.setTextAlign(Align.RIGHT);
		for(int i=0;i<yTitlesStrings.length;i++)
		{
			canvas.drawText(yTitlesStrings[i], 20, 50+i*hPerHeight+descent, titlePaint);
		}
		
		// 4  ���� X ����̶�
		
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
		
		// 5 ���Ʋ��Ծ���
		
		if(test != null && test.size() >0)
		{
			int thisCount = test.size();
			
			
			for(int i=0;i<thisCount;i++)
			{
				double value = test.get(i);
				
//				float num = 10 - value / 10;
				recPaint.setColor(0xFFC8E7FF);  //����ɫ
								
				Rect rect = new Rect();
				
				rect.left  = 0+ step * (i+1)  - 30;
 				rect.right = 0+ step * (i+1)  + 30;
 				//��ǰ����Ը߶ȣ�
//				float rh = (leftHeight * num) / 5;//����
// 				rect.top = (int) (rh + 20);				
 				int rh = (int)(leftHeight-(value/255)*leftHeight);		
				
				rect.top = (int) (rh);				
				rect.bottom = getHeight()-50 ;
				
				canvas.drawRect(rect, recPaint);
			}
		}
		
		/*
		 * ���Ʊ�׼���������
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
		 * ���Ʊ�׼�����ұ���
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
	 
		//������	
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