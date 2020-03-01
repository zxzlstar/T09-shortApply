/**
 * 配送信息-订单管理js
 */
var vm = new Vue({
	el:'#orderDetailVue',
	data: {
		orderVo:{},
	},
	methods : {
		acceptDeatial: function(id) {
			$.SetForm({
				url: baseUrl+'/shop/order/info?_' + $.now(),
		    	param: id,
		    	success: function(data) {
		    		
		    		
		    		
		    		if(data.status == '1'){
		    			data.status = "待付款";
		    		}else if(data.status == '2'){
		    			data.status = "已付款";
		    		}else if(data.status == '3'){
		    			data.status = '已发货';
		    		}else if(data.status == '4'){
		    			data.status = '已完成';
		    		}else if(data.status == '5'){
		    			data.status = '转入售后';
		    		}else if(data.status == '6'){
		    			data.status = '已取消';
		    		}
		    		vm.orderVo = data;
		    		debugger;
		    	}
			}); 
		}
	}
})

