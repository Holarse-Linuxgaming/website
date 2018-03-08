<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<section class="container">
    <header>
        <h2>Holarse-Konto erstellen</h2>
    </header>
    <form:form modelAttribute="createAccountCommand" action="${flowExecutionUrl}">
        <div class="form-group row">
            <label for="email" class="col-sm-2 col-form-label">Email</label>
            <div class="col-sm-10">
                <input type="email" class="form-control" name="email" aria-describedby="emailHelp" placeholder="Email-Adresse eingeben">
                <small id="emailHelp" class="form-text text-muted">Deine E-Mail-Adresse zum Bestätigen des Kontos und zur Wiederherstellung</small>
            </div>
        </div>
        <div class="form-group row">
            <label for="login" class="col-sm-2 col-form-label">Benutzername</label>
            <div class="col-sm-10">
                <input type="login" class="form-control" name="login" aria-describedby="loginHelp" placeholder="Benutzernamen eingeben">
                <small id="loginHelp" class="form-text text-muted">Wie du auf Holarse heissen möchtest</small>
            </div>
        </div>
        <div class="form-group row">
            <label for="password" class="col-sm-2 col-form-label">Passwort</label>
            <div class="col-sm-10">    
                <input type="password" class="form-control" name="password" placeholder="Password">
            </div>
        </div>
        <div class="form-group row">
            <label for="passwordConfirmation" class="col-sm-2 col-form-label">Passwort Wiederholung</label>
            <div class="col-sm-10">                        
                <input type="password" class="form-control" name="passwordConfirmation" placeholder="Password wiederholen gegen Vertipper">
            </div>
        </div>        
        <button type="submit" class="btn btn-primary" name="_eventId_register">Registrieren</button>
    </form:form>
    <footer class="text-center">
        <p>Du hast schon ein Konto? Dann <a class="" href="/login">einloggen</a>.</p>
    </footer>
</section>
