<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>

<!-- Header -->
    <header id="js-header" class="u-header u-header--change-appearance" data-header-fix-moment="500" data-header-fix-effect="slide">
      <div class="u-header__section u-header__section--light g-bg-white g-transition-0_3 g-py-15" data-header-fix-moment-exclude="g-bg-white g-py-15" data-header-fix-moment-classes="g-bg-white-opacity-0_8 u-shadow-v18 g-py-5">
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
              <img src="/assets/custom/img/logo.svg" alt="Image Description">
            </a>
            <!-- End Logo -->

            <!-- Navigation -->
            <div class="collapse navbar-collapse align-items-center flex-sm-row g-pt-10 g-pt-5--lg g-mr-40--sm" id="navBar">
              <ul class="navbar-nav text-uppercase g-font-weight-600 mx-auto">
                <li class="nav-item g-mx-25--lg">
                  <a href="#" class="nav-link px-0">Home
                
              </a>
                </li>
                <li class="nav-item g-mx-25--lg">
                  <a href="#" class="nav-link px-0">Features
                
              </a>
                </li>
                <li class="nav-item g-mx-25--lg active">
                  <a href="#" class="nav-link px-0">Shortcodes
                <span class="sr-only">(current)</span>
              </a>
                </li>
                <li class="nav-item g-mx-25--lg">
                  <a href="#" class="nav-link px-0">Pages
                
              </a>
                </li>
                <li class="nav-item g-mx-25--lg">
                  <a href="#" class="nav-link px-0">Demos
                
              </a>
                </li>
                <li class="nav-item g-mx-25--lg g-mr-0--lg">
                    <s:authorize access="hasRole('ROLE_ANONYMOUS')">
                        <a href="/login" class="nav-link px-0">Login</a>
                    </s:authorize>
                    <s:authorize access="hasRole('ROLE_USER')">
                        <a href="<c:url value="/logout" />" class="nav-link px-0">Logout</a>
                    </s:authorize>
                </li>
              </ul>
            </div>
            <!-- End Navigation -->

            <!-- Search -->
            <div class="d-inline-block g-pos-rel g-valign-middle g-ml-30 g-ml-0--lg">
              <a href="#" class="g-font-size-18 g-color-main" aria-haspopup="true" aria-expanded="false" aria-controls="searchform-1" data-dropdown-target="#searchform-1" data-dropdown-type="css-animation" data-dropdown-duration="300" data-dropdown-animation-in="fadeInUp"
              data-dropdown-animation-out="fadeOutDown">
                <i class="fa fa-search"></i>
              </a>

              <!-- Search Form -->
              <form id="searchform-1" class="u-searchform-v1 u-dropdown--css-animation u-dropdown--hidden g-bg-white g-pa-10 g-mt-30--lg g-mt-20--lg--scrolling">
                <div class="input-group g-brd-primary--focus">
                  <input class="form-control rounded-0 u-form-control" type="search" placeholder="Enter Your Search Here...">

                  <div class="input-group-addon p-0">
                    <button class="btn rounded-0 btn-primary btn-md g-font-size-14 g-px-18" type="submit">Go</button>
                  </div>
                </div>
              </form>
              <!-- End Search Form -->
            </div>
            <!-- End Search -->
          </div>
        </nav>
      </div>
    </header>
    <!-- End Header -->