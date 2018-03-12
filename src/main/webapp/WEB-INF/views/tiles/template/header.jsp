<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<header>
    <!-- Top bar -->
    <nav class="navbar navbar-expand-lg holarse-topbar">
     
        <span class="navbar-text">
          Eure deutschsprache Linuxspiele-Quelle
        </span>         
        <ul class="navbar-nav ml-auto p-2">
            <!-- Login oder User -->
            <s:authorize access="hasRole('ANONYMOUS')">
                <li class="nav-item">
                    <a class="nav-link" href="/login">Login</a>
                </li>
            </s:authorize>

            <s:authorize access="hasRole('USER')">
                <li class="nav-item">                
                    <a class="nav-link" href="/users/${currentUser.login}">${currentUser.login}</a>
                </li>
            </s:authorize>       

            <!-- Register oder Logout -->
            <s:authorize access="hasRole('ANONYMOUS')">
                <li class="nav-item">
                    <a class="nav-link" href="/register">Registrieren</a>
                </li>
            </s:authorize>

            <s:authorize access="hasRole('USER')">
                <form:form class="form-inline" method="POST" action="/logout">
                    <button class="btn btn-link" type="submit">Ausloggen</button>
                </form:form>
            </s:authorize>             
        </ul>
    </nav>

    <!-- Menu bar -->
    <nav class="navbar navbar-expand-lg holarse-menubar">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <a class="navbar-brand" href="/">
            <img src="assets/img/logo-with-text.png" height="60" />
        </a>           
                       
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#">News</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Spielefinder</a>
                </li>                
                <li class="nav-item">
                    <a class="nav-link" href="#">Kategorien</a>
                </li>                                
            </ul>
            <form:form class="form-inline my-2 my-lg-0" method="POST" action="/search">
                <input class="form-control mr-sm-2" type="search" name="query" placeholder="Holarse durchsuchen" aria-label="Search" value="${query}"></input>
                <button class="btn btn-primary my-2 my-sm-0 btn-search" type="submit">Los!</button>
            </form:form>
        </div>
    </nav>
</header>