<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="de">
    <head>
        <title><tiles:getAsString name="title" /></title>

        <!-- Meta -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- Favicon -->
        <link rel="shortcut icon" href="favicon.ico">    
    </head>
    <body>
        <!-- Header -->
        <tiles:insertAttribute name="header" />
        <!-- Body -->
        <tiles:insertAttribute name="body" />
        <!-- Footer -->
        <tiles:insertAttribute name="footer" />
    </body>
</html>