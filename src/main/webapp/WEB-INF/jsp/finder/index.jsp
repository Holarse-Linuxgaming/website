<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="row">
    <div class="col-md-2">
        <div id="tag-filter-accordion" class="u-accordion" role="tablist" aria-multiselectable="true">
            <c:forEach items="${tagGroups}" var="tagGroup">
           
                <!-- Card -->
                  <div class="card rounded-0 g-bg-gray-light-v4 g-brd-none">
                    <div id="accordion-06-heading-01" class="u-accordion__header g-pa-20" role="tab">
                      <h5 class="mb-0 text-uppercase g-font-size-default g-font-weight-700">
                        <a class="d-flex justify-content-between g-color-main g-text-underline--none--hover" href="#tag-filter-accordion-body-${tagGroup.id}" data-toggle="collapse" data-parent="#tag-filter-accordion" aria-expanded="true" aria-controls="tag-filter-accordion-body-${tagGroup.id}">
                          ${tagGroup.label} (${tagGroup.groupedUseCount})
                          <span class="u-accordion__control-icon g-ml-10">
                            <i class="fa fa-angle-down"></i>
                            <i class="fa fa-angle-up"></i>
                          </span>
                        </a>
                      </h5>
                    </div>
                    <div id="tag-filter-accordion-body-${tagGroup.id}" class="collapse show" role="tabpanel" aria-labelledby="accordion-06-heading-01" data-parent="#tag-filter-accordion">
                      <div class="u-accordion__body g-color-gray-dark-v5 g-pa-0-20-20">
                            <ul class="list-group">
                                    <c:forEach items="${tagGroup.tags}" var="tag" varStatus="tagstatus">
                                        <li class="list-group-item list-group-item-action" data-taggroupid="${tagGroup.id}" data-usecount="${tag.useCount}" data-count="${tagstatus.count}">
                                            <spring:url value="/finder/" var="p">
                                                <spring:param name="tag" value="${tag.label}" />
                                                <spring:param name="tags" value="${taglist}" />
                                                <spring:param name="q" value="${q}" />
                                            </spring:url>
                                                <a class="u-tags-v1 g-color-main g-brd-around g-brd-gray-light-v3 g-bg-gray-dark-v2--hover g-brd-gray-dark-v2--hover g-color-white--hover g-py-4 g-px-10" href="${p}" title="${tag.useCount} Artikel">
                                                    <i class="fa fa-tag mr-1"></i>
                                                    ${tag.name}
                                                </a>                        
                                        </li>
                                    </c:forEach>
                            </ul>
                      </div>
                    </div>
                  </div>
                  <!-- End Card -->   
            </c:forEach>              
        </div>        
    </div>
    <div class="col-md-10">
        <!-- Tags -->
        <div class="row">
            <div class="col-md-8">
                <strong>Aktuell verwendete Tags</strong>
                <ul class="list-inline">
                    <c:forEach items="${tags}" var="tag">
                        <li class="list-inline-item">
                            <spring:url value="/finder/" var="p">
                                <spring:param name="tag" value="${tag.name}" />
                                <spring:param name="tags" value="${taglist}" />                        
                                <spring:param name="q" value="${q}" />                          
                            </spring:url>                      
                            <a href="${p}" title="Klicken zum Entfernen">${tag.name}</a>
                        </li>
                        </c:forEach>
                </ul>
            </div>
            <div class="col-md-4">
                <form class="form-control-md" method="GET">
                    <input type="hidden" name="tags" value="${taglist}" />
                    <div class="form-group g-mb-20">
                        <label class="g-mb-10">Zusätzlicher Suchbegriff</label>
                        <div class="input-group g-brd-primary--focus">
                          <input class="form-control form-control-md rounded-0 pr-0" type="search" placeholder="Suchergebnisse nach Text filtern" id="query" name="q" value="${q}">
                            <div class="input-group-addon p-0">
                                <button class="btn rounded-0 btn-primary btn-md g-font-size-18 g-px-18" type="submit">
                                    <i class="fa fa-search"></i>
                                </button>
                            </div>                          
                        </div>
                    </div>
                </form>                
            </div>
        </div>

        <div class="row">
            <div class="col-md-2">
                ${pagination.maxItems} Ergebnis(se) in ${duration} ms.
            </div>
        </div>
                           
        <div class="row">
            <ul class="list-unstyled">
            <c:forEach items="${searchResults}" var="view">                    
              <li class="media g-brd-around g-brd-gray-light-v4 g-brd-left-3 g-brd-blue-left g-rounded-3 g-pa-20 g-mb-7">
                <div class="d-flex g-mt-2 g-mr-15">
                    <a href="${view.url}">
                        <img class="img" src="https://via.placeholder.com/100" alt="Image Description">
                    </a>
                </div>
                <div class="media-body">
                  <div class="d-flex justify-content-between">
                    <h5 class="h6 g-font-weight-600 g-color-black"><a href="${view.url}">${view.title}</a></h5>
                    <h6>
                    <c:forEach items="${view.subtitles}" var="subtitle">
                        <span class="u-label u-label--sm g-bg-lightred-v2 g-color-black g-rounded-3 g-px-10">${subtitle}</span>
                    </c:forEach>
                        </h6>
                    <span class="small text-nowrap g-color-blue">2 min ago</span>
                  </div>
                  <p>${view.teaser}</p>
                    <c:forEach items="${view.tags}" var="tag" varStatus="x">
                        <spring:url value="/finder/" var="pt">
                            <spring:param name="tag" value="${tag}" />
                            <spring:param name="tags" value="${taglist}" />  
                            <spring:param name="q" value="${q}" />
                        </spring:url>                      
                        <a class="u-label u-label--sm g-bg-transparent--hover g-px-10" href="${pt}">${tag}</a>
                    </c:forEach>                  
                </div>
              </li>
            </c:forEach>
            </ul>
        </div>
        <div class="row">
            <%@include file="/WEB-INF/jspf/pagination.jspf" %>
        </div>
    </div>
</div>


