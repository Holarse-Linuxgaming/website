$(document).ready(function () {

    $('.grid').masonry({
        // set itemSelector so .grid-sizer is not used in layout
        itemSelector: '.grid-item',
        // use element for option
        columnWidth: '.grid-sizer',
        percentPosition: true
    });

    $("#toggle-holarse-context-menu").click(function (e) {
        e.preventDefault();
        $(".holarse-context-menu").slideToggle("slow");
    });

});