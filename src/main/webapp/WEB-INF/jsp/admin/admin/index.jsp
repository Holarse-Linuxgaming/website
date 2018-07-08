<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Admin-Bereich</h1>

<h2>Suchindex</h2>
<a href="/admin/search/reindex" class="btn btn-primary">Komplette Reindizierung</a>

<h2>Zugriffsstatistiken</h2>
<div class="table-responsive">
    <table class="table table-hover">
        <thead>
            <tr>
                <th scope="col">Url</th>
                <th scope="col">Visits</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${mainPageVisits}" var="mainPageVisit">
                <tr>
                    <th scope="row"><a href="${mainPageVisit.url}" target="_blank">${mainPageVisit.url}</a></th>
                    <td>${mainPageVisit.count}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<h2>Suchen</h2>
<div class="table-responsive">
    <table class="table table-hover">
        <thead>
            <tr>
                <th scope="col">Suchwort</th>
                <th scope="col">Häufigkeit</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${searches}" var="search">
                <tr>
                    <th scope="row">${search.searchword}</th>
                    <td>${search.count}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
