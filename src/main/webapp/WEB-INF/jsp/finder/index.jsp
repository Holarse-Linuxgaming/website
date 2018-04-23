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
        
        <strong>Händler</strong>
        <ul class="list-group">
            <c:forEach items="${stores}" var="store">
                <li class="list-group-item list-group-item-action">
                    <spring:url value="/finder/" var="p">
                        <spring:param name="tag" value="${store.name}" />
                        <spring:param name="tags" value="${taglist}" />
                    </spring:url>
                    <a href="${p}">${store.name}</a>
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
                <li class="list-inline-item">
                    <spring:url value="/finder/" var="p">
                        <spring:param name="tag" value="${tag.name}" />
                        <spring:param name="tags" value="${taglist}" />                        
                    </spring:url>                      
                    <a href="${p}">${tag.name}</a>
                </li>
                </c:forEach>
        </ul>

        <%@include file="/WEB-INF/jspf/nodes/nodelist.jspf" %>


    </div>
</div>


