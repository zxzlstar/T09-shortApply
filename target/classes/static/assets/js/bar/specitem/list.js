/**
 * 规格项管理js
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
		url: baseUrl + '/shop/specitem/list?_' + $.now(),
		height: $(window).height()-54,
		queryParams: function(params){
			params.paramsName = vm.paramsName;
			return params;
		},
		columns: [{
			checkbox: true
		}, {
			field : "id",
			title : "编号",
			visible:false
		}, {
			field : "paramsName",
			title : "规格项",
			width : "150px",
			align: 'center', valign: 'middle'
		},{
			field : "paramsValue",
			title : "规格值",
			width : "350px",
			height : "100px",
			align: 'center', valign: 'middle',
			formatter : function(value, row, index){
				return value;
			}
		}, {
			field : "remark",
			title : "备注",
			width : "300px",
			align: 'center', valign: 'middle'
		}]
	})
}

var vm = new Vue({
	el:'#specitemListVue',
	data: {
        paramsName: null
	},
	methods : {
		load: function() {
			$('#dataGrid').bootstrapTable('refresh');
		},
		add: function() {
			dialogOpen({
				title: '新增规格项',
				url: baseUrl + '/v/bar/specitem/add.html?_' + $.now(),
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
					title: '编辑规格项',
					url: baseUrl + '/v/bar/specitem/edit.html?_' + $.now(),
					width: '500px',
					height: '520px',
					scroll : true,
					success: function(iframeId){
						top.frames[iframeId].vm.specItem.id = ck[0].id;
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
					url: baseUrl + '/shop/specitem/remove?_' + $.now(),
			    	param: ids,
			    	success: function(data) {
			    		vm.load();
			    	}
				});
			}
		}
	}
})