package com.example.yijia.third.waveform;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.example.yijia.third.asyn.user.AsynMedirianSingle;
import com.example.yijia.third.bean.user.MedirianSingle;
import com.example.yijia.third.bean.user.MedirianSingleData;
import com.example.yijia.third.bean.user.SingleData;
import com.example.yijia.third.tool.DataPaser;
import com.example.yijia.third.tool.LogUtils;
import com.example.yijia_third.R;

/**
 * @author 丑旦
 * @date 创建时间：2015-10-3 上午11:10:42
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
/**
 * @author Administrator
 * 
 */
public class SinglePointFrag extends ChartBasicFragment {
	public LineChartView chart;
	public LineChartData data;
	boolean hasLabelForSelected = true;// 显示数值
	public RadioButton shou, jiao, one_day, fifth_days;
	public ImageButton left, right;
	public boolean isHandOrFoot = true;
	public ArrayList<String> labelList;// X坐标
//	public String[] labelArray;// X坐标
	public String nextStartDate;// 下次查询开始时间
	public int beansCount;// 经络豆个数
	public int days = 1;// 请求的天数
	public ArrayList<RadioButton> listRadioButton;

	public String[] label = { "2014-04-01", "2014-04-02", "2014-04-03",
			"2014-04-04", "2014-04-05", "2014-04-06", "2014-04-07",
			"2014-04-08", "2014-04-09", "2014-04-10" };

	public String[] labelNew = { "2010-04-02", "2011-04-02", "2012-04-02",
			"2013-04-02", "2014-04-02", "2015-04-02", "2016-04-02",
			"2017-04-08", "2018-04-09", "2019-04-10" };

