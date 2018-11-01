<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<header>
    <!-- Top bar -->
    <nav class="navbar navbar-expand-lg holarse-topbar">
        <a class="navbar-brand" href="/">Eure deutschsprache Linuxspiele-Quelle</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>  

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto p-2">

                <!-- Login oder User -->
                <s:authorize access="hasRole('ADMIN')">
                    <li class="nav-item">
                        <a class="nav-link" href="/admin/">Admin-Bereich</a>
                    </li>
                </s:authorize>            


                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" title="Neue Beiträge hinzufügen">
                        <i class="fas fa-plus"></i>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="/contact"><i class="fas fa-envelope"></i> Nachricht an uns</a>
                        <a class="dropdown-item" href="/hints/new"><i class="far fa-hand-point-right"></i> Schnellhinweis abgeben</a>
                        <s:authorize access="hasRole('USER')">
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="/news/new"><i class="far fa-newspaper"></i> News erstellen</a>
                            <a class="dropdown-item" href="/shortnews/new"><i class="far fa-newspaper"></i> Kurznews erstellen</a>                            
                            <a class="dropdown-item" href="/articles/new"><i class="far fa-file-alt"></i> Artikel erstellen</a>
                            <a class="dropdown-item" href="/forum/new"><i class="far fa-comments"></i> Forenbeitrag erstellen</a>
                        </s:authorize>
                    </div>
                </li>         

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
        </div>
    </nav>

    <!-- Menu bar -->
    <nav class="navbar navbar-expand-lg holarse-menubar">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <a class="navbar-brand" href="/" title="Frontpage">
            <img src="assets/img/logo-with-text.png" height="60" />
        </a>           

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/news/" title="Neuigkeiten aus der Linuxspiele-Welt">
                        <i class="fas fa-newspaper"></i>
                        News
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/hot-topics/" title="Aktuelle Beiträge aus der Linuxspiele-Welt">
                        <i class="fab fa-hotjar"></i>
                        Aktuelle Themen
                    </a>
                </li>                                                
                <li class="nav-item">
                    <a class="nav-link" href="/finder/" title="Spiele unter Linux; Windows-Spiele unter Linux">
                        <i class="fas fa-gamepad"></i>
                        Spiele finden
                    </a>
                </li>                
                <li class="nav-item">
                    <a class="nav-link" href="/categories/" title="Spiele-Datenbank nach Genre/Kategorien sortiert">
                        <i class="fas fa-sitemap"></i>
                        Kategorien
                    </a>
                </li>                                
                <li class="nav-item">
                    <a class="nav-link" href="/dedicated-servers/" title="Server für viele Spiele">
                        <i class="fas fa-server"></i>
                        Dedicated Servers
                    </a>
                </li>                                                
                <li class="nav-item">
                    <a class="nav-link" href="/emulators/" title="Playstation, Game Boy, GBA, SNES, Nintendo Wii">
                        <i class="fab fa-windows"></i>
                        Emulationen
                    </a>
                </li>                                                    
                <li class="nav-item">
                    <a class="nav-link" href="/hardware/" title="Gaming-Mäuse, Tasaturen, Grafikkarten, NVIDIA, AMD, Intel, Vulkan">
                        <i class="fas fa-cogs"></i>
                        Hardware &amp; Treiber
                    </a>
                </li>                      
                <li class="nav-item">
                    <a class="nav-link" href="/tools/" title="VOIP und Server-Browser">
                        <i class="fas fa-headset"></i>
                        Gaming Tools
                    </a>
                </li>                                      
                <li class="nav-item">
                    <a class="nav-link" href="/downloads/" title="Updates, Patches, Demos">
                        <i class="fas fa-cloud-download-alt"></i>
                        Downloads
                    </a>
                </li>                       
                <li class="nav-item">
                    <a class="nav-link" href="/forum/" title="Community, Fragen und Antworten">
                        <i class="fas fa-comments"></i>
                        Forum
                    </a>
                </li>                                                
            </ul>
            <form:form class="form-inline my-2 my-lg-0" method="POST" action="/search">
                <input class="form-control mr-sm-2" type="text" name="query" placeholder="Holarse durchsuchen" aria-label="Search" value="${query}"></input>
                <button class="btn btn-primary my-2 my-sm-0 btn-search" type="submit">Los!</button>
            </form:form>
        </div>
    </nav>
</header>