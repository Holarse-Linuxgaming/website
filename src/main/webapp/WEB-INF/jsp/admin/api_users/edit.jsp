<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Form -->
<form:form action="/admin/api_users/${tag.id}" method="POST">
    <form:button class="btn btn-primary">Speichern</form:button>
        <fieldset>
            <form:label path="login">Login</form:label>
            <form:input path="login" class="form-control" />

            <form:label path="roleName">Rolle</form:label>
            <form:input path="roleName" class="form-control" />
            
            <form:label path="validUntil">Gültig bis</form:label>
            <form:input path="validUntil" class="form-control" />

            <form:label path="active">Aktiv</form:label>
            <form:checkbox path="active" class="form-control" />
        </fieldset>
</form:form>
<!-- End Form -->

