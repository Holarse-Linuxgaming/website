<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<p>
<a href="/wiki/${nodeId}">zurück</a>
</p>

<table class="table">
    <thead>
        <tr>
            <th>Revision</th>
            <th>Inhalt</th>
            <th>Changelog</th>
            <th>Autor</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${revisions}" var="revision">
            <tr>
                <td>${revision.revision}</td>
                <td>${revision.content}</td>
                <td>${revision.changelog}</td>
                <td>${revision.author.login}</td>
            </tr>
    </c:forEach>        
</tbody>
</table>
