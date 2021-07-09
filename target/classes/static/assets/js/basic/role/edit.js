/**
 * 编辑-角色管理js
 */
var vm = new Vue({
	el:'#roleEditVue',
	data: {
		role: {}
	},
	methods : {
		setForm: function() {
			$.SetForm({
				url: baseUrl + '/basic/role/info?_' + $.now(),
		    	param: vm.role.id,
		    	success: function(data) {
		    		vm.role = data;
		    	}
			});
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.ConfirmForm({
		    	url: baseUrl + '/basic/role/modify?_' + $.now(),
		    	param: vm.role,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})