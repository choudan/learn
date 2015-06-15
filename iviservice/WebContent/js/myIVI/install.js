var j = false;
var code; // 在全局 定义验证码
var color = '#'; // 在全局 定义验证码背景色
var fontColor = '#'; // 在全局 定义验证码字体颜色

/* 验证电话号码 */
function tele1() {
	document.getElementById("span1").innerHTML = "";
}

/*function checktele(e) {
	var sPhone = document.getElementById(e).value;
	if (document.getElementById(e).value == null
			|| document.getElementById(e).value == "") {
		document.getElementById("span1").innerHTML = "请输入您的电话号码！";
		document.getElementById("span1").style.color = "red";
	} else if (!(/^1[3|4|5|7|8]\d{9}$/.test(sPhone))) {
		document.getElementById("span1").innerHTML = "您的电话号码格式不正确！";
		document.getElementById("span1").style.color = "red";
	} else{
		j = true;		
	}
}*/

function checktele() {
	var sPhone = document.getElementById("phoneNumber").value;
	if (sPhone == null
			|| sPhone == "") {
		document.getElementById("telephone").innerHTML = "请输入您的电话号码！";
		document.getElementById("telephone").style.color = "red";
	} else if (!(/^1[3|4|5|7|8]\d{9}$/.test(sPhone))) {
		document.getElementById("telephone").innerHTML = "您的电话号码格式不正确！";
		document.getElementById("telephone").style.color = "red";
	} else{
		j = true;		
	}
}

/* 绑定手机号 */
function binding() {
	var obj = {};
	obj.telephone = phoneNum.value;
	$.ajax({
		type : "get",
		dataType : "json",
		url : "http://123.57.60.214/tyj_weixin_dingyuehao/api/linkapp",
		data : JSON.stringify(obj),
		success : function(data) {
			if (data.valid) {
				alert("绑定成功！");
				self.location.href = "myIVI.html";
			} else {
				alert("对不起，您并未在app进行注册");
				self.location.href = "install.html";
			}
		},
		error : function(data) {
			alert(data.responseText);
		}
	})
}
/* 验证码 */
/* 产生验证码 */
function createCode() {
	code = "";
	var codeLength = 4;// 验证码的长度
	var checkCode = document.getElementById("checkCode");
	var selectChar = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');// 所有候选组成验证码的字符，当然也可以用中文的

	for (var i = 0; i < codeLength; i++) {

		var charIndex = Math.floor(Math.random() * 36);
		code += selectChar[charIndex];

	}
	// alert(code);
	if (checkCode) {
		checkCode.className = "code";
		checkCode.value = code;
	}
}

/* 验证验证码及其他信息 */
function put() {
	var inputCode = document.getElementById("input1").value;
	inputCode = inputCode.toUpperCase();

	var event = document.createEvent("MouseEvents");
	event.initMouseEvent("blur", true, true, document.defaultView, 0, 0, 0, 0,
			0, false, false, false, false, 0, null);
	var Num = document.getElementById("phoneNumber");
	Num.dispatchEvent(event);

	if (j == false) {
		alert("请输入正确的电话号码！");
	} else if (inputCode != code) {
		alert("验证码输入错误！");
		createCode();// 刷新验证码
	} else {
		var com=confirm("确认提交吗？");
		if(com)
			putOn();
		else{
			alert("安装失败");
		}				
	}
}

function putOn(){
	var info=new Object();
	info.phoneNumber=$("#phoneNumber").val();
	info.name=$("#name").val();
	info.age=$("#age").val();
	info.identity_no=$("#identity_no").val();
	info.email=$("#email").val();
	info.drive_year=$("#drive_year").val();
	info.type=$("#type").val();
	info.adress=$("#adress").val();
	// alert(JSON.stringify(obj));
	if(info.phoneNumber.length==0){
		alert("电话号码不能为空");
	}else{
		$.ajax({
			type : "get",
			dataType : "json",
			url : "http://123.57.60.214/tyj_weixin_dingyuehao/api/register",
			// 将对象转换成字符串，并传递给后台
			data : {
				"register" : JSON.stringify(info)
			},
			success : function(data) {
				alert(data.data.submitFail);
			},
			error : function() {
				alert(data.data.submitFail);
			}
		});		
	}
}

/*
function surePage() {
	document.getElementById("surePage").style.display = "inline-block";
	document.getElementById("inputPage").style.display = "none";
	$("#sure_phoneNumber").html($("#phoneNumber").val());
	$("#sure_name").html($("#name").val());
	$("#sure_age").html($("#age").val());
	$("#sure_identity_no").html($("#identity_no").val());
	$("#sure_email").html($("#email").val());
	$("#sure_drive-year").html($("#drive_year").val());
	// 获取家庭地址信息到span

	var Prov = document.getElementById("Nowprovince");
	var City = document.getElementById("Nowcity");
	var Dist = document.getElementById("Nowdistrict");
	var Street = document.getElementById("home_addr_street");

	$("#sure_address").html(
			Prov.options[Prov.selectedIndex].text + " "
					+ City.options[City.selectedIndex].text + " "
					+ Dist.options[Dist.selectedIndex].text);
	$("#sure_home_addr_street").html(Street.value);
}

function reEdit() {
	document.getElementById("surePage").style.display = "none";
	document.getElementById("inputPage").style.display = "block";
}*/

/*var nowAddress = new addressSelector(document.getElementById("Nowprovince"),document.getElementById("Nowcity"),document.getElementById("Nowdistrict"));
*/

/*function subForm() {
	// according the background to edit
	var obj = {};
	obj.carType = $("#carType").val();
	obj.buyYear = $("#buyYear").val();
	obj.carNumber = $("#carNumber").val();
	obj.insurance = $("#insurance").val();
	obj.lastYearCosts = $("#lastYearCosts").val();
	obj.installAddress = $("#installAddress").val();
	obj.device_id = $("#device_id").val();
	
	// alert(JSON.stringify(obj));
	$.ajax({
		type : "get",
		dataType : "json",
		url : "http://182.92.224.124/tyj_weixin_dingyuehao/api/installInfo",
		// 将对象转换成字符串，并传递给后台
		data : {
			"installInfo" : JSON.stringify(obj)
		},
		success : function(data) {
			alert(data.data.submitFail);
		},
		error : function() {
			alert("提交失败，请重新注册");
		}
	})
}
*/