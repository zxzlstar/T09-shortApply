/**
 * 配送信息-订单管理js
 */
var vm = new Vue({
	el:'#transportVue',
	data: {
	},
	methods : {
		acceptClick: function(transportNo) {
			window.location.href="https://m.ickd.cn/result.html#no="+transportNo+"&com=shunfeng";
		}
	}
})

