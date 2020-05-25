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
      
  
      
      $("#query").autocomplete({
        source: "/webapi/autocomplete/search.json",
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
      
      $("#tags").tagsInput({
          minChars : 2,
          autocomplete_url: '/webapi/autocomplete/tags'
      });
  
      var access_chart = $("#access_chart");
      if (access_chart.length() > 0) {
        var nodeid = $("[data-nodeid]").data("nodeid");
        $.getJSON("/webapi/pagevisits", {nodeId: nodeid}, function(result) {
          var mappeddata = $.map(result.items, function(item) {
              return { x: item.date, y: item.visits };
          });
          console.debug(mappeddata);
          var myChart = new Chart(access_chart, {
            type: 'line',
            data: {
              label: ["Zugriffe"],
              datasets: [
                {
                  label: "Zugriffe",
                  backgroundColor: '#e84d3c',
                  data: mappeddata
                }
              ]
            },
            options: {
              scales: {
                  xAxes: [{
                      type: 'time',
                      time: {
                          parser: "YYYY-MM-DD",
                          unit: 'day'
                      }
                  }]
              }
            }
          });
        });
      }
  });
  