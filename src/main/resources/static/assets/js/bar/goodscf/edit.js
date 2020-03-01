/**
 * 编辑-商品类别js
 */
var vm = new Vue({
	el:'#goodscfEditVue',
	data: {
		goodscf:{
			pname:'' ,
			pid:'',
			orderNum:0,
			icon: null
		}
	},
	methods : {
		goodscfTree: function(){
			var id = vm.goodscf.id;
			debugger;
		    dialogOpen({
				id: 'layerGoodscfTree',
				title: '选择菜单',
		        url: baseUrl + '/v/bar/goodscf/tree.html?treeType=2&id='+id+'&_' + $.now(),
		        scroll : true,
		        width: "300px",
		        height: "450px",
		        yes : function(iframeId) {
		        	top.frames[iframeId].vm.acceptClick();
				}
		    });
		},
		setForm: function() {
			$.SetForm({
				url: baseUrl + '/shop/goodscf/info?_' + $.now(),
		    	param: vm.goodscf.goodscfId,
		    	success: function(data) {
		    		vm.goodscf = data;
		    		if(vm.goodscf.image != ""){
		    			$("#imageView").attr("src",vm.goodscf.imageUrl);
		    			$("#goodscfTree").val(vm.goodscf.image);
		    		}else{
		    			$("#imageView").hide();
		    		}
		    	}
			});
		},
		removeParent:function(){
			vm.goodscf.pid = '';
			vm.goodscf.pname = '';
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
			debugger;
			if(vm.goodscf.pid != ""){
				var imageFile = $("#goodscfTree").val();
				if(imageFile == ""){
					layer.open({
						  type: 4, 
						  content: ['不能为空','#imageFile'],
						  shade: 0,
						  tips: [3, '#B94A48'],
						  closeBtn: 0,
						  time: 1000
						});
					return false;
				}
			}
		    $.ConfirmForm({
		    	url: baseUrl + '/shop/goodscf/modify?_' + $.now(),
		    	param: vm.goodscf,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
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
			
			vm.goodscf.image = data.data.file;
			$("#imageView").parent('div').css('display','block');
			$("#imageView").attr('src' , data.data.imageUrl);
			$("#goodscfTree").val(data.data.imageUrl);
			$("#imageView").show();
		}
	});
}
