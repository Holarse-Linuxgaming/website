<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Form -->
<form:form modelAttribute="articleCommand" action="/wiki/${article.id}" method="PUT">
    <form:button class="btn btn-primary">Speichern</form:button>
    <fieldset>
        <form:label path="title">Titel</form:label>
        <form:input path="title" class="form-control"></form:input>

        <form:label path="tags">Tags</form:label>
        <form:input path="tags" class="form-control"></form:input>    
    </fieldset>
    <fieldset>
        <form:label path="content">Inhalt</form:label>
        <form:textarea path="content" class="form-control" rows="25"></form:textarea>

        <form:label path="contentType">Format des Inhalts</form:label>    
        <form:select path="contentType" items="${contentTypes}" class="form-control"></form:select>
    </fieldset>
    <fieldset>
        <form:label path="changelog">Changelog</form:label>
        <form:textarea path="changelog" class="form-control"></form:textarea>
    </fieldset>
</form:form>
<!-- End Form -->

