<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/x-template" id="admin-webuser">
    <tr>    
        <td>{{webuser.id}}</td>
        <td>{{webuser.login}}</td>
        <td>{{webuser.email}}</td>
        <td>
            <label class="form-check-inline u-check mx-0 mb-0">
                <input class="g-hidden-xs-up g-pos-abs g-top-0 g-right-0" name="radGroup1_1" type="checkbox" checked="" v-model="webuser.verified">
                <div class="u-check-icon-radio-v8">
                  <i class="fa" data-check-icon=""></i>
                </div>
              </label>
              <label class="form-check-inline u-check mx-0 mb-0">
                <input class="g-hidden-xs-up g-pos-abs g-top-0 g-right-0" name="radGroup1_2" type="checkbox" checked="" v-model="webuser.locked">
                <div class="u-check-icon-radio-v8">
                  <i class="fa" data-check-icon=""></i>
                </div>
              </label>              
        </td>
        <td>{{webuser.created}}</td>
        <td>{{webuser.updated}}</td>
        <td>{{webuser.lastlogin}}</td>
        <td>
        </td>
        <td>
            <a href="#" class="btn btn-sm u-btn-info g-mr-10 g-mb-15" title="Bearbeiten">
                <i class="fa fa-pencil"></i>
            </a>               
            <a href="#" class="btn btn-sm u-btn-danger g-mr-10 g-mb-15" title="Löschen">
                <i class="fa fa-trash-o"></i>
            </a>
        </td>
    </tr>    
</script>

<h1>Web-Benutzer</h1>
<div id="v-admin-webusers">
<div class="card-block g-pa-15">
    <p class="card-text">
        Die Webseiten-Benutzerkonten. Konten können mit bestimmten Rollen versehen werden, um dem Konto erweiterte Berechtigungen
        zu gewähren.
    </p>
    </div>

    <table class="table table-hover">
        <thead>
            <tr>
                <th>#</th>
                <th>Login</th>
                <th>E-Mail</th>
                <th>Token</th>
                <th>Verifiziert / Gesperrt</th>
                <th>Erstellt</th>
                <th>Aktualisiert</th>
                <th>Letztes Login</th>
            </tr>
        </thead>
        <tbody>
            <tr is="webuser" v-for="(webuser, index) in webusers" :key="webuser.id" 
                                                                  :webuser="webuser" />
        </tbody>
    </table>
</div>