$("#form-blogbrowe-details").validate({
	rules : {
		xxxx : {
			required : true,
		},
	},
	submitHandler : function(form) {
		update();
	}
});

function update() {
	_ajax_save(ctx + "blog/blogbrowe/save", $('#form-blogbrowe-details')
			.serialize());
}