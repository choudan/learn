var website = function(){
    return {
        width : $(window).width(),
        run : function(){
            website.slider();
            website.web_menu();
        },
        slider : function(){
            var px = website.width * 0.68;
            var pe = website.width * 0.16;
            $(".slider_box").width(px);
            $(".slider_box").find(".slider_box_item").width(px);
            $(".slider_box").find(".slider_box_item").height(px);
            $(".slider_box").find(".slider_box_item").css({'display':'none'});
            $(".slider_box").find(".slider_box_item").eq($(".slider_box").attr('data-slide')).css({'display':'block'});
            $(".slider_box").find(".slider_box_item").eq($(".slider_box").attr('data-slide')).find('img').animate({'opacity':'1'},1000);
            $(".slider_nav").width(pe);
            $(".slider_nav").find(".slider_nav_item").width(pe);
            $(".slider_nav").find(".slider_nav_item").height(pe);
            $(".slider_nav").find(".slider_nav_item").css({'line-height':pe + 'px'});
            $(".slider_nav_item").eq(0).find('img').animate({'opacity':'1'},500);
            var len = $(".slider_nav").find(".slider_nav_item").length;
            var t=setInterval(function(){
                var i = parseInt($(".slider_box").attr('data-slide'));
                $(".slider_box_item").eq(i).find('img').animate({opacity:'0.5'},1000,function(){
                    $(".slider_box_item").eq(i).css({'display':'none'});
                   // $(".slider_nav_item").find('img').animate({'opacity':'1'},1000);
                    if(i == (len - 1)){
                    	$(".slider_nav_item").find('img').animate({'opacity':'0.5'},1000);
                        $(".slider_nav_item").eq(0).find('img').animate({'opacity':'1'},1000);
                        $(".slider_box_item").eq(0).css({'display':'block'});
                        $(".slider_box_item").eq(0).find('img').animate({'opacity':'1'},1000);
                        $(".slider_box").attr('data-slide','0');
                    }else{
                    	$(".slider_nav_item").find('img').animate({'opacity':'0.5'},1000);
                        $(".slider_nav_item").eq(i + 1).find('img').animate({'opacity':'1'},1000);;
                        $(".slider_box_item").eq(i + 1).css({'display':'block'});
                        $(".slider_box_item").eq(i + 1).find('img').animate({'opacity':'1'},1000);
                        $(".slider_box").attr('data-slide',i + 1);
                    }
                })
            },1500);
           
        },  
        web_menu : function(){
            var px = website.width * 0.20;
            $(".web_menu_item").width(px);
            var cp = px * 0.80;
            $(".menu_circle").width(cp);
            $(".menu_circle").height(cp);
            $(".menu_circle").css({'line-height':cp + 'px'});
        }
    }
}();
 $(function(){
	 $("slider_nav_item").mouseover(function(){
   		 window.clearInterval(t);
   		});
 }) 

