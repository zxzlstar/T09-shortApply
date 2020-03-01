/**
 * 商品管理js
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
		url: baseUrl + '/shop/goods/list?_' + $.now(),
		height: $(window).height()-54,
		queryParams: function(params){
			params.name = vm.keyword;
			params.status = vm.status;
			return params;
		},
		columns: [{
			checkbox: true
		}, {
			field : "id",
			title : "编号",
			visible:false
		}, {
			field : "number",
			title : "商品编号",
			width : "150px",align: 'center', valign: 'middle'
		}, {
			field : "name",
			title : "商品名称",
			width : "200px",align: 'center', valign: 'middle'
		}, {
			field : "classify",
			title : "商品类型",
			width : "100px",align: 'center', valign: 'middle',
			formatter: function(value,row,index){
				return value.pname + "_"+ value.name;
			}
		}, {
			field : "pictureUrl",
			title : "图片",
			width : "150px",
			height : "100px",
			align : 'center',
			formatter : function(value, row, index){
				if(value == ''){
					return '暂无图片';
				}
				return '<img style="width:100px;height:100px" src="'+value+'" />'
			}
		}, {
			field : "shareDesc",
			title : "分享描述",
			width : "200px",align: 'center', valign: 'middle'
		}, {
			field : "price",
			title : "商品价格",
			width : "70px",align: 'center', valign: 'middle'
		}, {
			field : "originalPrice",
			title : "原价",
			width : "70px",align: 'center', valign: 'middle'
		},{
			field : "stock",
			title : "库存",
			width : "120px",align: 'center', valign: 'middle'
		},{
			field : "sales",
			title : "销量",
			width : "120px",align: 'center', valign: 'middle'
		},{field: 'status',title: '商品状态', align: 'center', valign: 'middle', width: '120px', formatter: function(value, row, index){
            if(value == '1'){
                return '<i class="fa" style="color:red">未上架</i>';
            }else if(value == '2'){
                return '<i class="fa" style="color:green">已上架</i>';
            }
        }}]
	})
}

var vm = new Vue({
	el:'#goodsListVue',
	data: {
		keyword: null,
		status: null
	},
	methods : {
		load: function() {
			$('#dataGrid').bootstrapTable('refresh');
		},
		add: function() {
			dialogOpen({
				title: '新增商品',
				url: baseUrl + '/v/bar/goods/add.html?_' + $.now(),
		        scroll : true,
				width: '80%',
				height: '90%',
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				},
			});
		},
		edit: function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections');
			if(checkedRow(ck)){
				dialogOpen({
					title: '编辑商品',
					url: baseUrl + '/v/bar/goods/edit.html?_' + $.now(),
					scroll : true,
					width: '80%',
					height: '90%',
					success: function(iframeId){
						top.frames[iframeId].vm.goods.id = ck[0].id;
						top.frames[iframeId].vm.setForm();
					},
					yes: function(iframeId){
						top.frames[iframeId].vm.acceptClick();
					}
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
					url: baseUrl + '/shop/goods/remove?_' + $.now(),
			    	param: ids,
			    	success: function(data) {
			    		vm.load();
			    	}
				});
			}
		},
		stick: function(){
			var ck = $('#dataGrid').bootstrapTable('getSelections'), ids = [];	
			if(checkedArray(ck)){
				$.each(ck, function(idx, item){
					ids[idx] = item.id;
				});
				$.StickForm({
					url: baseUrl + '/shop/goods/stick?_' + $.now(),
			    	param: ids,
			    	success: function(data) {
			    		vm.load();
			    	}
				});
			}
		},
		ccelStick: function(){
			var ck = $('#dataGrid').bootstrapTable('getSelections'), ids = [];	
			if(checkedArray(ck)){
				$.each(ck, function(idx, item){
					ids[idx] = item.id;
				});
				$.CcelStickForm({
					url: baseUrl + '/shop/goods/ccelStick?_' + $.now(),
			    	param: ids,
			    	success: function(data) {
			    		vm.load();
			    	}
				});
			}
		},
		recommend: function(){
			var ck = $('#dataGrid').bootstrapTable('getSelections'), ids = [];	
			if(checkedArray(ck)){
				$.each(ck, function(idx, item){
					ids[idx] = item.id;
				});
				$.RecommendForm({
					url: baseUrl + '/shop/goods/recommend?_' + $.now(),
			    	param: ids,
			    	success: function(data) {
			    		vm.load();
			    	}
				});
			}
		},
		ccelRecommend: function(){
			var ck = $('#dataGrid').bootstrapTable('getSelections'), ids = [];	
			if(checkedArray(ck)){
				$.each(ck, function(idx, item){
					ids[idx] = item.id;
				});
				$.CcelRecommendForm({
					url: baseUrl + '/shop/goods/ccelRecommend?_' + $.now(),
			    	param: ids,
			    	success: function(data) {
			    		vm.load();
			    	}
				});
			}
		},
		sellIn: function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections'), ids = [];	
			if(checkedArray(ck)){
				$.each(ck, function(idx, item){
					ids[idx] = item.id;
				});
				$.SellInForm({
					url: baseUrl + '/shop/goods/sellIn?_' + $.now(),
			    	param: ids,
			    	success: function(data) {
			    		vm.load();
			    	}
				});
			}
		},
		sellOut: function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections'), ids = [];	
			if(checkedArray(ck)){
				$.each(ck, function(idx, item){
					ids[idx] = item.id;
				});
				$.SellOutForm({
					url: baseUrl + '/shop/goods/sellOut?_' + $.now(),
			    	param: ids,
			    	success: function(data) {
			    		vm.load();
			    	}
				});
			}
		}
	}
})