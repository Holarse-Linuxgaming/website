<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<section class="container">
    <header>
        <h2>Kontakt aufnehmen/News melden</h2>
    </header>
    <form:form modelAttribute="contactCommand" action="/contact/send" method="POST">
        <form:hidden path="userId" />
        <div class="form-group row">
            <label for="name" class="col-sm-2 col-form-label">Name</label>
            <div class="col-sm-10">
                <form:input path="name" class="form-control" placeholder="Dein Name" />
                <small id="emailHelp" class="form-text text-muted">Dein Name, wie wir dich bei Quellenangaben nennen sollen (Automatisch dein Nick, wenn du angemeldet bist)</small>
            </div>
        </div>
        <div class="form-group row">
            <label for="content" class="col-sm-2 col-form-label">Deine Nachricht</label>
            <div class="col-sm-10">
                <form:textarea rows="25" path="content" class="form-control" placeholder="Deine Nachricht an uns" />
                <small id="contentHelp" class="form-text text-muted">Deine Nachricht an uns</small>
            </div>
        </div>   
                
        <button type="submit" class="btn btn-primary">Nachricht abschicken</button>                
    </form:form>
</section>