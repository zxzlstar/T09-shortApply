/**
 * 消费金额修改-会员管理js
 */
var vm = new Vue({
	el:'#memberAmountVue',
	data: {
		member:{
		},
	},
	methods : {
		setForm: function() {
			$.SetForm({
				url: baseUrl+'/shop/member/info?_' + $.now(),
		    	param: vm.member.id,
		    	success: function(data) {
		    		vm.member = data;
		    	}
			});
		},
		acceptClick: function(id) {
			if (!$('#form').Validform()) {
		        return false;
		    }
			var d = vm.member;
			vm.member.id = id;
		    $.SaveForm({
		    	url: baseUrl + '/shop/member/updateAmount?_' + $.now(),
		    	param: vm.member,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})

