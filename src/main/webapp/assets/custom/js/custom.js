$(document).ready(function () {

//    $('.grid').masonry({
//        // set itemSelector so .grid-sizer is not used in layout
//        itemSelector: '.grid-item',
//        // use element for option
//        columnWidth: '.grid-sizer',
//        percentPosition: true
//    });

    hljs.initHighlighting();
    

    $("#toggle-holarse-context-menu").click(function (e) {
        e.preventDefault();
        $(".holarse-context-menu").slideToggle("slow");
    });
    

    
    $( "#searchField" ).autocomplete({
      source: "/search.json",
      minLength: 2,
      select: function( event, ui ) {
        console.debug( "Selected: " + ui.item.url );
        window.location.replace(ui.item.url);
      }
    }).autocomplete("instance")._renderItem = function(ul, item) {
        return $( "<li>" ).attr("data-value", item.url)
                .append(item.title)
                .appendTo(ul);
    };
     
});
