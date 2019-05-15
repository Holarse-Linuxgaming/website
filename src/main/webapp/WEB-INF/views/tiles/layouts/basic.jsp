<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="de">

<!-- multipage/blog-magazine/classic/bm-classic-single-1 -->

<head>
  <!-- Title -->
  <title>${title} | Holarse - Spielen unter Linux</title>

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
  <link rel="stylesheet" href="assets/vendor/bootstrap/offcanvas.css">
  <!-- CSS Global Icons -->
  <link rel="stylesheet" href="assets/vendor/fontawesome/fontawesome.min.css">
  <link rel="stylesheet" href="assets/vendor/icon-line/css/simple-line-icons.css">
  <link rel="stylesheet" href="assets/vendor/icon-etlinefont/style.css">
  <link rel="stylesheet" href="assets/vendor/icon-line-pro/style.css">
  <link rel="stylesheet" href="assets/vendor/icon-hs/style.css">
  <link rel="stylesheet" href="assets/vendor/dzsparallaxer/dzsparallaxer.css">
  <link rel="stylesheet" href="assets/vendor/dzsparallaxer/dzsscroller/scroller.css">
  <link rel="stylesheet" href="assets/vendor/dzsparallaxer/advancedscroller/plugin.css">
  <link rel="stylesheet" href="assets/vendor/animate.css">
  <link rel="stylesheet" href="assets/vendor/hamburgers/hamburgers.min.css">
  <link rel="stylesheet" href="assets/vendor/hs-megamenu/src/hs.megamenu.css">  
  <link rel="stylesheet" href="assets/vendor/malihu-scrollbar/jquery.mCustomScrollbar.min.css">
  <link rel="stylesheet" href="assets/vendor/slick-carousel/slick/slick.css">
  <link rel="stylesheet" href="assets/vendor/fancybox/jquery.fancybox.css">

  <link rel="stylesheet" href="assets/vendor/highlightjs/styles/hybrid.css">  
  
  <!-- CSS Unify -->
  <link rel="stylesheet" href="assets/vendor/unify/unify-core.css">
  <link rel="stylesheet" href="assets/vendor/unify/unify-components.css">
  <link rel="stylesheet" href="assets/vendor/unify/unify-globals.css">

  <!-- CSS Customization -->
  <link rel="stylesheet" href="assets/custom/css/custom.css">
</head>

<body>
  <main>
    <tiles:insertAttribute name="header" />      

     <!-- News Content -->
     <section class="g-pt-30 g-pb-100">
      <div class="container">
        <tiles:insertAttribute name="body" />          
      </div>
    </section>
    <!-- End News Content -->

      <!-- Page Title -->
      <section class="g-bg-secondary g-py-50" hidden>
        <div class="container">
          <div class="d-sm-flex text-center">
            <div class="align-self-center">
              <h2 class="h3 g-mb-10 g-mb-0--md">Blog Single Page</h2>
            </div>

            <div class="align-self-center ml-auto">
              <ul class="u-list-inline">
                <li class="list-inline-item g-mr-5">
                  <a class="u-link-v5 g-color-main" href="#!">Home</a>
                  <i class="g-color-gray-light-v2 g-ml-5">/</i>
                </li>
                <li class="list-inline-item g-mr-5">
                  <a class="u-link-v5 g-color-main" href="#!">Pages</a>
                  <i class="g-color-gray-light-v2 g-ml-5">/</i>
                </li>
                <li class="list-inline-item g-color-primary">
                  <span>Blog Single Page</span>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </section>
      <!-- End Page Title -->

      <tiles:insertAttribute name="footer" />     

    <a class="js-go-to u-go-to-v1" href="#" data-type="fixed" data-position='{
     "bottom": 15,
     "right": 15
   }' data-offset-top="400" data-compensation="#js-header" data-show-effect="zoomIn">
      <i class="hs-icon hs-icon-arrow-top"></i>
    </a>
  </main>

  <!-- JS Global Compulsory -->
  <script src="assets/vendor/jquery/jquery.min.js"></script>
  <script src="assets/vendor/jquery-migrate/jquery-migrate.min.js"></script>
  <script src="assets/vendor/popper.js/popper.min.js"></script>
  <script src="assets/vendor/bootstrap/bootstrap.min.js"></script>
  <script src="assets/vendor/jquery-ui/jquery-ui.min.js"></script>

  <script src="assets/vendor/bootstrap/offcanvas.js"></script>
  <script src="assets/vendor/fontawesome/fontawesome.min.js"></script>
  
  <!-- JS Implementing Plugins -->
  <script src="assets/vendor/hs-megamenu/src/hs.megamenu.js"></script>  
  <script src="assets/vendor/dzsparallaxer/dzsparallaxer.js"></script>
  <script src="assets/vendor/dzsparallaxer/dzsscroller/scroller.js"></script>
  <script src="assets/vendor/dzsparallaxer/advancedscroller/plugin.js"></script>
  <script src="assets/vendor/masonry/dist/masonry.pkgd.min.js"></script>
  <script src="assets/vendor/imagesloaded/imagesloaded.pkgd.min.js"></script>
  <script src="assets/vendor/malihu-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
  <script src="assets/vendor/slick-carousel/slick/slick.js"></script>
  <script src="assets/vendor/fancybox/jquery.fancybox.min.js"></script>

  <!-- JS Unify -->
  <script src="assets/vendor/unify/hs.core.js"></script>

  <script src="assets/vendor/unify/components/hs.header.js"></script>
  <script src="assets/vendor/unify/helpers/hs.hamburgers.js"></script>

  <script src="assets/vendor/unify/components/hs.dropdown.js"></script>
  <script src="assets/vendor/unify/components/hs.scrollbar.js"></script>
  <script src="assets/vendor/unify/components/hs.popup.js"></script>
  <script src="assets/vendor/unify/components/hs.carousel.js"></script>

  <script src="assets/vendor/unify/components/hs.go-to.js"></script>

  <script src="assets/vendor/highlightjs/highlight.pack.js"></script>  
  <script src="assets/vendor/vuejs/vue.min.js"></script>
  <!-- JS Custom -->
  <script src="assets/custom/js/holarse-vue.js"></script>  
  <script src="assets/custom/js/custom.js"></script>

  <!-- JS Plugins Init. -->
  <script>
    $(document).on('ready', function () {
      // initialization of go to
      $.HSCore.components.HSGoTo.init('.js-go-to');

      // initialization of carousel
      $.HSCore.components.HSCarousel.init('.js-carousel');

      // initialization of HSDropdown component
      $.HSCore.components.HSDropdown.init($('[data-dropdown-target]'), {
        afterOpen: function(){
          $(this).find('input[type="search"]').focus();
        }
      });

      // initialization of HSScrollBar component
      $.HSCore.components.HSScrollBar.init($('.js-scrollbar'));

      // initialization of masonry
      $('.masonry-grid').imagesLoaded().then(function () {
        $('.masonry-grid').masonry({
          columnWidth: '.masonry-grid-sizer',
          itemSelector: '.masonry-grid-item',
          percentPosition: true
        });
      });

      // initialization of popups
      $.HSCore.components.HSPopup.init('.js-fancybox');
    });

    $(window).on('load', function () {
      // initialization of header
      $.HSCore.components.HSHeader.init($('#js-header'));
      $.HSCore.helpers.HSHamburgers.init('.hamburger');

      // initialization of HSMegaMenu component
      $('.js-mega-menu').HSMegaMenu();      
    });
  </script>
  
  <script>
      $(document).ready(function () {
          console.log("Welcome to Holarse...");
      });
  </script>
</body>

</html>
