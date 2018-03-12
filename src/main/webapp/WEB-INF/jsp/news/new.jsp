<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Form -->
<form:form modelAttribute="newsCommand" action="/news/" method="POST">
    <fieldset>
        <form:label path="title">Titel</form:label>
        <form:input path="title" class="form-control"></form:input>

        <form:label path="subtitle">Untertitel</form:label>
        <form:input path="subtitle" class="form-control"></form:input>    
        
        <form:label path="category">Kategorie</form:label>    
        <form:select path="category" items="${categories}" class="form-control"></form:select>        
    </fieldset>
    <fieldset>
        <form:label path="content">Inhalt</form:label>
        <form:textarea path="content" class="form-control" rows="25"></form:textarea>

        <form:label path="contentType">Format des Inhalts</form:label>    
        <form:select path="contentType" items="${contentTypes}" class="form-control"></form:select>
    </fieldset>
    <form:button class="form-control">Anlegen</form:button>
</form:form>
<!-- End Form -->

