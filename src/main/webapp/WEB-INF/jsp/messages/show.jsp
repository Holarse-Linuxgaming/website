<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>

<h1>
    ${message.uuid}
</h1>

<p>Absender: <a href="${message.author.url}">${message.author.login}</a></p>
<p>Empfänger: 
<ul>
    <c:forEach items="${message.recipients}" var="recipient">
        <li><a href="${recipient.url}">${recipient.login}</a></li>
    </c:forEach>
    <li></li>
</ul>
</p>

<h2>Nachricht</h2>
<p>
    ${message.content}
</p>
