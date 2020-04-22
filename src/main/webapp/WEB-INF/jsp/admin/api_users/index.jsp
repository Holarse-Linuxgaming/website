<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/x-template" id="admin-apiuser">
    <tr>    
        <td>{{apiuser.id}}</td>
        <td>{{apiuser.login}}</td>
        <td>{{apiuser.rolename}}</td>
        <td>{{apiuser.token}}</td>
        <td>{{apiuser.created}}</td>
        <td>{{apiuser.updated}}</td>
        <td>
            <label class="form-check-inline u-check mx-0 mb-0">
                <input class="g-hidden-xs-up g-pos-abs g-top-0 g-right-0" name="radGroup1_1" type="checkbox" checked="" v-model="apiuser.active">
                <div class="u-check-icon-radio-v8">
                  <i class="fa" data-check-icon=""></i>
                </div>
              </label>
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

<h1>API-Benutzer</h1>
<div id="v-admin-apiusers">
<div class="card-block g-pa-15">
    <p class="card-text">
        API-Benutzerkonten, die von Bots oder externen Tools zur Datenabfrage oder Eingabe genutzt werden können. API-Konten
        sind immer einer bestimmten spezifischen Rolle zugeordnet, so dass auch bei ungewünschtem Zugriff, nicht die
        gesamte API offen liegt.
    </p>
    <p class="card-text">  
        <div class="row">
            <div class="col-md-12">
                <a href="#" class="btn u-btn-primary g-mr-10 g-mb-15">
                    <i class="fa fa-plus g-mr-5"></i>
                    API-Benutzer anlegen
                  </a>
            </div>
        </div>    
    </p>        
    </div>

    <table class="table table-hover">
        <thead>
            <tr>
                <th>#</th>
                <th>Login</th>
                <th>Rolename</th>
                <th>Token</th>
                <th>Erstellt</th>
                <th>Aktualisiert</th>
                <th>Aktiv?</th>
                <th>Aktionen</th>
            </tr>
        </thead>
        <tbody>
            <tr is="apiuser" v-for="(apiuser, index) in apiusers" :key="apiuser.id" 
                                                                  :apiuser="apiuser" />
        </tbody>
    </table>
</div>


