function dbl_save_changes(id) {
    alert("saveDrückblick(" + id + ")");
}

var dbl_mark_busy_run;
function dbl_mark_busy(id) {
    console.log("Drückblick #" + id + " is busy now");
    $.ajax({
        url: '/api/'
    });

    clearTimeout(dbl_mark_busy_run);
    
    dbl_mark_busy_run = window.setTimeout(function() {
    }, 500);
}

function dbl_mark_deleted(id) {
    console.log("Drückblick #" + id + " is marked as deleted");
    $.ajax({
        url: '/admin/api/drueckblick/mark_dirty',
        method: "PUT",
        data: { "id": parseInt(id.replace("dbl-edit-", "")) },
        headers: 
        {
            'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        }
    });
}

function dbl_get_dirty_marks() {
    $.ajax({
        url: "/admin/api/drueckblick/get_dirty_marks", 
        method: "GET", 
        headers: {
            'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        }, 
        success: function(result) {
            
        }
    });
}

function admin_apiusers_get_token() {
    $.ajax({
        url: "/holarseweb/admin/api/apiusers/token.json", 
        method: "GET", 
        headers: {
        }, 
        success: function(result) {
            $("#apiuser-token").value = result.password;
        }
    });
}