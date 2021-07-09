/**
 * 用户管理js
 */

$(function () {
	initialPage();
	getGrid();
});

function initialPage() {
	$(window).resize(function() {
		$('#dataGrid').bootstrapTable('resetView', {height: $(window).height()-54});
	});
}

function getGrid() {
	$('#dataGrid').bootstrapTableEx({
		url: baseUrl + '/basic/user/list?_' + $.now(),
		height: $(window).height()-54,
		queryParams: function(params){
			params.username = vm.keyword;
			return params;
		},
		columns: [{
			radio: true
		}, {
			field : "id",
			title : "编号",
			visible:false
		}, {
			field : "username",
			title : "用户名",
			width : "200px"
		}, {
			field : "email",
			title : "邮箱",
			width : "300px"
		}, {
			field : "mobile",
			title : "手机号",
			width : "130px"
		}, {
			field : "status",
			title : "状态",
			width : "60px",
			formatter : function(value , row, index) {
				if(value=='0'){
	        		return '<span class="label label-danger">禁用</span>';
	        	}else if(value=='1'){
	        		return '<span class="label label-success">正常</span>';
	        	}
		   }
		}, {
			field : "ctime",
			title : "创建时间"
		}]
	})
}

var vm = new Vue({
	el:'#userListVue',
	data: {
		keyword: null
	},
	methods : {
		load: function() {
			$('#dataGrid').bootstrapTable('refresh');
		},
		add: function() {
			dialogOpen({
				title: '新增用户',
				url: baseUrl + '/v/basic/user/add.html?_' + $.now(),
				width: '600px',
				height: '350px',
				scroll : true,
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				},
			});
		},
		edit: function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections');
			if(checkedRow(ck)){
				dialogOpen({
					title: '编辑用户',
					url: baseUrl + '/v/basic/user/edit.html?_' + $.now(),
					width: '600px',
					height: '350px',
					scroll : true,
					success: function(iframeId){
						top.frames[iframeId].vm.user.id = ck[0].id;
						top.frames[iframeId].vm.setForm();
					},
					yes : function(iframeId) {
						top.frames[iframeId].vm.acceptClick();
					},
				});
			}
		},
		remove: function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections'), ids = [];	
			if(checkedRow(ck)){
				$.each(ck, function(idx, item){
					ids[idx] = item.id;
				});
				$.RemoveForm({
					url: baseUrl + '/basic/user/remove?_' + $.now(),
			    	param: ids,
			    	success: function(data) {
			    		vm.load();
			    	}
				});
			}
		},
		disable: function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections'), ids = [];	
			if(checkedRow(ck)){
				$.each(ck, function(idx, item){
					ids[idx] = item.id;
				});
				$.ConfirmForm({
					msg: '您是否要禁用所选用户吗？',
					url: baseUrl + '/basic/user/disable?_' + $.now(),
			    	param: ids,
			    	success: function(data) {
			    		vm.load();
			    	}
				});
			}
		},
		enable: function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections'), ids = [];	
			if(checkedRow(ck)){
				$.each(ck, function(idx, item){
					ids[idx] = item.id;
				});
				$.ConfirmForm({
					msg: '您是否要启用所选用户吗？',
					url: baseUrl + '/basic/user/enable?_' + $.now(),
			    	param: ids,
			    	success: function(data) {
			    		vm.load();
			    	}
				});
			}
		},
		reset: function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections');
			if(checkedRow(ck)){
				dialogOpen({
					title: '重置密码',
					url: baseUrl + '/v/basic/user/reset.html?_' + $.now(),
					width: '400px',
					height: '220px',
					success: function(iframeId){
						top.frames[iframeId].vm.user.id = ck[0].id;
					},
					yes : function(iframeId) {
						top.frames[iframeId].vm.acceptClick();
					},
				});
			}
		}
	}
})