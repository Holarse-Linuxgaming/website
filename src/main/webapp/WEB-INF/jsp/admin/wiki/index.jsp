<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Wiki</h1>

<div class="table-responsive">
    <table class="table table-hover">
        <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Title</th>
                <th scope="col">Alternative Titles</th>
                <th scope="col">Wörter</th>
                <th scope="col">Gelöscht</th>
                <th scope="col">Entwurf</th>
                <th scope="col">Gesperrt</th>
                <th scope="col">Veröffentlicht</th>
                <th scope="col">Archiviert</th>
                <th scope="col">Erstellt</th>
                <th scope="col">Bearbeitet</th>
                <th scope="col">Revision</th>
                <th scope="col">Aktion</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${nodes}" var="node">
                <tr>
                    <th scope="row"><a href="${node.url}">${node.id}</a></th>
                    <td>${node.title}</td>
                    <td>${node.alternativeTitles}</td>
                    <td>${node.wordCount}</td>

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
                    <td>${node.revision}</td>
                    <td colspan="2">
                        <div class="btn-group">
                          <button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Aktionen
                          </button>
                          <div class="dropdown-menu">
                            <a href="/admin/frontpage/post/ARTICLE/${node.id}" class="dropdown-item">
                                <i class="fas fa-arrow-alt-circle-up"></i>Auf die Frontpage
                            </a>
                            <div class="dropdown-divider"></div>
                            <a href="/admin/search/reindex/${node.nodeType}/${node.id}" class="dropdown-item">
                                <i class="fas fa-search-plus"></i>Reindex
                            </a>
                          </div>
                        </div>                         
                    </td>                                        
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>