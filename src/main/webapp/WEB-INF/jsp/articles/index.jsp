<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

Available Articles:
<table class="table">
    <thead>
        <tr>
            <th>ID</th>
            <th>Titel</th>
            <th>letzter Autor</th>
            <th>Zuletzt geändert am</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${articles}" var="article">    
            <tr>
                <td>${article.id}</td>
                <td><a href="/wiki/${article.id}/">${article.title}</a></td>
                <td>${empty article.author.login ? "unbekannt" : article.author.login}</td>
                <td>${empty article.updated ? article.created : article.updated}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
