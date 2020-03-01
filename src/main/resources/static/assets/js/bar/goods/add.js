/**
 * 新增-商品管理js
 */
var vm = new Vue({
	el:'#goodsAddVue',
	data: {
		goods:{
			classifyId:'',
			classifyName:'',
			picbfvd:'',
			type:1
		},
		goodscf:{
			pname:'', //上级名称
			pid:'',
			orderNum:0,
			icon: null
		}
	},
	methods : {
		goodsTree: function(){
		    dialogOpen({
				id: 'layerGoodsTree',
				title: '选择商品类型',
		        url: baseUrl + '/v/bar/goods/tree.html?_' + $.now(),
		        scroll : true,
		        width: "300px",
		        height: "500px",
		        yes : function(iframeId) {
		        	top.frames[iframeId].vm.acceptClick();
				}
		    })
		},
		removeParent:function(){
			vm.goodscf.pid = '';
			vm.goodscf.pname = '';
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
 			vm.goods.particulars = editor1.html();
 			if(vm.goods.status){
 				vm.goods.status = 2;
 			}else{
 				vm.goods.status = 1;
 			}
 			var goodsAndSpec = {};
 			if(vm.goods.isSpecification){
 				var params = packageSpecitemTable();
 				if(params.length == 0){
 					alert("请添加商品规格属性参数");
 					return;
 				}
 				vm.goods.isSpecification = 2;
 				goodsAndSpec.spec = params;
 			}else{
 				vm.goods.isSpecification = 1;
 			}
 			goodsAndSpec.goods = vm.goods;
		    $.SaveForm({
		    	url: baseUrl + '/shop/goods/save?_' + $.now(),
		    	param: goodsAndSpec,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		},
        selectSku: function(){
			dialogOpen({
				id:'skuTree',
				title: '选取规格项',
				url: baseUrl + '/v/bar/goods/selectSku.html?_' + $.now(),
				scroll : true,
				width: "300px",
				height: "450px",
				success: function(iframeId){
				//	top.frames[iframeId].vm.specitem.id = ck[0].id;
					top.frames[iframeId].vm.getSku();
				},
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				}
			});
        }
	},
	created : function() {
		//获取属性列表
		$.ajax({
			 type:'GET',
			 url: baseUrl + "/shop/specitem/listall",
			 dataType : "json",
			 success : function(data) {
				 if (data.code == '200') {
					 $.each(data.data, function(i, item){
						 $("#itemlist").append("<option value='"+item.paramsName+"'>"+item.paramsName+"</option>");
					 }); 
				 }else if(data.code == '400'){
					 
				 }
			 }
		});
	}
})


//图片上传
function uploadImage(){
	$.upload({
		selector: '#imageFile',
		url: baseUrl + '/basic/upload/file?_' + $.now(),
		onMessage:function(msg){
			layer.msg(msg ,2);
		},
		onComplete:function(data){
			vm.goods.picbfvd = data.data.file;
			$("#imageView").parent('div').css('display','block');
			$("#imageView").attr('src' , data.data.imageUrl);
			$("#imageView").show();
		}
	});
}

//开启商品规格事件
$("#goodsSpec").click(function(){
	if(vm.goods.isSpecification){
		$("#goodspecTr").show();
		$("#goodStockShow").show();
		$("#goods_price").hide();
		$("#goods_originalPrice").hide();
		$("#goods_stock").hide();
		$("#goods_price").find("input").attr("checkexpession","DoubleOrNull");
		$("#goods_originalPrice").find("input").attr("checkexpession","DoubleOrNull");
		$("#goods_stock").find("input").attr("checkexpession","DoubleOrNull");
	}else{
		$("#goodspecTr").hide();
        $("#goodStockShow").hide();
		$("#goods_price").show();
		$("#goods_originalPrice").show();
		$("#goods_stock").show();
		$("#goods_price").find("input").attr("checkexpession","Double");
		$("#goods_originalPrice").find("input").attr("checkexpession","Double");
		$("#goods_stock").find("input").attr("checkexpession","Double");
	}
});


//选择商品的时候，要先确认是否已经选择了商品类型
$("#selectGoods").click(function(){
	if(vm.goodscf.classifyName == ''){
		layer.open({
			  type: 4, 
			  content: ['不能为空','#selectGoodscf'],
			  shade: 0,
			  tips: [3, '#B94A48'],
			  closeBtn: 0,
			  time: 1000
			});
	}
});


//添加商品规格
function addSku(){
	$("#skuForm").append(createSkuName());
	var tr_sku_value = createTr("sku_value");
    createSkuValueTitle(tr_sku_value);
    createSkuValueTd(tr_sku_value);
    $("#skuForm").append(tr_sku_value);
}

//创建规格项
function createSkuName(){
    //规格项名称
    var tr_sku_name = createTr("sku_name");
    var td_formTitle = createTd("formTitle","参数名：");
    var td_formValue = createTd("formValue",createInput("参数名"));
    var td_a_span = createTd("",createA("glyphicon-trash","del_tr"));
    tr_sku_name.append(td_formTitle);
    tr_sku_name.append(td_formValue);
    tr_sku_name.append(td_a_span);
    return tr_sku_name;
}

//创建规格项值表单以及增加和删除按钮
function createSkuValueTd(tr_obj){
	var td_formValue = createTd("formValue",createInput("参数值"));
	var td_a_span = createTd("",createA("glyphicon-plus","add_td")).append(createA("glyphicon-trash","del_td")).css("padding-right","10px");
	$(tr_obj).append(td_formValue).append(td_a_span);
}

//创建规格项值的标题
function createSkuValueTitle(tr_obj){
    var td_formTitle = createTd("formTitle","参数值：");
    $(tr_obj).prepend(td_formTitle);
}


//创建tr
function createTr(className){
 	var tr = $("<tr></tr>").addClass(className);
 	return tr;
}

//创建td
function createTd(className,text){
    var td ;
    if(className != '') {
        td = $("<td></td>").addClass(className).html(text);
    }else{
        td = $("<td></td>").html(text);
	}
	return td;
}


//创建input
function createInput(placeholderName){
	var input = $("<input />").addClass("form-control").attr({"type":"text","placeholder":placeholderName});
	return input;
}

//创建a
function createA(className,type){
	var span = $("<span></span>").addClass("glyphicon").addClass(className);
    var a ;
	if(type == 'del_tr'){
        a = $("<a href='javascript:void(0)'></a>").attr("onclick","delTr(this)");
	}else if(type == 'del_td'){
        a = $("<a href='javascript:void(0)'></a>").attr("onclick","delTd(this)");
	}else{
        a = $("<a href='javascript:void(0)'></a>").attr("onclick","addTd(this)");
	}
	a.append(span);
	return a;
}

//删除规格项
function delTr(e){
	var len = $("#skuForm").find(".sku_name").length;
	if(len > 1) {
        $(e).parent().parent().next().remove();
        $(e).parent().parent().remove();
    }
}

//删除规格项值
function delTd(e){
    var len = $(e).parent().parent().find(".formValue").length;
    if(len>1) {
        $(e).parent().prev().remove();
        $(e).parent().remove();
    }
}

//添加规格项值
function addTd(e){
    var td_formValue = createTd("formValue",createInput("参数值"));
    var td_a_span = createTd("",createA("glyphicon-plus","add_td")).append(createA("glyphicon-trash","del_td")).css("padding-right","10px");
    $(e).parent().after(td_a_span).after(td_formValue);
}

//生成规格明细表
function generateSku(){
    $("#goodStockShow").show();
	var table = $(".generateTable");
	table.empty();
	var lv1Arr = getFieldName();

	var width_one = 100/(lv1Arr.length+2);
    width_one = width_one + "%";
    var arr_value =getFieldValue();
	//
	//组合出所有的情况

    var tableHTML = '';
    tableHTML += '<table class="table table-bordered">';
    tableHTML += '    <thead>';
    tableHTML += '        <tr>';
    for (var i = 0; i < lv1Arr.length; i++) {
        tableHTML += '<th width="50" style="text-align: center">' + lv1Arr[i] + '</th>';
    }
    tableHTML += '            <th width="20" style="text-align: center">价格（元）</th>';
    tableHTML += '            <th width="20" style="text-align: center">库存</th>';
    tableHTML += '        </tr>';
    tableHTML += '    </thead>';
    tableHTML += '    <tbody>';

    var numsArr = new Array();
    var idxArr = new Array();
    for (var i = 0; i < arr_value.length; i++) {
        numsArr.push(arr_value[i].length);
        idxArr[i] = 0;
    }

    var len = 1;
    var rowsArr = new Array();
    for (var i = 0; i < numsArr.length; i++) {
        len = len * numsArr[i];

        var tmpnum = 1;
        for (var j = numsArr.length - 1; j > i; j--) {
            tmpnum = tmpnum * numsArr[j];
        }
        rowsArr.push(tmpnum);
    }

    for (var i = 0; i < len; i++) {
        tableHTML += '        <tr data-row="' + (i+1) + '">';
        var name = '';
        for (var j = 0; j < lv1Arr.length; j++) {
            var n = parseInt(i / rowsArr[j]);
            if (j == 0) {
            } else if (j == lv1Arr.length - 1) {
                n = idxArr[j];
                if (idxArr[j] + 1 >= numsArr[j]) {
                    idxArr[j] = 0;
                } else {
                    idxArr[j]++;
                }
            } else {
                var m = parseInt(i / rowsArr[j]);
                n = m % numsArr[j];
            }

            var text = arr_value[j][n];
            if (j != lv1Arr.length - 1) {
                name += text + '_';
            } else {
                name += text;
            }
            if (i % rowsArr[j] == 0) {
                tableHTML += '<td width="'+width_one+'" style="text-align: center;display: table-cell;vertical-align: middle" rowspan="' + rowsArr[j] + '" data-rc="' + (i+1) + '_' + (j+1) + '">' + text + '</td>';
            }
        }

        tableHTML += '<td><input type="text" isvalid="yes" checkexpession="Double" class="form-control" name="price" /></td>';
        tableHTML += '<td><input type="text" isvalid="yes" checkexpession="Num" class="form-control" name="stock" /><input type="hidden" name="sku_item" class="form-control" value="'+name+'" /></td>';
        tableHTML += '</tr>';
    }
    tableHTML += '</tbody>';
    tableHTML += '</table>';

    table.html(tableHTML);


}

//获取所有的属性名
function getFieldName(){
	var arr = [];
	$("#skuForm").find(".sku_name").each(function(){
		arr.push($(this).find("input").val());
	});
	return arr;
}


//获取所有的规格值
function getFieldValue(){
	var arr = [];
    $("#skuForm").find(".sku_value").each(function(index,e){
    	var arr_item = [];
		$(e).find("input").each(function (lg,f) {
			if($.trim($(f).val()) != '')
            arr_item.push($.trim($(f).val()));
        });
		arr.push(arr_item);
    });
    return arr;
}

//封装所有的商品规格库存参数表
function packageSpecitemTable(){
	var arr = [];

	//获取规格表中的表头名
	var name =[];
	$(".table-bordered thead").find("th").each(function(index,e){
		name.push($(e).text());
	});

	$("input[name='sku_item']").each(function(index,e){
        var obj = new Object();
        obj.price = $(e).parent().prev().children().val();
        obj.stock = $(e).prev().val();
		var rowsValue = $(e).val();
		var dataRow = rowsValue.split("_");
        var obj2 = new Object();
		$.each(dataRow,function (i,j) {
            obj2[name[i]] = dataRow[i];
        });
		obj.paramsValue = obj2;
		obj.goodsName = vm.goods.name;
		obj.status = 1;
        arr.push(obj);
	});

	return JSON.stringify(arr);
}