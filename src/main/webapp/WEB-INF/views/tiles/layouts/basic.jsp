<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>        

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
            
            <!-- Flash message verfügbar? -->
            <c:if test="${flashMessage != null}">
                <div class="alert alert-${flashMessage.cssMode}" role="alert">
                  <h4 class="alert-heading">${flashMessage.title}</h4>
                  <p>${flashMessage.message}</p>
                  <hr>
                  <p class="mb-0">${flashMessage.solution}</p>
                </div>            
            </c:if>            
            
            <!-- Body -->
            <tiles:insertAttribute name="body" />
        </main>

        <!-- Footer -->
        <tiles:insertAttribute name="footer" />


        <!-- JS Global Compulsory -->
        <script src="assets/vendor/jquery/jquery-3.3.1.min.js"></script>
        <script src="assets/vendor/bootstrap/bootstrap.bundle.min.js"></script>
        <script src="assets/vendor/masonry/masonry.pkgd.min.js"></script>
        <script src="assets/vendor/vue/vue.js"></script>
        
        <!-- JS Custom -->
        <script src="assets/js/custom.js"></script>
        
        <script src="assets/js/holarsevue.js"></script>        

        <script>
            $(document).ready(function () {
                console.log("Welcome to Holarse...");
            });
        </script>
    </body>
</html>
