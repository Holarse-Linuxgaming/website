<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<ul>
    <li><a href="/forum/">Foren</a></li>
    <li><a href="${forum.url}">${forum.title}</a></li>
    <li><a href="${node.url}">${node.title}</a></li>
</ul>

<h1>${node.title}</h1>
<p>${node.content}</p>

<%@include file="/WEB-INF/jspf/comments/list.jspf" %>


<div class="row">
    <div class="col-md-12">

        <%@include file="/WEB-INF/jspf/comments/form.jspf" %>

    </div>
</div>
