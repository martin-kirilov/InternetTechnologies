var id;

$("#button_send").click(function() {
    var toSend = {
    	latitude : $("#latitude").val(),
    	longitude : $("#longitude").val(),
    	address : $("#address").val(),
    	message : $("#message").val()
    };
    
    var jqXHR =  $.ajax({
    	url: "api/Rest/add",
    	type: "POST",
    	data: JSON.stringify(toSend),
    	contentType: 'application/json; charset=utf-8',
    	dataType: 'json',
    	async: false
    });
    alert(jQuery.parseJSON(jqXHR.responseText).id);
    id = jQuery.parseJSON(jqXHR.responseText).id;
    return jqXHR;
});


$("#button_getAll").click(function() {
	return getAllMessages();
});

(function($){
	$(document).ready(function(){
		$('#dragzone').bind('dragover',function(event){
			$(event.target).css('background-color','red');
			event.stopPropagation();
			event.preventDefault();  
		});
		$('#dragzone').bind('drop',function(event){
			$(event.target).css('background-color','white');
			$.FileUpload($(event.target).attr('path'),event.originalEvent.dataTransfer.files);
			event.stopPropagation();
			event.preventDefault();
		});
	});
})(jQuery);

$.FileUpload = function(path,files) {

	var file = files.item(0);
	var xhr = new XMLHttpRequest();
	
	fileUpload = xhr.upload,
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4) {
			response = $.parseJson(xhr.responseText);
			alert(response.status);
		}
	};

	xhr.open("POST", 'api/Rest/' + id + '/image', true);
	xhr.setRequestHeader("Cache-Control", "no-cache");
	xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
	xhr.setRequestHeader("X-File-Name", encodeURIComponent(file.fileName));
	xhr.setRequestHeader("X-File-Size", file.fileSize);
	xhr.setRequestHeader("Content-Type", "application/octet-stream");
	xhr.send(file);
};

function getAllMessages() {
	return $.ajax({
		url: "api/Rest/getAll",
		type: "GET",
		contentType: 'application/json; charset=utf-8',
		dataType: 'json'
	});
}

function renderComplaint(container, complaint) {
	var line = $("<tr><td>" + complaint.id + "</td><td>" + complaint.latitude + 
			"</td><td>" + complaint.longitude + "</td><td>" + complaint.address + 
			"</td><td>" + complaint.message + "</td><td>" + complaint.plateNumber + "</td></tr>");
	
	container.append(line);
}

function reloadList() {
	getAllMessages().done(function(data) {
		var container = $("#container_configuration");
		$.each(data, function() {
			renderComplaint(container, this);
		});
		
	});
}

reloadList();
