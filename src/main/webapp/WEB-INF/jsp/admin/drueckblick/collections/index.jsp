<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/x-template" id="admin-drueckblick-collection">
    <tr>    
        <td>{{collection.id}}</td>
        <td>{{collection.begin}}</td>
        <td>{{collection.end}}</td>
        <td>{{collection.name}}</td>
        <td>{{collection.title}}</td>
        <td></td>
    </tr>    
</script>

<h1>Drückblick-Organisation</h1>
<div id="v-admin-drueckblick-collections">
<div class="card-block g-pa-15">
    <p class="card-text">
        Übersicht über bestehende Drückblicke
    </p>
    <p class="card-text">      
    </p>        
    </div>

    <table class="table table-hover">
        <thead>
            <tr>
                <th>#</th>
                <th>Beginn Erfassung</th>
                <th>Ende Erfassung</th>
                <th>Nummer</th>
                <th class="g-min-width-400">Titel</th>
                <th>Meldungen</th>
                <th>Aktionen</th>
            </tr>
        </thead>
        <tbody>
            <tr is="drueckblick-collection" v-for="(collection, index) in collections" :key="collection.id" 
                                                                                       :collection="collection" />
        </tbody>
    </table>
</div>


