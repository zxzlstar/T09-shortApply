/**
 * 新增-通用字典js
 */
var vm = new Vue({
	el:'#dictAddVue',
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
		        url: baseUrl + '/v/basic/dict/tree.html?_' + $.now(),
		        scroll : true,
		        width: "300px",
		        height: "450px",
		        yes : function(iframeId) {
		        	top.frames[iframeId].vm.acceptClick();
				}
		    })
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.SaveForm({
		    	url: baseUrl + '/basic/dict/save?_' + $.now(),
		    	param: vm.dict,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
