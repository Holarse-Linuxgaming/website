var vcomments = new Vue({
    el: '#v-comments',
    data: {
        message: 'Hello Comments',
        comments: []
    },
    mounted: function () {
        var nodeId = $("article").data("nodeid");
        console.debug("Loading comments for nodeid " + nodeId);
        $.getJSON("http://localhost:8080/node/" + nodeId + "/comments/", function (data) {
            vcomments.comments = data;
        });
    }
});