/**
 * 新增-商品类别js
 */
var vm = new Vue({
	el:'#goodscfAddVue',
	data: {
		goodscf:{
			pname:'', //上级名称
			pid:'',
			orderNum:0,
			icon: null
		}
	},
	methods : {
		goodscfTree: function(){
		    dialogOpen({
				id: 'layerGoodscfTree',
				title: '选择上级商品类型',
		        url: '/v/bar/goodscf/tree.html??treeType=1&_' + $.now(),
		        scroll : true,
		        width: "300px",
		        height: "450px",
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
			if(vm.goodscf.pid != ""){
				var imageFile = $("#imageFile").val();
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
		    $.SaveForm({
		    	url: '/shop/goodscf/save?_' + $.now(),
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
			$("#imageView").show();
		}
	});
}
