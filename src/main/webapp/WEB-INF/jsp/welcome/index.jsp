<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<ul>
    <li>See a list of all users: <a href="users/">All users</a>.</li>
    <li>See a list of all users: <a href="wiki/">All articles</a>.</li>
</ul>

Was hier noch so erscheinen soll:
<ul>
    <li>News in chronologischer Reihenfolge</li>
    <li>Neue Artikel</li>
    <li>Aggregated News</li>
    <li>Beliebte Artikel</li>
    <li>Artikel mit neuen Kommentaren</li>
    <li>Shouts mit Spielaufrufen und ähnlichem</li>
    <li>Upcoming...</li>
</ul>

<p>
    Insgesamt sollen Beiträge "an die Frontpage" geschickt werden können. Dabei ist es egal, welcher Art diese Beiträge sind. So können das überarbeitete Artikel, aber auch interessante Kommentare,
    neue Videos oder ähnliches sein.
</p>

<div class="container-fluid">
    <div class="grid">
        <div class="grid-sizer"></div>
        <c:forEach items="${items}" var="item">
            <div class="grid-item">
                <div class="grid-item-content">
                    <a href="${item.url}">
                        <img src="https://placeimg.com/640/480/any" />
                        ${item.title}</a>                    
                </div>
            </div>
        </c:forEach>
    </div>
</div>
