/**
 * 商品类型js
 */

$(function () {
	initialPage();
	getGrid();
});

function initialPage() {
	$(window).resize(function() {
		Goodscf.table.resetHeight({height: $(window).height()-100});
	});
}

function getGrid() {
	var colunms = Goodscf.initColumn();
    var table = new TreeTable(Goodscf.id, baseUrl + '/shop/goodscf/list?_' + $.now(), colunms);
    table.setExpandColumn(2);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("pid");
    table.setExpandAll(false);
    table.setHeight($(window).height()-100);
    table.init();
    Goodscf.table = table;
}

var vm = new Vue({
	el:'#goodscfListVue',
	methods : {
		load: function() {
			Goodscf.table.refresh();
		},
		add: function() {
			dialogOpen({
				title: '新增商品类别',
				url: baseUrl + '/v/bar/goodscf/add.html?_' + $.now(),
				width: '600px',
				height: '500px',
				scroll : true,
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				},
			});
		},
		edit: function() {
			var ck = Goodscf.table.getSelectedRow();
			if(checkedRow(ck)){
				dialogOpen({
					title: '编辑商品类别',
					url: baseUrl + '/v/bar/goodscf/edit.html?_' + $.now(),
					width: '600px',
					height: '500px',
					scroll : true,
					success: function(iframeId){
						top.frames[iframeId].vm.goodscf.goodscfId = ck[0].id;
						top.frames[iframeId].vm.setForm();
					},
					yes : function(iframeId) {
						top.frames[iframeId].vm.acceptClick();
					},
				});
			}
		},
		remove: function() {
			var ck = Goodscf.table.getSelectedRow(), ids = [];
			if(checkedArray(ck)){
				$.each(ck, function(idx, item){
					ids[idx] = item.id;
				});
				$.RemoveForm({
					url: baseUrl + '/shop/goodscf/remove?_' + $.now(),
			    	param: ids,
			    	success: function(data) {
			    		vm.load();
			    	}
				});
			}
		}
	}
})

var Goodscf = {
    id: "dataGrid",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Goodscf.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true,align: 'center', valign: 'middle'},
        {title: 'ID', field: 'id', visible: false,hidden:true, align: 'center', valign: 'middle', width: '50px'},
        {title: '名称', field: 'name', align: 'center', valign: 'middle', width: '150px'},
        {title: '上级商品类别', field: 'pname', align: 'center', valign: 'middle', width: '150px'},
        {
			field : "image",
			title : "图片",
			width : "150px",
			height : "100px",
			align : 'center', valign: 'middle',
			formatter : function(value, row, index){
				if(value.pid == ''){
					return '';
				}
				return '<img style="width:100px;height:100px" src="'+value.imageUrl+'" />'
			}
		}, 
		{title: '排序', field: 'rank', align: 'center', valign: 'middle', width: '60px'},
        {title: '备注', field: 'remark', align: 'center', valign: 'middle', width: '500px'}
        ]
    return columns;
};
