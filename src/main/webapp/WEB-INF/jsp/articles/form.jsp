<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>



<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
    <li class="nav-item">
        <a class="nav-link active" id="pills-content-tab" data-toggle="pill" href="#pills-content" role="tab" aria-controls="pills-content" aria-selected="true">Inhalt bearbeiten</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" id="pills-attachments-tab" data-toggle="pill" href="#pills-attachments" role="tab" aria-controls="pills-attachments" aria-selected="false">Links/Bilder/Videos</a>
    </li>
    <c:if test="${not empty node.id}">
        <li class="nav-item">    
            <a href="/wiki/${node.id}/edit/abort" class="btn btn-outline-primary">Abbrechen und zurück</a>
        </li>    
    </c:if>
</ul>
<div class="tab-content" id="pills-tabContent">
    <div class="tab-pane fade show active" id="pills-content" role="tabpanel" aria-labelledby="pills-content-tab">
        <!-- Form -->
        <form:form modelAttribute="articleCommand" action="/wiki/${node.id}" method="POST">
            <form:button class="btn btn-primary">Speichern</form:button>
                <fieldset>
                <form:label path="title">Titel</form:label>
                <form:input path="title" class="form-control"></form:input>

                <form:label path="tags">Tags</form:label>
                <form:input path="tags" class="form-control"></form:input>            

                <form:label path="alternativeTitle1">Weiterer Titel</form:label>
                <form:input path="alternativeTitle1" class="form-control"></form:input>    

                <form:label path="alternativeTitle2" class="hidden">Weiterer Titel</form:label>
                <form:input path="alternativeTitle2" class="form-control hidden"></form:input>    

                <form:label path="alternativeTitle3" class="hidden">Weiterer Titel</form:label>
                <form:input path="alternativeTitle3" class="form-control hidden"></form:input>    

                </fieldset>
                <fieldset>
                <form:label path="content">Inhalt</form:label>
                <form:textarea path="content" class="form-control" rows="25"></form:textarea>

                <form:label path="contentType">Format des Inhalts</form:label>    
                <form:select path="contentType" items="${contentTypes}" class="form-control"></form:select>
                </fieldset>
                <fieldset>
                <form:label path="changelog">Changelog</form:label>
                <form:textarea path="changelog" class="form-control"></form:textarea>
                </fieldset>
        </form:form>
        <!-- End Form -->        
    </div>
    <div class="tab-pane fade" id="pills-attachments" role="tabpanel" aria-labelledby="pills-attachments-tab">
        <form id="attachmentsForm" action="/node/${node.id}/attachments" method="POST">
            <c:forEach items="${node.attachments}" var="attachment" varStatus="idx">
                
                <fieldset>
                    <label for="attachment-${attachment.id}">#${idx.index} ${attachment.attachmentType}</label>
                    <field type
                </fieldset>
                
            </c:forEach>
        </form>
    </div>
</div>    

