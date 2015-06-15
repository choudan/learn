var qiandao = function() {
	return {
		width : $(window).width(),
		run : function() {
			qiandao.qd();
			qiandao.user_acc();
			// 优惠活动
			$(".sfq_tit").click(function() {
				$(this).siblings('.sfq_con1').slideToggle("slow");
			});
		},

		qd : function() {
			var wi = qiandao.width * 0.57;
			$(".qiandao").width(wi);
			$(".qiandao").css('text-align', 'center');
			$(".qiandao .qd_disc").width(wi);
			$(".qiandao .qd_disc").height(wi);
			$(".qiandao b").css({
				'line-height' : wi + 'px',
			});
		},
		user_acc : function() {
			var wi = qiandao.width * 0.22;
			$(".user_acc div").width(wi);
			$(".user_acc div").height(wi);
			$(".user_acc div").css({
				'line-height' : wi + 'px',
			});
			$(".user_acc div b").css({
				'line-height' : (wi - 16) + 'px',
			});
			$(".user_acc div em").css({
				'line-height' : (wi + 16) + 'px',
			});
		}
	}
}();

