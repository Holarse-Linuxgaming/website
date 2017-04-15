<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<title>Willkommen auf Holarse</title>
<ul>
    <c:forEach var="art" items="${articles}">
        <li><a href="/wiki/${art.uid}">${art.mainTitle}</a></li>
    </c:forEach>
</ul>