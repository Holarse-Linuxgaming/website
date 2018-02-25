<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

Available Articles:
<ul>
    <a href="/articles/new">Neuer Artikel</a>
    <c:forEach items="${articles}" var="article">
        <li><a href="/wiki/${article.id}">${article.title}</a></li>
    </c:forEach>
</ul>