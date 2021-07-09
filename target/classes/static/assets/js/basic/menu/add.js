/**
 * 新增-菜单管理js
 */
var vm = new Vue({
	el:'#menuAddVue',
	data: {
		menu:{
			pname:'', //上级名称
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
		        url: '/v/basic/menu/icon.html?_' + $.now(),
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
		        url: '/v/basic/menu/tree.html?_' + $.now(),
		        scroll : true,
		        width: "300px",
		        height: "450px",
		        yes : function(iframeId) {
		        	top.frames[iframeId].vm.acceptClick();
				}
		    })
		},
		removeParent:function(){
			vm.menu.pid = '';
			vm.menu.pname = '';
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.SaveForm({
		    	url: '/basic/menu/save?_' + $.now(),
		    	param: vm.menu,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
