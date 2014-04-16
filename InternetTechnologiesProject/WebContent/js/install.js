$("#install_db").click(function() {
	$.ajax({
		url: "api/Rest/install",
		type: "POST"
	});
});