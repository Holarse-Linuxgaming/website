<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Wiki</h1>

<div class="table-responsive">
    <table class="table table-hover">
        <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Title</th>
                <th scope="col">Alternative Titles</th>
                <th scope="col">Wörter</th>
                <th scope="col">Status (DEL, DRA, LOC, PUB, ARC)</th>
                <th scope="col">Erstellt</th>
                <th scope="col">Bearbeitet</th>
                <th scope="col">Revision</th>
                <th scope="col">Aktion</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${nodes}" var="node">
                <tr>
                    <th scope="row"><a href="${node.url}">${node.id}</a></th>
                    <td>${node.title}</td>
                    <td>${node.alternativeTitles}</td>
                    <td>${node.wordCount}</td>
                    <td>${node.deleted} ${node.draft} ${node.locked} ${node.published} ${node.archived}</td>
                    <td>${node.created}</td>
                    <td>${node.updated}</td>
                    <td>${node.revision}</td>
                    <td>
                        <a href="/admin/frontpage/post/ARTICLE/${node.id}" class="btn btn-danger">Auf die Frontpage</a>
                    </td>                    
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>