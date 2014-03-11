function addComplaint(text) {
	return $.ajax({
		url: "api/rest/add",
		type: "POST",
		data: JSON.stringify({text: text}),
		contentType: 'application/json; charset=utf-8',
		dataType: 'json'
	});
}

        		
        		
function getAllMessages() {
	return $.ajax({
		url: "api/hello/test",
		type: "GET",
		contentType: 'application/json; charset=utf-8',
		dataType: 'json'
	});
}

