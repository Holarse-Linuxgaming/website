<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Benutzer</h1>

<table class="table">
    <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Login</th>
            <th scope="col">Email</th>
            <th scope="col">Status (Locked / Verified)</th>
            <th scope="col">Rollen</th>
            <th scope="col">Erstellt</th>
            <th scope="col">Letzer Login</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${users}" var="user">
        <tr>
            <th scope="row"><a href="/users/${user.id}">${user.id}</a></th>
            <td>${user.login}</td>
            <td>${user.email}</td>
            <td>${user.locked} / ${user.verified}</td>
            <td>${user.roles}</td>
            <td>${user.created}</td>
        </tr>
        </c:forEach>
    </tbody>
</table>
