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
    <link rel="shortcut icon" href="assets/custom/img/favicon.ico">

    <!-- Fonts selbst mitliefern -->

    <!-- CSS Global Compulsory -->
    <link rel="stylesheet" href="assets/vendor/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="assets/vendor/bootstrap/offcanvas.css">

    <link rel="stylesheet" href="assets/vendor/icon-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/vendor/icon-line/css/simple-line-icons.css">
    <link rel="stylesheet" href="assets/vendor/icon-etlinefont/style.css">
    <link rel="stylesheet" href="assets/vendor/icon-line-pro/style.css">
    <link rel="stylesheet" href="assets/vendor/icon-hs/style.css">    
    <link rel="stylesheet" href="assets/vendor/hs-megamenu/src/hs.megamenu.css">
    
    <link rel="stylesheet" href="assets/vendor/animate.css">

    <link rel="stylesheet" href="/assets/vendor/slick-carousel/slick/slick.css">
    <link rel="stylesheet" href="/assets/vendor/fancybox/jquery.fancybox.css">
    
    <!-- CSS Unify -->
    <link rel="stylesheet" href="assets/css/unify-core.css">
    <link rel="stylesheet" href="assets/css/unify-components.css">
    <link rel="stylesheet" href="assets/css/unify-globals.css">

    <!-- CSS Customization -->
    <link rel="stylesheet" href="assets/css/custom.css">
  </head>

  <body>
    <main>
        <!-- Header -->
        <tiles:insertAttribute name="header" />
        <!-- Body -->
        <div class="container g-pt-150">
        <tiles:insertAttribute name="body" />
        </div>
        <!-- Footer -->
        <tiles:insertAttribute name="footer" />
    </main>

    <!-- JS Global Compulsory -->
    <script src="assets/vendor/jquery/jquery.min.js"></script>
    <script src="assets/vendor/jquery-migrate/jquery-migrate.min.js"></script>
    <script src="assets/vendor/jquery.easing/js/jquery.easing.js"></script>
    <script src="assets/vendor/popper.min.js"></script>
    <script src="assets/vendor/bootstrap/bootstrap.min.js"></script>
    <script src="assets/vendor/bootstrap/offcanvas.js"></script>
    <script src="assets/vendor/slick-carousel/slick/slick.js"></script>
    <script src="assets/vendor/fancybox/jquery.fancybox.min.js"></script>
    <script src="assets/vendor/hs-megamenu/src/hs.megamenu.js"></script>
    
    <!-- JS Unify -->
    <script src="assets/js/hs.core.js"></script>
    <script src="assets/js/components/hs.header.js"></script>
    <script src="assets/js/helpers/hs.hamburgers.js"></script>
    <script src="assets/js/helpers/hs.focus-state.js"></script>
    <script src="assets/js/components/hs.dropdown.js"></script>
    <script src="assets/js/components/hs.tabs.js"></script>
    <script src="assets/js/components/hs.scrollbar.js"></script>
    <script src="assets/js/components/hs.popup.js"></script>
    <script src="assets/js/components/hs.carousel.js"></script>

    <script src="assets/js/components/hs.go-to.js"></script>


    <!-- JS Custom -->
    <script src="assets/js/custom.js"></script>

    <script>
 $(document).on('ready', function () {
     $.HSCore.helpers.HSFocusState.init();
     
      // initialization of go to
      $.HSCore.components.HSGoTo.init('.js-go-to');

      // initialization of carousel
      $.HSCore.components.HSCarousel.init('.js-carousel');

      $('#we-provide').slick('setOption', 'responsive', [{
        breakpoint: 992,
        settings: {
          slidesToShow: 2
        }
      }, {
        breakpoint: 576,
        settings: {
          slidesToShow: 1
        }
      }], true);

      // initialization of HSDropdown component
      $.HSCore.components.HSDropdown.init( $('[data-dropdown-target]'), {
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
      $('.js-mega-menu').HSMegaMenu({
        event: 'hover',
        pageContainer: $('.container'),
        breakpoint: 850
      });      
    });
    
      $(window).on('resize', function () {
        setTimeout(function () {
          $.HSCore.components.HSTabs.init('[role="tablist"]');
        }, 200);
      });    
    </script>
  </body>
</html>
