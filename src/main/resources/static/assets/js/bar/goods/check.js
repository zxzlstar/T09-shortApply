/**
 * 审核商品-商品管理js
 */
var vm = new Vue({
	el:'#goodsCheckVue',
	data: {
		goods:{
			checkStatus : 2
		},
	},
	methods : {
		acceptClick: function(ids) {
			if (!$('#form').Validform()) {
		        return false;
		    }
			var d = new Object();
			d.ids = ids;
			d.checkStatus = vm.goods.checkStatus;
		    $.SaveForm({
		    	url: baseUrl + '/shop/goods/check?_' + $.now(),
		    	param: d,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})

