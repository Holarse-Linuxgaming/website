$(document).ready(function() {
   
   //$(".datepicker").datepicker($.datepicker.regional[ "de" ]);
   
   ///////////////////////////////////////////////////////////////////////////////
   // Drückblick
   ///////////////////////////////////////////////////////////////////////////////
   Vue.component('drueckblick-entry', {
      props: ['entry', 'categories', 'index'],
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
               url: "/admin/drueckblick/entries/update_entry",
               type: "post",
               data: data,
               dataType: "json",
               beforeSend: function(request) {
                  request.setRequestHeader(holarse.csrf_header, holarse.csrf_token);
               },
               success: function(result) {
                  obj.changed = false;
               }
            });
         },
         delete_entry: function(obj) {
            var data = {
               id: obj.id,
               deleted: !obj.deleted,
            }

            $.ajax({
               url: "/admin/drueckblick/entries/delete_entry",
               type: "post",
               data: data,
               dataType: "json",
               beforeSend: function(request) {
                  request.setRequestHeader(holarse.csrf_header, holarse.csrf_token);
               },
               success: function(result) {
                  obj.changed = false;
                  obj.deleted = result.deleted;
                  console.debug(result.deleted);
               }
            });            
         }
      }
   });

   var vadmdbl_entries = new Vue({
            el: "#v-admin-drueckblick-entries",
            data: {
               entries: [],
               categories: [],
               drueckblick_proposal: {}
            },
            methods: {
               load_data: function() {
                  $.ajax({
                     dataType: "json",
                     url: "/admin/drueckblick/entries/",
                     success: function(result) {
                        vadmdbl_entries.entries = $.map(result, function(i) {
                           i.changed = false;
                           return i;
                        });
                     }
                  });
               },
               load_categories: function() {
                  $.ajax({
                     dataType: "json",
                     url: "/admin/drueckblick/entries/categories",
                     success: function(result) {
                        vadmdbl_entries.categories = result;
                     }
                  });
               },
               request_dbl: function() {
                  $.ajax({
                     dataType: "json",
                     url: "/admin/drueckblick/propose",
                     success: function(result) {
                        vadmdbl_entries.drueckblick_proposal = result;
                        console.debug(vadmdbl_entries.drueckblick_proposal);
                     }
                  });                  
               }
            },
            mounted: function() {
               this.load_data();
               this.load_categories();
            }
   });


});