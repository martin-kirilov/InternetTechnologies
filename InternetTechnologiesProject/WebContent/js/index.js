$( document ).ready(function() {
	$.Mustache.options.domTemplateType="text/x-mustache-template";
	$.Mustache.addFromDom();
	i18n.init({ lng: "bg", fallbackLng: "bg"}, function() {
		$(document).trigger("onAjaxUpdateRequired");
	});
	
	
	
	$(document).on("click", "#button_showRemoveMessage", function() {
		var id = $(this).data('id');
		
		bootbox.confirm("Are you sure", function(result) {
			if (result) {
				executeRequest("api/hello/"+id, "DELETE", null, function(data) {
					$(document).trigger("onAjaxUpdateRequired");
				});
			}
		});
	});
	
	$(document).on('click', '#dialog-message-create' ,function() {
			var message = {
				text: $("#dialog_message").find("#text").val()
			};
			executeRequest("api/hello/add", "POST", message, function(data) {
				$(document).trigger("onAjaxUpdateRequired");
				$('#dialog_message').modal('hide');
			});

	});
	$(document).on('click', '#dialog-message-update' ,function() {
		var message = {
				id: $(this).data("id"),
				text: $("#dialog_message").find("#text").val()
			};

			executeRequest("api/hello/update", "PUT", message, function(data) {
				$(document).trigger("onAjaxUpdateRequired");
				$('#dialog_message').modal('hide');
			});
	});
	
});
