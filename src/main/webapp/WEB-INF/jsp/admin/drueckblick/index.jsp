<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/x-template" id="admin-drueckblick-entry-x">
    <tr>    
        <td v-bind:data_entry_id="entry.id">{{index}}</td>
        <td>{{entry.category}}</td>
        <td>{{entry.created}}</td>
        <td>{{entry.bearer}}</td>
        <td>{{entry.message}}</td>
        <td>{{entry.link}}</td>    
    </tr>    
</script>

<script type="text/x-template" id="admin-drueckblick-entry">
    <tr>    
        <td v-bind:data_entry_id="entry.id">{{index}}</td>
        <td>{{entry.category}}</td>
        <td>{{entry.created}}</td>
        <td><input v-model="entry.bearer"></td>
        <td><textarea v-model="entry.message"></textarea></td>
        <td><input v-model="entry.link"></td>    
    </tr>    
</script>

<h1>Drückblick-Organisation</h1>
<div id="v-admin-drueckblick">
<div class="card-block g-pa-15">
    <p class="card-text">
        Zusammenstellung neuer eingegangener Drückblick-Meldungen. Alle nachfolgenden Meldungen sind noch keinem Drückblick zugeordnet und damit als Neu zu bewerten.
        Änderungen an den Texten werden sofort gespeichert. Auswahl der Zeilen ermöglicht das Zusammenfügen zu einem neuen Drückblick.</p>
    <p class="card-text">
        <button type="button" class="btn btn-primary g-mr-10 g-mb-15">Drückblick aus Ausgewählten erstellen</button>
    </p>        
    </div>

    <table class="table table-hover">
        <thead>
            <tr>
                <th>#</th>
                <th>Kategorie</th>
                <th>Eingang</th>
                <th>Melder</th>
                <th>Meldung</th>
                <th>Link</th>
            </tr>
        </thead>
        <tbody>
            <tr is="drueckblick-entry" v-for="(entry, index) in entries" :key="entry.id" :entry="entry" :index="index + 1" />
        </tbody>
    </table>
</div>


