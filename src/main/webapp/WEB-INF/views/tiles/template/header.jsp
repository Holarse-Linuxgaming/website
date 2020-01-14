<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Header -->
<header id="js-header" class="u-header u-header--static u-header--change-appearance" data-header-fix-moment="500" data-header-fix-effect="slide">

    <!-- Top Bar -->
    <div class="u-header__section u-header__section--hidden u-header__section--dark g-bg-holarse g-py-10">
        <div class="container">
            <div class="row flex-column flex-md-row justify-content-between align-items-center text-uppercase g-font-weight-600 g-color-white">
                <div class="col-auto g-px-15">
                    Eure Linuxspiele-Quelle seit fast 20 Jahren!
                </div>


                <div class="col-auto g-pos-rel g-px-15">
                    <ul class="list-inline g-overflow-hidden g-font-size-12 g-mt-minus-10 g-mx-minus-4 mb-0">
                        <!-- Login oder User -->
                        <s:authorize access="hasRole('ADMIN')">
                        <li class="list-inline-item g-mx-4 g-mt-10">
                            <a href="/admin" class="g-color-white g-color-primary--hover g-text-underline--none--hover">Admin-Bereich</a>
                        </li>
                        <li class="list-inline-item g-mx-4 g-mt-10">|</li>                        
                        </s:authorize>                           
                        <s:authorize access="hasRole('ANONYMOUS')">                        

                        <li class="list-inline-item g-mx-4 g-mt-10">
                            <a href="/register" class="g-color-white g-color-primary--hover g-text-underline--none--hover">Registrieren</a>
                        </li>
                            <li class="list-inline-item g-mx-4 g-mt-10">|</li>
                            <li class="list-inline-item g-mx-4 g-mt-10">
                                <a href="/login" class="g-color-white g-color-primary--hover g-text-underline--none--hover">Login</a>
                            </li>
                        </s:authorize>
                        <s:authorize access="hasRole('USER')">
                            <li class="list-inline-item g-mx-4 g-mt-10">
                                <a href="/users/${currentUser.login}" class="g-color-white g-color-primary--hover g-text-underline--none--hover">${currentUser.login}</a>
                            </li>      
                            <li class="list-inline-item g-mx-4 g-mt-10">
                                <a href="/logout" class="g-color-white g-color-primary--hover g-text-underline--none--hover">Logout</a>
                            </li>                         
                        </s:authorize>                            
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <!-- End Top Bar -->

    <!-- Header-Menu -->
    <div class="u-header__section u-header__section--light g-brd-bottom holarse-box-shadow g-transition-0_3 g-py-10" data-header-fix-moment-exclude="u-header__section--light g-bg-white g-brd-bottom g-py-10" data-header-fix-moment-classes="g-bg-white-opacity-0_7 u-shadow-v18 g-py-0">
        <nav class="js-mega-menu navbar navbar-expand-lg">
            <div class="container"> 
                <!-- Responsive Toggle Button -->
                <button class="navbar-toggler navbar-toggler-right btn g-line-height-1 g-brd-none g-pa-0 g-pos-abs g-top-3 g-right-0" type="button" aria-label="Toggle navigation" aria-expanded="false" aria-controls="navBar" data-toggle="collapse" data-target="#navBar">
                    <span class="hamburger hamburger--slider">
                        <span class="hamburger-box">
                            <span class="hamburger-inner"></span>
                        </span>
                    </span>
                </button>
                <!-- End Responsive Toggle Button -->

                <!-- Logo -->
                <a href="/" class="navbar-brand">
                    <img src="/assets/img/holarse_logo.svg" alt="Holarse - Spielen unter Linux" width="125%">
                </a>
                <!-- End Logo -->

                <!-- Navigation -->
                <div class="collapse navbar-collapse align-items-center flex-sm-row g-pt-10 g-pt-5--lg" id="navBar">
                    <ul class="navbar-nav text-uppercase g-font-weight-600 ml-auto u-main-nav-v5">
                        <li class="nav-item g-mx-20--lg">
                            <a href="/news" class="nav-link px-0">News</a>
                        </li>
                        <li class="nav-item g-mx-20--lg">
                            <a href="/finder" class="nav-link px-0">Spielefinder</a>
                        </li>
                        <li class="nav-item g-mx-20--lg active">
                            <a href="/server" class="nav-link px-0">Server</a>
                        </li>
                        <li class="nav-item g-mx-20--lg">
                            <a href="/community" class="nav-link px-0">Community</a>
                        </li>
                        <!-- Mega Menu Item -->
                        <li class="hs-has-mega-menu nav-item g-mx-20--lg" data-animation-in="fadeIn" data-animation-out="fadeOut" data-position="right">
                            <a id="mega-menu-label-1" class="nav-link g-px-0" href="#" aria-haspopup="true" aria-expanded="false">Genres
                                <i class="hs-icon hs-icon-arrow-bottom g-font-size-11 g-ml-7"></i>
                            </a>

                            <!-- Mega Menu -->
                            <div class="hs-mega-menu u-shadow-v11 font-weight-normal g-text-transform-none g-brd-top g-brd-primary g-brd-top-2 g-mt-17 g-mt-7--lg--scrolling" aria-labelledby="mega-menu-label-1">
                                <div class="row align-items-stretch">
                                    <div class="col-lg-auto">
                                        <section class="g-pa-20">
                                            <ul class="list-unstyled">
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">General Typography</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Headings Options</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Dividers</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Blockquote Blocks</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Box Shadows</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Testimonials</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Tagline Boxes</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Grid Layouts</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link active g-px-0 g-color-primary--hover">Alerts &amp; Messages</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Labels &amp; Badges</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Audio/Videos &amp; Images</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Paginations</a>
                                                </li>
                                            </ul>
                                        </section>
                                    </div>

                                    <div class="col-lg-auto g-brd-left--lg g-brd-gray-light-v5">
                                        <section class="g-pa-20 g-pl-5--lg">
                                            <ul class="list-unstyled">
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">General Buttons</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Brand &amp; Social Buttons</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Loading &amp; Hover Effects</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">General Icons</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Font Awesome Icons</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Line Icons</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Line Icons Pro</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Glyphicons Icons (Bootstrap)</a>
                                                </li>
                                            </ul>
                                        </section>
                                    </div>

                                    <div class="col-lg-auto g-brd-left--lg g-brd-gray-light-v5">
                                        <section class="g-pa-20 g-pl-5--lg">
                                            <ul class="list-unstyled">
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">General Typography</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Headings Options</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Dividers</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Blockquote Blocks</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Box Shadows</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Testimonials</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Tagline Boxes</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Grid Layouts</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Alerts &amp; Messages</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Labels &amp; Badges</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Audio/Videos &amp; Images</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Paginations</a>
                                                </li>
                                            </ul>
                                        </section>
                                    </div>

                                    <div class="col-lg-auto g-brd-left--lg g-brd-gray-light-v5">
                                        <section class="g-pa-20 g-pl-5--lg">
                                            <ul class="list-unstyled">
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">General Buttons</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Brand &amp; Social Buttons</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Loading &amp; Hover Effects</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">General Icons</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Font Awesome Icons</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Line Icons</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Line Icons Pro</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Glyphicons Icons (Bootstrap)</a>
                                                </li>
                                            </ul>
                                        </section>
                                    </div>

                                    <div class="col-lg-auto g-brd-left--lg g-brd-gray-light-v5">
                                        <section class="g-pa-20 g-pl-5--lg">
                                            <ul class="list-unstyled">
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">General Typography</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Headings Options</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Dividers</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Blockquote Blocks</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Box Shadows</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Testimonials</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Tagline Boxes</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Grid Layouts</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Alerts &amp; Messages</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Labels &amp; Badges</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Audio/Videos &amp; Images</a>
                                                </li>
                                                <li>
                                                    <a href="#" class="nav-link g-px-0 g-color-primary--hover">Paginations</a>
                                                </li>
                                            </ul>
                                        </section>
                                    </div>
                                </div>
                            </div>
                            <!-- End Mega Menu -->
                        </li>
                        <!-- End Mega Menu Item -->

                    </ul>
                    <!-- Search -->
                    <!-- Search Form -->
                    <form id="searchform-1" class="u-searchform-v1" method="POST" action="/search">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="input-group g-brd-primary--focus">
                            <input class="form-control rounded-0 u-form-control" type="search" placeholder="Linuxspiele finden..." id="query" name="term" value="${term}">
                            <div class="input-group-addon p-0">
                                <button class="btn rounded-0 btn-primary btn-md g-font-size-14 g-px-18" type="submit">
                                    <i class="fa fa-search"></i>
                                </button>
                            </div>
                        </div>
                    </form>
                    <!-- End Search Form -->
                    <!-- End Search -->               
                </div>
                <!-- End Navigation -->



            </div> <!-- Ende Container -->
        </nav>
    </div>
</header>
<!-- End Header -->