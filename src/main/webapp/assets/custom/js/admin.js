$(document).ready(function() {
   
   $(".datepicker").datepicker($.datepicker.regional[ "de" ]);
   
   ///////////////////////////////////////////////////////////////////////////////
   // Drückblick
   ///////////////////////////////////////////////////////////////////////////////
   Vue.component('drueckblick-entry', {
      props: ['entry', 'index'],
      template: '#admin-drueckblick-entry',
      methods: {
         update_entry: function(obj) {
            console.debug(obj);
            var data = {
               id: obj.id,
               category: obj.category,
               bearer: obj.bearer,
               link: obj.link,
               message: obj.message,
            };

            $.ajax({
               url: "/admin/drueckblick/update_entry",
               type: "post",
               data: data,
               dataType: "json",
               beforeSend: function(request) {
                  request.setRequestHeader(holarse.csrf_header, holarse.csrf_token);
               },
               complete: function(result) {
                  obj.changed = false;
               }
            });
            console.debug(holarse);
         }
      }
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
                        vadmdbl.entries = $.map(result, function(i) {
                           i.changed = false;
                           return i;
                        });
                     }
                  });
               }
            },
            mounted: function() {
               this.load_data();
            }
   });


});