<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<p>
    Hier kann eine neue Videonews angelegt werden. Videonews erhalten automatisch ein Frontpage-Eintrag.
</p>

<!-- Form -->
<form:form modelAttribute="command" action="/shortnews/" method="POST" data-name="shortnews-form">
    <fieldset>
        <form:label path="title">Titel</form:label>
        <form:input path="title" class="form-control" placeholder="Der Titel der Kurznews"></form:input>
    </fieldset>
    <fieldset>
        <label>Bild oder Text?</label>
        <form:label path="teaser">Teaser-Text</form:label>
        <form:input path="teaser" class="form-control" placeholder="Teaser-Text für die Kurznews - wird bei einem Video automatisch gesetzt"></form:input>

        <form:label path="teaserImage">Teaser-Bild</form:label>
        <form:input path="teaserImage" class="form-control" placeholder="Teaser-Bild für die Kurznews - wird bei einem Video automatisch gesetzt"></form:input>        
    </fieldset>
    <fieldset>
        <form:label path="link">Link</form:label>
        <form:input path="link" class="form-control" placeholder="Der Link, wo es hingeht, oder Video-Url"></form:input>
    </fieldset>
    <form:button class="form-control">Anlegen</form:button>
</form:form>
<!-- End Form -->

