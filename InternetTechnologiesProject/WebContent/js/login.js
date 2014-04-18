
$("#button_register_user").click(function() {
	showRegisterDialog("", {
		"Create": function() {
			var dialog = $(this);
			var toSend = {
				username: $("#username").val(),
				password: $("#password").val(),
				role: "USER"
			};
			$.ajax({
				url: "api/Rest/register",
		    	type: "POST",
		    	data: JSON.stringify(toSend),
		    	contentType: 'application/json; charset=utf-8',
		    	dataType: 'json'
			}).done(function() {
				dialog.dialog('close');
			});
		}
	});
});

function showRegisterDialog(text, buttons) {
	$("#register_dialog").dialog({
		modal: true,
		buttons: buttons
	}).show();
}