/**
 * 新增-角色管理js
 */
var vm = new Vue({
	el:'#roleAddVue',
	data: {
		role: {}
	},
	methods : {
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.SaveForm({
		    	url: baseUrl + '/basic/role/save?_' + $.now(),
		    	param: vm.role,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
