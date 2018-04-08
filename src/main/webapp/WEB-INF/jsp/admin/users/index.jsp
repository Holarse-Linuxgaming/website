<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Benutzer</h1>

<div class="table-responsive">
    <table class="table table-hover">
        <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Login</th>
                <th scope="col">Email</th>
                <th scope="col">Gesperrt?</th>
                <th scope="col">Verifizert?</th>
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
                    <td>
                        <c:choose>  
                            <c:when test="${user.locked}"><i class="fas fa-lock"></i></c:when>
                            <c:otherwise><i class="fas fa-unlock"></i></c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>  
                            <c:when test="${user.verified}"><i class="far fa-check-square"></i></c:when>
                            <c:otherwise><i class="far fa-square"></i></c:otherwise>
                        </c:choose>
                    </td>            
                    <td>${user.roles}</td>
                    <td>${user.created}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>