var holarse = {};
holarse.csrf_token  = $("meta[name=_csrf]").attr("content");
holarse.csrf_header = $("meta[name=_csrf_header]").attr("content");
    
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

var varticleeditor = new Vue({
    el: "#v-article-editor",
    data: {
        ctrl: {
            show_additional_titles: false,
            is_new: true
        },
        node: {}
    },
    mounted: function () {
        this.load();
    }, 
    methods: {
        load: function() {
        var nodeId = $("#v-article-editor").data("nodeid");
        if (nodeId === undefined || nodeId === "") { return false; }
        console.debug("Loading article data for nodeid " + nodeId);
        
            $.getJSON("/wiki/" + nodeId + "/edit.json", function (data) {
                console.debug(data);
                varticleeditor.node = data;
                varticleeditor.ctrl.is_new = false;
            }).done(function() {
                varticleeditor.ctrl.show_additional_titles = false;
            });            
        },
        submit: function(event) {
            event.preventDefault();
            $.ajax({
                url: '/wiki/' + (varticleeditor.ctrl.is_new ? 'create' : this.node.nodeId),
                type: 'post',
                data: this.node,
                beforeSend: function(request) {
                    request.setRequestHeader(holarse.csrf_header, holarse.csrf_token);
                },
                dataType: 'json',
                success: function(result) {
                    console.debug(result);
                    window.location = result.followUrl;
                }
            });
        },
        abortEdit: function(event) {
            event.preventDefault();
            
            var nodeId = $("#v-article-editor").data("nodeid");
            if (nodeId === undefined || nodeId === "") { return false; }            
            $.getJSON("/wiki/" + nodeId + "/abortEdit.json", function (data) {
                varticleeditor.node = {};
                console.debug(data);
                window.location = data;  
            });            
        }
    }
});

