/**
 * 编辑-广告管理js
 */
var vm = new Vue({
	el:'#advertiseEditVue',
	data: {
		advertise:{
		}
	},
	methods : {
		setForm: function() {
			$.SetForm({
				url: baseUrl+'/bar/advertise/info?_' + $.now(),
		    	param: vm.advertise.id,
		    	success: function(data) {
		    		vm.advertise = data;
		    		$("#imageView").attr("src",vm.advertise.imageUrl);
		    	}
			});
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
			if($('#datetime1').val()>=$('#datetime2').val()){
				layer.open({
					  type: 4, 
					  content: ['请重新选择大于发放起始日期的发放结束日期','#datetime2'],
					  shade: 0,
					  tips: [3, '#B94A48'],
					  closeBtn: 0,
					  time: 2500
					});
				return false;
			}
			vm.advertise.startTime = $('#datetime1').val(); 
			vm.advertise.endTime = $('#datetime2').val();
			
		    $.ConfirmForm({
		    	url: baseUrl+'/bar/advertise/modify?_' + $.now(),
		    	param: vm.advertise,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})


//时间控件
laydate.render({
	  elem: '#datetime1',
	  type: 'datetime',
	  format:'yyyy-MM-dd HH:mm',
	  theme:'#00a65a'
});

//时间控件
laydate.render({
	  elem: '#datetime2',
	  type: 'datetime',
	  format:'yyyy-MM-dd HH:mm',
	  theme:'#00a65a'
});

//图片上传
function uploadImage(){
	$.upload({
		selector: '#imageFile',
		url: baseUrl + '/basic/upload/file?_' + $.now(),
		onMessage:function(msg){
			layer.msg(msg ,2);
		},
		onComplete:function(data){
			vm.advertise.image = data.data.file;
			$("#imageView").parent('div').css('display','block');
			$("#imageView").attr('src' , baseUrl + '/basic/upload/images/' + data.data.file);
		}
	});
}