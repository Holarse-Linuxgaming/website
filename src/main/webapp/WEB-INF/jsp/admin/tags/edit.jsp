<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Form -->
<form:form modelAttribute="tagCommand" action="/admin/tags/${tag.id}/" method="POST">
    <form:button class="btn btn-primary">Speichern</form:button>
        <fieldset>
            <form:label path="name">Name</form:label>
            <form:input path="name" class="form-control"></form:input>

            <form:label path="alias.id">Alias</form:label>
            <form:select path="alias.id" class="form-control">
                <form:option value="" label="Keine Auswahl" />
                <form:options items="${tags}" itemLabel="name" itemValue="id" />
            </form:select>

            <form:label path="tagGroup.id">TagGroup</form:label>
            <form:select path="tagGroup.id" class="form-control">
                <form:option value="" label="Keine Gruppe" />
                <form:options items="${tagGroups}" itemLabel="name" itemValue="id" />
            </form:select>
        </fieldset>
</form:form>
<!-- End Form -->

