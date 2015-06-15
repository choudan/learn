var j1 = false;
var j2 = false;
var j3 = false;//阅读声明是否被选中
//车型验证
function clear_carType(){
    document.getElementById("alarm_carType").innerHTML = "";
}

function check_carType(e){
    if(e.value.replace(/\s+/g,"") == ""){
        document.getElementById("alarm_carType").innerHTML = "请输入您的车型！";
        j1 = false;
    }
    else
        j1 = true;
}

//车牌号验证
function clear_carNumber(){
    document.getElementById("alarm_carNumber").innerHTML = "";
}

function check_carNumber(e){
    if(e.value.replace(/\s+/g,"") == ""){
        document.getElementById("alarm_carNumber").innerHTML = "请输入您的车牌号！";
        j2 = false;
    }
    else if(!(/^[\u4e00-\u9fa5][A-Z_0-9]{6}$/.test(e.value))){
        document.getElementById("alarm_carNumber").innerHTML = "您输入的车牌号不正确！";
        j2 = false;
    }
    else {
        j2 = true;
    }
}

   

//提交信息
function validate(){
	var Ev1 = document.getElementById("carType");
	var Ev2 = document.getElementById("carNumber");
	var event = document.createEvent("MouseEvents");
    event.initMouseEvent("blur", true, true, document.defaultView, 0, 0, 0, 0, 0,false, false, false, false, 0, null);
    Ev1.dispatchEvent(event);
    Ev2.dispatchEvent(event);
	j3 = document.getElementById("statement").checked; //是否勾选阅读声明
	if(j1&&j2&&j3){
        return surePage();
	}
	else if(j1&&j2&&(!j3)){
		alert("请勾选阅读声明");
	}else{
		alert("请输入正确的信息");
	}
}



                                                /*确认页面*/
function surePage(){
    $("#sure_carType").html($("#carType").val());
    $("#sure_buyYear").html($("#buyYear").val());
    $("#sure_carNumber").html($("#carNumber").val());
    $("#sure_insurance").html($("#insurance").val());
    $("#sure_lastYearCosts").html($("#lastYearCosts").val());
    $("#sure_installAddress").html($("#installAddress").val());
    $("#sure_device_id").html($("#device_id").val());

    document.getElementById("page1").style.display = "none";
    document.getElementById("page2").style.display = "block";
}
                                                /*返回修改*/
function reEdit(){
    document.getElementById("page1").style.display = "block";
    document.getElementById("page2").style.display = "none";
}
                                                /*提交表单*/

function subForm(){

	//传递给后台
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
		type:"get",
		dataType:"json",
		url:"http://123.57.60.214/tyj_weixin_dingyuehao/api/complete_info",
		data: {'compinfo':JSON.stringify(obj)},
		success:function(data){
			alert("提交成功");
			self.location.href = "../index.html";
		},
		error: function(data){
			alert("提交失败,请重新提交");
		}
	})
}