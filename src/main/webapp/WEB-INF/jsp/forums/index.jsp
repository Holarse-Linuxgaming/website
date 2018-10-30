<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="row">
    <div class="col-md-6 p-3 mb-2 bg-dark text-white">Forum</div>
    <div class="col-md-2 p-3 mb-2 bg-dark text-white">Themen</div>
    <div class="col-md-2 p-3 mb-2 bg-dark text-white">Beiträge</div>
    <div class="col-md-2 p-3 mb-2 bg-dark text-white">Letzter Beitrag</div>
</div>
<div class="row">
        <c:forEach items="${forums}" var="forum">
            <div class="col-md-6">
                <a href="${forum.url}">${forum.title}</a>
                <p>${forum.description}</p>
            </div>
            <div class="col-md-2">
                ${forum.threadCount}
            </div>
            <div class="col-md-2">
                ${forum.messageCount}
            </div>
            <div class="col-md-2">
                0
            </div>
        </c:forEach>
    </div>
</div>


