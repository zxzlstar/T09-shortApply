/**
 * 编辑-行政区域js
 */
var vm = new Vue({
	el:'#regionEditVue',
	data: {
		region: {
			pcode: 0,
			pname: null,
			status: 1,
			orderNum: 0
		}
	},
	methods : {
		setForm: function() {
			$.SetForm({
				url: baseUrl + '/basic/region/info?_' + $.now(),
		    	param: vm.region.id,
		    	success: function(data) {
		    		vm.region = data;
		    	}
			});
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.ConfirmForm({
		    	url: baseUrl + '/basic/region/modify?_' + $.now(),
		    	param: vm.region,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})