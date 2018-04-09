<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table class="table">
    <thead>
        <tr>
            <th>Titel</th>
            <th>Untertitel</th>
            <th>Tags</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${results}" var="result">
            <tr>
                <td><a href="${result.url}">${result.title}</a></td>
                <td>${result.content}</td>
                <td>
                    <c:forEach items="${result.tags}" var="tag">
                        <a href="/tags/${tag.name}">${tag.name}</a>, 
                    </c:forEach>
                </td>
            </tr>
    </c:forEach>        
</tbody>
</table>
