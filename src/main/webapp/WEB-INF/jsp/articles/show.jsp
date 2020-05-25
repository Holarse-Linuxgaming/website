<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>
    ${view.mainTitle}
    <p><small class="text-muted">${view.alternativeTitle1}</small></p>
    <p><small class="text-muted">${view.alternativeTitle2}</small></p>
    <p><small class="text-muted">${view.alternativeTitle3}</small></p>  
</h1>

<div class="row">
    <div class="col-md-12">
        <a href="${view.editUrl}">Bearbeiten</a>
    </div>
</div>

<div class="row">
    <article class="col-md-8" data-nodeid="${view.nodeId}">
        ${view.formattedContent}    
    </article>
    <!-- Tags -->
    
    <!-- Attachments -->
    <div class="col-md-4">
        <!-- Access -->
        <canvas id="access_chart" width="400" height="200"></canvas>

        <!-- Tags -->
        <ul class="u-list-inline">
            <li class="list-inline-item g-mb-10">
                <c:forEach items="${view.tags}" var="tag">
                    <a class="u-tags-v1 g-color-main g-brd-around g-brd-gray-light-v3 g-bg-gray-dark-v2--hover g-brd-gray-dark-v2--hover g-color-white--hover g-py-4 g-px-10" href="/finder/?tag=${tag.name}" title="Klicken für weitere Artikel mit diesem Tag">
                        <i class="fa fa-tag mr-1"></i>
                        ${tag.name}
                    </a>
                </c:forEach>                
            </li>
        </ul>        
        
        <!-- Screenshots -->
        <div id="carouselScreenshots" class="carousel slide" data-ride="carousel">
            <ol class="carousel-indicators">
                <c:forEach items="${view.screenshots}" var="screenshot" varStatus="c">
                    <li data-target="#carouselScreenshots" data-slide-to="${c.index}" class="${c.first ? 'active' : ''}"></li>    
                </c:forEach>
            </ol>
            <div class="carousel-inner">
                <c:forEach items="${view.screenshots}" var="screenshot" varStatus="c">
                    <div class="carousel-item ${c.first ? 'active' : ''}">
                        <img class="d-block w-100" src="https://www.holarse-linuxgaming.de/${screenshot.attachmentData}" alt="First slide">
                        <div class="carousel-caption d-none d-md-block">
                            <p>${screenshot.description}</p>
                        </div>                    
                    </div>
                </c:forEach>
            </div>
            <a class="carousel-control-prev" href="#carouselScreenshots" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#carouselScreenshots" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>  

        <!-- Videos -->
        <div id="carouselVideos" class="carousel slide" data-ride="carousel">
            <ol class="carousel-indicators">
                <c:forEach items="${view.videos}" var="video" varStatus="c">
                    <li data-target="#carouselVideos" data-slide-to="${c.index}" class="${c.first ? 'active' : ''}"></li>    
                </c:forEach>
            </ol>
            <div class="carousel-inner">
                <c:forEach items="${view.videos}" var="video" varStatus="c">
                    <div class="carousel-item ${c.first ? 'active' : ''}">
                        <div class="embed-responsive embed-responsive-16by9">
                            <iframe class="embed-responsive-item" src="https://www.youtube-nocookie.com/embed/${video.attachmentData}" allowfullscreen></iframe>
                        </div>                          
                    </div>
                </c:forEach>
            </div>
            <a class="carousel-control-prev" href="#carouselVideos" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#carouselVideos" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>          

        <!-- Websites -->
        <div class="list-group">
            <c:forEach items="${view.websites}" var="website">
                <a href="${website.data}" class="list-group-item list-group-item-action" title="${website.data}">${empty website.description ? website.data : website.description}</a>    
            </c:forEach>
        </div>               
        
    </div>
</div>

<!-- Kommentare -->
<div id="v-comments">
    <comment v-for="comment in comments" v-bind:comment="comment"></comment>
</div>
    
<s:authorize access="hasRole('USER')">
<div class="row">
    <div class="col-md-12">
        <div id="v-comment-editor" data-nodeid="${view.nodeId}">
            <form>
                <input type="hidden" name="nodeId" v-model="newcomment.nodeId" />
                <fieldset>
                    <label path="content"></label>
                    <textarea name="content" class="form-control" placeholder="Dein Kommentar..." v-model="newcomment.content"></textarea>
                </fieldset>
                <button class="btn btn-primary" v-on:click="submit">Kommentieren</button>
            </form>        
        </div>
    </div>
</div>
</s:authorize>       
<s:authorize access="hasRole('ANONYMOUS')">
<div class="row">
    <div class="col-md-12">
        <a href="/login?returnto=${requestScope['javax.servlet.forward.request_uri']}" class="btn btn-primary">Zum Kommentieren einloggen</a>            
    </div>
</div>
</s:authorize>   
