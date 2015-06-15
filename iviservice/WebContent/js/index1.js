var index = function(){
    return {
        width : $(window).width(),
        run : function(){
            setTimeout(function(){
                index.ma_disc();
                index.sm_disc();
            },1000);
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
            settimg($(".ma_disc .disc_bl"));
        },
        sm_disc:function(){
            var px = index.width * 0.18694;
            $(".sm_disc .disc_df").width(px);
            $(".sm_disc .disc_df").height(px);
            $(".sm_disc .ms_disc span").css('line-height',px + 'px');
            $(".sm_disc .disc_xh").width(px);
            $(".sm_disc .disc_xh").height(px);
            $(".sm_disc .ms_disc span").css('line-height',px + 'px');
            settimg($(".sm_disc .df_ms"),function(){
                $(".sm_disc .disc_df").css('transform','rotate(360deg)');
            });
        }
    }
}()
var settimg = function(is,func){
    var da = parseInt(is.attr('data-bl'));
    setInterval(function(){
        var i = parseInt(is.find('b').text());
        if(i >= da)
        {
            ent(func)
        }else{
            is.find('b').text((i + 1));
        }
    },10);
}
function ent(func)
{
    clearTimeout(settimg);
    func();
}