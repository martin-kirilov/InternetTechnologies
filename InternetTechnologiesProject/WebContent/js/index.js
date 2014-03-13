$("#button_send").click(function() {
    var toSend = {
    	latitude : $("#latitude").val(),
    	longitude : $("#longitude").val(),
    	address : $("#address").val(),
    	message : $("#message").val()
    };
    
    return $.ajax({
    	url: "api/Rest/add",
    	type: "POST",
    	data: JSON.stringify(toSend),
    	contentType: 'application/json; charset=utf-8',
    	dataType: 'json'
    });
});

$("#button_getAll").click(function() {
	return $.ajax({
		url: "api/Rest/getAll",
		type: "GET",
		contentType: 'application/json; charset=utf-8',
		dataType: 'json'
	});
});