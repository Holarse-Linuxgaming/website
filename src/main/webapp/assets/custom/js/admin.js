$(document).ready(function() {
   
   $(".datepicker").datepicker($.datepicker.regional[ "de" ]);
   
   Vue.component('drueckblick-entry', {
      props: ['entry', 'index'],
      template: '#admin-drueckblick-entry'
   });

   var vadmdbl = new Vue({
            el: "#v-admin-drueckblick",
            data: {
               entries: [],
            },
            methods: {
               load_data: function() {
                  $.ajax({
                     dataType: "json",
                     url: "/admin/drueckblick",
                     success: function(result) {
                        vadmdbl.entries = result;
                     }
                  });
               }
            },
            mounted: function() {
               this.load_data();
            }
   });


});