/**
 * 新增-行政区域js
 */
var vm = new Vue({
	el:'#regionAddVue',
	data: {
		region: {
			pcode: 0,
			pname: null,
			status: 1,
			orderNum: 0
		}
	},
	methods : {
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.SaveForm({
		    	url: baseUrl + '/basic/region/save?_' + $.now(),
		    	param: vm.region,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
