<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<section class="container">
    <div class="row">
        <!-- Login-Form -->
        <div class="col-md">
            <header>
                <h2>An Holarse anmelden</h2>
            </header>
            <form:form name="loginform" action="/login" method="POST">
                <div class="form-group">
                    <input type="login" class="form-control" name="login" aria-describedby="loginHelp" placeholder="Benutzername oder Email eingeben">
                    <small id="loginHelp" class="form-text text-muted">Du kannst dich mit deinem Benutzernamen oder deiner E-Mail-Adresse anmelden.</small>
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" name="password" placeholder="Password">
                </div>
                <button type="submit" class="btn btn-primary">Anmelden</button>
            </form:form>
                
            <footer class="text-center">
                <p>Noch kein Konto bei Holarse? <a href="/register">Hier Konto erstellen</a>!
            </footer>
        </div>

        <!-- Vorteile -->
        <div class="col-md">
            <div class="card">
                <div class="card-header">
                    Vorteiles eines Holarse-Kontos
                </div>
                <div class="card-body">
                    <p class="card-text">Ein Konto bei Holarse eröffnet einige Linuxgamer-Dienste für dich.Was man mit seinem Holarse-Konto alles anstellen kann, stellen wir hier kurz vor:</p>
                </div>                
                <div class="card-body">
                    <h5 class="card-title">Mitgestalten und Schreiben</h5>
                    <p class="card-text">Die Holarse-Artikel sind für jeden wie als Wiki bearbeitbar. So kannst du dich einfach beteiligen und auch Kommentare schreiben.</p>
                </div>
                <div class="card-body">
                    <h5 class="card-title">Mumble</h5>
                    <p class="card-text">Dein Holarse-Konto ermöglicht dir automatisch auch auf den Mumble-Server zuzugreifen.</p>
                </div>
                <div class="card-body">
                    <h5 class="card-title">Jabber</h5>
                    <p class="card-text">Wer noch nicht genug hat, kann mit dem Holarse-Konto direkt unseren Jabber-Server mitbenutzen.</p>
                </div>
                <div class="card-body">
                    <h5 class="card-title">Game Server für Linuxspieler</h5>
                    <p class="card-text">Wir stellen kostenlose und freie Game Server für Linuxspieler je nach Bedarf zur Verfügung.</p>
                </div>                
            </div>
        </div>
    </div>
</section>
