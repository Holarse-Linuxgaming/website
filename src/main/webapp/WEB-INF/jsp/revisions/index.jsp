<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<p>
<a href="/wiki/${nodeId}">zurück</a>
</p>

<table class="table">
    <thead>
        <tr>
            <th>Revision</th>
            <th>Version</th>
            <th>Autor</th>            
            <th>Changelog</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${revisions}" var="revision">
            <tr>
                <td>${revision.revision}</td>
                <td>${revision.created}</td>
                <td>${revision.author.login}</td>                
                <td>${revision.changelog}</td>
            </tr>
    </c:forEach>        
</tbody>
</table>
