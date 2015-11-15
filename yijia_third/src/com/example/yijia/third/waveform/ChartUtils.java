package com.example.yijia.third.waveform;

import java.util.ArrayList;
import java.util.List;

import com.example.yijia.third.bean.user.MeridianPoints;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.PointValue;

/**
 * @author 丑旦
 * @date 创建时间：2015/10/5 下午10:13:49
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public class ChartUtils {
	
	public static List<AxisValue> getY(int heightOfY) {
		List<AxisValue> listX = new ArrayList<AxisValue>();// x轴
		for (int i = 0; i < heightOfY; i++) {
			listX.add(new AxisValue((float) i));
		}
		return listX;
	}
	
	public static List<AxisValue> getX(String[] label, float xoffset) {
		List<AxisValue> listX = new ArrayList<AxisValue>();// x,带偏移
		for (int i = 0; i < label.length; i++) {
			listX.add(new AxisValue(i + xoffset).setLabel(label[i]));
		}
		return listX;
	}

	public static List<AxisValue> getX(ArrayList<String> label) {
		List<AxisValue> listX = new ArrayList<AxisValue>();// x坐标
		for (int i = 0; i < label.size(); i++) {
			listX.add(new AxisValue(i).setLabel(label.get(i)));
		}
		return listX;
	}
	
	public static List<AxisValue> getX(ArrayList<String> label,float xoffset) {
		List<AxisValue> listX = new ArrayList<AxisValue>();// x,带偏移
		for (int i = 0; i < label.size(); i++) {
			listX.add(new AxisValue(i + xoffset).setLabel(label.get(i)));
		}
		return listX;
	}
	
	public static List<PointValue> getXFasten(int maxValue) {//maxValue为y的最大值
		List<PointValue> listXFasten = new ArrayList<PointValue>();// x、y轴从0开始，不显示
		for (int i = 0; i < maxValue; i++) {
			listXFasten.add(new PointValue(0,i));
		}
		return listXFasten;
	}
	
	public static List<PointValue> getListPointValues(int numberOfPoints) {
		List<PointValue> values = new ArrayList<PointValue>();// 点值
		for (int i = 0; i < numberOfPoints; i++) {
			values.add(new PointValue(i, i * 12));
		}
		return values;
	}
	
	public static List<PointValue> getAvgList(int numberOfPoints, float centerPoint) {
		List<PointValue> values = new ArrayList<PointValue>();// 平均值（此条曲线从0开始，支撑坐标轴）
		for (int i = 0; i < 11; i++) {
			values.add(new PointValue(i, centerPoint));
		}
		return values;
	}
	
	public static List<List<PointValue>> getDoubleList(int numberOfPoints , float centerPoint) {
		List<List<PointValue>> values = new ArrayList<List<PointValue>>();// 矩形点值（此条曲线不显示，支撑坐标轴）
		for (int i = 0; i < 11; i++) {//numberOfLines = 11 ,numberOfPoints =5,centerPoint=1
			List<PointValue> value = new ArrayList<PointValue>();
			for(int j = 0; j < numberOfPoints; j++){
				switch(j){
				case 0:
					value.add(new PointValue((float)(centerPoint * i) ,0));
					break;
				case 1:
					value.add(new PointValue((float)(centerPoint * i) ,4));
					break;
				case 2:
					value.add(new PointValue((float)(centerPoint * i+0.2) ,6));
					break;
				case 3:
					value.add(new PointValue((float)(centerPoint * i+0.4) ,5));	
					break;
				case 4:
					value.add(new PointValue((float)(centerPoint * i+0.4) ,0));	
					break;
					default:
						break;
				}					
			}
			values.add(value);
		}
		return values;
	}
	
	public static List<Line> getPairLine(List<MeridianPoints> list,int colorLeft,int colorRight){	
		List<Line> listLines = new ArrayList<Line>();
		for (MeridianPoints meridianPoints : list) {
			List<PointValue> valueBlue = new ArrayList<PointValue>();
			List<PointValue> valueRed = new ArrayList<PointValue>();
			valueBlue.add(new PointValue((float)(meridianPoints.getMeridianId().floatValue()-1+0.05) ,0));
			valueBlue.add(new PointValue((float)(meridianPoints.getMeridianId().floatValue()-1+0.05) ,meridianPoints.getLeftValue1().floatValue()));
			valueBlue.add(new PointValue((float)(meridianPoints.getMeridianId().floatValue()-1+0.25),meridianPoints.getLeftValue2().floatValue()));
			valueBlue.add(new PointValue((float)(meridianPoints.getMeridianId().floatValue()-1+0.45) ,meridianPoints.getLeftValue3().floatValue()));
			valueBlue.add(new PointValue((float)(meridianPoints.getMeridianId().floatValue()-1+0.45) ,0));
			Line lineBlue = ChartUtils.getNoPointsLine(colorLeft,valueBlue);
			valueRed.add(new PointValue((float)(meridianPoints.getMeridianId().floatValue()-1+0.35) ,0));
			valueRed.add(new PointValue((float)(meridianPoints.getMeridianId().floatValue()-1+0.35) ,meridianPoints.getRightValue1().floatValue()));
			valueRed.add(new PointValue((float)(meridianPoints.getMeridianId().floatValue()-1+0.55),meridianPoints.getRightValue2().floatValue()));
			valueRed.add(new PointValue((float)(meridianPoints.getMeridianId().floatValue()-1+0.75) ,meridianPoints.getRightValue3().floatValue()));
			valueRed.add(new PointValue((float)(meridianPoints.getMeridianId().floatValue()-1+0.75) ,0));			
			Line lineRed = ChartUtils.getNoPointsLine(colorRight,valueRed);
			listLines.add(lineBlue);
			listLines.add(lineRed);			
		}
		return listLines;
	}
	
	public static List<List<PointValue>> getRedDoubleList(int numberOfPoints , float centerPoint) {
		List<List<PointValue>> values = new ArrayList<List<PointValue>>();// 矩形点值，红
		for (int i = 0; i < 10; i++) {//numberOfLines = 10 ,numberOfPoints =5,centerPoint=1
			List<PointValue> value = new ArrayList<PointValue>();
			for(int j = 0; j < numberOfPoints; j++){
				switch(j){
				case 0:
					value.add(new PointValue((float)(centerPoint * i+0.2) ,0));
					break;
				case 1:
					value.add(new PointValue((float)(centerPoint * i+0.2) ,45));
					break;
				case 2:
					value.add(new PointValue((float)(centerPoint * i+0.4) ,60));
					break;
				case 3:
					value.add(new PointValue((float)(centerPoint * i+0.6) ,50));	
					break;
				case 4:
					value.add(new PointValue((float)(centerPoint * i+0.6) ,0));	
					break;
				default:
					break;
				}					
			}
			values.add(value);
		}
		return values;
	}
	
	public static List<List<PointValue>> getBlueDoubleList(int numberOfPoints , float centerPoint) {
		List<List<PointValue>> values = new ArrayList<List<PointValue>>();// 矩形点值，蓝
		for (int i = 0; i < 10; i++) {
			List<PointValue> value = new ArrayList<PointValue>();
			for(int j = 0; j < numberOfPoints; j++){
				switch(j){
				case 0:
					value.add(new PointValue((float)(centerPoint * i+0.5) ,0));
					break;
				case 1:
					value.add(new PointValue((float)(centerPoint * i+0.5) ,50));
					break;
				case 2:
					value.add(new PointValue((float)(centerPoint * i+0.7) ,60));
					break;
				case 3:
					value.add(new PointValue((float)(centerPoint * i+0.9) ,55));	
					break;
				case 4:
					value.add(new PointValue((float)(centerPoint * i+0.9) ,0));	
					break;
					default:
						break;
				}					
			}
			values.add(value);
		}
		return values;
	}
	
	public static Line getLine(int color, List<PointValue> values) {
		return getLine(color, true ,true,values);
	}
	
	public static Line getNoPointsLine(int color, List<PointValue> values) {
		return getLine(color, true, false, values);
	}

	public static Line getLine(int color, boolean hasLabelForSelected,boolean hasPoints,
			List<PointValue> values) {
		Line line = new Line();
		line.setValues(values);
		line.setColor(color);
 		line.setHasLabelsOnlyForSelected(hasLabelForSelected);
		line.setHasPoints(hasPoints);
		line.setStrokeWidth(0);
		return line;
	}
	
	public static Line getLine(int color, boolean hasLabelForSelected,
			List<PointValue> values) {
		Line line = new Line();
		line.setValues(values);
		line.setColor(color);
		line.setHasLabelsOnlyForSelected(hasLabelForSelected);
		line.setStrokeWidth(0);
		return line;
	}

	public static Axis getAxis(int color, List<AxisValue> list) {
		return getAxis(color, list, null);
	}

	public static Axis getAxis(int color, List<AxisValue> list, String name) {
		Axis axis = new Axis();
		axis.setValues(list);
		axis.setTextColor(color);
		axis.setName(name);
		return axis;
	}

	public static Axis getAxis(int color, int textSize, List<AxisValue> list,
			String name) {
		Axis axis = new Axis();
		axis.setValues(list);
		axis.setTextColor(color);
		axis.setTextSize(textSize);
		axis.setName(name);
		return axis;
	}
}
