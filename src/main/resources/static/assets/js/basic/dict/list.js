/**
 * 通用字典js
 */

$(function () {
	initialPage();
	getGrid();
});

function initialPage() {
	$(window).resize(function() {
		Dict.table.resetHeight({height: $(window).height()-100});
	});
}

function getGrid() {
	var colunms = Dict.initColumn();
    var table = new TreeTable(Dict.id, baseUrl + '/basic/dict/list?_' + $.now(), colunms);
    table.setExpandColumn(1);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("pid");
    table.setExpandAll(false);
    table.setHeight($(window).height()-100);
    table.init();
    Dict.table = table;
}

var vm = new Vue({
	el:'#dictListVue',
	methods : {
		load: function() {
			Dict.table.refresh();
		},
		add: function() {
			dialogOpen({
				title: '新增字典',
				url: baseUrl + '/v/basic/dict/add.html?_' + $.now(),
				width: '600px',
				height: '420px',
				scroll : true,
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				},
			});
		},
		edit: function() {
			var ck = Dict.table.getSelectedRow();
			if(checkedRow(ck)){
				dialogOpen({
					title: '编辑字典',
					url: baseUrl + '/v/basic/dict/edit.html?_' + $.now(),
					width: '600px',
					height: '420px',
					scroll : true,
					success: function(iframeId){
						top.frames[iframeId].vm.dict.id = ck[0].id;
						top.frames[iframeId].vm.setForm();
					},
					yes : function(iframeId) {
						top.frames[iframeId].vm.acceptClick();
					},
				});
			}
		},
		remove: function() {
			var ck = Dict.table.getSelectedRow(), ids = [];
			if(checkedArray(ck)){
				$.each(ck, function(idx, item){
					ids[idx] = item.id;
				});
				$.RemoveForm({
					url: baseUrl + '/basic/dict/remove?_' + $.now(),
			    	param: ids,
			    	success: function(data) {
			    		vm.load();
			    	}
				});
			}
		}
	}
})

var Dict = {
    id: "dataGrid",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Dict.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '参数名', field: 'name', align: 'center', valign: 'middle', width: '180px'},
        {title: '参数值', field: 'value', align: 'center', valign: 'middle', width: '180px'},
        {title: '所属类别', field: 'pname', align: 'center', valign: 'middle', width: '100px'},
        {title: '类型', field: 'type', align: 'center', valign: 'middle', width: '60px', formatter: function(item, index){
        	 if(item.type === 0){
                 return '<span class="label label-primary">目录</span>';
             }
             if(item.type === 1){
                 return '<span class="label label-warning">参数</span>';
             }
        }},
        {title: '排序', field: 'orderNum', align: 'center', valign: 'middle', width: '50px'},
        {title: '可用', field: 'status', align: 'center', valign: 'middle', width: '60px', formatter: function(item, index){
            if(item.status === 0){
                return '<i class="fa fa-toggle-off"></i>';
            }
            if(item.status === 1){
                return '<i class="fa fa-toggle-on"></i>';
            }
        }},
        {title: '备注', field: 'remark', align: 'left', valign: 'middle'}
    ]
    return columns;
};