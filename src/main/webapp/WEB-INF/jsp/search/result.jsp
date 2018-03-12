<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table class="table">
    <thead>
        <tr>
            <th>Titel</th>
            <th>Inhalt</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${results}" var="result">
            <tr>
                <td><a href="/wiki/${result.id}">${result.title}</a></td>
                <td>${result.content}</td>
            </tr>
    </c:forEach>        
</tbody>
</table>
