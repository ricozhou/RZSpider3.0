$("#form-blogcomment-add").validate({
	rules:{
		xxxx:{
			required:true,
		},
	},
	submitHandler:function(form){
		add();
	}
});

function add() {
    _ajax_save(ctx + "blog/blogcomment/save", $('#form-blogcomment-add').serialize());
}