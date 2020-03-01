/**
 * 编辑-用户管理js
 */
var vm = new Vue({
	el:'#userEditVue',
	data: {
		roleList:{},
		user:{
			status: 1,
			roleIdList:[]
		}
	},
	methods : {
		getRoleList: function(){
			$.get(baseUrl + '/basic/role/select?_' + $.now(), function(r){
				vm.roleList = r.data;
			});
		},
		setForm: function() {
			$.SetForm({
				url: baseUrl+'/basic/user/info?_' + $.now(),
		    	param: vm.user.id,
		    	success: function(data) {
		    		vm.user = data;
		    	}
			});
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.ConfirmForm({
		    	url: baseUrl+'/basic/user/modify?_' + $.now(),
		    	param: vm.user,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	},
	created : function() {
		this.getRoleList();
	}
})
