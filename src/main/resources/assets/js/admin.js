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
    alert("Drückblick #" + id + " is marked as deleted");
}