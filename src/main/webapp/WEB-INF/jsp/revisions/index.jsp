<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach items="${revisions}" var="revision">
    <div class="row">
        <div class="col-md-2">
            ${revision.id}
        </div>
        <div class="col-md-10">
            ${revision.content}
        </div>
    </div>
</c:forEach>