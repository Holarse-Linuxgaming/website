<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Head -->
<div class="media-md align-items-center g-mb-30">
    <div class="d-flex g-mb-15 g-mb-0--md">
        <h3 class="g-font-weight-400 g-font-size-16 g-color-black mb-0">Modern Table</h3>
    </div>

    <div class="media d-md-flex align-items-center ml-auto">
        <div class="d-flex g-ml-15 g-ml-55--md">
            <div class="input-group g-pos-rel g-width-320--md">
                <input id="datatableSearch2" class="form-control g-font-size-default g-brd-gray-light-v7 g-brd-lightblue-v3--focus g-rounded-20 g-pl-20 g-pr-50 g-py-10" placeholder="Search for name, position" type="text">
                <button class="btn g-pos-abs g-top-0 g-right-0 g-z-index-2 g-width-60 h-100 g-bg-transparent g-font-size-16 g-color-primary g-color-secondary--hover rounded-0" type="submit">
                    <i class="hs-admin-search g-absolute-centered"></i>
                </button>
            </div>
        </div>
    </div>
</div>
<!-- Head Ende -->

<!-- Body -->
<div class="g-mb-40">
    <div class="table-responsive">
        <table class="js-datatable table table-hover u-table--v3--borderless u-table--v3--row-border-bottom g-color-black" data-dt-info="#datatableInfo2" data-dt-search="#datatableSearch2" data-dt-is-show-paging="true" data-dt-pagination="datatablePagination2"
               data-dt-page-length="5" data-dt-is-responsive="false" data-dt-pagination-classes="list-inline text-right mb-0" data-dt-pagination-items-classes="list-inline-item g-hidden-sm-down" data-dt-pagination-links-classes="u-pagination-v1__item u-pagination-v1-2 g-bg-secondary--active g-color-white--active g-brd-gray-light-v7 g-brd-secondary--hover g-brd-secondary--active g-rounded-4 g-py-8 g-px-15"
               data-dt-pagination-next-classes="list-inline-item" data-dt-pagination-next-link-classes="u-pagination-v1__item u-pagination-v1-2 g-brd-gray-light-v7 g-brd-secondary--hover g-rounded-4 g-py-8 g-px-12" data-dt-pagination-next-link-markup='<span class="g-line-height-1 g-valign-middle" aria-hidden="true"><i class="hs-admin-angle-right"></i></span><span class="sr-only">Next</span>'
               data-dt-pagination-prev-classes="list-inline-item" data-dt-pagination-prev-link-classes="u-pagination-v1__item u-pagination-v1-2 g-brd-gray-light-v7 g-brd-secondary--hover g-rounded-4 g-py-8 g-px-12" data-dt-pagination-prev-link-markup='<span class="g-line-height-1 g-valign-middle" aria-hidden="true"><i class="hs-admin-angle-left"></i></span><span class="sr-only">Prev</span>'
               data-dt-is-selectable="true">
            <thead class="thead-light">
                <tr>
                    <th class="g-hidden-xs-up"></th>
                    <th class="g-first-child">
                        <div class="media">
                            <div class="d-flex align-self-center">Login</div>

                            <div class="d-flex align-self-center ml-auto">
                                <span class="d-inline-block g-width-10 g-line-height-1 g-font-size-10">
                                    <a class="g-color-gray-light-v6 g-color-secondary--hover g-text-underline--none--hover" href="#">
                                        <i class="hs-admin-angle-up"></i>
                                    </a>
                                    <a class="g-color-gray-light-v6 g-color-secondary--hover g-text-underline--none--hover" href="#">
                                        <i class="hs-admin-angle-down"></i>
                                    </a>
                                </span>
                            </div>
                        </div>
                    </th>
                    <th>
                        <div class="media">
                            <div class="d-flex align-self-center">Email</div>

                            <div class="d-flex align-self-center ml-auto">
                                <span class="d-inline-block g-width-10 g-line-height-1 g-font-size-10">
                                    <a class="g-color-gray-light-v6 g-color-secondary--hover g-text-underline--none--hover" href="#">
                                        <i class="hs-admin-angle-up"></i>
                                    </a>
                                    <a class="g-color-gray-light-v6 g-color-secondary--hover g-text-underline--none--hover" href="#">
                                        <i class="hs-admin-angle-down"></i>
                                    </a>
                                </span>
                            </div>
                        </div>
                    </th>
                    <th>
                        <div class="media">
                            <div class="d-flex align-self-center">Rollen</div>

                            <div class="d-flex align-self-center ml-auto">
                                <span class="d-inline-block g-width-10 g-line-height-1 g-font-size-10">
                                    <a class="g-color-gray-light-v6 g-color-secondary--hover g-text-underline--none--hover" href="#">
                                        <i class="hs-admin-angle-up"></i>
                                    </a>
                                    <a class="g-color-gray-light-v6 g-color-secondary--hover g-text-underline--none--hover" href="#">
                                        <i class="hs-admin-angle-down"></i>
                                    </a>
                                </span>
                            </div>
                        </div>
                    </th>                    
                    <th></th>
                </tr>
            </thead>

            <tbody>
                <c:forEach items="${view.content}" var="user">

                    <tr class="g-bg-gray-light-v8--opacity-0_4--checked">
                        <td class="g-hidden-xs-up">1</td>
                        <td class="js-select text-left g-brd-left g-brd-left-2 g-brd-left-lightblue-v3--parent-checked g-first-child">
                            <label class="form-check-inline u-check g-pl-45 mb-0 mr-0">
                                <input class="g-hidden-xs-up g-pos-abs g-top-0 g-left-0" type="checkbox">
                                <div class="u-check-icon-checkbox-v6 g-absolute-centered--y g-left-0 g-bg-lightblue-v3--checked g-brd-lightblue-v3--checked">
                                    <i class="fa" data-check-icon="&#xf00c"></i>
                                </div>
                                ${user.login}
                            </label>
                        </td>
                        <td>${user.email}</td>
                        <td>
                            <ul class="list-inline u-list-stack g-mb-0">
                                <c:forEach items="${user.roles}" var="role">
                                    <li class="list-inline-item g-mb-10 g-mb-0--sm mr-0">
                                        ${role}
                                    </li>
                                </c:forEach>
                            </ul>
                        </td>
                        <td class="text-right">
                            <div class="g-pos-rel g-top-3 d-inline-block">
                                <a id="dropDown3_1Invoker" class="u-link-v5 g-line-height-0 g-font-size-24 g-color-gray-light-v6 g-color-secondary--hover" href="#" aria-controls="dropDown3_1" aria-haspopup="true" aria-expanded="false" data-dropdown-event="click" data-dropdown-target="#dropDown3_1">
                                    <i class="hs-admin-more-alt"></i>
                                </a>

                                <div id="dropDown3_1" class="u-shadow-v31 g-pos-abs g-right-0 g-z-index-2 g-bg-white u-dropdown--css-animation u-dropdown--hidden u-dropdown--reverse-y" aria-labelledby="dropDown3_1Invoker">
                                    <ul class="list-unstyled g-nowrap mb-0">
                                        <li>
                                            <a class="d-flex align-items-center u-link-v5 g-bg-gray-light-v8--hover g-font-size-12 g-font-size-default--md g-color-gray-dark-v6 g-px-25 g-py-14" href="#">
                                                <i class="hs-admin-pencil g-font-size-18 g-color-gray-light-v6 g-mr-10 g-mr-15--md"></i>
                                                Edit
                                            </a>
                                        </li>
                                        <li>
                                            <a class="d-flex align-items-center u-link-v5 g-bg-gray-light-v8--hover g-font-size-12 g-font-size-default--md g-color-gray-dark-v6 g-px-25 g-py-14" href="#">
                                                <i class="hs-admin-archive g-font-size-18 g-color-gray-light-v6 g-mr-10 g-mr-15--md"></i>
                                                Archive
                                            </a>
                                        </li>
                                        <li>
                                            <a class="d-flex align-items-center u-link-v5 g-bg-gray-light-v8--hover g-font-size-12 g-font-size-default--md g-color-gray-dark-v6 g-px-25 g-py-14" href="#">
                                                <i class="hs-admin-check g-font-size-18 g-color-gray-light-v6 g-mr-10 g-mr-15--md"></i>
                                                Mark as Done
                                            </a>
                                        </li>
                                        <li>
                                            <a class="d-flex align-items-center u-link-v5 g-bg-gray-light-v8--hover g-font-size-12 g-font-size-default--md g-color-gray-dark-v6 g-px-25 g-py-14" href="#">
                                                <i class="hs-admin-plus g-font-size-18 g-color-gray-light-v6 g-mr-10 g-mr-15--md"></i>
                                                New Task
                                            </a>
                                        </li>
                                        <li>
                                            <a class="d-flex align-items-center u-link-v5 g-bg-gray-light-v8--hover g-font-size-12 g-font-size-default--md g-color-gray-dark-v6 g-px-25 g-py-14" href="#">
                                                <i class="hs-admin-trash g-font-size-18 g-color-gray-light-v6 g-mr-10 g-mr-15--md"></i>
                                                Delete
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>                
            </tbody>
        </table>
    </div>
</div>
<!-- Body Ende -->

<%@include file="/WEB-INF/jspf/pagination.jspf" %>