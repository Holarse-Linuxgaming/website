<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1>Drückblick-Organisation</h1>
<div id="v-admin-drueckblick">

    <h1>{{msg}}</h1>

    <table class="table table-hover">
        <thead>
            <tr>
                <th>#</th>
                <th>Drückblick</th>
                <th>Kategorie</th>
                <th>Eingang</th>
                <th>Melder</th>
                <th>Meldung</th>
                <th>Link</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="(entry, index) in entries" :key="entry.id">
                <td v-bind:data_entry_id="entry.id">{{index}}</td>
                <td>{{entry.drueckblick.name}}</td>
                <td>{{entry.category}}</td>
                <td>{{entry.created}}</td>
                <td>{{entry.bearer}}</td>
                <td>{{entry.message}}</td>
                <td>{{entry.link}}</td>
            </tr>
        </tbody>
    </table>

</div>

