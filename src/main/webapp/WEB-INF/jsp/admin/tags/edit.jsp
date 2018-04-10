<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Form -->
<form:form modelAttribute="tagCommand" action="/admin/tags/${tag.id}/" method="PUT">
    <form:button class="btn btn-primary">Speichern</form:button>
        <fieldset>
            <form:label path="name">Name</form:label>
            <form:input path="name" class="form-control"></form:input>

            <form:label path="aliasId">Alias</form:label>
            <form:select path="aliasId" class="form-control">
                <form:option value="" label="Keine Auswahl" />
                <form:options items="${allTags}" itemLabel="name" itemValue="id" />
            </form:select>

            <form:label path="tagGroup">TagGroup</form:label>
            <form:input path="tagGroup" class="form-control"></form:input>    
        </fieldset>
</form:form>
<!-- End Form -->

