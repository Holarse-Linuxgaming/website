<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Hello World from ${welcomeMessage}</h1>

Available Users:
<ul>
    <c:forEach items="${users}" var="user">
        <li>${user.login}</li>
    </c:forEach>
</ul>