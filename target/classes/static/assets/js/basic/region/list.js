/**
 * 行政区域js
 */

$(function() {
	initialPage();
	getGrid();
});

function initialPage() {
	$("#treePanel").css('height', $(window).height()-54);
	$(window).resize(function() {
		$("#treePanel").css('height', $(window).height()-54);
		$('#dataGrid').bootstrapTable('resetView', {
			height : $(window).height() - 108
		});
	});
}

function getGrid() {
	$('#dataGrid').bootstrapTableEx({
		url : baseUrl + '/basic/region/list?_' + $.now(),
		height : $(window).height() - 108,
		queryParams : function(params) {
			params.name = vm.keyword;
			params.pcode = vm.pcode;
			return params;
		},
		pagination : false,
		columns : [ {
			checkbox : true
		}, {
			field : "code",
			title : "区域代码",
			width : "100px"
		}, {
			field : "name",
			title : "区域名称",
			width : "200px"
		}, {
			field : "layer",
			title : "层级",
			width : "60px",
			formatter : function(value, row, index) {
				if (value == 1) {
					return '<span class="label label-primary">省级</span>';
				}
				if (value == 2) {
					return '<span class="label label-success">地市</span>';
				}
				if (value == 3) {
					return '<span class="label label-warning">区县</span>';
				}
			}
		}, {
			field : "orderNum",
			title : "排序",
			width : "60px",
			align : "center",
		}, {
			field : "status",
			title : "可用",
			width : "60px",
			align : "center",
			formatter : function(value, row, index) {
				if (value == 0) {
					return '<i class="fa fa-toggle-off"></i>';
				}
				if (value == 1) {
					return '<i class="fa fa-toggle-on"></i>';
				}
			}
		}, {
			field : "remark",
			title : "备注"
		} ]
	})
}

var setting = {
	async : {
		enable: true,
		type: "get",
		url: baseUrl + "/basic/region/select",
		autoParam: ["code"],
		dataFilter:function(treeId , parentNode,responseData){
			return responseData.data ;
		}
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "code",
			pIdKey : "pcode",
			rootPId : "0"
		},
		key : {
			url : "nourl"
		}
	},
	callback : {
		onClick : function(event, treeId, treeNode) {
			vm.pcode = treeNode.code;
			vm.pname = treeNode.name;
			vm.load();
		}
	}
};
var ztree;

var vm = new Vue({
	el : '#regionListVue',
	data : {
		keyword : null,
		pcode : '0',
		pname : null
	},
	methods : {
		load : function() {
			$('#dataGrid').bootstrapTable('refresh');
		},
		getRegion : function(pcode) {
			$.get(baseUrl + '/basic/region/select', {code: pcode},function(r) {
				ztree = $.fn.zTree.init($("#regionTree"), setting, r.data);
			})
		},
		add : function() {
			dialogOpen({
				title : '新增区域',
				url : baseUrl + '/v/basic/region/add.html?_' + $.now(),
				width : '500px',
				height : '445px',
				success : function(iframeId) {
					top.frames[iframeId].vm.region.pcode = vm.pcode;
					top.frames[iframeId].vm.region.pname = vm.pname;
					top.frames[iframeId].vm.setForm();
				},
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				},
			});
		},
		edit : function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections');
			if (checkedRow(ck)) {
				dialogOpen({
					title : '编辑区域',
					url : baseUrl + '/v/basic/region/edit.html?_' + $.now(),
					width : '500px',
					height : '445px',
					success : function(iframeId) {
						top.frames[iframeId].vm.region.id = ck[0].id;
						top.frames[iframeId].vm.setForm();
					},
					yes : function(iframeId) {
						top.frames[iframeId].vm.acceptClick();
					}
				});
			}
		},
		remove : function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections'), ids = [];
			if (checkedArray(ck)) {
				$.each(ck, function(idx, item) {
					ids[idx] = item.id;
				});
				$.RemoveForm({
					url : baseUrl + '/basic/region/remove?_' + $.now(),
					param : ids,
					success : function(data) {
						vm.load();
					}
				});
			}
		}
	},
	created : function() {
		this.getRegion(this.pcode);
	}
})