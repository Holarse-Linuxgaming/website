<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="/forum/">Foren</a></li>
    <li class="breadcrumb-item"><a href="${forum.url}">${forum.title}</a></li>
  </ol>
</nav>

<h1>${forum.title}</h1>
<p>${forum.description}</p>

<div class="row">
    <div class="col-md-12">
        <a href="${forum.urlid}/new" class="btn btn-primary">Neuen Post</a>        
    </div>
</div>

    <div class="row">
        <div class="col-md-12">
            <ul class="list-group">
                <c:forEach items="${forum.forumThreads}" var="thread">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    <a href="${thread.url}">${thread.title}</a>
                  <span class="badge badge-primary badge-pill">${thread.comments.size()}</span>
                </li>
                </c:forEach>
            </ul>
        </div>
    </div>

