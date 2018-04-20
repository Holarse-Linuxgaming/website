<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="row">
    <div class="col-md-2">
        <strong>Lizenzen</strong>
        <ul class="list-group">
            <c:forEach items="${licenses}" var="license">
                <li class="list-group-item list-group-item-action">
                    <spring:url value="/finder/" var="p">
                        <spring:param name="tag" value="${license.name}" />
                        <spring:param name="tags" value="${taglist}" />
                    </spring:url>
                    <a href="${p}">${license.name}</a>
                </li>
            </c:forEach>
        </ul>

        <strong>Genres</strong>
        <ul class="list-group">
            <c:forEach items="${genres}" var="genre">
                <li class="list-group-item list-group-item-action">
                    <spring:url value="/finder/" var="p">
                        <spring:param name="tag" value="${genre.name}" />
                        <spring:param name="tags" value="${taglist}" />                        
                    </spring:url>     
                    <a href="${p}">${genre.name}</a>
                </li>
            </c:forEach>
        </ul>

        <strong>Freie Stichwörter</strong>        
        <ul class="list-group">
            <c:forEach items="${freeTags}" var="freeTag">
                <li class="list-group-item list-group-item-action">
                    <spring:url value="/finder/" var="p">
                        <spring:param name="tag" value="${freeTag.name}" />
                        <spring:param name="tags" value="${taglist}" />                        
                    </spring:url>                  
                    <a href="${p}">${freeTag.name}</a>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div class="col-md-10">
        <strong>Aktuell verwendete Tags</strong>
        <ul class="list-inline">
            <c:forEach items="${tags}" var="tag">
                <li class="list-inline-item">${tag.name}</li>
                </c:forEach>
        </ul>

        Gefundene Spiele:
        <table class="table">
            <thead>
                <tr>
                    <th>Titel</th>
                    <th>Teaser</th>
                    <th>letzter Autor</th>
                    <th>Zuletzt geändert am</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${nodes}" var="node">    
                    <tr>
                        <td><a href="${node.url}">${node.title}</a></td>
                        <td>${node.teaser}</td>
                        <td>${empty node.author.login ? "unbekannt" : node.author.login}</td>
                        <td>${empty node.updated ? node.created : node.updated}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>


    </div>
</div>


