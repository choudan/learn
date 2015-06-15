/*
function color(e) {
	var btns = document.getElementById("btn-group");
	var btn = btns.getElementsByTagName('span');
	for (var i = 0; i < btn.length; i++) {
		btn[i].style.backgroundColor = "#dcdcdc";
		btn[i].style.color = "#9b9696";
	}
	e.style.backgroundColor = '#cc0000';
	e.style.color = "#fff";
}
   // 过去3天
   function data1(){
	   var args={"interval":0};
	   $.getJSON("http://182.92.224.124/tyj_weixin_dingyuehao/api/driving_trend",args,function(data){
//		   lineChartData.labels = [data.labels[0],data.labels[1],data.labels[2]];
//		   //告警
//		   lineChartData.datasets[1].data=[data.keypoint1[0],data.keypoint1[1],data.keypoint1[2]];
//		   //严重告警
//		   lineChartData.datasets[0].data=[data.keypoint2[0],data.keypoint2[1],data.keypoint2[2]];
		  
		    
		   lineChartData.labels = ["first","second","third"];
		   lineChartData.datasets[0].data=[3,5,2];  //严重告警
		   lineChartData.datasets[1].data=[7,8,6];  //告警
		    	    
		   var ctx = document.getElementById("canvas").getContext("2d");
		   window.myLine = new Chart(ctx).Line(lineChartData, {
				responsive: true
			});
		   
		 });	
		
   }  
   //过去一周
   function data2(){
	   var args={"interval":1};
	   $.getJSON("http://182.92.224.124/tyj_weixin_dingyuehao/api/driving_trend",args,function(data){
		   lineChartData.labels = [data.labels[0],data.labels[1],data.labels[2],data.labels[3],data.labels[4],data.labels[5],data.labels[6]];
		   //告警
		   lineChartData.datasets[1].data=[data.keypoint1[0],data.keypoint1[1],data.keypoint1[2],data.keypoint1[3],data.keypoint1[4],data.keypoint1[5],data.keypoint1[6]];
		   //严重告警
		   lineChartData.datasets[0].data=[data.keypoint2[0],data.keypoint2[1],data.keypoint2[2],data.keypoint2[3],data.keypoint2[4],data.keypoint2[5],data.keypoint2[6]];
		   var ctx = document.getElementById("canvas").getContext("2d");
		   window.myLine = new Chart(ctx).Line(lineChartData, {
				responsive: true
			}); 
	   
	   });	
	   
	   
	   lineChartData.labels = ["January","February","March","April","May","June","July"];
	   lineChartData.datasets[0].data=[5,8,10,4,3,2,60];
	   lineChartData.datasets[1].data=[7,10,12,15,16,13,20];
	   
	   
		
   }  
  //过去一个月
   function data3(){
	   var args={"interval":2};
	   $.getJSON("http://182.92.224.124/tyj_weixin_dingyuehao/api/driving_trend",args,function(data){
		   lineChartData.labels = [data.labels[0],data.labels[1],data.labels[2],data.labels[3]];
		   //告警
		   lineChartData.datasets[1].data=[data.keypoint1[0],data.keypoint1[1],data.keypoint1[2],data.keypoint1[3]];
		   //严重告警
		   lineChartData.datasets[0].data=[data.keypoint2[0],data.keypoint2[1],data.keypoint2[2],data.keypoint2[3]];
		   var ctx = document.getElementById("canvas").getContext("2d");
		   window.myLine = new Chart(ctx).Line(lineChartData, {
				responsive: true
			});	  
	   
	   });	
	   
	   
	   lineChartData.labels = ["first","second","third","fourth"];
	   lineChartData.datasets[0].data=[3,5,2,4];
	   lineChartData.datasets[1].data=[8,8,9,10];
	   
	   
	  
   } 
   //过去半年
   function data4(){
	   var args={"interval":3};
	   $.getJSON("http://182.92.224.124/tyj_weixin_dingyuehao/api/driving_trend",args,function(data){
		   lineChartData.labels = [data.labels[0],data.labels[1],data.labels[2],data.labels[3],data.labels[4],data.labels[5],];
		   //告警
		   lineChartData.datasets[1].data=[data.keypoint1[0],data.keypoint1[1],data.keypoint1[2],data.keypoint1[3],data.keypoint1[4],data.keypoint1[5]];
		   //严重告警
		   lineChartData.datasets[0].data=[data.keypoint2[0],data.keypoint2[1],data.keypoint2[2],data.keypoint2[3],data.keypoint2[4],data.keypoint2[5]];
		   var ctx = document.getElementById("canvas").getContext("2d");
		   window.myLine = new Chart(ctx).Line(lineChartData, {
				responsive: true
			});	 
	   
	   });	
	     
	   lineChartData.labels = ["first","second","third","fourth","fifth","sixth"];
	   lineChartData.datasets[0].data=[3,2,2,4,4,6];
	   lineChartData.datasets[1].data=[10,8,6,6,8,10];
	   
	   
   }  
    */