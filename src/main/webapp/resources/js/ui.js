$(function(){

	// Go top
	$(".go_top").click( function(){
	  $("html, body").animate({ scrollTop : 0 }, 200);
	  return false;
	});
	

	// Shortcuts
	$(".go_content > a").on("focus", function(){
		$(this).css({ height : "45px", lineHeight : "45px" });
	});
	$(".go_content > a").on("blur", function(){
		$(this).css({ height : '0' });
	});


	// Gnb
	$(".gnb_wrap ul li a.mobile_none").hover(function(){
		$(".header_wrap").addClass("open");
	});
	$(".gnb_wrap").mouseleave(function(){
		$(".header_wrap").removeClass("open");
	});

	// Sticky menu
	$(window).scroll(function(){
		var scrollTop = $(document).scrollTop();
		scrollTop > 1 ? $(".header_wrap.main_header").addClass("active") : $(".header_wrap.main_header").removeClass("active");
		scrollTop > 2230 ? $(".main_contents").addClass("active") : $(".main_contents").removeClass("active");
		scrollTop > 2230 ? $(".notice_main").addClass("active") : $(".notice_main").removeClass("active");
		scrollTop > 2230 ? $(".today_wrap").addClass("active") : $(".today_wrap").removeClass("active");
		scrollTop > 1650 ? $(".today_wrap .today").addClass("active") : $(".today_wrap .today").removeClass("active");
	});
});


// Mobile gnb open/close
function gnbOpen (_this) {
	var _this= $(_this);
	_this.parents("header").find(".gnb").animate({right:"0"},200);
	_this.parents("header").find(".gnb_bg").show();
}
function gnbClose (_this) {
	var _this= $(_this);
	_this.parents("header").find(".gnb").animate({right:"-320px"},200);
	_this.parents("header").find(".gnb_bg").hide();
}
function menuOpen (_this) {
	var _this = $(_this);
	if (_this.parent().hasClass("active")) {
		_this.closest("ul").find(".active").removeClass("active");
		_this.closest("ul").find("ul.depth2").slideUp(200);
	}else {
		_this.closest("ul").find(".active").removeClass("active");
		_this.closest("ul").find("ul.depth2").slideUp(200);
		_this.parent().addClass("active");
		_this.parent().find(">ul.depth2").slideDown(200);
	}
}


// Accodian
function accodianFunc (_this) {
	var _this = $(_this);
	if (_this.closest("li").hasClass("active")) {
		_this.closest("ul").find("li").removeClass("active");
		_this.closest("ul").find("li .text").stop().slideUp(200);
	}else {
		_this.closest("ul").find("li").removeClass("active");
		_this.closest("ul").find("li .text").stop().slideUp(200);
		_this.closest("li").addClass("active");
		_this.closest("li").find(".text").stop().slideDown(200);
	}
}


// Tab
function tabFunc (_this, _thisId) {
	var _this= $(_this);
	var _thisId = $(_thisId);
	_this.closest("ul").find(".active").removeClass("active");
	_this.parent().addClass("active");
	_this.parents(".tab_wrap").find(".tab_cont").hide();
	$(_thisId).show();
}


// Popup
function popOpen (popup) {
	var popup = $(popup);
	popup.show();
}
function popClose (_this) {
	var _this = $(_this);
	_this.parents(".popup_wrap").hide();
}


// Select
function selectFunc (_this) {
	var _this = $(_this);
	_this.next("ul").slideToggle(250);
}
function selectClick (_this) {
	var _this = $(_this);
	_this.parents(".select_box ul").prev().html(_this.find("a").html());
	_this.parents(".select_box ul").slideUp(250);
}


// Tooltip 
function tooltipFunc (_this) {
	var _this = $(_this);
	_this.find(".tooltip").toggle();
}


// Add class
function addfunc (_this) {
	var _this = $(_this);
	_this.toggleClass("active");
}


// Delete
function delFunc (_this) {
	var _this = $(_this);
	_this.parent().remove();
}

// 이용안내 효과
function viewCover (_this) {
	var _this = $(_this);
	if (_this.parents("li").hasClass("active")) {
		_this.parents("ul").find("li").removeClass("active");
	}else {
		_this.parents("ul").find("li").removeClass("active");
		_this.parents("li").addClass("active");
	}
}
