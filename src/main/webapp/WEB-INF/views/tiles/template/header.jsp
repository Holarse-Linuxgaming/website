<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>

<!-- Header -->
    <header id="js-header" class="u-header u-header--sticky-top u-header--toggle-section u-header--change-appearance" data-header-fix-moment="300">
      <!-- Top Bar -->
      <div class="u-header__section u-header__section--hidden u-header__section--dark g-bg-holarse g-py-13">
        <div class="container">
          <div class="row flex-column flex-md-row align-items-center justify-content-between text-uppercase g-color-white g-font-size-11 g-mx-minus-15">
            <!-- Responsive Toggle Button -->
            <button class="g-hidden-md-up d-block btn btn-xs u-btn-primary g-brd-none g-line-height-1 mx-auto" type="button" aria-controls="dropdown-megamenu" aria-expanded="false" aria-label="Toggle navigation" data-toggle="collapse" data-target="#dropdown-megamenu">
              <span class="hamburger hamburger--slider">
            <span class="hamburger-box">
              <span class="hamburger-inner g-bg-white"></span>
              </span>
              </span>
            </button>
            <!-- End Responsive Toggle Button -->

            <div class="col-auto g-px-15 w-100 g-width-auto--md">
              <ul id="dropdown-megamenu" class="d-md-block collapse list-inline g-line-height-1 g-mx-minus-4 mb-0">
                <li class="d-block d-md-inline-block g-mx-4">
                    <a href="/">HOLARSE - SPIELEN unter LINUX</a>
                </li>
                <li class="d-block g-hidden-md-down d-md-inline-block g-mx-4">|</li>
                <li class="d-block d-md-inline-block g-mx-4">
                  <a href="#" class="g-color-white g-color-primary--hover g-text-underline--none--hover">Kontakt</a>
                </li>
                <li class="d-block g-hidden-md-down d-md-inline-block g-mx-4">|</li>
                <li class="d-block d-md-inline-block g-mx-4">
                  <a href="#" class="g-color-white g-color-primary--hover g-text-underline--none--hover">Über Holarse</a>
                </li>
                <li class="d-block g-hidden-md-down d-md-inline-block g-mx-4">|</li>
                <li class="hs-has-sub-menu d-block d-md-inline-block g-pos-rel g-mx-4">
                  <a href="#" id="dropdown-invoker-3" class="g-color-white g-color-primary--hover g-text-underline--none--hover" aria-haspopup="true" aria-expanded="false" aria-controls="dropdown-3">Holarse Gaming Services
              </a>
                  <ul id="dropdown-3" class="hs-sub-menu list-unstyled g-bg-gray-dark-v1 g-py-10 g-px-20 g-mt-13" aria-labelledby="dropdown-invoker-3">
                    <li class="g-py-10">
                      <a href="#" class="d-block g-text-underline--none--hover g-color-white g-color-primary--hover">Dropdown Item</a>
                    </li>
                    <li class="g-py-10">
                      <a href="#" class="d-block g-text-underline--none--hover g-color-white g-color-primary--hover">Dropdown Item</a>
                    </li>
                    <li class="hs-has-sub-menu g-py-10">
                      <a href="#" id="dropdown-invoker-4" class="d-block g-text-underline--none--hover g-color-white g-color-primary--hover" aria-haspopup="true" aria-expanded="false" aria-controls="dropdown-4">Dropdown Item
                  </a>
                      <ul id="dropdown-4" class="hs-sub-menu list-unstyled g-bg-gray-dark-v1 g-py-10 g-px-20" aria-labelledby="dropdown-invoker-4">
                        <li class="g-py-10">
                          <a href="#" class="d-block g-text-underline--none--hover g-color-white g-color-primary--hover">Dropdown Item</a>
                        </li>
                        <li class="g-py-10">
                          <a href="#" class="d-block g-text-underline--none--hover g-color-white g-color-primary--hover">Dropdown Item</a>
                        </li>
                        <li class="hs-has-sub-menu g-py-10">
                          <a href="#" id="dropdown-invoker-5" class="d-block g-text-underline--none--hover g-color-white g-color-primary--hover" aria-haspopup="true" aria-expanded="false" aria-controls="dropdown-5">Dropdown Item
                      </a>
                          <ul id="dropdown-5" class="hs-sub-menu list-unstyled g-bg-gray-dark-v1 g-py-10 g-px-20" aria-labelledby="dropdown-invoker-5">
                            <li class="g-py-10">
                              <a href="#" class="d-block g-text-underline--none--hover g-color-white g-color-primary--hover">Dropdown Item</a>
                            </li>
                            <li class="g-py-10">
                              <a href="#" class="d-block g-text-underline--none--hover g-color-white g-color-primary--hover">Dropdown Item</a>
                            </li>
                          </ul>
                        </li>
                      </ul>
                    </li>
                  </ul>
                </li>
              </ul>
            </div>

            <div class="col-auto g-px-15">
              <ul class="list-inline g-line-height-1 g-mt-minus-10 g-mx-minus-4 mb-0">
                <li class="list-inline-item g-mx-4 g-mt-10">
                    <s:authorize access="hasRole('ANONYMOUS')">
                        <a href="/login" class="g-color-white g-color-primary--hover g-text-underline--none--hover">Login</a>
                    </s:authorize>
                    <s:authorize access="hasRole('USER')">
                        <a href="/users/${currentUser.login}" class="g-color-white g-color-primary--hover g-text-underline--none--hover">${currentUser.login}</a>
                    </s:authorize>                        
                </li>
                <li class="list-inline-item g-mx-4 g-mt-10">|</li>
                <li class="list-inline-item g-mx-4 g-mt-10">
                    <s:authorize access="hasRole('ANONYMOUS')">
                        <a href="/register" class="g-color-white g-color-primary--hover g-text-underline--none--hover">Registrieren</a>
                    </s:authorize>
                    <s:authorize access="hasRole('USER')">
                        <a href="/logout" class="g-color-white g-color-primary--hover g-text-underline--none--hover">Logout</a>
                    </s:authorize>                        
                </li>
                <li class="list-inline-item g-mx-4 g-mt-10">
                  <!-- Search -->
                  <div class="g-pos-rel">
                    <a href="#" class="g-font-size-14 g-color-white g-color-primary--hover g-ml-5" aria-haspopup="true" aria-expanded="false" aria-controls="searchform-6" data-dropdown-target="#searchform-6" data-dropdown-type="css-animation" data-dropdown-duration="300"
                    data-dropdown-animation-in="fadeInUp" data-dropdown-animation-out="fadeOutDown">
                      <i class="fa fa-search"></i>
                    </a>

                    <!-- Search Form -->
                    <form id="searchform-6" class="u-searchform-v1 g-bg-black g-box-shadow-none g-pa-10 g-mt-10">
                      <div class="input-group g-brd-primary--focus">
                        <input class="form-control rounded-0 u-form-control g-brd-white" type="search" placeholder="Holarse durchsuchen...">
                        <div class="input-group-addon p-0">
                          <button class="btn rounded-0 btn-primary btn-md g-font-size-14 g-px-18" type="submit">Go</button>
                        </div>
                      </div>
                    </form>
                    <!-- End Search Form -->
                  </div>
                  <!-- End Search -->
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
      <!-- End Top Bar -->

      <div class="u-header__section u-header__section--light g-bg-white-opacity-0_8 g-py-10" data-header-fix-moment-exclude="g-bg-white-opacity-0_8 g-py-10" data-header-fix-moment-classes="g-bg-white u-shadow-v18 g-py-0">
        <nav class="navbar navbar-expand-lg">
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
              <img src="assets/custom/img/logo.svg" alt="Holarse - Spielen unter Linux Logo">
            </a>
            <!-- End Logo -->

            <!-- Navigation -->
            <div class="collapse navbar-collapse align-items-center flex-sm-row g-pt-10 g-pt-5--lg" id="navBar">
              <ul class="navbar-nav text-uppercase g-font-weight-600 ml-auto">
                <li class="nav-item g-mx-20--lg">
                  <a href="#" class="nav-link px-0">News
                
              </a>
                </li>
                <li class="nav-item g-mx-20--lg">
                  <a href="/articles/" class="nav-link px-0">Artikel
                
              </a>
                </li>
                <li class="nav-item g-mx-20--lg active">
                  <a href="#" class="nav-link px-0">Gameserver
                <span class="sr-only">(current)</span>
              </a>
                </li>
                <li class="nav-item g-mx-20--lg">
                  <a href="#" class="nav-link px-0">Spielefinder
                
              </a>
                </li>
                <li class="nav-item g-mx-20--lg">
                  <a href="#" class="nav-link px-0">Downloads
                
              </a>
                </li>
              </ul>
            </div>
            <!-- End Navigation -->
          </div>
        </nav>
      </div>
    </header>
    <!-- End Header -->