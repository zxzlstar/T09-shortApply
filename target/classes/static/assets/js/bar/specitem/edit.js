/**
 * 编辑-规格项管理js
 */
var vm = new Vue({
	el:'#specitemEditVue',
	data: {
        specItem:{
		}
	},
	methods : {
        setForm: function() {
            $.SetForm({
                url: baseUrl+'/shop/specitem/info?_' + $.now(),
                param: vm.specItem.id,
                success: function(data) {
                    vm.specItem = data;
                    //加载规格项的多个值
					loadSpecVals($.parseJSON(data.paramsValue));
                }
            });
        },
		acceptClick: function() {
            if (!$('#form').Validform()) {
                return false;
            }
            vm.specItem.paramsValue = getParamsValuesJson();
            $.ConfirmForm({
                url: baseUrl+'/shop/specitem/modify?_' + $.now(),
                param: vm.specItem,
                success: function(data) {
                    $.currentIframe().vm.load();
                }
            });
		}
	},
	created : function() {
		
	}
});


function loadSpecVals(val){
	var len =val.length;
	for (var i=0;i<len;i++){
		if(i>0){
			var obj = $("#signTop").prev().find(".glyphicon-plus").parent();
            valPlus(obj);

		}
        $("#signTop").prev().find(".paramsValue").val(val[i]);
	}
}

function getParamsValuesJson(){
	var arr =[];
	$(".paramsValue").each(function () {
		arr.push($(this).val());
    });
	return JSON.stringify(arr);
}


function createTd(type){
	var td =$("<td></td>").addClass(type);
	if(type =='formValue') {
        td.append(createInput());
        td.append(createButton(1));
        td.append(createButton(2));
    }
	return td;
}

function createInput(){
	var input = $("<input type='text' class='form-control'>").css({'width':'70%','display':'inline-block','margin-right':'2px'}).attr({"placeholder":"值","isvalid":"yes","checkexpession":"NotNull"});
	input.addClass("paramsValue");
	return input;
}

function createButton(type) {
	var button = $("<button></button>").addClass("btn btn-default btn-sm").attr("type","button").css("margin","0 2px");
	if(type == 1){ //添加按钮
        button.addClass("addButton").attr("onclick","valPlus(this)");
	}else{
		//删除按钮
        button.addClass("delButton").attr("onclick","valRemove(this)");
	}
    button.append(createSpan(type));
	return button;
}

function createSpan(type) {
	var span = $("<span></span>").addClass("glyphicon")
	if(type == 1){
		//添加图标
        span.addClass("glyphicon-plus");
	}else{
		//删除图标
        span.addClass("glyphicon-remove");
	}
	return span;
}

function valPlus(e){
    var tdTitle =  createTd('formTitle');
    var tdVal = createTd('formValue');
    var tr = $("<tr></tr>");
    tr.append(tdTitle);
    tr.append(tdVal);
	$(e).parent().parent().after(tr);
}

function valRemove(e){
    $(e).parent().parent().remove();
}





