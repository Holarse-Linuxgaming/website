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
        <c:forEach items="${view.results}" var="result">
            <tr>
                <td><a href="${result.url}">${result.title}</a></td>
                <td>                    
                    <c:forEach items="${result.subtitles}" var="subtitle">
                        ${subtitle}
                    </c:forEach>
                </td>
                <td>
                    <c:forEach items="${result.tags}" var="tag">
                        <a href="/finder/?tag=${tag}" title="Klicken für weitere Artikel mit diesem Tag">${tag}</a>
                    </c:forEach>
                </td>
                <td>${result.teaser}</td>
            </tr>
    </c:forEach>        
</tbody>
</table>

<%@include file="/WEB-INF/jspf/pagination.jspf" %>