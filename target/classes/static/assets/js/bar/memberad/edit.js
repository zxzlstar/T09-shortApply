/**
 * 编辑-会员广告图js
 */
var vm = new Vue({
	el:'#memberadEditVue',
	methods : {
		setForm: function() {	
			$.ajax({
				url: baseUrl+'/shop/parametercf/bgpicture?_' + $.now(),
				type: 'GET',
				success: function(data){
					setFormData(data);
				}
			})
		},
		acceptClick: function(){
			if (!$('#form').Validform()) {
		        return false;
		    }
			//生成广告图需要的参数
			var object_param = new Object();
			getParamsData(object_param);
			$.ConfirmForm({
		    	url: baseUrl+'/shop/advertising/make?_' + $.now(),
		    	param: object_param,
		    	success: function(data) {
		    		
		    	}
		    });
		}
	}
})

//二维码位置更换
function changeqrcode(){
	var x = $("#qrcodeX").val();
	var y = $("#qrcodeY").val();
	if(!isNaN(x) && !isNaN(y) && x != '' && y != ''){
		if(x<=200 && x>0 && y<=200 && y>0){
			$("#qrcode").css("width", x); 
			$("#qrcode").css("height", y);
			$("#newqrcode").css("width", x); 
			$("#newqrcode").css("height", y);
			positonfunction();
		}
		if(x>200){
			$("#qrcodeX").val(200); 
			$("#qrcode").css("width", '200px'); 
			$("#newqrcode").css("width", '200px');
		}else if(x<1){
			$("#qrcodeX").val(1); 
			$("#qrcode").css("width", '1px'); 
			$("#newqrcode").css("width", '1px');
		}
		if(y>200){
			$("#qrcodeY").val(200);
			$("#qrcode").css("height", '200px'); 
			$("#newqrcode").css("height", '200px');
		}else if(y<1){
			$("#qrcodeY").val(1); 
			$("#qrcode").css("height", '1px'); 
			$("#newqrcode").css("height", '1px');
		}
	}
}

//会员头像的位置更换
function changetouxiang(){
	var xy = $("#touxiangXY").val();
	if(!isNaN(xy) && xy != ''){
		if(xy<=100 && xy>0){
			$("#touxiang").css("width", xy); 
			$("#touxiang").css("height", xy);
			$("#newtouxiang").css("width", xy); 
			$("#newtouxiang").css("height", xy);
			touxiangpositonfunction();
		}
		if(xy>100){
			$("#touxiangXY").val(100); 
			$("#touxiang").css("width", '100px'); 
			$("#touxiang").css("height", '100px'); 
			$("#newtouxiang").css("width", '100px');
			$("#newtouxiang").css("height", '100px');
		}else if(xy<1){
			$("#touxiangXY").val(1); 
			$("#touxiang").css("width", '1px'); 
			$("#touxiang").css("height", '1px');
			$("#newtouxiang").css("width", '1px');
			$("#newtouxiang").css("height", '1px');
		}
	}
}

function positonfunction(){
	var x = $("#positionX").val();
	var y = $("#positionY").val();
	var widthx = $("#qrcode").css("width");
	var heighty = $("#qrcode").css("height");
	var bgpicWidth = $("#bgpicture").css("width");
	var bgpicHeight = $("#bgpicture").css("height");
	if(!isNaN(x) && !isNaN(y) && x != '' && y != ''){
		if(x<= parseInt(bgpicWidth)-parseInt(widthx) && x>=0 && y<= parseInt(bgpicHeight)-parseInt(heighty) && y>=0){
			$("#newqrcode").css("left", x+"px");
			$("#newqrcode").css("top", y+"px");
		}
		
		if(x<0){
			$("#newqrcode").css("left", "0px");
			$("#positionX").val("0");
		}else if(x>parseInt(bgpicWidth)-parseInt(widthx)){
			$("#newqrcode").css("left", parseInt(bgpicWidth)-parseInt(widthx)+"px");
			$("#positionX").val(parseInt(bgpicWidth)-parseInt(widthx));
		}
		if(y<0){
			$("#newqrcode").css("top", "0px");
			$("#positionY").val("0");
		}else if(y>parseInt(bgpicHeight)-parseInt(heighty)){
			$("#newqrcode").css("top", parseInt(bgpicHeight)-parseInt(heighty)+"px");
			$("#positionY").val(parseInt(bgpicHeight)-parseInt(heighty));
		}
	}
}

