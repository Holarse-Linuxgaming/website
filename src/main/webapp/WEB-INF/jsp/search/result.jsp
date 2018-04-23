<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table class="table table-hover">
    <thead>
        <tr>
            <th>Titel</th>
            <th>Untertitel</th>
            <th>Inhalt</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${results}" var="result">
            <tr>
                <td><a href="${result.url}">${result.title}</a></td>
                <td>${result.alternativeTitle}</td>
                <td>${result.content}</td>
            </tr>
    </c:forEach>        
</tbody>
</table>
