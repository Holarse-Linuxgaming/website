<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Kommentare</h1>

<div class="table-responsive">
    <table class="table table-hover">
        <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Title</th>
                <th scope="col">Typ</th>
                <th scope="col">Autor</th>
                <th scope="col">Inhalt</th>
                <th scope="col">Gelöscht</th>
                <th scope="col">Entwurf</th>
                <th scope="col">Gesperrt</th>
                <th scope="col">Veröffentlicht</th>
                <th scope="col">Archiviert</th>
                <th scope="col">Erstellt</th>
                <th scope="col">Bearbeitet</th>
                <th scope="col">Aktion</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${comments}" var="node">
                <tr>
                    <th scope="row"><a href="${node.url}">${node.id}</a></th>
                    <td>TODO</td>
                    <td>${node.nodeType}</td>
                    <td>${node.author.login}</td>
                    <td>${node.content}</td>

                    <td>
                        <c:choose>  
                            <c:when test="${node.deleted}"><i class="far fa-check-square"></i></c:when>
                            <c:otherwise><i class="far fa-square"></i></c:otherwise>
                        </c:choose>
                    </td>

                    <td>
                        <c:choose>  
                            <c:when test="${node.draft}"><i class="far fa-check-square"></i></c:when>
                            <c:otherwise><i class="far fa-square"></i></c:otherwise>
                        </c:choose>
                    </td>

                    <td>
                        <c:choose>  
                            <c:when test="${node.locked}"><i class="far fa-check-square"></i></c:when>
                            <c:otherwise><i class="far fa-square"></i></c:otherwise>
                        </c:choose>
                    </td>

                    <td>
                        <c:choose>  
                            <c:when test="${node.published}"><i class="far fa-check-square"></i></c:when>
                            <c:otherwise><i class="far fa-square"></i></c:otherwise>
                        </c:choose>
                    </td>

                    <td>
                        <c:choose>  
                            <c:when test="${node.archived}"><i class="far fa-check-square"></i></c:when>
                            <c:otherwise><i class="far fa-square"></i></c:otherwise>
                        </c:choose>
                    </td>

                    <td>${node.created}</td>
                    <td>${node.updated}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>