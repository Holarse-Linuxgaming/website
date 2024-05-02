 function updateUrlWithSize(newSize) {
    const url = new URL(location.href);
    url.searchParams.set("size", newSize);
    location.href = url.href;
};

$.datepicker.setDefaults( $.datepicker.regional[ "de" ] );

$(function() {
    $(".btn-remove-article-title").click(function(evt) {
        var item = ".block-" + $(this).data("field");
        $(item).hide('slow');
    });
    
    $("#btn-workspace-add-title").click(function(evt) {
        evt.preventDefault();
        
        $(".block-title").each(function(i, item) {
            console.log(item);
        });
    });    
});

// Register Filepond-Plugins
FilePond.registerPlugin(FilePondPluginFileEncode);
const inputElement = document.querySelector('input[type="file"][name="filepond"]');
FilePond.create(inputElement, {
    storeAsFile: false,
    allowFileEncode: true,
    allowMultiple: true,
    allowReorder: true,
});