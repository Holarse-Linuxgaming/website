<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="de">
    <head>
        <!-- Title -->
        <title>Holarse - Spielen unter Linux</title>

        <!-- Required Meta Tags Always Come First -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="x-ua-compatible" content="ie=edge">

        <base href="/">

        <!-- Favicon -->
        <link rel="shortcut icon" href="assets/img/favicon.ico">

        <!-- CSS Global Compulsory -->
        <link rel="stylesheet" href="assets/vendor/bootstrap/bootstrap.min.css">
        <link rel="stylesheet" href="assets/vendor/fontawesome/fontawesome-all.min.css">
        <link rel="stylesheet" href="assets/vendor/jquery-ui/jquery-ui.min.css">

        <!-- CSS Customization -->
        <link rel="stylesheet" href="assets/css/admin.css">
    </head>

    <body>
        <!-- Header -->
        <header>
            <!-- Top bar -->
            <nav class="navbar navbar-dark bg-dark navbar-expand-lg">
                <a class="navbar-brand" href="/admin/">HOLARSE ADMIN-Bereich</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>                

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav ml-auto p-2">
                        <li class="nav-item">
                            <a class="nav-link" href="/admin/users/">Benutzer</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/admin/frontpage/">Frontpage</a>
                        </li>                        
                        <li class="nav-item">
                            <a class="nav-link" href="/admin/wiki/">Wiki</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/admin/news/">News</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/admin/tags/">Tags</a>
                        </li>                        
                        <li class="nav-item">
                            <a class="nav-link" href="/admin/import/">Import</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Erstellen
                            </a>
                            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <a class="dropdown-item" href="/messages/new">Nachricht an uns</a>
                                <a class="dropdown-item" href="/hints/new">Hinweis abschicken</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="/news/new">News erstellen</a>
                                <a class="dropdown-item" href="/articles/new">Artikel erstellen</a>
                                <a class="dropdown-item" href="/forums/new">Forenbeitrag erstellen</a>
                            </div>
                        </li>  
                        <li class="nav-item">
                            <a class="nav-link" href="/">Zurück</a>
                        </li>
                    </ul>
                </div>
            </nav>
        </header>

        <main class="content container-fluid">
            <!-- Body -->
            <tiles:insertAttribute name="body" />
        </main>

        <!-- JS Global Compulsory -->
        <script src="assets/vendor/jquery/jquery-3.3.1.min.js"></script>
        <script src="assets/vendor/bootstrap/bootstrap.bundle.min.js"></script>
        <script src="assets/vendor/jquery-ui/jquery-ui.min.js"></script>
        
        <!-- JS Custom -->
        <script src="assets/js/admin.js"></script>

        <script>
            $(document).ready(function () {
                console.log("Welcome to Holarse...");
            });
        </script>
    </body>
</html>
