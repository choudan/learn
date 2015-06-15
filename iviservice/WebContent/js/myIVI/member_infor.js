
var head=document.getElementsByName("head");
function  editimg() {
	var state=true;
	if(state){
		document.getElementById("saimg").style.display="block";
		document.getElementById("headid").innerHTML("保存");	
		state=false;
	}else{
		document.getElementById("saimg").style.display="none";
		document.getElementById("headid").innerHTML("更换头像");	
		state=true;
		saveHead();
	}
}
function chosHead(obj){	
	var head_pic="";
	for (var i = 0; i < head.length; i++) {
		head[i].style.border="none";
		head_pic=i;
	}
	obj.style.border="dotted 2px #0412CA";
}

function saveHead(){
	var  photo=-1;
	for (var i = 0; i < head.length; i++) {
		if(head[i].style.border != "none"){
			head[i].style.border == "none";		
			photo=i;
		}		
	}
	if(photo!=-1){
		$.ajax({
			type : "get",
			dataType : "json",
			url : "http://123.57.60.214/tyj_weixin_dingyuehao/api/changeHeadView",
			// 将对象转换成字符串，并传递给后台
			data : {
				"headView" : photo
			},
			success : function(data) {
				if(data.statusCode==-1){
					alert(data.data);
					document.getElementById("modimg").style.display="block";
					document.getElementById("saimg").style.display="none";
				}			
			},
			error : function() {
				alert("请求失败");
			}
		})		
	}else{
		alert("请先选择头像");
	}
	
	
	/*$.getJSON("http://182.92.224.124/tyj_weixin_dingyuehao/api/changeHeadView",args1,function(data){
		document.getElementById("headimg").src=data.data;
		document.getElementById("modimg").style.display="block";
		document.getElementById("saimg").style.display="none";
     });*/
	
}

function save(){
	document.getElementById("chg").style.display = "none";
	document.getElementById("cur").style.display = "block";
	var obj = {}; 
	 obj.name = $("#name").val();
	 obj.gender = $("#gender").val();
	 obj.age = $("#age").val();
	 obj.licenseType = $("#licenseType").val();
	 obj.licenseNumber = $("#licenseNumber").val();
	 obj.allowNumber = $("#allowNumber").val(); 
	 $.ajax({  
	        url: "http://123.57.60.214/tyj_weixin_dingyuehao/api/update_user_info",  
	        type:"get",
	        dataType:"json",  
	        data:{'update_user_info':JSON.stringify(obj)},  
	        success: function (data) { 	           
	   	        $.getJSON("http://123.57.60.214/tyj_weixin_dingyuehao/api/query_user_info",function(data){	   				 
	   	         $("#crt-name").html(data.data.name);
	   			 $("#crt-gender").html(data.data.gender);
	   			 $("#crt-age").html(data.data.age);
	   			 $("#crt-licenseType").html(data.data.licenseType);
	   			 $("#crt-licenseNumber").html(data.data.licenseNumber);
	   			 $("#crt-allowNumber").html(data.data.allowNumber);		
	   	      });
	   	     alert(data.data.msg);
   	        }
	    }); 
}

function edit(){
	document.getElementById("chg").style.display = "block";
	document.getElementById("cur").style.display = "none";
}

function validate(){
	if(document.getElementById("name").value.replace(/\s+/g,'') == '')
		alert("请输入姓名！");
	else if(document.getElementById("licenseNumber").value.search(/^[\u4e00-\u9fa5]{1}[A-Z_0-9]{6}$/) == -1)
		alert("请输入正确的车牌号！");
	else
		return save();
}