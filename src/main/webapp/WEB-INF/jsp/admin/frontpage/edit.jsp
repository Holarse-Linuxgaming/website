<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Form -->
<form:form modelAttribute="frontPageCommand" action="/admin/frontpage/post" method="POST">
    <form:button class="btn btn-primary">Speichern</form:button>
    <form:hidden path="id" />
    <form:hidden path="nodeId" />
    <fieldset>
        <form:label path="title">Titel</form:label>
        <form:input path="title" class="form-control"></form:input>

        <form:label path="teaser">Teaser</form:label>
        <form:input path="teaser" class="form-control"></form:input>    
        
        <form:label path="url">URL</form:label>
        <form:input path="url" class="form-control"></form:input>            
    </fieldset>
    <fieldset>
        <form:label path="pinned">Festgepinnt</form:label>
        <form:checkbox path="pinned" class="form-control" />
        
        <form:label path="publishFrom">Anzeigen ab</form:label>
        <form:input path="publishFrom" class="form-control datepicker"></form:input>    
        
        <form:label path="publishUntil">Anzeigen bis</form:label>
        <form:input path="publishUntil" class="form-control datepicker"></form:input>          
    </fieldset>
</form:form>
<!-- End Form -->

