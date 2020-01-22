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
        <c:forEach items="${views}" var="view">    
            <tr>
                <td>${view.nodeId}</td>
                <td><a href="${view.url}">${view.mainTitle}</a></td>
                <td></td>
                <td></td>
            </tr>
        </c:forEach>
    </tbody>
</table>
