var imgSrc = []; //图片路径
var imgFile = []; //文件流
var imgName = []; //图片名字
var imgEditSrc = []; //编辑的图片路径
var oldpic = 0;
//选择图片
function imgUpload(obj) {
	var oInput = '#' + obj.inputId;
	var imgBox = '#' + obj.imgBox;
	var btn = '#' + obj.buttonId;
	$(oInput).on("change", function() {
		$("#succes").hide();
		var fileImg = $(oInput)[0];
		var fileList = fileImg.files;
		for(var i = 0; i < fileList.length; i++) {
			var imgSrcI = getObjectURL(fileList[i]);
			imgName.push(fileList[i].name);
			imgSrc.push(imgSrcI);
			imgFile.push(fileList[i]);
		}
		//显示上传按钮
		$("#btn").show();
		addNewContent(imgBox);
	})
	$(btn).on('click', function() {
		if(!limitNum(obj.num)){
			alert("只能上传10张图片");
		  	return false;
		}
		//用formDate对象上传
		var fd = new FormData($('#upBox')[0]);
		for(var i=0;i<imgFile.length;i++){
			console.log(imgFile[i]);
			fd.append(obj.data,imgFile[i]);
		}
		
		submitPicture(obj.upUrl, fd);
	})
}
//图片展示
function addNewContent(obj) {
	$(imgBox).html("");
	for(var a = 0; a < imgEditSrc.length; a++) {
		var oldBox = $(obj).html();
		$(obj).html(oldBox + '<div class="imgEditContainer"><img src="'+imgEditSrc[a]+'" onclick="imgDisplay(this)"><p onclick="removeEditImg(this,' + a + ')" class="imgDelete">删除</p></div>');
	}
	for(var b = oldpic; b < imgSrc.length; b++) {
		var oldBox = $(obj).html();
		$(obj).html(oldBox + '<div class="imgContainer"><img title=' + imgName[b] + ' alt=' + imgName[b] + ' src=' + imgSrc[b] + ' onclick="imgDisplay(this)"><p onclick="removeImg(this,' + b + ')" class="imgDelete">删除</p></div>');
	}
	if(imgEditSrc.length+imgSrc.length == 0){
		$("#btn").hide();
		$("#inputBox").show();
		$("#succes").hide();
	}
}
//删除
function removeImg(obj, index) {
	imgSrc.splice(index, 1);
	imgFile.splice(index, 1);
	imgName.splice(index, 1);
	var boxId = "#" + $(obj).parent('.imgContainer').parent().attr("id");
	var templateSrc = [];
	templateSrc = vm.goods.picture.split(";");
	templateSrc.splice(imgEditSrc.length+index, 1);
	var imgEditStr = templateSrc.join(";");
	vm.goods.picture = imgEditStr;
	addNewContent(boxId);
}
//限制图片个数
function limitNum(num){
	if(!num){
		return true;
	}else if(imgFile.length+imgEditSrc.length>num){
		return false;
	}else{
		return true;
	}
}

//上传(将文件流数组传到后台)
function submitPicture(url,data) {
    for (var p of data) {
	  	console.log(data);
	}
	if(url&&data){
		$.ajax({
			type: "post",
			url: url,
			async: true,
			data: data,
			processData: false,
			contentType: false,
			success: function(dat) {
				var pict = vm.goods.picture;
				for(var i=0;i<dat.data.files.length;i++){
					if(pict != ""){
						pict += ";"
					}
					pict += dat.data.files[i];
				}
				vm.goods.picture = pict;
				$("#picture").val(pict);
				
				//上传成功之后，按钮禁用并显示请选择图片
				$("#btn").hide();
				$("#inputBox").hide();
				$("#succes").show();
			}
		});
	}else{
	  alert('请打开控制台查看传递参数！');
	}
}
//图片灯箱
function imgDisplay(obj) {
	var src = $(obj).attr("src");
	var imgHtml = '<div style="width: 100%;height: 100vh;overflow: auto;background: rgba(0,0,0,0.5);text-align: center;position: fixed;top: 0;left: 0;z-index: 1000;"><img src=' + src + ' style="margin-top: 100px;width: 70%;margin-bottom: 100px;"/><p style="font-size: 50px;position: fixed;top: 30px;right: 30px;color: white;cursor: pointer;" onclick="closePicture(this)">×</p></div>'
	$('body').append(imgHtml);
}
//关闭
function closePicture(obj) {
	$(obj).parent("div").remove();
}

//图片预览路径
function getObjectURL(file) {
	var url = null;
	if(window.createObjectURL != undefined) { // basic
		url = window.createObjectURL(file);
	} else if(window.URL != undefined) { // mozilla(firefox)
		url = window.URL.createObjectURL(file);
	} else if(window.webkitURL != undefined) { // webkit or chrome
		url = window.webkitURL.createObjectURL(file);
	}
	return url;
}

//编辑显示图片
function showImg(){
	if(vm.goods.picture == ''){
		return;
	}
	imgEditSrc = vm.goods.pictureUrl.split(";");
	for(var i=0;i<imgEditSrc.length;i++){
		var oldBox = $("#imgBox").html();
		$("#imgBox").html(oldBox + '<div class="imgEditContainer"><img title="" alt="" src="'+imgEditSrc[i]+'" onclick="imgDisplay(this)"><p onclick="removeEditImg(this,' + i + ')" class="imgDelete">删除</p></div>');
	}
	
	//隐藏上传按钮
	$("#btn").hide();
}

//编辑删除图片
function removeEditImg(obj, index){
	imgEditSrc.splice(index, 1);
	var templateSrc = [];
	templateSrc = vm.goods.picture.split(";");
	templateSrc.splice(index, 1);
	var imgEditStr = templateSrc.join(";");
	vm.goods.picture = imgEditStr;
	var boxId = "#" + $(obj).parent('.imgEditContainer').parent().attr("id");
	addNewPic(boxId);
}

//编辑删除图片后显示剩下的图片
function addNewPic(obj){
	$(imgBox).html("");
	for(var a = 0; a < imgEditSrc.length; a++) {
		var oldBox = $(obj).html();
		$(obj).html(oldBox + '<div class="imgEditContainer"><img src="'+imgEditSrc[a]+'" onclick="imgDisplay(this)"><p onclick="removeEditImg(this,' + a + ')" class="imgDelete">删除</p></div>');
	}
	for(var b = 0; b< imgSrc.length; b++){
		var oldBox = $(obj).html();
		$(obj).html(oldBox + '<div class="imgContainer"><img title=' + imgName[b] + ' alt=' + imgName[b] + ' src=' + imgSrc[b] + ' onclick="imgDisplay(this)"><p onclick="removeImg(this,' + a + ')" class="imgDelete">删除</p></div>');
	}
	if(imgEditSrc.length+imgSrc.length == 0){
		$("#btn").hide();
		$("#inputBox").show();
		$("#succes").hide();
	}
}