	public String[] labelFifth = { "2015-04-02", "2016-04-02", "2017-04-08",
			"2018-04-09", "2019-04-10", "2020-04-08", "2021-04-09",
			"2022-04-10", "2023-04-09", "2024-04-10" };
	public MedirianSingleData medirianSingleData = new MedirianSingleData();
	public String saveResult;
	public boolean hasPoints = false;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.shou:
			isHandOrFoot = true;
			setText(isHandOrFoot);
			break;
		case R.id.jiao:
			isHandOrFoot = false;
			setText(isHandOrFoot);
			break;
		case R.id.one_day:
			days = 1;
			break;
		case R.id.fifth_days:
			days = 15;
			break;
		case R.id.fei:
			break;
		case R.id.renmai:
			break;
		case R.id.dachang:
			break;
		case R.id.chongmai:
			break;
		case R.id.xinbao:
			break;
		case R.id.geshu:
			break;
		case R.id.dumai:
			break;
		case R.id.sanjiao:
			break;
		case R.id.xin:
			break;
		case R.id.xiaochang:
			break;
		case R.id.left:
			break;
		case R.id.right:
			break;
		default:
			break;
		}
		requestData("2015-01-01",null);
	}

	public void init(Bundle savedInstanceState) {
		LogUtils.getInstance().println("init", "该方法被执行");

		chart = (LineChartView) rootView.findViewById(R.id.chart);
		chart.setOnValueTouchListener(new LineChartOnValueSelectListener() {			
			@Override
			public void onValueDeselected() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValueSelected(int arg0, int arg1, PointValue arg2) {
				// TODO Auto-generated method stub
				
			}
		});
		listRadioButton = new ArrayList<RadioButton>();

		left = (ImageButton) rootView.findViewById(R.id.left);
		left.setOnClickListener(this);
		right = (ImageButton) rootView.findViewById(R.id.right);
		right.setOnClickListener(this);

		shou = (RadioButton) rootView.findViewById(R.id.shou);
		shou.setOnClickListener(this);
		jiao = (RadioButton) rootView.findViewById(R.id.jiao);
		jiao.setOnClickListener(this);
		one_day = (RadioButton) rootView.findViewById(R.id.one_day);
		one_day.setOnClickListener(this);
		fifth_days = (RadioButton) rootView.findViewById(R.id.fifth_days);
		fifth_days.setOnClickListener(this);

		fei = (RadioButton) rootView.findViewById(R.id.fei);
		fei.setOnClickListener(this);
		renmai = (RadioButton) rootView.findViewById(R.id.renmai);
		renmai.setOnClickListener(this);
		dachang = (RadioButton) rootView.findViewById(R.id.dachang);
		dachang.setOnClickListener(this);
		chongmai = (RadioButton) rootView.findViewById(R.id.chongmai);
		chongmai.setOnClickListener(this);
		xinbao = (RadioButton) rootView.findViewById(R.id.xinbao);
		xinbao.setOnClickListener(this);
		geshu = (RadioButton) rootView.findViewById(R.id.geshu);
		geshu.setOnClickListener(this);
		dumai = (RadioButton) rootView.findViewById(R.id.dumai);
		dumai.setOnClickListener(this);
		sanjiao = (RadioButton) rootView.findViewById(R.id.sanjiao);
		sanjiao.setOnClickListener(this);
		xin = (RadioButton) rootView.findViewById(R.id.xin);
		xin.setOnClickListener(this);
		xiaochang = (RadioButton) rootView.findViewById(R.id.xiaochang);
		xiaochang.setOnClickListener(this);
		listRadioButton.add(fei);
		listRadioButton.add(renmai);
		listRadioButton.add(dachang);
		listRadioButton.add(chongmai);
		listRadioButton.add(xinbao);
		listRadioButton.add(geshu);
		listRadioButton.add(dumai);
		listRadioButton.add(sanjiao);
		listRadioButton.add(xin);
		listRadioButton.add(xiaochang);
		
		requestData("2015-01-01",null);
	}

	public List<Line> initData() {
		List<Line> lines = new ArrayList<Line>();
		List<PointValue> valuesAvg = new ArrayList<PointValue>();
		List<PointValue> valuesBean = new ArrayList<PointValue>();
//		List<List<PointValue>> list = ChartUtils.getDoubleList(10,1f);
		
		nextStartDate = medirianSingleData.getNextStartDate();
		LogUtils.getInstance().println("nextStartDate", nextStartDate);
		LogUtils.getInstance().println("getBeansCount",
				"" + medirianSingleData.getBeansCount());
		beansCount = medirianSingleData.getBeansCount().intValue();
		LogUtils.getInstance().println("beansCount", "" + beansCount);
		// 均线,lines中的第一个元素
		List<SingleData> beans = medirianSingleData.getBeans();

		labelList = new ArrayList<String>();
		
		for (int i = 0; i < beansCount; i++) {
			valuesAvg.add(new PointValue(i, beans.get(i).getAvgValue().floatValue()));
			valuesBean.add(new PointValue(i, beans.get(i).getDayValue().floatValue()));
			labelList.add(beans.get(i).getDate());
		}
		
//		for (int i = 0; i < list.size(); i++) {
//			Line line = ChartUtils.getNoPointsLine(this.getResources()
//					.getColor(android.R.color.holo_blue_light), list.get(i));
//			lines.add(line);
//		}
		
		List<PointValue> listFasten = ChartUtils.getAvgList(10,3);
		Line lineFasten = ChartUtils.getNoPointsLine(
				this.getResources().getColor(android.R.color.holo_blue_dark),
				listFasten);
		
		Line lineAvg = ChartUtils.getLine(
				this.getResources().getColor(android.R.color.holo_blue_light),
				valuesAvg);
		Line lineBean = ChartUtils.getLine(
				this.getResources().getColor(android.R.color.holo_blue_light),
				valuesBean);		
	
		lines.add(lineAvg);
		lines.add(lineBean);
		lines.add(lineFasten);

		return lines;
		
//		List<Line> lines = new ArrayList<Line>();
//		List<PointValue> valuesAvg = new ArrayList<PointValue>();
//		List<PointValue> valuesBean = new ArrayList<PointValue>();
//
//		nextStartDate = medirianSingleData.getNextStartDate();
//		LogUtils.getInstance().println("nextStartDate", nextStartDate);
//		LogUtils.getInstance().println("getBeansCount",
//				"" + medirianSingleData.getBeansCount());
//		beansCount = medirianSingleData.getBeansCount().intValue();
//		LogUtils.getInstance().println("beansCount", "" + beansCount);
//		// 均线,lines中的第一个元素
//		List<SingleData> beans = medirianSingleData.getBeans();
//
//		labelList = new ArrayList<String>();
//		for (int i = 0; i < beansCount; i++) {
//			valuesAvg.add(new PointValue(i, beans.get(i).getAvgValue().floatValue()));
//			valuesBean.add(new PointValue(i, beans.get(i).getDayValue().floatValue()));
//			labelList.add(beans.get(i).getDate());
//		}
//		
//		Line lineAvg = ChartUtils.getLine(
//				this.getResources().getColor(android.R.color.holo_blue_light),
//				valuesAvg);
//		Line lineBean = ChartUtils.getLine(
//				this.getResources().getColor(android.R.color.holo_blue_light),
//				valuesBean);
//		
//		Line lineFasten = ChartUtils.getNoPointsLine(
//				this.getResources().getColor(android.R.color.holo_blue_light),
//				ChartUtils.getXFasten(beansCount));
//		lineFasten.setHasLines(false);
//		
//		lines.add(lineAvg);
//		lines.add(lineBean);
//		lines.add(lineFasten);
//
//		return lines;
	}

	
	
	//设置顶部和底部的值
