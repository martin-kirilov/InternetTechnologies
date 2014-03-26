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
		}
	};

	xhr.open("POST", 'api/Rest/' + id + '/image', true);
	xhr.setRequestHeader("Cache-Control", "no-cache");
	xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
	xhr.setRequestHeader("X-File-Name", encodeURIComponent(file.fileName));
	xhr.setRequestHeader("X-File-Size", file.fileSize);
	xhr.setRequestHeader("Content-Type", "application/octet-stream");
	xhr.send(file);
	window.location.reload();
};

function deleteComplaint(complaintId) {
	return $.ajax({
		url: "api/Rest/deleteComplaint/" + complaintId,
		type: "DELETE",
		contentType: 'application/json; charset=utf-8',
		dataType: 'json'
	});
}

function createDeleteButton(complaint) {
	return $("<button>Delete</button>").click(function() {
		deleteComplaint(complaint.id).done(function() {
			reloadList();
		});
	});
}

$("#buttonDialogTest").click(function() {
	$(" #testDialog ").dialog({
		modal: true
	}).show();
});

function showUpdateDialog(data, buttons) {
	$("#latitudeUpdate").val(data.latitude);
	$("#longitudeUpdate").val(data.longitude);
	$("#addressUpdate").val(data.address);
	$("#messageUpdate").val(data.message);
	$("#plateNumberUpdate").val(data.plateNumber);
	$(" #updateDialog ").dialog({
		modal: true,
		buttons: buttons, 
	}).show();
}

function updateComplaint(complaint) {
	var toSend = {
			id : complaint.id,
	    	latitude : complaint.latitude,
	    	longitude : complaint.longitude,
	    	address : complaint.address,
	    	message : complaint.message,
	    	plateNumber : complaint.plateNumber
	};
	   
	return $.ajax({
		url: "api/Rest/updateComplaint",
		type: "POST",
		data: JSON.stringify(toSend),
		contentType: 'application/json; charset=utf-8',
		dataType: 'json',
		async: false
	});
}

function createUpdateButton(complaint) {
	var buttonUpdate = $("<button>Update</button>").click(function() {
		showUpdateDialog(complaint, {
			"Update": function() {
				var dialog = $(this);
				complaint.latitude = $("#latitudeUpdate").val();
				complaint.longitude = $("#longitudeUpdate").val();
				complaint.address = $("#addressUpdate").val();
				complaint.message = $("#messageUpdate").val();
				complaint.plateNumber = $("#plateNumberUpdate").val();
				if(complaint.plateNumber && complaint.address && complaint.latitude && complaint.longitude) {
					updateComplaint(complaint).done(function() {
						reloadList();
						dialog.dialog('close');
					});
				}
				
			}
		});
	});
	return buttonUpdate;
}

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
	
	line.append(createDeleteButton(complaint));
	line.append(createUpdateButton(complaint));
	container.append(line);
}

function reloadList() {
	getAllMessages().done(function(data) {
		var container = $("#container_configuration");
		container.html("");
		container.append("<tr><th>Id</th><th>Latitude</th><th>Longitude</th><th>Address</th><th>Message</th><th>Number</th></tr>");
		$.each(data, function() {
			renderComplaint(container, this);
		});
		
	});
}

reloadList();
