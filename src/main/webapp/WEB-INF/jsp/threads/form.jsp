<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Form -->
<form:form modelAttribute="command" action="${forum.urlid}" method="POST">
    <form:hidden path="forum" />
    
    <form:button class="form-control">Speichern</form:button>    
    <fieldset>
        <form:label path="title">Titel</form:label>
        <form:input path="title" class="form-control"></form:input>
    <fieldset>
        <form:label path="content">Inhalt</form:label>
        <form:textarea path="content" class="form-control" rows="25"></form:textarea>

        <form:label path="contentType">Format des Inhalts</form:label>    
        <form:select path="contentType" items="${contentTypes}" class="form-control"></form:select>
    </fieldset>

</form:form>
<!-- End Form -->