<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Form -->
<form:form modelAttribute="articleCommand" action="/wiki/" method="POST">
    <fieldset>
        <form:button class="form-control btn btn-primary">Anlegen</form:button>
        <form:label path="title">Titel</form:label>
        <form:input path="title" class="form-control"></form:input>

        <form:label path="tags">Tags</form:label>
        <form:input path="tags" class="form-control"></form:input>            
        
        <form:label path="alternativeTitle1">Weiterer Titel</form:label>
        <form:input path="alternativeTitle1" class="form-control"></form:input>    
        
        <form:label path="alternativeTitle2">Weiterer Titel</form:label>
        <form:input path="alternativeTitle2" class="form-control"></form:input>    

        <form:label path="alternativeTitle3">Weiterer Titel</form:label>
        <form:input path="alternativeTitle3" class="form-control"></form:input>    
        
    </fieldset>
    <fieldset>
        <form:label path="content">Inhalt</form:label>
        <form:textarea path="content" class="form-control" rows="25"></form:textarea>

        <form:label path="contentType">Format des Inhalts</form:label>    
        <form:select path="contentType" items="${contentTypes}" class="form-control"></form:select>
    </fieldset>
</form:form>
<!-- End Form -->

