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

    <div class="grid">
        <div class="grid-sizer"></div>
        <c:forEach items="${items}" var="item">
            <div class="grid-item">
                <a href="${item.url}"><img src="assets/img/testbild.png" />${item.title}</a>
                <p>${item.teaser} <a href="${item.url}">weiter</a></p>
            </div>
        </c:forEach>
    </div>
