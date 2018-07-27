<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Tags</h1>

<div class="table-responsive">
    <table class="table table-hover">
        <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Name</th>
                <th scope="col">Gruppe</th>
                <th scope="col">Alias für</th>
                <th scope="col">Erstellt</th>
                <th scope="col">Bearbeitet</th>
                <th scope="col">Aktionen</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${tags}" var="tag">
                <tr>
                    <th scope="row">${tag.id}</th>
                    <td><a href="/admin/tags/${tag.id}/edit">${tag.name}</a></td>
                    <td>${tag.tagGroup.name}</td>
                    <td>${tag.alias.name}</td>
                    <td>${node.created}</td>
                    <td>${node.updated}</td>
                    <td>
                        <c:url value="/finder/" var="taglink">
                            <c:param name="tags" value="${tag.name}"></c:param>
                        </c:url>
                        <a href="${taglink}" target="_blank"><i class="fa fa-link"></i></a>
                        <a href="/admin/tags/${tag.id}/edit"><i class="fa fa-edit"></i></a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>