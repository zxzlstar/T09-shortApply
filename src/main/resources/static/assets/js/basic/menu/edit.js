/**
 * 编辑-菜单管理js
 */
var vm = new Vue({
	el:'#menuEditVue',
	data: {
		menu:{
			pname:'' ,
			pid:'',
			type:1,
			orderNum:0,
			icon: null
		}
	},
	methods : {
		selectIcon: function() {
			dialogOpen({
				id: 'iconSelect',
				title: '选取图标',
		        url: baseUrl + '/v/basic/menu/icon.html?_' + $.now(),
		        scroll : true,
		        width: "1030px",
		        height: "600px",
		        btn: false
		    })
		},
		menuTree: function(){
		    dialogOpen({
				id: 'layerMenuTree',
				title: '选择菜单',
		        url: baseUrl + '/v/basic/menu/tree.html?_' + $.now(),
		        scroll : true,
		        width: "300px",
		        height: "450px",
		        yes : function(iframeId) {
		        	top.frames[iframeId].vm.acceptClick();
				}
		    })
		},
		setForm: function() {
			$.SetForm({
				url: baseUrl + '/basic/menu/info?_' + $.now(),
		    	param: vm.menu.menuId,
		    	success: function(data) {
		    		vm.menu = data;
		    	}
			});
		},
		removeParent:function(){
			vm.menu.pid = '';
			vm.menu.pname = '';
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.ConfirmForm({
		    	url: baseUrl + '/basic/menu/modify?_' + $.now(),
		    	param: vm.menu,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
