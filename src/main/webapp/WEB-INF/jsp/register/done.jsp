<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section class="container">

    <div class="card">
        <div class="card-header">
            Registrierung abgeschlossen
        </div>
        <div class="card-body">
            <h5 class="card-title">Verifizierungsmail verschickt.</h5>
            <p class="card-text">Bitte aktivieren das Konto über den Link in der Mail, die wir gerade an ${verificationUser.email} geschickt haben.</p>
            <a href="/register/verify/${verificationUser.verificationKey}">Verifizierungslink</a>
        </div>
    </div>

</section>
