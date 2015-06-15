var common = function(){
    return {
        width : $(window).width(),
        run : function(){
            common.nav_round();
            common.logo();
        },
        nav_round : function(){
            var px = common.width * 0.19;
            $(".header .nav").css({
                'line-height':px + 'px',
                'height':px + 'px',
                'width':px + 'px',
            })
        },
        logo : function(){
            var wid = common.width * 0.2777;
            $(".header .logo").width(wid);
            $(".header .logo").height($('.header').height());
        },
    }
}()