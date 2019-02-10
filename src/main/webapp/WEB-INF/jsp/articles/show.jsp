<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>

<h1>
    ${view.mainTitle}
    <p><small class="text-muted">${view.alternativeTitle1}</small></p>
    <p><small class="text-muted">${view.alternativeTitle2}</small></p>
    <p><small class="text-muted">${view.alternativeTitle3}</small></p>  
</h1>

<div class="row justify-content-between">
    <div class="col-4">
            <s:authorize access="hasRole('USER')">
                <li class="nav-item nav-fill">
                    <a class="nav-link" href="#" id="toggle-holarse-context-menu" title="Aktionen anzeigen"><i class="fas fa-cogs"></i></a>
                </li>            
            </s:authorize> 
        </nav>
    </div>

<!--            <span class="navbar-text">Letzte Änderung: ${empty node.updated ? node.created : node.updated} durch ${empty node.author ? 'unbekannt' : node.author.login}</span>                 -->

</div>

<%@include file="/WEB-INF/jspf/nodes/menubar.jspf" %>

<div class="row">
    <article class="col-md-8">
        ${view.content}    
    </article>
    <!-- Tags -->
    
    <!-- Attachments -->
    <div class="col-md-4">
        <!-- Tags -->
        <div class="btn-group" role="group" aria-label="Basic example">
            <c:forEach items="${view.tags}" var="tag">
                <a class="btn btn-primary" href="/finder/?tag=${tag.name}" title="Klicken für weitere Artikel mit diesem Tag">${tag.name}</a>
            </c:forEach>
        </div>
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
                <a href="${website.attachmentData}" class="list-group-item list-group-item-action" title="${website.attachmentData}">${website.description}</a>    
            </c:forEach>
        </div>               
        
    </div>
</div>

<!-- Kommentare -->
<div class="comments">
    <c:forEach items="${view.comments}" var="comment">
    <div class="row">
        <div class="col-md-12">            
            <div class="media">
                <img class="align-self-start mr-3" src="https://placeimg.com/64/64/any" alt="Generic placeholder image">
                <div class="media-body">
                    <h5 class="mt-0"><a href="#">{{comment.author.login}}</a> am {{comment.created}}</h5>
                    <p>{{comment.content}}</p>
                </div>
            </div>        
        </div>
    </div>            
    </c:forEach>
</div>

<div class="row">
    <div class="col-md-12">

        <%@include file="/WEB-INF/jspf/comments/form.jspf" %>

    </div>
</div>


