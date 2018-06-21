$ = jQuery;

$("#menu-toggle").click(function(e) {
    e.preventDefault();
    $("#wrapper").toggleClass("toggled");
});
$(function() {
  $('.sidebar-nav a[href^="/' + location.pathname.split("/")[1] + '"]').addClass('active');
});