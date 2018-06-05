<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<p>${msg}</p>

<!-- Form -->
<form:form enctype="multipart/form-data" action="/admin/imports/articles/" method="POST" modelAttribute="uploadCommand">
    <fieldset>
        <form:label path="file">Artikel-XML zum Hochladen auswählen</form:label>
        <form:input type="file" path="file" />
    </fieldset>
    <button class="btn btn-primary">Speichern</button>            
</form:form>
<!-- End Form -->

