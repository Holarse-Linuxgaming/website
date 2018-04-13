$(document).ready(function() {
   
    var $grid = $(".grid").imagesLoaded(function () {
        $(".grid").masonry({
            itemSelector: '.grid-item',
            columnWidth: '.grid-sizer',
            percentPosition: true
        });
    });
  
    $("#toggle-holarse-context-menu").click(function(e) {
        e.preventDefault();
        $(".holarse-context-menu").slideToggle("slow");
    });
    
});