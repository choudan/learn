$(function(){
		$(".ruchang img").animate({width:'135px'},500,function(){
			setTimeout(function(){
				$(".ruchang img").animate({opacity:0.5},500,function(){
					loadrun();
				})
			},500);
		})		
    
    function loadrun()
    {
        $('body').prepend(loadhtml);
        var width = $(window).width();
        $(".load").width(width);
        var ivi_wid = parseInt(width / 10);
        var v = ivi_wid / 379;
        var ivi_hei = v * 327;
        $('.ivi-i').width(ivi_wid);
        $('.ivi-i').height(ivi_hei);
        $('.ivi-v').width(ivi_wid);
        $('.ivi-v').height(ivi_hei);
/*        $('.loading').css('marginTop',"-" + (ivi_hei / 2) + "px");
*/        $('.loading').css('marginTop',"-" + (ivi_hei / 3) + "px");
        var l = $('.ivi-v').css('left');
        var i = 1;
        var load = setInterval(function(){
            var l = parseInt($('.ivi-v').css('left'));
            $(".loading").attr('d',i);
            if( l < (width - ivi_wid))
            {
                $('.ivi-v').css({
                    'opacity':'0.3',
                    'left':(l + ivi_wid) + 'px'
                });
                $('.ivi-v').animate({'opacity':1},200);
            }else{
                ent();
                index.run();
            }
            i++;
        },300);
        function ent()
        {
            clearTimeout(load);
            $(".load").animate({'opacity':0},1200,function(){
                $('.ruchang').remove();
                $(".load").remove(); 
            })
        }      
    }
    
})
var loadhtml = '<div class="load">' +
                '<div class="loading" d=\'0\'>' +
                    '<div class="ivi-i-box">' +
                        '<span class="ivi-i"></span>' +
                        '<span class="ivi-i"></span>' +
                        '<span class="ivi-i"></span>' +
                        '<span class="ivi-i"></span>' +
                        '<span class="ivi-i"></span>' +
                        '<span class="ivi-i"></span>' +
                        '<span class="ivi-i"></span>' +
                        '<span class="ivi-i"></span>' +
                        '<span class="ivi-i"></span>' +
                        '<span class="ivi-i"></span>' +
                    '</div>' +
                    '<div class="ivi-v"></div>' +
                '</div>' +
            '</div>';