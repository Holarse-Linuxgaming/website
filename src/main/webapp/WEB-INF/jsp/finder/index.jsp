<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="row">
    <div class="col-md-2">
        <c:forEach items="${tagGroups}" var="tagGroup">
            <strong>${tagGroup.name}</strong>
        <ul class="list-group">
                <c:forEach items="${tagGroup.tags}" var="tag">
                <li class="list-group-item list-group-item-action">
                    <spring:url value="/finder/" var="p">
                        <spring:param name="tag" value="${tag.name}" />
                        <spring:param name="tags" value="${taglist}" />
                        <spring:param name="q" value="${q}" />
                    </spring:url>
                        <a href="${p}">${tag.name}</a>
                </li>
            </c:forEach>
        </ul>
            </c:forEach>

        <strong>Freie Stichw&ouml;rter</strong>        
        <ul class="list-group">
            <c:forEach items="${freeTags}" var="freeTag">
                <li class="list-group-item list-group-item-action">
                    <spring:url value="/finder/" var="p">
                        <spring:param name="tag" value="${freeTag.name}" />
                        <spring:param name="tags" value="${taglist}" />                        
                        <spring:param name="q" value="${q}" />
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
                        <spring:param name="q" value="${q}" />                          
                    </spring:url>                      
                    <a href="${p}">${tag.name}</a>
                </li>
                </c:forEach>
        </ul>

        <p>
            Query: ${q}
        </p>
        
        <%@include file="/WEB-INF/jspf/nodes/nodelist.jspf" %>

    </div>
</div>


