/**
 * 
 */
package com.example.yijia.third.tool;


/**
 * @author Administrator
 *
 */
public class Constant {
//	public final static String URL="http://123.57.44.78:8080/platform3";	
	public final static String URL="http://192.168.137.1:8080";	
	
	public final static int ADMIN_ROLE = 0;
	public final static int INAT_ROLE = 1;
	public final static int MSA_ROLE = 2;
	public final static int SA_ROLE = 3;
	public final static int USER_ROLE = 4;	
	
//	public final static String DATE_TIME="yyyy-MM-dd HH:mm:ss";	
//	public final static String DATE="yyyy-MM-dd";	
	
	public static final String APP_CHOICE_NO_WECHAT="暂无微信版本";
	public static final String CHOICE_DEFAULT_MSA="请选择默认主服务号";
	
	public static final String SERVICE_BEAN="serviceBean";
	public final static String USER_INFO_KEY="userInfo";
	public final static String CODE_TYPE="codeType";	
	public final static String CODE_STATE="codeState";
	
	public final static String QUERY_TYPE="codeState";//区分查询专家还是服务，二者的页面一样	
	public final static String EDITABLE="editable";//区分查看好人添加页面，显示的服务详情	
	
	public final static String ROLE_ID="roleId";	
	public final static String USER_ID="userId";	
	public final static String MSA_ID="msaId";	
	public final static String SA_ID="saId";	
	public final static String INST_ID="instId";	
	public final static String ADMIN_ID="adminId";	
	public final static String SERVICE_ID="serviceId";	
	
	public final static String USER_NAME="userName";	
	public final static String MSA_NAME="msaName";	
	public final static String SA_NAME="saName";	
	public final static String INST_NAME="instName";	
	public final static String ADMIN_NAME="adminName";	
		
	public final static String SIMPLE_INST="simpleInst";	
	public final static String SIMPLE_USER="simpleUser";	
	public final static String MSA="msa";
	
	public final static String MSA_OR_SA="msaOrSa";	
	
	public final static String IS_ASSIGNED="isAssigned";	
	public final static String CHANGE_USER_IDS="changeUserIds";	
	public final static String ASSIGN_OR_QUERY="assignOrQuery";	
	public final static String QUESTIONNAIRE="questionnaire";	
	public final static String QUESTIONNAIRE_LIST="questionnaireList";	
	public final static String ADD_OR_ITEM="addOrItem";	
	public final static String RECORD_BEAN="recordBean";	
	public final static String SEND_TIME="sendTime";	
	public final static String IS_FINISHED="isFinished";	
	
	public final static String UNFINISHED_SURVEY="unfinishedSurvey";	
	public final static String ORDER_STATE="orderState";	
	public final static String ORDER_ALTER="orderAlter";	
	public final static String ORDER="order";	
	
	public final static String IS_DELETE="isDelete";	
	public final static String DELETE="delete";	
	public final static int DELETE_RESQ=100;	
	public final static int DELETE_RESP_CONFIRM=-10;	
	public final static int DELETE_RESP_CANSEL=-20;	
	
	public static final int PERMIT = 0;//相机同意
	public static final int REFUSE = 1;//相机拒绝
	public static final int TAKE_PHOTO_REQUEST = 2;
	
	public final static String MSA_ADD_VIEW_TYPE="msaAddViewType";	
	public final static String MSA_SETTLEMENT_VIEW_TYPE="msaSettlementViewType";	
	public final static String SETTLEMENT_TASK="settlementTask";	
	public final static String SETTLEMENT_TASK_ID="settlementTaskId";	
	
	public static final String STATE_POSITION = "state_position";
	public static final String IMAGE_INDEX = "image_index"; 
	public static final String IMAGE_URLS = "image_urls";
		
	public static final String EXIT = "exit";
	//用户的注册类型，0设备 1手机号
	public static int REGISTER_TYPE = 0;
	
	//ble
	public static final String EXTRAS_DEVICE_ADDRESS = "extras_device_address";
	
	//dataType返回数据类型
	public static final int INTEGER = 0;
	public static final int STRING = 1;
	public static final int LONG = 2;
	public static final int JSONOBJECT = 3;
	public static final int JSONARRAY = 4;
	public static final int DOUBLE = 5;
	
	
	
//	@SuppressLint("SimpleDateFormat")
//	public static String dateTime(){
//		SimpleDateFormat sf=new SimpleDateFormat(DATE_TIME);
//		String dateTime=sf.format(new Date());
//		return dateTime;		
//	}
//	
//	@SuppressLint("SimpleDateFormat")
//	public static String date(){
//		SimpleDateFormat sf=new SimpleDateFormat(DATE);
//		String dateTime=sf.format(new Date());
//		return dateTime;		
//	}
//	
//	//年份加1	
//	public static String deadline() {
//		Calendar calendar = Calendar.getInstance();
//		Date date = new Date(System.currentTimeMillis());
//		calendar.setTime(date);
//		calendar.add(Calendar.YEAR, +1);
//		date = calendar.getTime();
//		SimpleDateFormat sf = new SimpleDateFormat(DATE);
//		String dateTime = sf.format(date);
//		return dateTime;
//	}
}
