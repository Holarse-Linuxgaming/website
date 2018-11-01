<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="/forum/">Foren</a></li>
    <li class="breadcrumb-item"><a href="${forum.url}">${forum.title}</a></li>
    <li class="breadcrumb-item active" aria-current="page">${node.title}</li>
  </ol>
</nav>

<h1>${node.title}</h1>
<p>${node.content}</p>
<p>Erstellt von <a href="${node.author.url}">${node.author.login}</a></p>

<%@include file="/WEB-INF/jspf/comments/list.jspf" %>


<div class="row">
    <div class="col-md-12">

        <%@include file="/WEB-INF/jspf/comments/form.jspf" %>

    </div>
</div>
