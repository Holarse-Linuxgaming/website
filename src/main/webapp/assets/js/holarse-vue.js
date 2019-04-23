// Kommentarsektion
var vcomments = new Vue({
    el: '#v-comments-1',
    data: {
        comments: []
    },
    mounted: function () {
        var nodeId = $("article").data("nodeid");
        if (nodeId === undefined) { return false; }
        console.debug("Loading comments for nodeid " + nodeId);
        $.getJSON("/nodes/comments/", { nodeId: nodeId }, function (data) {
            vcomments.comments = data;
        });
    }
});

var varticleeditor = new Vue({
    el: "#article-editor",
    data: {
        node: {}
    },
    mounted: function () {
        var nodeId = $("#article-editor").data("nodeid");
        if (nodeId === undefined || nodeId === "") { return false; }
        console.debug("Loading article data for nodeid " + nodeId);
        $.getJSON("/wiki/" + nodeId + "/edit.json", function (data) {
            varticleeditor.node = data;
        });
    }    
});