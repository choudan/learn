package com.example.yijia.third.waveform;

import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.yijia.third.asyn.user.AsynMedirianAll;
import com.example.yijia.third.bean.user.MedirianAll;
import com.example.yijia.third.bean.user.MedirianAllData;
import com.example.yijia.third.bean.user.MeridianPoints;
import com.example.yijia.third.tool.DataPaser;
import com.example.yijia.third.tool.LogUtils;
import com.example.yijia_third.R;

/**
 * @author 丑旦
 * @date 创建时间：2015/10/5 下午11:11:03
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public class AllPointFrag extends ChartBasicFragment implements
		OnMenuItemClickListener {
	public LineChartView chart;
	public LineChartData data;
	public LinearLayout text_dates;
	boolean hasLabelForSelected = true;// 显示数值
	public RadioButton shou, jiao;
	public Button pop_menu;
	public PopupMenu menu;
	public boolean isHandOrFoot = false;
	public ImageButton left, right;
	public MedirianAllData medirianAllData = new MedirianAllData();
	public int numberOfAllPoints = 10;
	public String saveResult;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fei:
			addView();
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
		case R.id.pop_menu:
			System.out.println("=-=-=pop_menu=-=-=");
			menu.show();
			break;
		case R.id.left:
			requestData("2015-01-01",
					this.getResources().getStringArray(R.array.labelTop));
			break;
		case R.id.right:
			requestData("2015-01-01",
					this.getResources().getStringArray(R.array.labelTop));
			break;
		default:
			break;
		}
	}

	/**
	 * @author 丑旦
	 * @return 增加日期视图
	 * */
	public void addView() {
		for (int i = 0; i < 10; i++) {
			TextView text_date = new TextView(getActivity());
			text_date.setLayoutParams(new LayoutParams(240, 100));
			text_date.setGravity(Gravity.CENTER);
			text_date.setText("" + i);
			if (i % 2 == 0)
				text_date.setBackgroundColor(this.getResources().getColor(
						android.R.color.holo_red_light));
			text_dates.addView(text_date);
		}
	}

	@Override
	protected void init(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		chart = (LineChartView) rootView.findViewById(R.id.chart);
		text_dates = (LinearLayout) rootView.findViewById(R.id.text_dates);

		left = (ImageButton) rootView.findViewById(R.id.left);
		left.setOnClickListener(this);
		right = (ImageButton) rootView.findViewById(R.id.right);
		right.setOnClickListener(this);

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

		pop_menu = (Button) rootView.findViewById(R.id.pop_menu);
		pop_menu.setOnClickListener(this);

		menu = new PopupMenu(getActivity(), pop_menu);
		menu.getMenuInflater().inflate(R.menu.pop_menu, menu.getMenu());
		menu.setOnMenuItemClickListener(this);

		addView();
	}

	public List<Line> initData(int numberOfAllPoints, float avgValue,
			List<MeridianPoints> list) {
		List<Line> lines = ChartUtils.getPairLine(list, this.getResources()
				.getColor(android.R.color.holo_blue_dark), this.getResources()
				.getColor(android.R.color.holo_red_dark));
		List<PointValue> listAvg = ChartUtils.getAvgList(numberOfAllPoints,avgValue);
		Line lineAvg = ChartUtils.getNoPointsLine(
				this.getResources().getColor(android.R.color.holo_blue_dark),
				listAvg);
		lines.add(lineAvg);

		return lines;
	}

	@Override
	protected int getLayout() {
		// TODO Auto-generated method stub
		return R.layout.waveform_all_line_chart;
	}

	public void initView(List<Line> lines, String[] label) {
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
		chart.startDataAnimation(500);
		chart.setValueSelectionEnabled(hasLabelForSelected);
		chart.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
		chart.setInteractive(true);
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		// TODO Auto-generated method stub
		System.out.println("=-=-=item=-=-=" + item.toString());
		return true;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	protected MedirianAllData requestData(String startTime, final String[] label) {
		// TODO Auto-generated method stub
		AsynMedirianAll asynMedirianAll = new AsynMedirianAll(getActivity(),
				getBean(startTime)) {
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				saveResult = result;
				parseResultInit(result, label);
			}
		};
		asynMedirianAll.execute();
		return null;
	}

	@Override
	protected MedirianAll getBean(String startTime) {
		// TODO Auto-generated method stub
		MedirianAll bean = new MedirianAll();
		bean.setUserId(2);
		bean.setStartDate(startTime);
		bean.setCount(1);
		bean.setDays(15);
		return bean;
	}

	public void parseResultInit(String result, String[] label) {
		MedirianAllData medirianAllData = new MedirianAllData();
		DataPaser<MedirianAllData> parseData = new DataPaser<MedirianAllData>();
		medirianAllData = parseData.parse(result, MedirianAllData.class);
		LogUtils.getInstance().println("medirianAllData",
				medirianAllData.toString());
		List<Line> lines = initData(numberOfAllPoints, medirianAllData
				.getBeans().get(0).getAvgLine().floatValue(), medirianAllData
				.getBeans().get(0).getMeridians());
		initView(lines, label);
	}
}
