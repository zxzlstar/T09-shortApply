/**
 * 订单管理js
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
		url: baseUrl + '/shop/order/list?_' + $.now(),
		height: $(window).height()-54,
		queryParams: function(params){
			params.oid = vm.oid;
			params.orderType = vm.orderType;
			params.transportNo = vm.transportNo;
			params.dateStr = vm.dateStr;
			params.dateStr = $("#date").val();
			params.status = vm.status;
			params.merchId = $("#merchId").val();
			return params;
		},
		columns: [{
			checkbox: true
		}, {
			field : "id",
			title : "编号",
			visible:false
		}, {
			field : "oid",
			title : "订单号",
			width : "150px",
			align: 'center', valign: 'middle'
		},{
			field : "date",
			title : "下单日期",
			width : "120px",
			height : "120px",
			align: 'center', valign: 'middle'
		}, {
			field : "time",
			title : "下单时间",
			width : "150px",
			align : 'center', valign: 'middle'
		},{
			field : "payTime",
			title : "支付时间",
			width : "220px",
			align : 'center', valign: 'middle'
		}, {
			field : "price",
			title : "单价",
			width : "60px",
			align: 'center', valign: 'middle'
		}, {
			field : "quantity",
			title : "数量",
			width : "60px",
			align: 'center', valign: 'middle'
		}, {
			field : "amount",
			title : "订单金额",
			width : "60px",
			align: 'right', valign: 'middle'
		},{
			field : "message",
			title : "卖家留言",
			width : "200px",
			align: 'center', valign: 'middle'
		},{
			field : "orderType",
			title : "订单类型",
			align: 'center', valign: 'middle',
			formatter : function(value, row, index){
				if(value == 1){
					return '<span class="label label-warning">普通</span>';
				}else if(value == 2){
					return '<span class="label label-success">竞猜</span>';
				}
			}
		},{
			field : "status",
			title : "订单状态",
			align: 'center', valign: 'middle',
			formatter : function(value, row, index){
				if(value == 1){
					return '<span class="label label-warning">待付款</span>';
				}else if(value == 2){
					return '<span class="label label-success">已付款</span>';
				}else if(value == 3){
					return '<span class="label label-info">已兑换</span>';
				}else if(value == 4){
					return '<span class="label label-primary">未中奖</span>';
				}else if(value == 5){
					return '<span class="label label-default">中奖待兑换</span>';
				}else if(value == 6){
					return '<span class="label label-danger">兑换码已过期</span>';
				}else if(value == 7){
					return '<span class="label label-danger">部分兑换</span>';
				}			
			}
		},{
			field : "deal",
			title : "操作",
			width : "270px",
			align: 'center', valign: 'middle',
			formatter : function(value, row, index){
				var str = "";
				str += '  <a class="btn btn-success btn-xs" onclick="orderDetail(\''+row.id+'\')"><i class="glyphicon glyphicon-search"></i>&nbsp;详情</a>'
				return str;
			}
		}]
	})
}

var vm = new Vue({
	el:'#orderListVue',
	data: {
		oid: null,
		transportNo: null,
		dateStr: null,
		status: null,
		merchId: null,
		orderType: null
	},
	methods : {
		load: function() {
			$('#dataGrid').bootstrapTable('refresh');
		}
	}
})

//时间控件
laydate.render({
	  elem: '#date'
	  ,range: true
});

//查看详情
function orderDetail(id){
	
	dialogOpen({
		title: '订单详情信息',
		url: baseUrl + '/shop/order/info?id='+id,
		width: '80%',
		height: '80%',
		scroll : true,
		btn: [],
		success : function(iframeId) {
			
		}
	});
}


