var index = function(){
    return {
        width : $(window).width(),
        run : function(){           
                index.ma_disc();
                index.sm_disc();        
        },
        ma_disc : function(){
            var wi = index.width * 0.63963963;
            $(".ma_disc").width(wi);
            $(".ma_disc").css('text-align','center');
            var px = index.width * 0.48583;
            $(".ma_disc .disc_bl").width(px);
            $(".ma_disc .disc_bl").height(px);
            $(".ma_disc .disc_bl").css({
                'line-height':px + 'px',
            });
        
        },
        sm_disc:function(){
            var px = index.width * 0.18694;
            $(".sm_disc .disc_df").width(px);
            $(".sm_disc .disc_df").height(px);
            $(".disc_df").css('text-align','center');
            $(".sm_disc .disc_df").css('line-height',px + 'px');
            $(".sm_disc .disc_xh").width(px);
            $(".sm_disc .disc_xh").height(px);
            $(".disc_xh").css('text-align','center');
            $(".sm_disc .disc_xh").css('line-height',px + 'px');
           
        }
    }
}()
