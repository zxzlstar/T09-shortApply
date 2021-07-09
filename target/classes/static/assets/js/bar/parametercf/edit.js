/**
 * 编辑-参数配置js
 */
var vm = new Vue({
	el:'#parametercfEditVue',
	data: {
		MERCH_GOODS_AUTO_CHECK:0,
		MEMBER_WITHDRAW_AUTO_CHECK:0,
		MERCH_WITHDRAW_AUTO_CHECK:0,
		CLERK_WITHDRAW_AUTO_CHECK:0,
		GUESS_GOODS_OVERDUE:0,
		POSITION_AUTO_CHECK:0,
		SUBJECT_AUTO_CHECK:0
	},
	methods : {
		setForm: function() {
			$.SetForm({
				url: baseUrl+'/shop/parametercf/list?_' + $.now(),
		    	success: function(data) {
		    		setFormData(data);
		    	}
			});
		},
		acceptClick: function(){
			if (!$('#form').Validform()) {
		        return false;
		    }
			var paramcflist = getFormData();
			paramcflist = getParam(paramcflist,"MERCH_GOODS_AUTO_CHECK",vm.MERCH_GOODS_AUTO_CHECK);
			paramcflist = getParam(paramcflist,"MEMBER_WITHDRAW_AUTO_CHECK",vm.MEMBER_WITHDRAW_AUTO_CHECK);
			paramcflist = getParam(paramcflist,"MERCH_WITHDRAW_AUTO_CHECK",vm.MERCH_WITHDRAW_AUTO_CHECK);
			paramcflist = getParam(paramcflist,"CLERK_WITHDRAW_AUTO_CHECK",vm.CLERK_WITHDRAW_AUTO_CHECK);
			paramcflist = getParam(paramcflist,"POSITION_AUTO_CHECK",vm.POSITION_AUTO_CHECK);
			paramcflist = getParam(paramcflist,"SUBJECT_AUTO_CHECK",vm.SUBJECT_AUTO_CHECK);
			$.ConfirmForm({
		    	url: baseUrl+'/bar/parametercf/edit?_' + $.now(),
		    	param: paramcflist,
		    	success: function(data) {
		    		/*$.currentIframe().vm.load();*/
		    		vm.setForm();
		    	}
		    });
		}
	}
})

function setFormData(listparams){
	for(var i = 0; i < listparams.length; i++){
		$("#"+listparams[i].name).val(listparams[i].val);
		$("."+listparams[i].name).attr("name",0);
		if(listparams[i].name == "WXPAY_CERT"){
			$("#"+listparams[i].name).siblings(".remark").val(listparams[i].remark);
		}else{
			$("#"+listparams[i].name).attr("placeholder",listparams[i].remark);
		}
		if(listparams[i].name == "MERCH_GOODS_AUTO_CHECK"){
			vm.MERCH_GOODS_AUTO_CHECK = listparams[i].val;
		}
		if(listparams[i].name == "MEMBER_WITHDRAW_AUTO_CHECK"){
			vm.MEMBER_WITHDRAW_AUTO_CHECK = listparams[i].val;
		}
		if(listparams[i].name == "MERCH_WITHDRAW_AUTO_CHECK"){
			vm.MERCH_WITHDRAW_AUTO_CHECK = listparams[i].val;
		}
		if(listparams[i].name == "CLERK_WITHDRAW_AUTO_CHECK"){
			vm.CLERK_WITHDRAW_AUTO_CHECK = listparams[i].val;
		}
		if(listparams[i].name == "POSITION_AUTO_CHECK"){
			vm.POSITION_AUTO_CHECK = listparams[i].val;
		}
		if(listparams[i].name == "SUBJECT_AUTO_CHECK"){
			vm.SUBJECT_AUTO_CHECK = listparams[i].val;
		}
	}
}

function getFormData(){
	var allParams = new Array();
	$(".form-inputcontrol").each(function(){
		var parameter = {};
		parameter.name = $(this).attr("id");
		parameter.val = $(this).val();
		if($(this).attr("id") == "WXPAY_CERT"){
			parameter.remark = $(this).siblings(".remark").val();
		}/*else{
			parameter.remark = $(this).attr("placeholder");
		}*/
		allParams.push(parameter);
	});
	return allParams;
}

function getParam(allParams,name,val){
	var parameter = {};
	parameter.name = name;
	parameter.val = val;
	allParams.push(parameter);
	return allParams;
}

function selectFile(){
	document.getElementById('uploadbtn').style.display = 'none';
	$("#uploadbtn").attr("value","上传");
	 var oFile = document.getElementById('testFile').files[0];
	 var rFilter =  /application\/x-pkcs12/;
    if (!rFilter.test(oFile.type)) {
        document.getElementById('error').style.display = 'block';
        return;
    }
    document.getElementById('error').style.display = 'none';
    $("#uploadbtn").show();
}

function uploadFile(){
	$.upload({
		selector: '#testFile',
		url: baseUrl + '/basic/upload/file?_' + $.now(),
		onComplete:function(data){
			var getFile = data.data.file;
			var oFile = document.getElementById('testFile').files[0];
			$("#WXPAY_CERT").val(getFile);
			/*$("#wxfile").show();*/
			$("#wxfile").val(oFile.name);
			$("#uploadbtn").attr("value","上传成功");
		}
	});
}

$(function(){ 
	vm.setForm();
});
