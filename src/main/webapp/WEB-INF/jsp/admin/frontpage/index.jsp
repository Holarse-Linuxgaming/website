<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Frontpage</h1>

<div class="table-responsive">
    <table class="table table-hover">
        <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Title</th>
                <th scope="col">Teaser</th>
                <th scope="col"></th>
                <th scope="col">Veröffentlicht ab</th>
                <th scope="col">Veröffentlicht bis</th>
                <th scope="col">Cooldown</th>
                <th scope="col">Angepinnt</th>
                <th scope="col">Aktionen</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${items}" var="item">
                <tr>
                    <th scope="row">
                        <a href="${item.url}">${node.id}</a>
                    </th>
                    <td>${item.title}</td>
                    <td>${item.teaser}</td>
                    <td></td>
                    <td>${item.publishFrom}</td>
                    <td>${item.publishUntil}</td>
                    <td>${item.cooldownUntil}</td>
                    <td>${item.pinned}</td>
                    <td>
                        <div class="btn-group">
                          <button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Aktionen
                          </button>
                          <div class="dropdown-menu">
                            <a href="/admin/frontpage/post/${item.nodeType}/${item.nodeId}" class="dropdown-item">
                            <c:choose>
                                <c:when test="${item.repostable}">
                                    Reposten
                                </c:when>
                                <c:otherwise>
                                    Bearbeiten
                                </c:otherwise>
                            </c:choose>
                            </a>
                            <div class="dropdown-divider"></div>
                            <a href="/admin/frontpage/delete/${item.id}" class="dropdown-item">Löschen</a>
                          </div>
                        </div>                        
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>