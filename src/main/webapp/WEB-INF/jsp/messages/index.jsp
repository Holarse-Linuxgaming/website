<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

Deine Nachrichten
<table class="table">
    <thead>
        <tr>
            <th>ID</th>
            <th>Absender</th>
            <th>Nachricht</th>
            <th>Gesendet am</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${messages}" var="message">    
            <tr>
                <td>${message.id}</td>
                <td><a href="${message.url}">${message.uuid}</a></td>
                <td>${empty node.author.login ? "unbekannt" : node.author.login}</td>
                <td>${empty node.updated ? node.created : node.updated}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
