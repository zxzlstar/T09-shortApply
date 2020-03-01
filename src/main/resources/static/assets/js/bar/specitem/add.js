/**
 * 新增-规格项管理js
 */
var vm = new Vue({
	el:'#specitemAddVue',
	data: {
        specItem:{
            paramsValue: null
		},
	},
	methods : {
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    vm.specItem.paramsValue = getParamsValuesJson();
		    $.SaveForm({
		    	url: baseUrl + '/shop/specitem/save?_' + $.now(),
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





