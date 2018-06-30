<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Form -->
<form:form modelAttribute="command" action="${user.url}" method="POST">
    <form:button class="btn btn-primary">Speichern</form:button>
    <a href="${user.url}" class="btn btn-default">Zurück</a>
    <fieldset>
        <legend>Passwort ändern - Leerlassen zum Beibehalten</legend>
        <form:label path="password">Passwort</form:label>
        <form:input path="password" class="form-control"></form:input>

        <form:label path="passwordConfirmation">Passwort Wiederholung</form:label>
        <form:input path="passwordConfirmation" class="form-control"></form:input>            
    </fieldset>
    <fieldset>
        <legend>Benutzerdaten</legend>
        <form:label path="signature">Signatur</form:label>
        <form:textarea path="signature" class="form-control"></form:textarea>
        
        <form:label path="email">Email-Adresse</form:label>
        <form:input path="email" class="form-control"></form:input>    
    </fieldset>
</form:form>
<!-- End Form -->

