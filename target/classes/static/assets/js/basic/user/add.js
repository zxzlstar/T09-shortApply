/**
 * 新增-用户管理js
 */
var vm = new Vue({
	el:'#userAddVue',
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
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.SaveForm({
		    	url: baseUrl + '/basic/user/save?_' + $.now(),
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