//	public Viewport getViewport(){
//		final Viewport v = new Viewport(chart.getMaximumViewport());
//        v.bottom = 0;
//        v.top = 105;
//        v.left = 0;
//        v.right = 5;
//        return v;
//	}
	
	public void initView(ArrayList<String> label, List<Line> lines) {		
		data = new LineChartData(lines);

		List<AxisValue> listX = ChartUtils.getX(label,0.4f);
		Axis axisX = ChartUtils.getAxis(
				this.getResources().getColor(android.R.color.background_dark),
				listX);
		Typeface typeface = Typeface.MONOSPACE;
		axisX.setTypeface(typeface);

		List<AxisValue> listY = ChartUtils.getY(13);
		Axis axisY = ChartUtils.getAxis(
				this.getResources().getColor(android.R.color.background_dark),
				listY, this.getResources().getString(R.string.jl_value));	
		
		data.setAxisXBottom(axisX);
		data.setAxisYLeft(axisY);
		data.setBaseValue(Float.NEGATIVE_INFINITY);

		chart.setLineChartData(data);
//		chart.setMaximumViewport(getViewport());
//		chart.setCurrentViewport(getViewport());		
		chart.startDataAnimation(500);
		chart.setValueSelectionEnabled(hasLabelForSelected);
		chart.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
		chart.setInteractive(true);
	}

	@Override
	protected int getLayout() {
		// TODO Auto-generated method stub
		return R.layout.waveform_single_line_chart;
	}

	@Override
	protected MedirianSingleData requestData(String startTime,String[] label) {
		// TODO Auto-generated method stub
		MedirianSingle bean = getBean(startTime);
		AsynMedirianSingle asynMedirianSingle = new AsynMedirianSingle(
				getActivity(), bean) {
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result != null) {
					saveResult = result;
					parseResultInit(result);
				}
			}
		};
		asynMedirianSingle.execute();
		return medirianSingleData;
	}

	public void parseResultInit(String result) {
		DataPaser<MedirianSingleData> parse = new DataPaser<MedirianSingleData>();
		medirianSingleData = parse.parse(result, MedirianSingleData.class);
		LogUtils.getInstance().println("medirianSingleData",medirianSingleData.toString());
		List<Line> lines = initData();
		initView(labelList, lines);
	}
	
	@Override
	protected MedirianSingle getBean(String startTime) {
		// TODO Auto-generated method stub
		MedirianSingle bean = new MedirianSingle();
		bean.setUserId(2);
		bean.setStartDate(startTime);
		bean.setCount(10);
		bean.setDays(days);
		bean.setMeridianId(getMeridianId(listRadioButton, isHandOrFoot));
		return bean;
	}

	/**
	 * @author Administrator 获取经络id
	 */
	public Number getMeridianId(ArrayList<RadioButton> listRadioButton,
			boolean isHandOrFoot) {
		for (int i = 0; i < listRadioButton.size(); i++) {
			if (listRadioButton.get(i).isChecked() && isHandOrFoot)
				return i + 1;
			else if (listRadioButton.get(i).isChecked() && !isHandOrFoot)
				return i + 11;
		}
		return null;
	}	
}


   