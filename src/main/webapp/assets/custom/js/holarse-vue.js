Vue.component('comment', {
    props: ['comment'],
    template: '#comment-template'
});

// Kommentarsektion
var vcomments = new Vue({
    el: '#v-comments',
    data: {
        comments: []
    },
    mounted: function () {
        var nodeId = $("article").data("nodeid");
        if (nodeId === undefined) { return false; }
        console.debug("Loading comments for nodeid " + nodeId);
        $.getJSON("/nodes/comments", { nodeId: nodeId }, function (data) {
            vcomments.comments = data;
        });
    }
});

var vcommenteditor = new Vue({
    el: "#v-comment-editor",
    data: {
        newcomment: {
            nodeId: 0,
            content: ""
        }
    },
    mounted: function() {
        var nodeId = $("#v-comment-editor").data("nodeid");
        if (nodeId === undefined) { return false; } 
        
        this.newcomment.nodeId = nodeId;
    },
    methods: {
        submit: function(event) {
            event.preventDefault();
            $.ajax({
                url: '/nodes/comments',
                type: 'post',
                data: this.newcomment,
                beforeSend: function(request) {
                    request.setRequestHeader(holarse.csrf_header, holarse.csrf_token);
                },
                dataType: 'json',
                success: function(result) {
                    console.debug(result);
                    vcommenteditor.newcomment.content = "";
                    vcomments.$mount();
                }
            });
        }
    }    
});


