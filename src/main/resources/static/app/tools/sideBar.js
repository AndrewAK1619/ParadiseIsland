jQuery(function($) {

	var sidebarBehavior = () => {
		var window_top = $(window).scrollTop() + 58;
		var footer_top = $(".footer").offset().top;
		var div_top = $('#sticky-anchor').offset().top;
		var div_height = $("#sidebar").height();

		var padding = 0;

		if (window_top + div_height > footer_top - padding) {
			$('#sidebar').css({top: (window_top + div_height - footer_top + padding - 58) * -1});
			$('#sidebar').css({'transition': 'none'});
		} else if (window_top > div_top) {
			$('#sidebar').css({'top': '58px'});
			$('#sidebar').css({'transition': 'all 0.3s ease'});
		} else {
			$('#sidebar').css({'transition': 'all 0.3s ease'});
		}
	};
	$(window).scroll(function() {
		if($("#sidebar").length === 1) {
			sidebarBehavior();
		}
	});
	
	var basicContent = $(".main").height();
	
	$(window).mouseover(function(){
		if($("#sidebar").length === 1) {
			var currentContent = $(".main").height();
			if(basicContent !== currentContent) {
				basicContent = currentContent;
				sidebarBehavior();
			}
		}
	}); 

	$(".sidebar-dropdown > a").click(function() {
		$(".sidebar-submenu").slideUp(200);
		if ($(this)
				.parent()
				.hasClass("active")
		) {
			$(".sidebar-dropdown").removeClass("active");
			$(this)
				.parent()
				.removeClass("active");
		} else {
			$(".sidebar-dropdown").removeClass("active");
			$(this)
				.next(".sidebar-submenu")
				.slideDown(200);
			$(this)
				.parent()
				.addClass("active");
		}
	});

	$("#close-sidebar").click(function() {
		$(".page-wrapper").removeClass("toggled");
	});
	$("#show-sidebar").click(function() {
		$(".page-wrapper").addClass("toggled");
	});
});