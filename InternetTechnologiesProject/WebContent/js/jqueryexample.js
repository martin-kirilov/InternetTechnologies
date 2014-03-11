
function showMessageDialog(text, buttons) {
	$("#messageDialogText").val(text);
    $( "#messageDialog" ).dialog({
      modal: true,
      buttons: buttons
    }).show();		
}
$("#button_showCreateMessage").click(function() {
				
	
	showMessageDialog("", {
		"Create": function() {
        	var dialog = $(this);
        	var text = $("#messageDialogText").val();
        	if(text) {
        		addMessage(text).done(function() {
        			reloadList();
               	 	dialog.dialog('close');
        		});
        	}
        }
	});

});

function createDeleteButton(message) {
	return  $("<button >delete</button>").
	click(function() {
		deleteMessage(message.id).done(function() {
			reloadList();
		});
	});
}
function createUpdateButton(message) {
	var buttonUpdate = $("<button >update</button>").
	click(function() {
		showMessageDialog(message.text, {
			"Update": function() {
	        	var dialog = $(this);
	        	message.text = $("#messageDialogText").val();
	        	if(message.text) {
	        		updateMessage(message).done(function() {
	        			reloadList();
	               	 	dialog.dialog('close');
	        		});
	        	}
	        }
			
		});
	});
	
	return buttonUpdate;
}
function renderMessage(container, message) {
	
	var li = $("<li>"+message.text+"</li>");
	li.append(createDeleteButton(message));
	li.append(createUpdateButton(message));

	container.append(li);
	
}

function reloadList() {

	getAllMessages().done(function(data) {
		var container = $("#container_configuration");
		container.html("");
		$.each(data, function() {
			renderMessage(container, this);
		});
	});
}

reloadList();