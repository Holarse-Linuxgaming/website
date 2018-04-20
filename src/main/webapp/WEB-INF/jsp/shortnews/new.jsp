<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<p>
    Hier kann eine neue Kurznews angelegt werden. Kurznews erhalten automatisch ein Frontpage-Eintrag.
</p>

<!-- Form -->
<form:form modelAttribute="command" action="/shortnews/" method="POST">
    <fieldset>
        <form:label path="title">Titel</form:label>
        <form:input path="title" class="form-control"></form:input>

        <form:label path="teaser">Teaser</form:label>
        <form:input path="teaser" class="form-control"></form:input>
        
        <form:label path="link">Link</form:label>
        <form:input path="link" class="form-control"></form:input>
    </fieldset>
    <form:button class="form-control">Anlegen</form:button>
</form:form>
<!-- End Form -->

