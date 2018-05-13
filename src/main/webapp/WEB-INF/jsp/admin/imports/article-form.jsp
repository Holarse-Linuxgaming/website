<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Form -->
<form:form enctype="multipart/form-data" action="/admin/imports/articles/" method="POST">
    <form:button class="btn btn-primary">Speichern</form:button>
    <fieldset>
        <form:label path="file">Artikel-XML zum Hochladen auswählen</form:label>
        <input type="file" name="file" />
    </fieldset>
</form:form>
<!-- End Form -->

