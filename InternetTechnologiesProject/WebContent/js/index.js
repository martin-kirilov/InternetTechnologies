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
	return $.ajax({
		url: "api/Rest/getAll",
		type: "GET",
		contentType: 'application/json; charset=utf-8',
		dataType: 'json'
	});
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
		
	/*
	fileUpload.onprogress = function(e){
		if (e.lengthComputable) {
			var _progress = Math.round((e.loaded * 100) / e.total);
			if (_progress != 100){
					$('#upload_progress_'+0).text(_progress + '%');
			}
		}
	};
	*/
	//alert("Posting to server...");

	xhr.open("POST", 'api/Rest/' + id + '/image', true);
	xhr.setRequestHeader("Cache-Control", "no-cache");
	xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
	xhr.setRequestHeader("X-File-Name", encodeURIComponent(file.fileName));
	xhr.setRequestHeader("X-File-Size", file.fileSize);
	xhr.setRequestHeader("Content-Type", "application/octet-stream");
	xhr.send(file);
	
	//alert("After post to server...");

};
