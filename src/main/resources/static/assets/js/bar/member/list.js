/**
 * 会员管理js
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
		url: baseUrl + '/shop/member/list?_' + $.now(),
		height: $(window).height()-54,
		queryParams: function(params){
			params.nickname = vm.nickname;
			params.ctime = vm.ctime;
			params.ctime = $("#date").val();
			params.sex = vm.sex;
			params.invitation = vm.invitation;
			params.amount = vm.amount
			return params;
		},
		columns: [{
			checkbox: true
		}, {
			field : "id",
			title : "编号",
			visible:false
		}, {
			field : "headimgurl",
			title : "头像",
			width : "100px",
			align: 'center', valign: 'middle',
			formatter : function(value, row, index){
				if(value == ''){
					return '暂无图片';
				}
				return '<img style="width:30px;height:30px" src="'+value+'" />'
			}
		}, {
			field : "nickname",
			title : "用户昵称",
			width : "150px",
			align: 'center', valign: 'middle'
		},{
			field : "sex",
			title : "性别",
			width : "80px",
			align: 'center', valign: 'middle',
			formatter: function(value, row, index){
				if(value == 1){
					return '<span class="label label-success">男</span>';
				}else if(value == 2){
					return '<span class="label label-warning">女</span>';
				}else if(value == 0){
					return '<span class="label label-info">不确定</span>';
				}
			}
		}/*,{
			field : "mobile",
			title : "手机",
			width : "120px",
			height : "120px",
			align: 'center', valign: 'middle'
		}*/, {
			field : "ctime",
			title : "注册时间",
			width : "150px",
			align : 'center', valign: 'middle'
		}, {
			field : "country",
			title : "国家",
			width : "100px",
			align: 'center', valign: 'middle'
		},{
			field : "province",
			title : "省份",
			width : "100px",
			align : 'center', valign: 'middle'
		}, {
			field : "city",
			title : "城市",
			width : "100px",
			align: 'center', valign: 'middle'
		}, {
			field : "referrer",
			title : "推荐人",
			width : "60px",
			align: 'center', valign: 'middle'
		},{
			field : "invitation",
			title : "邀请码",
			width : "200px",
			align: 'center', valign: 'middle'
		},{
			field : "amount",
			title : "消费累计金额",
			width : "200px",
			align: 'center', valign: 'middle'
		},{
			field : "deal",
			title : "修改金额",
			width : "100px",
			align: 'center', valign: 'middle',
			formatter : function(value, row, index){
				var str = "";
					str += '<a v-if="hasPermission(bar:member:updateAmount)" class="btn btn-warning btn-xs" onclick="updateAmount(\''+row.id+'\')"><i class="fa fa-pencil-square-o"></i>&nbsp;修改金额</a>';
				return str;
			}
		},{
			field : "deal",
			title : "是否已关注",
			width : "100px",
			align: 'center', valign: 'middle',
			formatter : function(value, row, index){
				var str = "";
					str += '  <a class="btn btn-success btn-xs" onclick="searchSubscribe(\''+row.id+'\')"><i class="glyphicon glyphicon-search"></i>&nbsp;查看</a>';
				return str;
			}
		}]
	})
}

var vm = new Vue({
	el:'#memberListVue',
	data: {
		nickname: null,
		ctime: null,
		sex: null,
		invitation: null,
		amount: null
	},
	methods : {
		load: function() {
			$('#dataGrid').bootstrapTable('refresh');
		}
	}
})

//时间控件
laydate.render({
	  elem: '#date',
	  range: true
});

//修改金额
function updateAmount(id){
	dialogOpen({
		title: '配送信息',
		url: baseUrl + '/v/bar/member/updateAmount.html?_' + $.now(),
		width: '350px',
		height: '180px',
		scroll : true,
		success: function(iframeId){
			top.frames[iframeId].vm.member.id = id;
			top.frames[iframeId].vm.setForm();
		},
		yes : function(iframeId) {
			top.frames[iframeId].vm.acceptClick(id);
		},
	});
}

//查询是否关注了公众号
function searchSubscribe(id){
	var data = "id=" + id;
	$.ajax({
		type: "POST",
		url: baseUrl + '/shop/member/subscribe?_' + $.now(),
	    data: data,
	    dataType: "json",
	    success: function(result){
			if(result.code == 200){
				if(result.message == "yes"){
					dialogMsg("已关注", 'success');
				}else if(result.message == "no"){
					dialogMsg("未关注", 'error');
				}
			}else{
				dialogMsg("请求出错,无效的openId", 'error');
			}
		}
	});
}
