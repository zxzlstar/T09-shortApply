/**
 * 编辑-通用字典js
 */
var vm = new Vue({
	el:'#dictEditVue',
	data: {
		dict:{
			pname: '',
			pid: '',
			type: 1,
			orderNum: 0,
			status: 1
		}
	},
	methods : {
		dictTree: function(){
		    dialogOpen({
				id: 'layerDictTree',
				title: '选择目录',
		        url:  baseUrl + '/v/basic/dict/tree.html?_' + $.now(),
		        scroll : true,
		        width: "300px",
		        height: "450px",
		        yes : function(iframeId) {
		        	top.frames[iframeId].vm.acceptClick();
				}
		    })
		},
		setForm: function() {
			$.SetForm({
				url: baseUrl + '/basic/dict/info?_' + $.now(),
		    	param: vm.dict.id,
		    	success: function(data) {
		    		vm.dict = data;
		    	}
			});
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.ConfirmForm({
		    	url: baseUrl + '/basic/dict/modify?_' + $.now(),
		    	param: vm.dict,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
