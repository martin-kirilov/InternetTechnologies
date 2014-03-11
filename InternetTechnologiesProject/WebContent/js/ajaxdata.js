	function executeRequest(url, method, data, successCallback, errorCallback) {
		$.ajax({
			url: url,
			type: method,
			data: data?JSON.stringify(data):null,
			contentType: 'application/json; charset=utf-8',
			dataType: 'json'
		}).done(function(data) {
			if (successCallback) {
				successCallback(data);
			}
		}).fail(function(data, statusText, status) {
			if (errorCallback) {
				errorCallback(data, statusText, status);
			} else {
				bootbox.alert("Error: " +  status);
//				bootbox.alert(LOCALIZE.translate("alert_error").replace("{message}", status));
			}
		});
	}

	
(function($) {
    'use strict';
		
        function handleAjaxConfiguration(sourcePanel) {
				var panelToFill = sourcePanel.data("target")?$(sourcePanel.data("target")):sourcePanel;
				panelToFill.empty().addClass('loading');
				var dataUrl = sourcePanel.data("url");
				if (dataUrl) {
					
					executeRequest("api/hello/test", "GET", null, function(data) {
							showAjaxResult(sourcePanel, panelToFill, data);
					});
				} else {
					var data = sourcePanel.data("value");
					if (!data) {
						data = {};
					}
					showAjaxResult(sourcePanel, panelToFill, data);
				}
        }
        function handleAjaxDialog(sourcePanel) {
			var lambda = function (data) {
						$(sourcePanel.data("target")).remove();
						showAjaxResult(sourcePanel, $('#container'), data, function() {
								
								$(sourcePanel.data("target")).on('shown.bs.modal', function() {
										$(this).find("[autofocus]:first").focus();
								}).modal('show');
						});
			
			};
			var dataUrl = sourcePanel.data("url");
			if (dataUrl) {
				executeRequest(dataUrl, "GET", null, lambda);
			} else {
				var value = sourcePanel.data("value");
				if (!value) {
					value = {};
				}
				lambda(value);
			}
        }
        
        function showAjaxResult(sourcePanel, panelToFill, data, callback) {
					var templateName = "template-" + sourcePanel.data("template");
					panelToFill.mustache(templateName, data);
					
					panelToFill.removeClass('loading');
					
					$(document).i18n();

					if (callback) {
						callback();
					}
        }
        
		$(document).on("onAjaxUpdateRequired", function() {
			$('[data-provide="ajax"]').each(function() {
				handleAjaxConfiguration($(this));
			});
		});
		$(document).on("click", "[data-provide='ajax-dialog']", function(e) {
			handleAjaxDialog($(e.target));
		});
})(jQuery);

