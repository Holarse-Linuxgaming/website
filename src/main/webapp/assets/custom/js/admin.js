$(document).ready(function() {
   
   $(".datepicker").datepicker($.datepicker.regional[ "de" ]);
   
   var v_admin_drueckblick = new Vue({
            el: "#v-admin-drueckblick",
            data: {
               entries: [],
               msg: "Hello world"
            },
            methods: {
               load_data: function() {
                  $.ajax({
                     dataType: "json",
                     url: "/admin/drueckblick",
                     success: function(result) {
                        entries = result;
                        console.debug("Loaded " + entries.length + " entries...");
                     }
                  });
               }
            },
            mounted: function() {
               //this.load_data();
            }
   });


});