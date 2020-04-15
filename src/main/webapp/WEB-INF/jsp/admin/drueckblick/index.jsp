<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/x-template" id="admin-drueckblick-entry">
    <tr v-on:change="entry.changed = true">    
        <td v-bind:data_entry_id="entry.id">{{index}}</td>
        <td>{{entry.category}}</td>
        <td>{{entry.created}}</td>
        <td><input class="form-control form-control-md rounded-0" v-model="entry.bearer"     :disabled="entry.deleted"></td>
        <td><textarea class="form-control form-control-md u-textarea-expandable rounded-0" rows="3" v-model="entry.message" :disabled="entry.deleted"></textarea></td>
        <td><textarea class="form-control form-control-md u-textarea-expandable rounded-0"  v-model="entry.link"       :disabled="entry.deleted"></textarea></td>  
        <td class="align-middle text-nowrap text-center">
            <button class="btn u-btn-primary g-mr-10 g-mb-15" v-on:click="update_entry(entry)" :disabled="!entry.changed" title="Speichern">
                <i class="fa fa-floppy-o g-mr-3"></i>
                Speichern
            </button>
            <button class="btn u-btn-outline-red g-mr-10 g-mb-15" v-on:click="delete_entry(entry)" title="Löschen oder undo">
                <i class="fa fa-trash-o g-mr-3"></i>
            </button>
        </td>  
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
                <th class="g-min-width-200">Melder</th>
                <th class="g-min-width-400">Meldung</th>
                <th class="g-min-width-300">Link</th>
            </tr>
        </thead>
        <tbody>
            <tr is="drueckblick-entry" v-for="(entry, index) in entries" :key="entry.id" :entry="entry" :index="index + 1" />
        </tbody>
    </table>
</div>


