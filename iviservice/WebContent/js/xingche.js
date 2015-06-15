var xingche = function(){
	return {
		width: $('window').width(),
		init:function(){
			xingche.inputw();
		},
		inputw:function(){
			var p = $('.form').width() / 2;
			$(".item").css({'width':p + "px"})
			$(".item input").css({'width':(p - 24) + 'px'})
		}
	}
}()