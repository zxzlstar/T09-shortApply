/**
 * 配送信息-订单管理js
 */
var vm = new Vue({
	el:'#orderAddVue',
	data: {
		roleList:{},
		order:{
		},
	},
	methods : {
		acceptClick: function(id) {
			if (!$('#form').Validform()) {
		        return false;
		    }
			var d = vm.order;
			vm.order.id = id;
		    $.SaveForm({
		    	url: baseUrl + '/shop/order/transport?_' + $.now(),
		    	param: vm.order,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})

