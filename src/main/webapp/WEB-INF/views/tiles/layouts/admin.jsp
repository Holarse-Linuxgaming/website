<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="de">

    <head>
        <!-- Title -->
        <title>Holarse Admin-Bereich</title>

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
        <!-- CSS Global Icons -->
        <link rel="stylesheet" href="assets/vendor/icon-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" href="assets/vendor/icon-line/css/simple-line-icons.css">
        <link rel="stylesheet" href="assets/vendor/icon-etlinefont/style.css">
        <link rel="stylesheet" href="assets/vendor/icon-line-pro/style.css">
        <link rel="stylesheet" href="assets/vendor/icon-hs/style.css">

        <link rel="stylesheet" href="assets/vendor/hs-admin-icons/hs-admin-icons.css">

        <link rel="stylesheet" href="assets/vendor/animate.css">
        <link rel="stylesheet" href="assets/vendor/malihu-scrollbar/jquery.mCustomScrollbar.min.css">  

        <link rel="stylesheet" href="assets/vendor/flatpickr/dist/css/flatpickr.min.css">
        <link rel="stylesheet" href="assets/vendor/bootstrap-select/css/bootstrap-select.min.css">
        <link rel="stylesheet" href="assets/vendor/bootstrap-tagsinput/css/bootstrap-tagsinput.css">

        <link rel="stylesheet" href="assets/vendor/chartist-js/chartist.min.css">
        <link rel="stylesheet" href="assets/vendor/chartist-js-tooltip/chartist-plugin-tooltip.css">
        <link rel="stylesheet" href="assets/vendor/fancybox/jquery.fancybox.min.css">

        <link rel="stylesheet" href="assets/vendor/hamburgers/hamburgers.min.css">

        <!-- CSS Unify -->
        <link rel="stylesheet" href="assets/vendor/unify/unify-admin.css">

        <!-- CSS Customization -->
        <link rel="stylesheet" href="assets/custom/css/custom.css">
    </head>

    <body>
        <!-- Header -->
        <header id="js-header" class="u-header u-header--sticky-top">
            <div class="u-header__section u-header__section--admin-dark g-min-height-65">
                <nav class="navbar no-gutters g-pa-0">
                    <div class="col-auto d-flex flex-nowrap u-header-logo-toggler g-py-12">
                        <!-- Logo -->
                        <a href="/admin" class="navbar-brand d-flex align-self-center g-hidden-xs-down g-line-height-1 py-0 g-mt-5">
                            <img src="/assets/img/icon.png" alt="Holarse Logo" style="max-width: 50%;">
                        </a>
                        <!-- End Logo -->

                        <!-- Sidebar Toggler -->
                        <a class="js-side-nav u-header__nav-toggler d-flex align-self-center ml-auto" href="#" data-hssm-class="u-side-nav--mini u-sidebar-navigation-v1--mini" data-hssm-body-class="u-side-nav-mini" data-hssm-is-close-all-except-this="true" data-hssm-target="#sideNav">
                            <i class="hs-admin-align-left"></i>
                        </a>
                        <!-- End Sidebar Toggler -->
                    </div>

                    <!-- Top Search Bar -->
                    <form id="searchMenu" class="u-header--search col-sm g-py-12 g-ml-15--sm g-ml-20--md g-mr-10--sm" aria-labelledby="searchInvoker" action="#!">
                        <div class="input-group g-max-width-450--sm">
                            <input class="form-control h-100 form-control-md g-rounded-4 g-pr-40" type="text" placeholder="Enter search keywords">
                            <button type="submit" class="btn u-btn-outline-primary g-brd-none g-bg-transparent--hover g-pos-abs g-top-0 g-right-0 d-flex g-width-40 h-100 align-items-center justify-content-center g-font-size-18 g-z-index-2"><i class="hs-admin-search"></i>
                            </button>
                        </div>
                    </form>
                    <!-- End Top Search Bar -->

                    <!-- Messages/Notifications/Top Search Bar/Top User -->
                    <div class="col-auto d-flex g-py-12 g-pl-40--lg ml-auto">
                        <!-- Top Messages -->
                        <div class="g-pos-rel g-hidden-sm-down g-mr-5">
                            <a id="messagesInvoker" class="d-block text-uppercase u-header-icon-v1 g-pos-rel g-width-40 g-height-40 rounded-circle g-font-size-20" href="#" aria-controls="messagesMenu" aria-haspopup="true" aria-expanded="false" data-dropdown-event="click" data-dropdown-target="#messagesMenu"
                               data-dropdown-type="css-animation" data-dropdown-duration="300" data-dropdown-animation-in="fadeIn" data-dropdown-animation-out="fadeOut">
                                <span class="u-badge-v1 g-top-7 g-right-7 g-width-18 g-height-18 g-bg-primary g-font-size-10 g-color-white rounded-circle p-0">7</span>
                                <i class="hs-admin-comment-alt g-absolute-centered"></i>
                            </a>

                            <!-- Top Messages List -->
                            <div id="messagesMenu" class="g-absolute-centered--x g-width-340 g-max-width-400 g-mt-17 rounded" aria-labelledby="messagesInvoker">
                                <div class="media u-header-dropdown-bordered-v1 g-pa-20">
                                    <h4 class="d-flex align-self-center text-uppercase g-font-size-default g-letter-spacing-0_5 g-mr-20 g-mb-0">3 new messages</h4>
                                    <div class="media-body align-self-center text-right">
                                        <a class="g-color-secondary" href="#">View All</a>
                                    </div>
                                </div>

                                <ul class="p-0 mb-0">
                                    <!-- Top Messages List Item -->
                                    <li class="media g-pos-rel u-header-dropdown-item-v1 g-pa-20">
                                        <div class="d-flex g-mr-15">
                                            <img class="g-width-40 g-height-40 rounded-circle" src="../../assets/img-temp/100x100/img5.jpg" alt="Image Description">
                                        </div>

                                        <div class="media-body">
                                            <h5 class="g-font-size-16 g-font-weight-400 g-mb-5"><a href="#">Verna Swanson</a></h5>
                                            <p class="g-mb-10">Not so many years businesses used to grunt at using</p>

                                            <em class="d-flex align-self-center align-items-center g-font-style-normal g-color-lightblue-v2">
                                                <i class="hs-admin-time icon-clock g-mr-5"></i> <small>5 Min ago</small>
                                            </em>
                                        </div>
                                        <a class="u-link-v2" href="#">Read</a>
                                    </li>
                                    <!-- End Top Messages List Item -->

                                    <!-- Top Messages List Item -->
                                    <li class="media g-pos-rel u-header-dropdown-item-v1 g-pa-20">
                                        <div class="d-flex g-mr-15">
                                            <img class="g-width-40 g-height-40 rounded-circle" src="../../assets/img-temp/100x100/img6.jpg" alt="Image Description">
                                        </div>

                                        <div class="media-body">
                                            <h5 class="g-font-size-16 g-font-weight-400 g-mb-5"><a href="#">Eddie Hayes</a></h5>
                                            <p class="g-mb-10">But today and influence of is growing right along illustration</p>

                                            <em class="d-flex align-self-center align-items-center g-font-style-normal g-color-lightblue-v2">
                                                <i class="hs-admin-time icon-clock g-mr-5"></i> <small>22 Min ago</small>
                                            </em>
                                        </div>
                                        <a class="u-link-v2" href="#">Read</a>
                                    </li>
                                    <!-- End Top Messages List Item -->

                                    <!-- Top Messages List Item -->
                                    <li class="media g-pos-rel u-header-dropdown-item-v1 g-pa-20">
                                        <div class="d-flex g-mr-15">
                                            <img class="g-width-40 g-height-40 rounded-circle" src="../../assets/img-temp/100x100/img7.jpg" alt="Image Description">
                                        </div>

                                        <div class="media-body">
                                            <h5 class="g-font-size-16 g-font-weight-400 g-mb-5"><a href="#">Herbert Castro</a></h5>
                                            <p class="g-mb-10">But today, the use and influence of illustrations is growing right along</p>

                                            <em class="d-flex align-self-center align-items-center g-font-style-normal g-color-lightblue-v2">
                                                <i class="hs-admin-time icon-clock g-mr-5"></i> <small>15 Min ago</small>
                                            </em>
                                        </div>
                                        <a class="u-link-v2" href="#">Read</a>
                                    </li>
                                    <!-- End Top Messages List Item -->
                                </ul>
                            </div>
                            <!-- End Top Messages List -->
                        </div>
                        <!-- End Top Messages -->

                        <!-- Top Notifications -->
                        <div class="g-pos-rel g-hidden-sm-down">
                            <a id="notificationsInvoker" class="d-block text-uppercase u-header-icon-v1 g-pos-rel g-width-40 g-height-40 rounded-circle g-font-size-20" href="#" aria-controls="notificationsMenu" aria-haspopup="true" aria-expanded="false" data-dropdown-event="click"
                               data-dropdown-target="#notificationsMenu" data-dropdown-type="css-animation" data-dropdown-duration="300" data-dropdown-animation-in="fadeIn" data-dropdown-animation-out="fadeOut">
                                <i class="hs-admin-bell g-absolute-centered"></i>
                            </a>

                            <!-- Top Notifications List -->
                            <div id="notificationsMenu" class="js-custom-scroll g-absolute-centered--x g-width-340 g-max-width-400 g-height-400 g-mt-17 rounded" aria-labelledby="notificationsInvoker">
                                <div class="media text-uppercase u-header-dropdown-bordered-v1 g-pa-20">
                                    <h4 class="d-flex align-self-center g-font-size-default g-letter-spacing-0_5 g-mr-20 g-mb-0">Notifications</h4>
                                </div>

                                <ul class="p-0 mb-0">
                                    <!-- Top Notifications List Item -->
                                    <li class="media u-header-dropdown-item-v1 g-parent g-px-20 g-py-15">
                                        <div class="d-flex align-self-center u-header-dropdown-icon-v1 g-pos-rel g-width-55 g-height-55 g-font-size-22 rounded-circle g-mr-15">
                                            <i class="hs-admin-bookmark-alt g-absolute-centered"></i>
                                        </div>

                                        <div class="media-body align-self-center">
                                            <p class="mb-0">A Pocket PC is a handheld computer features</p>
                                        </div>

                                        <a class="d-flex g-color-lightblue-v2 g-font-size-12 opacity-0 g-opacity-1--parent-hover g-transition--ease-in g-transition-0_2" href="#">
                                            <i class="hs-admin-close"></i>
                                        </a>
                                    </li>
                                    <!-- End Top Notifications List Item -->

                                    <!-- Top Notifications List Item -->
                                    <li class="media u-header-dropdown-item-v1 g-parent g-px-20 g-py-15">
                                        <div class="d-flex align-self-center u-header-dropdown-icon-v1 g-pos-rel g-width-55 g-height-55 g-font-size-22 rounded-circle g-mr-15">
                                            <i class="hs-admin-blackboard g-absolute-centered"></i>
                                        </div>

                                        <div class="media-body align-self-center">
                                            <p class="mb-0">The first is a non technical method which requires</p>
                                        </div>

                                        <a class="d-flex g-color-lightblue-v2 g-font-size-12 opacity-0 g-opacity-1--parent-hover g-transition--ease-in g-transition-0_2" href="#">
                                            <i class="hs-admin-close"></i>
                                        </a>
                                    </li>
                                    <!-- End Top Notifications List Item -->

                                    <!-- Top Notifications List Item -->
                                    <li class="media u-header-dropdown-item-v1 g-parent g-px-20 g-py-15">
                                        <div class="d-flex align-self-center u-header-dropdown-icon-v1 g-pos-rel g-width-55 g-height-55 g-font-size-22 rounded-circle g-mr-15">
                                            <i class="hs-admin-calendar g-absolute-centered"></i>
                                        </div>

                                        <div class="media-body align-self-center">
                                            <p class="mb-0">Stu Unger is of the biggest superstarsis</p>
                                        </div>

                                        <a class="d-flex g-color-lightblue-v2 g-font-size-12 opacity-0 g-opacity-1--parent-hover g-transition--ease-in g-transition-0_2" href="#">
                                            <i class="hs-admin-close"></i>
                                        </a>
                                    </li>
                                    <!-- End Top Notifications List Item -->

                                    <!-- Top Notifications List Item -->
                                    <li class="media u-header-dropdown-item-v1 g-parent g-px-20 g-py-15">
                                        <div class="d-flex align-self-center u-header-dropdown-icon-v1 g-pos-rel g-width-55 g-height-55 g-font-size-22 rounded-circle g-mr-15">
                                            <i class="hs-admin-pie-chart g-absolute-centered"></i>
                                        </div>

                                        <div class="media-body align-self-center">
                                            <p class="mb-0">Sony laptops are among the most well known laptops</p>
                                        </div>

                                        <a class="d-flex g-color-lightblue-v2 g-font-size-12 opacity-0 g-opacity-1--parent-hover g-transition--ease-in g-transition-0_2" href="#">
                                            <i class="hs-admin-close"></i>
                                        </a>
                                    </li>
                                    <!-- End Top Notifications List Item -->
                                    <!-- Top Notifications List Item -->
                                    <li class="media u-header-dropdown-item-v1 g-parent g-px-20 g-py-15">
                                        <div class="d-flex align-self-center u-header-dropdown-icon-v1 g-pos-rel g-width-55 g-height-55 g-font-size-22 rounded-circle g-mr-15">
                                            <i class="hs-admin-bookmark-alt g-absolute-centered"></i>
                                        </div>

                                        <div class="media-body align-self-center">
                                            <p class="mb-0">A Pocket PC is a handheld computer features</p>
                                        </div>

                                        <a class="d-flex g-color-lightblue-v2 g-font-size-12 opacity-0 g-opacity-1--parent-hover g-transition--ease-in g-transition-0_2" href="#">
                                            <i class="hs-admin-close"></i>
                                        </a>
                                    </li>
                                    <!-- End Top Notifications List Item -->

                                    <!-- Top Notifications List Item -->
                                    <li class="media u-header-dropdown-item-v1 g-parent g-px-20 g-py-15">
                                        <div class="d-flex align-self-center u-header-dropdown-icon-v1 g-pos-rel g-width-55 g-height-55 g-font-size-22 rounded-circle g-mr-15">
                                            <i class="hs-admin-blackboard g-absolute-centered"></i>
                                        </div>

                                        <div class="media-body align-self-center">
                                            <p class="mb-0">The first is a non technical method which requires</p>
                                        </div>

                                        <a class="d-flex g-color-lightblue-v2 g-font-size-12 opacity-0 g-opacity-1--parent-hover g-transition--ease-in g-transition-0_2" href="#">
                                            <i class="hs-admin-close"></i>
                                        </a>
                                    </li>
                                    <!-- End Top Notifications List Item -->
                                </ul>
                            </div>
                            <!-- End Top Notifications List -->
                        </div>
                        <!-- End Top Notifications -->

                        <!-- Top Search Bar (Mobi) -->
                        <a id="searchInvoker" class="g-hidden-sm-up text-uppercase u-header-icon-v1 g-pos-rel g-width-40 g-height-40 rounded-circle g-font-size-20" href="#" aria-controls="searchMenu" aria-haspopup="true" aria-expanded="false" data-is-mobile-only="true" data-dropdown-event="click"
                           data-dropdown-target="#searchMenu" data-dropdown-type="css-animation" data-dropdown-duration="300" data-dropdown-animation-in="fadeIn" data-dropdown-animation-out="fadeOut">
                            <i class="hs-admin-search g-absolute-centered"></i>
                        </a>
                        <!-- End Top Search Bar (Mobi) -->

                        <!-- Top User -->
                        <div class="col-auto d-flex g-pt-5 g-pt-0--sm g-pl-10 g-pl-20--sm">
                            <div class="g-pos-rel g-px-10--lg">
                                <a id="profileMenuInvoker" class="d-block" href="#" aria-controls="profileMenu" aria-haspopup="true" aria-expanded="false" data-dropdown-event="click" data-dropdown-target="#profileMenu" data-dropdown-type="css-animation" data-dropdown-duration="300"
                                   data-dropdown-animation-in="fadeIn" data-dropdown-animation-out="fadeOut">
                                    <span class="g-pos-rel">
                                        <span class="u-badge-v2--xs u-badge--top-right g-hidden-sm-up g-bg-secondary g-mr-5"></span>
                                        <img class="g-width-30 g-width-40--md g-height-30 g-height-40--md rounded-circle g-mr-10--sm" src="../assets/img-temp/130x130/img1.jpg" alt="Image description">
                                    </span>
                                    <span class="g-pos-rel g-top-2">
                                        <span class="g-hidden-sm-down">${currentUser.login}</span>
                                        <i class="hs-admin-angle-down g-pos-rel g-top-2 g-ml-10"></i>
                                    </span>
                                </a>

                                <!-- Top User Menu -->
                                <ul id="profileMenu" class="g-pos-abs g-left-0 g-width-100x--lg g-nowrap g-font-size-14 g-py-20 g-mt-17 rounded" aria-labelledby="profileMenuInvoker">
                                    <li class="g-hidden-sm-up g-mb-10">
                                        <a class="media g-py-5 g-px-20" href="#">
                                            <span class="d-flex align-self-center g-pos-rel g-mr-12">
                                                <span class="u-badge-v1 g-top-minus-3 g-right-minus-3 g-width-18 g-height-18 g-bg-secondary g-font-size-10 g-color-white rounded-circle p-0">10</span>
                                                <i class="hs-admin-comment-alt"></i>
                                            </span>
                                            <span class="media-body align-self-center">Unread Messages</span>
                                        </a>
                                    </li>
                                    <li class="g-hidden-sm-up g-mb-10">
                                        <a class="media g-py-5 g-px-20" href="#">
                                            <span class="d-flex align-self-center g-mr-12">
                                                <i class="hs-admin-bell"></i>
                                            </span>
                                            <span class="media-body align-self-center">Notifications</span>
                                        </a>
                                    </li>
                                    <li class="g-mb-10">
                                        <a class="media g-color-primary--hover g-py-5 g-px-20" href="#">
                                            <span class="d-flex align-self-center g-mr-12">
                                                <i class="hs-admin-user"></i>
                                            </span>
                                            <span class="media-body align-self-center">My Profile</span>
                                        </a>
                                    </li>
                                    <li class="g-mb-10">
                                        <a class="media g-color-primary--hover g-py-5 g-px-20" href="#">
                                            <span class="d-flex align-self-center g-mr-12">
                                                <i class="hs-admin-rocket"></i>
                                            </span>
                                            <span class="media-body align-self-center">Upgrade Plan</span>
                                        </a>
                                    </li>
                                    <li class="g-mb-10">
                                        <a class="media g-color-primary--hover g-py-5 g-px-20" href="#">
                                            <span class="d-flex align-self-center g-mr-12">
                                                <i class="hs-admin-layout-grid-2"></i>
                                            </span>
                                            <span class="media-body align-self-center">Latest Projects</span>
                                        </a>
                                    </li>
                                    <li class="g-mb-10">
                                        <a class="media g-color-primary--hover g-py-5 g-px-20" href="#">
                                            <span class="d-flex align-self-center g-mr-12">
                                                <i class="hs-admin-headphone-alt"></i>
                                            </span>
                                            <span class="media-body align-self-center">Get Support</span>
                                        </a>
                                    </li>
                                    <li class="mb-0">
                                        <a class="media g-color-primary--hover g-py-5 g-px-20" href="/">
                                            <span class="d-flex align-self-center g-mr-12">
                                                <i class="hs-admin-shift-right"></i>
                                            </span>
                                            <span class="media-body align-self-center">Zurück zur Seite</span>
                                        </a>
                                    </li>
                                </ul>
                                <!-- End Top User Menu -->
                            </div>
                        </div>
                        <!-- End Top User -->
                    </div>
                    <!-- End Messages/Notifications/Top Search Bar/Top User -->

                    <!-- Top Activity Toggler -->
                    <a id="activityInvoker" class="text-uppercase u-header-icon-v1 g-pos-rel g-width-40 g-height-40 rounded-circle g-font-size-20" href="#" aria-controls="activityMenu" aria-haspopup="true" aria-expanded="false" data-dropdown-event="click" data-dropdown-target="#activityMenu"
                       data-dropdown-type="css-animation" data-dropdown-animation-in="fadeInRight" data-dropdown-animation-out="fadeOutRight" data-dropdown-duration="300">
                        <i class="hs-admin-align-right g-absolute-centered"></i>
                    </a>
                    <!-- End Top Activity Toggler -->
                </nav>

                <!-- Top Activity Panel -->
                <div id="activityMenu" class="js-custom-scroll u-header-sidebar g-pos-fix g-top-0 g-left-auto g-right-0 g-z-index-4 g-width-300 g-width-400--sm g-height-100vh" aria-labelledby="activityInvoker">
                    <div class="u-header-dropdown-bordered-v1 g-pa-20">
                        <a id="activityInvokerClose" class="pull-right g-color-lightblue-v2" href="#" aria-controls="activityMenu" aria-haspopup="true" aria-expanded="false" data-dropdown-event="click" data-dropdown-target="#activityMenu" data-dropdown-type="css-animation" data-dropdown-animation-in="fadeInRight"
                           data-dropdown-animation-out="fadeOutRight" data-dropdown-duration="300">
                            <i class="hs-admin-close"></i>
                        </a>
                        <h4 class="text-uppercase g-font-size-default g-letter-spacing-0_5 g-mr-20 g-mb-0">Activity</h4>
                    </div>

                    <!-- Activity Short Stat. -->
                    <section class="g-pa-20">
                        <div class="media align-items-center u-link-v5 g-color-white">
                            <div class="media-body align-self-center g-line-height-1_3 g-font-weight-300 g-font-size-40">
                                624 <span class="g-font-size-16">+3%</span>
                            </div>

                            <div class="d-flex align-self-center g-font-size-25 g-line-height-1 g-color-secondary ml-auto">$49,000</div>

                            <div class="d-flex align-self-center g-ml-8">
                                <svg class="g-fill-white-opacity-0_5" width="12px" height="20px" viewBox="0 0 12 20" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                                <g transform="translate(-21.000000, -751.000000)">
                                <g transform="translate(0.000000, 64.000000)">
                                <g transform="translate(20.000000, 619.000000)">
                                <g transform="translate(1.000000, 68.000000)">
                                <polygon points="6 20 0 13.9709049 0.576828937 13.3911999 5.59205874 18.430615 5.59205874 0 6.40794126 0 6.40794126 18.430615 11.4223552 13.3911999 12 13.9709049"></polygon>
                                </g>
                                </g>
                                </g>
                                </g>
                                </svg>
                                <svg class="g-fill-lightblue-v3" width="12px" height="20px" viewBox="0 0 12 20" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                                <g transform="translate(-33.000000, -751.000000)">
                                <g transform="translate(0.000000, 64.000000)">
                                <g transform="translate(20.000000, 619.000000)">
                                <g transform="translate(1.000000, 68.000000)">
                                <polygon transform="translate(18.000000, 10.000000) scale(1, -1) translate(-18.000000, -10.000000)" points="18 20 12 13.9709049 12.5768289 13.3911999 17.5920587 18.430615 17.5920587 0 18.4079413 0 18.4079413 18.430615 23.4223552 13.3911999 24 13.9709049"></polygon>
                                </g>
                                </g>
                                </g>
                                </g>
                                </svg>
                            </div>
                        </div>

                        <span class="g-font-size-16">Transactions</span>
                    </section>
                    <!-- End Activity Short Stat. -->

                    <!-- Activity Bars -->
                    <section class="g-pa-20 g-mb-10">
                        <!-- Advertising Income -->
                        <div class="g-mb-30">
                            <div class="media u-link-v5  g-color-white g-mb-10">
                                <span class="media-body align-self-center">Advertising Income</span>

                                <span class="d-flex align-self-center">
                                    <svg class="g-fill-white-opacity-0_5" width="12px" height="20px" viewBox="0 0 12 20" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                                    <g transform="translate(-21.000000, -751.000000)">
                                    <g transform="translate(0.000000, 64.000000)">
                                    <g transform="translate(20.000000, 619.000000)">
                                    <g transform="translate(1.000000, 68.000000)">
                                    <polygon points="6 20 0 13.9709049 0.576828937 13.3911999 5.59205874 18.430615 5.59205874 0 6.40794126 0 6.40794126 18.430615 11.4223552 13.3911999 12 13.9709049"></polygon>
                                    </g>
                                    </g>
                                    </g>
                                    </g>
                                    </svg>
                                    <svg class="g-fill-lightblue-v3" width="12px" height="20px" viewBox="0 0 12 20" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                                    <g transform="translate(-33.000000, -751.000000)">
                                    <g transform="translate(0.000000, 64.000000)">
                                    <g transform="translate(20.000000, 619.000000)">
                                    <g transform="translate(1.000000, 68.000000)">
                                    <polygon transform="translate(18.000000, 10.000000) scale(1, -1) translate(-18.000000, -10.000000)" points="18 20 12 13.9709049 12.5768289 13.3911999 17.5920587 18.430615 17.5920587 0 18.4079413 0 18.4079413 18.430615 23.4223552 13.3911999 24 13.9709049"></polygon>
                                    </g>
                                    </g>
                                    </g>
                                    </g>
                                    </svg>
                                </span>
                            </div>

                            <div class="progress g-height-4 g-bg-gray-light-v8 g-rounded-2">
                                <div class="progress-bar g-bg-teal-v2 g-rounded-2" role="progressbar" style="width: 70%" aria-valuenow="70" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                        </div>
                        <!-- End Advertising Income -->

                        <!-- Projects Income -->
                        <div class="g-mb-30">
                            <div class="media u-link-v5  g-color-white g-mb-10">
                                <span class="media-body align-self-center">Projects Income</span>
                                <span class="d-flex align-self-center">
                                    <svg class="g-fill-red" width="12px" height="20px" viewBox="0 0 12 20" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                                    <g transform="translate(-21.000000, -751.000000)">
                                    <g transform="translate(0.000000, 64.000000)">
                                    <g transform="translate(20.000000, 619.000000)">
                                    <g transform="translate(1.000000, 68.000000)">
                                    <polygon points="6 20 0 13.9709049 0.576828937 13.3911999 5.59205874 18.430615 5.59205874 0 6.40794126 0 6.40794126 18.430615 11.4223552 13.3911999 12 13.9709049"></polygon>
                                    </g>
                                    </g>
                                    </g>
                                    </g>
                                    </svg>
                                    <svg class="g-fill-white-opacity-0_5" width="12px" height="20px" viewBox="0 0 12 20" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                                    <g transform="translate(-33.000000, -751.000000)">
                                    <g transform="translate(0.000000, 64.000000)">
                                    <g transform="translate(20.000000, 619.000000)">
                                    <g transform="translate(1.000000, 68.000000)">
                                    <polygon transform="translate(18.000000, 10.000000) scale(1, -1) translate(-18.000000, -10.000000)" points="18 20 12 13.9709049 12.5768289 13.3911999 17.5920587 18.430615 17.5920587 0 18.4079413 0 18.4079413 18.430615 23.4223552 13.3911999 24 13.9709049"></polygon>
                                    </g>
                                    </g>
                                    </g>
                                    </g>
                                    </svg>
                                </span>
                            </div>

                            <div class="progress g-height-4 g-bg-gray-light-v8 g-rounded-2">
                                <div class="progress-bar g-bg-lightblue-v3 g-rounded-2" role="progressbar" style="width: 40%" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                        </div>
                        <!-- End Projects Income -->

                        <!-- Template Sales -->
                        <div>
                            <div class="media u-link-v5  g-color-white g-mb-10">
                                <span class="media-body align-self-center">Template Sales</span>
                                <span class="d-flex align-self-center">
                                    <svg class="g-fill-white-opacity-0_5" width="12px" height="20px" viewBox="0 0 12 20" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                                    <g transform="translate(-21.000000, -751.000000)">
                                    <g transform="translate(0.000000, 64.000000)">
                                    <g transform="translate(20.000000, 619.000000)">
                                    <g transform="translate(1.000000, 68.000000)">
                                    <polygon points="6 20 0 13.9709049 0.576828937 13.3911999 5.59205874 18.430615 5.59205874 0 6.40794126 0 6.40794126 18.430615 11.4223552 13.3911999 12 13.9709049"></polygon>
                                    </g>
                                    </g>
                                    </g>
                                    </g>
                                    </svg>
                                    <svg class="g-fill-lightblue-v3" width="12px" height="20px" viewBox="0 0 12 20" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                                    <g transform="translate(-33.000000, -751.000000)">
                                    <g transform="translate(0.000000, 64.000000)">
                                    <g transform="translate(20.000000, 619.000000)">
                                    <g transform="translate(1.000000, 68.000000)">
                                    <polygon transform="translate(18.000000, 10.000000) scale(1, -1) translate(-18.000000, -10.000000)" points="18 20 12 13.9709049 12.5768289 13.3911999 17.5920587 18.430615 17.5920587 0 18.4079413 0 18.4079413 18.430615 23.4223552 13.3911999 24 13.9709049"></polygon>
                                    </g>
                                    </g>
                                    </g>
                                    </g>
                                    </svg>
                                </span>
                            </div>

                            <div class="progress g-height-4 g-bg-gray-light-v8 g-rounded-2">
                                <div class="progress-bar g-bg-darkblue-v2 g-rounded-2" role="progressbar" style="width: 90%" aria-valuenow="90" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                        </div>
                        <!-- End Template Sales -->
                    </section>
                    <!-- End Activity Bars -->

                    <!-- Activity Accounts -->
                    <section class="g-pa-20">
                        <h5 class="text-uppercase g-font-size-default g-letter-spacing-0_5 g-mb-10">My accounts</h5>

                        <div class="media u-header-dropdown-bordered-v2 g-py-10">
                            <div class="d-flex align-self-center g-mr-12">
                                <span class="u-badge-v2--sm g-pos-stc g-transform-origin--top-left g-bg-teal-v2"></span>
                            </div>

                            <div class="media-body align-self-center">Credit Card</div>

                            <div class="d-flex text-right">$12.240</div>
                        </div>

                        <div class="media u-header-dropdown-bordered-v2 g-py-10">
                            <div class="d-flex align-self-center g-mr-12">
                                <span class="u-badge-v2--sm g-pos-stc g-transform-origin--top-left g-bg-lightblue-v3"></span>
                            </div>

                            <div class="media-body align-self-center">Debit Card</div>

                            <div class="d-flex text-right">$228.110</div>
                        </div>

                        <div class="media g-py-10">
                            <div class="d-flex align-self-center g-mr-12">
                                <span class="u-badge-v2--sm g-pos-stc g-transform-origin--top-left g-bg-darkblue-v2"></span>
                            </div>

                            <div class="media-body align-self-center">Savings Account</div>

                            <div class="d-flex text-right">$128.248.000</div>
                        </div>
                    </section>
                    <!-- End Activity Accounts -->

                    <!-- Activity Transactions -->
                    <section class="g-pa-20">
                        <h5 class="text-uppercase g-font-size-default g-letter-spacing-0_5 g-mb-10">Transactions</h5>

                        <!-- Transaction Item -->
                        <div class="u-header-dropdown-bordered-v2 g-py-20">
                            <div class="media g-pos-rel">
                                <div class="d-flex align-self-start g-pt-3 g-mr-12">
                                    <i class="hs-admin-plus g-color-lightblue-v3"></i>
                                </div>

                                <div class="media-body align-self-start">
                                    <strong class="d-block g-font-size-17 g-font-weight-400 g-line-height-1">$240.00</strong>
                                    <p class="mb-0 g-mt-5">Addiction When Gambling Becomes</p>
                                </div>

                                <em class="d-flex align-items-center g-pos-abs g-top-0 g-right-0 g-font-style-normal g-color-lightblue-v2">
                                    <i class="hs-admin-time icon-clock g-font-size-default g-mr-8"></i> <small>5 Min ago</small>
                                </em>
                            </div>
                        </div>
                        <!-- End Transaction Item -->

                        <!-- Transaction Item -->
                        <div class="u-header-dropdown-bordered-v2 g-py-20">
                            <div class="media g-pos-rel">
                                <div class="d-flex align-self-start g-pt-3 g-mr-12">
                                    <i class="hs-admin-minus g-color-red"></i>
                                </div>

                                <div class="media-body align-self-start">
                                    <strong class="d-block g-font-size-17 g-font-weight-400 g-line-height-1">$126.00</strong>
                                    <p class="mb-0 g-mt-5">Make Myspace Your</p>
                                </div>

                                <em class="d-flex align-items-center g-pos-abs g-top-0 g-right-0 g-font-style-normal g-color-lightblue-v2">
                                    <i class="hs-admin-time icon-clock g-font-size-default g-mr-8"></i> <small>25 Nov 2017</small>
                                </em>
                            </div>
                        </div>
                        <!-- End Transaction Item -->

                        <!-- Transaction Item -->
                        <div class="u-header-dropdown-bordered-v2 g-py-20">
                            <div class="media g-pos-rel">
                                <div class="d-flex align-self-start g-pt-3 g-mr-12">
                                    <i class="hs-admin-plus g-color-lightblue-v3"></i>
                                </div>

                                <div class="media-body align-self-start">
                                    <strong class="d-block g-font-size-17 g-font-weight-400 g-line-height-1">$560.00</strong>
                                    <p class="mb-0 g-mt-5">Writing A Good Headline</p>
                                </div>

                                <em class="d-flex align-items-center g-pos-abs g-top-0 g-right-0 g-font-style-normal g-color-lightblue-v2">
                                    <i class="hs-admin-time icon-clock g-font-size-default g-mr-8"></i> <small>22 Nov 2017</small>
                                </em>
                            </div>
                        </div>
                        <!-- End Transaction Item -->

                        <!-- Transaction Item -->
                        <div class="u-header-dropdown-bordered-v2 g-py-20">
                            <div class="media g-pos-rel">
                                <div class="d-flex align-self-start g-pt-3 g-mr-12">
                                    <i class="hs-admin-plus g-color-lightblue-v3"></i>
                                </div>

                                <div class="media-body align-self-start">
                                    <strong class="d-block g-font-size-17 g-font-weight-400 g-line-height-1">$6.00</strong>
                                    <p class="mb-0 g-mt-5">Buying Used Electronic Equipment</p>
                                </div>

                                <em class="d-flex align-items-center g-pos-abs g-top-0 g-right-0 g-font-style-normal g-color-lightblue-v2">
                                    <i class="hs-admin-time icon-clock g-font-size-default g-mr-8"></i> <small>13 Oct 2017</small>
                                </em>
                            </div>
                        </div>
                        <!-- End Transaction Item -->

                        <!-- Transaction Item -->
                        <div class="u-header-dropdown-bordered-v2 g-py-20">
                            <div class="media g-pos-rel">
                                <div class="d-flex align-self-start g-pt-3 g-mr-12">
                                    <i class="hs-admin-plus g-color-lightblue-v3"></i>
                                </div>

                                <div class="media-body align-self-start">
                                    <strong class="d-block g-font-size-17 g-font-weight-400 g-line-height-1">$320.00</strong>
                                    <p class="mb-0 g-mt-5">Gambling Becomes A Problem</p>
                                </div>

                                <em class="d-flex align-items-center g-pos-abs g-top-0 g-right-0 g-font-style-normal g-color-lightblue-v2">
                                    <i class="hs-admin-time icon-clock g-font-size-default g-mr-8"></i> <small>27 Jul 2017</small>
                                </em>
                            </div>
                        </div>
                        <!-- End Transaction Item -->

                        <!-- Transaction Item -->
                        <div class="u-header-dropdown-bordered-v2 g-py-20">
                            <div class="media g-pos-rel">
                                <div class="d-flex align-self-start g-pt-3 g-mr-12">
                                    <i class="hs-admin-minus g-color-red"></i>
                                </div>

                                <div class="media-body align-self-start">
                                    <strong class="d-block g-font-size-17 g-font-weight-400 g-line-height-1">$28.00</strong>
                                    <p class="mb-0 g-mt-5">Baby Monitor Technology</p>
                                </div>

                                <em class="d-flex align-items-center g-pos-abs g-top-0 g-right-0 g-font-style-normal g-color-lightblue-v2">
                                    <i class="hs-admin-time icon-clock g-font-size-default g-mr-8"></i> <small>05 Mar 2017</small>
                                </em>
                            </div>
                        </div>
                        <!-- End Transaction Item -->

                        <!-- Transaction Item -->
                        <div class="u-header-dropdown-bordered-v2 g-py-20">
                            <div class="media g-pos-rel">
                                <div class="d-flex align-self-start g-pt-3 g-mr-12">
                                    <i class="hs-admin-plus g-color-lightblue-v3"></i>
                                </div>

                                <div class="media-body align-self-start">
                                    <strong class="d-block g-font-size-17 g-font-weight-400 g-line-height-1">$490.00</strong>
                                    <p class="mb-0 g-mt-5">Adwords Keyword Research For Beginners</p>
                                </div>

                                <em class="d-flex align-items-center g-pos-abs g-top-0 g-right-0 text-uppercase g-font-size-11 g-letter-spacing-0_5 g-font-style-normal g-color-lightblue-v2">
                                    <i class="hs-admin-time icon-clock g-font-size-default g-mr-8"></i> 09 Feb 2017
                                </em>
                            </div>
                        </div>
                        <!-- End Transaction Item -->

                        <!-- Transaction Item -->
                        <div class="u-header-dropdown-bordered-v2 g-py-20">
                            <div class="media g-pos-rel">
                                <div class="d-flex align-self-start g-pt-3 g-mr-12">
                                    <i class="hs-admin-minus g-color-red"></i>
                                </div>

                                <div class="media-body align-self-start">
                                    <strong class="d-block g-font-size-17 g-font-weight-400 g-line-height-1">$14.20</strong>
                                    <p class="mb-0 g-mt-5">A Good Autoresponder</p>
                                </div>

                                <em class="d-flex align-items-center g-pos-abs g-top-0 g-right-0 text-uppercase g-font-size-11 g-letter-spacing-0_5 g-font-style-normal g-color-lightblue-v2">
                                    <i class="hs-admin-time icon-clock g-font-size-default g-mr-8"></i> 09 Feb 2017
                                </em>
                            </div>
                        </div>
                        <!-- End Transaction Item -->
                    </section>
                    <!-- End Activity Transactions -->
                </div>
                <!-- End Top Activity Panel -->

            </div>
        </header>
        <!-- End Header -->


        <main class="container-fluid px-0 g-pt-65">
            <div class="row no-gutters g-pos-rel g-overflow-hidden">
                <!-- Sidebar Nav -->
                <div id="sideNav" class="col-auto u-sidebar-navigation-v1 u-sidebar-navigation--dark">
                    <ul id="sideNavMenu" class="u-sidebar-navigation-v1-menu u-side-nav--top-level-menu g-min-height-100vh mb-0">
                        <!-- Drückblick -->
                        <li class="u-sidebar-navigation-v1-menu-item u-side-nav--has-sub-menu u-side-nav--top-level-menu-item">
                            <a class="media u-side-nav--top-level-menu-link u-side-nav--hide-on-hidden g-px-15 g-py-12" href="#" data-hssm-target="#subMenu_db">
                                <span class="d-flex align-self-center g-pos-rel g-font-size-18 g-mr-18">
                                    <i class="hs-admin-layout-accordion-list"></i>
                                </span>
                                <span class="media-body align-self-center">Drückblick</span>
                                <span class="d-flex align-self-center u-side-nav--control-icon">
                                    <i class="hs-admin-angle-right"></i>
                                </span>
                                <span class="u-side-nav--has-sub-menu__indicator"></span>
                            </a>

                            <!-- Drückblick: Submenu -->
                            <ul id="subMenu_db" class="u-sidebar-navigation-v1-menu u-side-nav--second-level-menu mb-0">
                                <!-- Drückblicke -->
                                <li class="u-sidebar-navigation-v1-menu-item u-side-nav--second-level-menu-item">
                                    <a class="media u-side-nav--second-level-menu-link g-px-15 g-py-12" href="/admin/drueckblick/groups/">
                                    <span class="d-flex align-self-center g-mr-15 g-mt-minus-1">
                                        <i class="hs-admin-layout-media-overlay"></i>
                                    </span>
                                    <span class="media-body align-self-center">Drückblicke</span>
                                    </a>
                                </li>
                                <!-- End Aktuelle Eingänge -->

                                <!-- Aktuelle Eingänge -->
                                <li class="u-sidebar-navigation-v1-menu-item u-side-nav--second-level-menu-item">
                                    <a class="media u-side-nav--second-level-menu-link g-px-15 g-py-12" href="/admin/drueckblick/entries/">
                                    <span class="d-flex align-self-center g-mr-15 g-mt-minus-1">
                                        <i class="hs-admin-layers-alt"></i>
                                    </span>
                                    <span class="media-body align-self-center">Aktuelle Eingänge</span>
                                    </a>
                                </li>
                                <!-- End Aktuelle Eingänge -->
                            </ul>
                            <!-- End Drückblick: Submenu -->                            
                        </li>
                        <!-- End Drückblick -->
                    </ul>
                </div>
                <!-- End Sidebar Nav -->


                <div class="col g-ml-45 g-ml-0--lg g-pb-65--md">
                    <div class="g-pa-20">
                        <tiles:insertAttribute name="body" /> 
                    </div>

                    <!-- Footer -->
                    <footer id="footer" class="u-footer--bottom-sticky g-bg-white g-color-gray-dark-v6 g-brd-top g-brd-gray-light-v7 g-pa-20">
                        <div class="row align-items-center">
                            <!-- Footer Nav -->
                            <div class="col-md-4 g-mb-10 g-mb-0--md">
                                <ul class="list-inline text-center text-md-left mb-0">
                                    <li class="list-inline-item">
                                        <a class="g-color-gray-dark-v6 g-color-secondary--hover" href="https://git.holarse-linuxgaming.de">Git</a>
                                    </li>
                                    <li class="list-inline-item">
                                        <span class="g-color-gray-dark-v6">|</span>
                                    </li>
                                    <li class="list-inline-item">
                                        <a class="g-color-gray-dark-v6 g-color-secondary--hover" href="#">Support</a>
                                    </li>
                                    <li class="list-inline-item">
                                        <span class="g-color-gray-dark-v6">|</span>
                                    </li>
                                    <li class="list-inline-item">
                                        <a class="g-color-gray-dark-v6 g-color-secondary--hover" href="#">Contact Us</a>
                                    </li>
                                </ul>
                            </div>
                            <!-- End Footer Nav -->
                        </div>
                    </footer>
                    <!-- End Footer -->
                </div>
            </div>
        </main>

        <!-- JS Global Compulsory -->
        <script src="assets/vendor/jquery/jquery.min.js"></script>
        <script src="assets/vendor/jquery-migrate/jquery-migrate.min.js"></script>

        <script src="assets/vendor/popper.js/popper.min.js"></script>
        <script src="assets/vendor/bootstrap/bootstrap.min.js"></script>

        <script src="assets/vendor/cookiejs/jquery.cookie.js"></script>


        <!-- jQuery UI Core -->
        <script src="assets/vendor/jquery-ui/ui/widget.js"></script>
        <script src="assets/vendor/jquery-ui/ui/version.js"></script>
        <script src="assets/vendor/jquery-ui/ui/keycode.js"></script>
        <script src="assets/vendor/jquery-ui/ui/position.js"></script>
        <script src="assets/vendor/jquery-ui/ui/unique-id.js"></script>
        <script src="assets/vendor/jquery-ui/ui/safe-active-element.js"></script>

        <!-- jQuery UI Helpers -->
        <script src="assets/vendor/jquery-ui/ui/widgets/menu.js"></script>
        <script src="assets/vendor/jquery-ui/ui/widgets/mouse.js"></script>

        <!-- jQuery UI Widgets -->
        <script src="assets/vendor/jquery-ui/ui/widgets/datepicker.js"></script>

        <!-- JS Plugins Init. -->
        <script src="assets/vendor/appear.js"></script>
        <script src="assets/vendor/bootstrap-select/js/bootstrap-select.min.js"></script>
        <script src="assets/vendor/flatpickr/dist/js/flatpickr.min.js"></script>
        <script src="assets/vendor/malihu-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>  
        <script src="assets/vendor/chartist-js/chartist.min.js"></script>
        <script src="assets/vendor/chartist-js-tooltip/chartist-plugin-tooltip.js"></script>
        <script src="assets/vendor/fancybox/jquery.fancybox.min.js"></script> 
        <script src="assets/vendor/bloodhound-js/js/bloodhound.min.js"></script>
        <script src="assets/vendor/bloodhound-js/js/typeahead.jquery.min.js"></script>
        <script src="assets/vendor/bootstrap-tagsinput/js/bootstrap-tagsinput.min.js"></script>
        <script src="assets/vendor/datatables/media/js/jquery.dataTables.min.js"></script>
        <script src="assets/vendor/datatables/media/js/dataTables.select.js"></script>        

        <!-- JS Unify -->
        <script src="assets/vendor/unify/hs.core.js"></script>
        <script src="assets/vendor/unify/helpers/hs.hamburgers.js"></script>
        <script src="assets/vendor/unify/components/hs.datepicker.js"></script>
        <script src="assets/vendor/unify/components/hs.dropdown.js"></script>
        <script src="assets/vendor/unify/components/hs.scrollbar.js"></script>
        <script src="assets/vendor/unify/helpers/hs.focus-state.js"></script>
        <script src="assets/vendor/unify/components/hs.dropdown.js"></script>
        <script src="assets/vendor/unify/components/hs.side-nav.js"></script>
        <script src="assets/vendor/unify/components/hs.range-datepicker.js"></script>
        <script src="assets/vendor/unify/components/hs.area-chart.js"></script>
        <script src="assets/vendor/unify/components/hs.donut-chart.js"></script>
        <script src="assets/vendor/unify/components/hs.bar-chart.js"></script>
        <script src="assets/vendor/unify/components/hs.popup.js"></script>
        <script src="assets/vendor/unify/helpers/hs.focus-state.js"></script>
        <script src="assets/vendor/unify/components/hs.datatables.js"></script>
        
        <!-- JS Custom -->
        <script src="/assets/custom/js/holarse.js"></script>          
        <script src="assets/vendor/vuejs/vue.js"></script>
        <script src="assets/custom/js/admin.js"></script>

        <!-- JS Plugins Init. -->
        <script>
            $(document).on('ready', function () {
                // initialization of custom select
                $('.js-select').selectpicker();

                // initialization of hamburger
                $.HSCore.helpers.HSHamburgers.init('.hamburger');

                // initialization of charts
                $.HSCore.components.HSAreaChart.init('.js-area-chart');
                $.HSCore.components.HSDonutChart.init('.js-donut-chart');
                $.HSCore.components.HSBarChart.init('.js-bar-chart');

                // initialization of sidebar navigation component
                $.HSCore.components.HSSideNav.init('.js-side-nav', {
                    afterOpen: function () {
                        setTimeout(function () {
                            $.HSCore.components.HSAreaChart.init('.js-area-chart');
                            $.HSCore.components.HSDonutChart.init('.js-donut-chart');
                            $.HSCore.components.HSBarChart.init('.js-bar-chart');
                        }, 400);
                    },
                    afterClose: function () {
                        setTimeout(function () {
                            $.HSCore.components.HSAreaChart.init('.js-area-chart');
                            $.HSCore.components.HSDonutChart.init('.js-donut-chart');
                            $.HSCore.components.HSBarChart.init('.js-bar-chart');
                        }, 400);
                    }
                });

                // initialization of HSDropdown component
                $.HSCore.components.HSDropdown.init($('[data-dropdown-target]'), {
                    dropdownHideOnScroll: false,
                    dropdownType: 'css-animation',
                    dropdownAnimationIn: 'fadeIn',
                    dropdownAnimationOut: 'fadeOut'
                });

                // initialization of range datepicker
                $.HSCore.components.HSRangeDatepicker.init('#rangeDatepicker, #rangeDatepicker2, #rangeDatepicker3');

                // initialization of datepicker
                $.HSCore.components.HSDatepicker.init('#datepicker', {
                    dayNamesMin: [
                        'SO',
                        'MO',
                        'DI',
                        'MI',
                        'DO',
                        'FR',
                        'SA'
                    ]
                });

                // initialization of HSDropdown component
                $.HSCore.components.HSDropdown.init($('[data-dropdown-target]'), {dropdownHideOnScroll: false});

                // initialization of custom scrollbar
                $.HSCore.components.HSScrollBar.init($('.js-custom-scroll'));

                // initialization of popups
                $.HSCore.components.HSPopup.init('.js-fancybox', {
                    btnTpl: {
                        smallBtn: '<button data-fancybox-close class="btn g-pos-abs g-top-25 g-right-30 g-line-height-1 g-bg-transparent g-font-size-16 g-color-gray-light-v3 g-brd-none p-0" title=""><i class="hs-admin-close"></i></button>'
                    }
                });


                // initialization of tagsinput
                $('[data-role="tagsinput"]').on('itemAddedOnInit', function (event) {
                    if ($(this).siblings().find('.tag').length >= 1) {
                        $(this).siblings().find('input').attr('placeholder', '');
                    } else {
                        $(this).siblings().find('input').attr('placeholder', 'Type your tag here...');
                    }
                });

                $('[data-role="tagsinput"]').on('itemAdded', function (event) {
                    if ($(this).siblings().find('.tag').length >= 1) {
                        $(this).siblings().find('input').attr('placeholder', '');
                    } else {
                        $(this).siblings().find('input').attr('placeholder', 'Type your tag here...');
                    }
                });

                $('[data-role="tagsinput"]').on('itemRemoved', function (event) {
                    if ($(this).siblings().find('.tag').length >= 1) {
                        $(this).siblings().find('input').attr('placeholder', '');
                    } else {
                        $(this).siblings().find('input').attr('placeholder', 'Type your tag here...');
                    }
                });

                $('[data-role="tagsinput"]').tagsinput();
            });
        </script>
    </body>

</html>
