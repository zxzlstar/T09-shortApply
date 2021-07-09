/**
 * 幻灯片管理js
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
		url: baseUrl + '/shop/banner/list?_' + $.now(),
		height: $(window).height()-54,
		queryParams: function(params){
			params.name = vm.keyword;
			params.position = vm.position;
			return params;
		},
		columns: [{
			checkbox: true
		}, {
			field : "id",
			title : "编号",
			visible:false
		}, {
			field : "name",
			title : "名称",
			width : "150px",
			align: 'center', valign: 'middle'
		},{
			field : "imageUrl",
			title : "图片",
			width : "150px",
			height : "100px",
			align: 'center', valign: 'middle',
			formatter : function(value, row, index){
				if(value == ''){
					return '暂无图片';
				}
				return '<img style="width:100px;height:100px" src="'+value+'" />'
			}
		}, {
			field : "link",
			title : "链接地址",
			width : "300px",
			align: 'center', valign: 'middle',
			formatter : function(value, row, index){
				if(value == ''){
					return "暂无地址";
				}
				return value;
			}
		}, {
			field : "rank",
			title : "排序",
			width : "60px",
			align: 'center', valign: 'middle'
		}, {
			field : "position",
			title : "位置",
			width : "60px",
			align: 'center', valign: 'middle',
			formatter : function(value , row, index) {
				if(value == '1'){
	        		return '<span class="label label-success">首页</span>';
	        	}else if(value == '2'){
	        		return '<span class="label label-info">竞猜页面</span>';
	        	}
		   }
		}, {
			field : "ctime",
			title : "创建时间",
			width : "250px",
			align: 'center', valign: 'middle'
		},{
			field : "remark",
			title : "备注",
			align: 'center', valign: 'middle'
		}]
	})
}

var vm = new Vue({
	el:'#bannerListVue',
	data: {
		keyword: null,
		position: null
	},
	methods : {
		load: function() {
			$('#dataGrid').bootstrapTable('refresh');
		},
		add: function() {
			dialogOpen({
				title: '新增banner',
				url: baseUrl + '/v/bar/banner/add.html?_' + $.now(),
				width: '500px',
				height: '520px',
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
					title: '编辑banner',
					url: baseUrl + '/v/bar/banner/edit.html?_' + $.now(),
					width: '500px',
					height: '520px',
					scroll : true,
					success: function(iframeId){
						top.frames[iframeId].vm.banner.id = ck[0].id;
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
			if(checkedArray(ck)){
				$.each(ck, function(idx, item){
					ids[idx] = item.id;
				});
				$.RemoveForm({
					url: baseUrl + '/shop/banner/remove?_' + $.now(),
			    	param: ids,
			    	success: function(data) {
			    		vm.load();
			    	}
				});
			}
		}
	}
})