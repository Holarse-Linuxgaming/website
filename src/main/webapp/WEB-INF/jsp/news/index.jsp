<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

Available News:
<table class="table">
    <thead>
        <tr>
            <th>ID</th>
            <th>Titel</th>
            <th>Zuletzt geändert am</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${views}" var="view">    
            <tr>
                <td>${view.nodeId}</td>
                <td><a href="${view.url}">${view.title}</a></td>
                <td>${empty view.updated ? view.created : view.updated}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<%@include file="/WEB-INF/jspf/pagination.jspf" %>
