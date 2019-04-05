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