function touxiangpositonfunction(){
	var x = $("#touxiangpositionX").val();
	var y = $("#touxiangpositionY").val();
	var widthx = $("#touxiang").css("width");
	var heighty = $("#touxiang").css("height");
	var bgpicWidth = $("#bgpicture").css("width");
	var bgpicHeight = $("#bgpicture").css("height");
	if(!isNaN(x) && !isNaN(y) && x != '' && y != ''){
		if(x<= parseInt(bgpicWidth)-parseInt(widthx) && x>=0 && y<= parseInt(bgpicHeight)-parseInt(heighty) && y>=0){
			$("#newtouxiang").css("left", x+"px");
			$("#newtouxiang").css("top", y+"px");
		}
		
		if(x<0){
			$("#newtouxiang").css("left", "0px");
			$("#touxiangpositionX").val("0");
		}else if(x>parseInt(bgpicWidth)-parseInt(widthx)){
			$("#newtouxiang").css("left", parseInt(bgpicWidth)-parseInt(widthx)+"px");
			$("#touxiangpositionX").val(parseInt(bgpicWidth)-parseInt(widthx));
		}
		if(y<0){
			$("#newtouxiang").css("top", "0px");
			$("#touxiangpositionY").val("0");
		}else if(y>parseInt(bgpicHeight)-parseInt(heighty)){
			$("#newtouxiang").css("top", parseInt(bgpicHeight)-parseInt(heighty)+"px");
			$("#touxiangpositionY").val(parseInt(bgpicHeight)-parseInt(heighty));
		}
	}
}

function setFormData(data){
	if(data.data != null){
		$("#bgpicture").attr("src",data.data.imageUrl);
		$("#bgfile").val(data.data.remark);
		$("#advertise").val(data.data.val);
		if(data.data.qrcode_width != null){
			$("#qrcodeX").val(data.data.qrcode_width);
			$("#qrcodeY").val(data.data.qrcode_height);
			$("#qrcode").css("width", data.data.qrcode_width); 
			$("#qrcode").css("height", data.data.qrcode_height);
			$("#newqrcode").css("width", data.data.qrcode_width); 
			$("#newqrcode").css("height", data.data.qrcode_height);
		}
		if(data.data.qrcode_positionX != null){
			$("#positionX").val(data.data.qrcode_positionX);
			$("#positionY").val(data.data.qrcode_positionY);
			$("#newqrcode").css("left", data.data.qrcode_positionX+"px");
			$("#newqrcode").css("top", data.data.qrcode_positionY+"px");
		}
		if(data.data.touxiang_width_height != null){
			$("#touxiangXY").val(data.data.touxiang_width_height);
			$("#touxiangXY").val(data.data.touxiang_width_height);
			$("#touxiang").css("width", data.data.touxiang_width_height); 
			$("#touxiang").css("height", data.data.touxiang_width_height);
			$("#newtouxiang").css("width", data.data.touxiang_width_height); 
			$("#newtouxiang").css("height", data.data.touxiang_width_height);
		}
		if(data.data.touxiang_positionX != null){
			$("#touxiangpositionX").val(data.data.touxiang_positionX);
			$("#touxiangpositionY").val(data.data.touxiang_positionY);
			$("#newtouxiang").css("left", data.data.touxiang_positionX+"px");
			$("#newtouxiang").css("top", data.data.touxiang_positionY+"px");
		}
		if(data.data.istouxiang_in_qrcode != null){
			$("input[name='QRCODE_INCLUDE_WXTOUXIANG'][value='" + data.data.istouxiang_in_qrcode + "']").prop("checked", "checked");
		}else{
			$("input[name='QRCODE_INCLUDE_WXTOUXIANG'][value='0']").prop("checked", "checked");
		}
	}
}

function getParamsData(data){
	data.bgpicture_width = $("#bgpicture").css("width").replace('px','');
	data.bgpicture_height = $("#bgpicture").css("height").replace('px','');
	data.qrcode_positionX = $("#positionX").val();
	data.qrcode_positionY = $("#positionY").val();
	data.qrcode_width = $("#qrcodeX").val();
	data.qrcode_height = $("#qrcodeY").val();
	data.advertise = $("#advertise").val();
	data.touxiang_width_height = $("#touxiangXY").val();
	data.touxaing_positionX = $("#touxiangpositionX").val();
	data.touxaing_positionY = $("#touxiangpositionY").val();
	data.remark =$("#bgfile").val();
	data.istouxiang_in_qrcode = $("input[name='QRCODE_INCLUDE_WXTOUXIANG']:checked").val();
}


function selectFile1(){
	document.getElementById('uploadbtn1').style.display = 'none';
	$("#uploadbtn1").attr("value","上传");
	 var oFile = document.getElementById('testFile1').files[0];
	 var rFilter =  /[image]\/(jpg|jpeg|png|bmp)$/;
    if (!rFilter.test(oFile.type)) {
        document.getElementById('error1').style.display = 'block';
        return;
    }
    document.getElementById('error1').style.display = 'none';
    $("#uploadbtn1").show();
}

function uploadFile1(){
	$.upload({
		selector: '#testFile1',
		url: baseUrl + '/basic/upload/file?_' + $.now(),
		onComplete:function(data){
			var getFile = data.data.file;
			var oFile = document.getElementById('testFile1').files[0];
			$("#advertise").val(getFile);
			$("#bgfile").val(oFile.name);
			$("#uploadbtn1").attr("value","上传成功");
			$("#bgpicture").attr("src",data.data.imageUrl);
		}
	});
}

$(function(){ 
	vm.setForm();
});
