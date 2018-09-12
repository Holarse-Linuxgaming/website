<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Benutzer</h1>

<p>
<a class="btn btn-primary" href="/api/api_users/new">
    <i class="fa fa-plus"></i>
    API-User erstellen
</a>
</p>

<div class="table-responsive">
    <table class="table table-hover">
        <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Login</th>
                <th scope="col">Token</th>
                <th scope="col">Rolle</th>
                <th scope="col">Aktiv?</th>
                <th scope="col">Erstellt</th>
                <th scope="col">Gültig bis</th>                
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <th scope="row"><a href="/users/${user.id}">${user.id}</a></th>
                    <td>${user.login}</td>
                    <td>${user.token}</td>
                    <td>${user.roleName}</td>
                    <td>${user.active}</td>
                    <td>${user.created}</td>
                    <td>${user.validUntil}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>