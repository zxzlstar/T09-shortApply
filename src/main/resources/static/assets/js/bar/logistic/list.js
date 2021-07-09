/**
 * 物流管理js
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
		url: baseUrl + '/shop/logistic/list?_' + $.now(),
		height: $(window).height()-54,
		queryParams: function(params){
			params.company = vm.company;
			params.number = vm.number;
			params.senderPhone = vm.senderPhone;
			params.phoneEnd = vm.phoneEnd;
			return params;
		},
		detailView: true,
		detailFormatter: function(index, row) {
			var _html = '<div style="color: red;font-size: 15px;font-weight: bold">物流轨迹信息</div>';
				_html += '<table>';
				_html += '<tr><th width="80%">轨迹</th><th width="20%">时间</th></tr>';
				$.each(row.data, function (e, n) {
					_html += '<tr>'+ creatTd(n.context) + creatTd(n.timeCn) +'</tr>';
				});
				_html += '</table>';
			return _html;
		},
		columns: [{
			checkbox: true
		}, {
			field : "id",
			title : "编号",
			visible:false
		}, {
			field : "nu",
			title : "物流单号",
			width : "180px",
			align: 'center', valign: 'middle'
		}, {
			field : "senderPhone",
			title : "派送人电话",
			width : "180px",
			align: 'center', valign: 'middle'
		}, {
			field : "companyName",
			title : "物流商",
			width : "180px",
			align: 'center', valign: 'middle'
		}, {
			field : "stateCn",
			title : "状态",
			width : "60px",
			align: 'center', valign: 'middle'
		}]
	})
}

var vm = new Vue({
	el:'#logisticListVue',
	data: {
		number: null,
		senderPhone: null,
		company: "",
		phoneEnd: null
	},
	computed : {
	 	options : function () {
	 		var array =[];
			//获取属性列表
			$.ajax({
				type:'post',
				async : false,
				url: baseUrl + "/shop/logistic/loadExpressCompany",
				dataType : "json",
				success : function(data) {
					if (data.code == '200') {
						array = data.data;
					}
				}
			});
			return array;
		}
	},
	methods : {
		load: function() {
			if (vm.number != "" && vm.number != null) {
				//获取属性列表
				$.ajax({
					type:'get',
					async : false,
					url: baseUrl + "/shop/logistic/isSFExpress?number=" + vm.number,
					dataType : "json",
					success : function(data) {
						if (data.code == '200') {
							dialogOpen({
								title: '手机尾号后四位',
								url: baseUrl + '/v/bar/logistic/phoneNum.html?_' + $.now(),
								scroll : true,
								width: '30%',
								height: '20%',
								yes : function(iframeId) {
									vm.phoneEnd = top.frames[iframeId].vm.phoneEnd;
									top.layer.close(top.layer.index);
									$('#dataGrid').bootstrapTable('refresh');
								},
							});
						} else {
							$('#dataGrid').bootstrapTable('refresh');
						}
					}
				});
			} else {
				$('#dataGrid').bootstrapTable('refresh');
			}
		}

	}
})

function creatTd(text) {
	var td = "<td style='border-color: #ccc;border-width: 0 1px 1px 0;border-style: dotted;margin: 0;padding: 0;'>"+ text +"</td>";
	return td;
}