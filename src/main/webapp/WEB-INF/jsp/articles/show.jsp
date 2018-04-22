<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>





<h1>
    ${node.title}
    <p><small class="text-muted">${node.alternativeTitle1}</small></p>
    <p><small class="text-muted">${node.alternativeTitle2}</small></p>
    <p><small class="text-muted">${node.alternativeTitle3}</small></p>  
</h1>

<div class="row justify-content-between">
    <div class="col-4">
        <nav class="nav holarse-tags">

            <c:forEach items="${node.tags}" var="tag">
                <li class="nav-item">
                    <a class="nav-link" href="/finder/?tag=${tag.name}" title="Klicken für weitere Artikel mit diesem Tag">${tag.name}</a>
                </li>                
            </c:forEach>

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
        ${node.content}    
    </article>
    <div class="col-md-4">
        <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
            <ol class="carousel-indicators">
                <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
            </ol>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img class="d-block w-100" src="https://placeimg.com/640/480/any" alt="First slide">
                    <div class="carousel-caption d-none d-md-block">
                        <p>Screenshot aus der Beta</p>
                    </div>                    
                </div>
                <div class="carousel-item">
                    <img class="d-block w-100" src="https://placeimg.com/640/480/any" alt="Second slide">
                    <div class="carousel-caption d-none d-md-block">
                        <p>Selbstgemachter Screenshot</p>
                    </div>                    
                </div>
                <div class="carousel-item">
                    <img class="d-block w-100" src="https://placeimg.com/640/480/any" alt="Third slide">
                    <div class="carousel-caption d-none d-md-block">
                        <p>Produktbild</p>
                    </div>
                </div>
            </div>
            <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>        

        <div class="embed-responsive embed-responsive-16by9">
            <iframe class="embed-responsive-item" src="https://www.youtube-nocookie.com/embed/zpOULjyy-n8?rel=0" allowfullscreen></iframe>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/jspf/comments/list.jspf" %>


<div class="row">
    <div class="col-md-12">

        <%@include file="/WEB-INF/jspf/comments/form.jspf" %>

    </div>
</div>


