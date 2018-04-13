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

        <!-- CSS Customization -->
        <link rel="stylesheet" href="assets/css/custom.css">
    </head>

    <body>
        <!-- Header -->
        <tiles:insertAttribute name="header" />

        <main class="content container-fluid">
            <!-- Body -->
            <tiles:insertAttribute name="body" />
        </main>

        <!-- Footer -->
        <tiles:insertAttribute name="footer" />


        <!-- JS Global Compulsory -->
        <script src="assets/vendor/jquery/jquery-3.3.1.min.js"></script>
        <script src="assets/vendor/bootstrap/bootstrap.bundle.min.js"></script>
        <script src="assets/vendor/masonry/masonry.pkgd.min.js"></script>
        
        <!-- JS Custom -->
        <script src="assets/js/custom.js"></script>

        <script>
            $(document).ready(function () {
                console.log("Welcome to Holarse...");
            });
        </script>
    </body>
</html>
