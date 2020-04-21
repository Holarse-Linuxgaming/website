<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/x-template" id="admin-drueckblick-entry">
    <tr v-on:change="entry.changed = true">    
        <td v-bind:data_entry_id="entry.id">{{index}}</td>
        <td>
            <select class="form-control form-control-md rounded-0" v-model="entry.category" :disabled="entry.deleted">
                <option v-for="category in categories" v-bind:value="category.value">
                  {{ category.label }}
                </option>
              </select>
        </td>
        <td><span :title="entry.created">{{entry.createdAgo}}</span></td>
        <td><input class="form-control form-control-md rounded-0" v-model="entry.bearer"     :disabled="entry.deleted"></td>
        <td><textarea class="form-control form-control-md u-textarea-expandable rounded-0" rows="3" v-model="entry.message" :disabled="entry.deleted"></textarea></td>
        <td><textarea class="form-control form-control-md u-textarea-expandable rounded-0"  v-model="entry.link"       :disabled="entry.deleted"></textarea></td>  
        <td class="align-middle text-nowrap text-center">
            <button class="btn u-btn-primary g-mr-10 g-mb-15" v-on:click="update_entry(entry)" :disabled="!entry.changed" title="Speichern">
                <i class="fa fa-floppy-o g-mr-3"></i>
                Speichern
            </button>
            <button class="btn u-btn-outline-red g-mr-10 g-mb-15" v-on:click="delete_entry(entry)" title="Löschen oder wieder freigeben">
                <i class="fa fa-trash-o g-mr-3"></i>
            </button>
        </td>  
    </tr>    
</script>

<h1>Drückblick-Organisation</h1>
<div id="v-admin-drueckblick-entries">
<div class="card-block g-pa-15">
    <p class="card-text">
        Zusammenstellung neuer eingegangener Drückblick-Meldungen. Alle nachfolgenden Meldungen sind noch keinem Drückblick zugeordnet und damit als Neu zu bewerten.
        Änderungen an den Texten werden sofort gespeichert. Mit <strong>Drückblick anfordern</strong> wird ein neuer Drückblick
        vorgeschlagen. Zu diesem können dann alle noch offenen hinzugefügt werden. Gelöscht Einträge werden übersprungen.
    </p>
    <p class="card-text">      
        <div class="row">
            <div class="col-md-3">
                <button type="button" class="btn btn-primary g-mr-10 g-mb-15" v-on:click="request_dbl">Drückblick anfordern</button>
            </div>
            
            <div class="col-md-3">
                <div class="form-group g-mb-20">
                    <label class="g-mb-10" for="dbl_prop_name">Name des Drückblicks (seine Nummer)</label>
                    <input id="dbl_prop_name" v-model="drueckblick_proposal.name" class="form-control form-control-md rounded-0">
                    <small class="form-text text-muted g-font-size-default g-mt-10">Die fortlaufende Nummer des Drückblicks.</small>
                </div>                            
            </div> 

            <div class="col-md-3">
                <div class="form-group g-mb-20">
                    <label class="g-mb-10" for="dbl_prop_begin">Drückblick Erfassungsbeginn</label>
                    <div class="input-group g-brd-primary--focus">
                        <div class="input-group-prepend">
                            <span class="input-group-text rounded-0 g-color-gray-dark-v5"><i class="icon-calendar"></i></span>
                        </div>                                            
                        <input id="dbl_prop_begin" v-model="drueckblick_proposal.begin" class="form-control form-control-md rounded-0 u-datepicker-v1" type="text" data-mask="9999-99-99"  data-range="true" data-to="dbl_prop_end">
                        <small class="form-text text-muted g-font-size-default g-mt-10">Das Datum ab dem dieser Drückblick beginnt (meist letzter Samstag).</small>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="form-group g-mb-20">
                    <label class="g-mb-10" for="dbl_prop_end">Drückblick Erfassungsende</label>
                    <div class="input-group g-brd-primary--focus">
                        <div class="input-group-prepend">
                            <span class="input-group-text rounded-0 g-color-gray-dark-v5"><i class="icon-calendar"></i></span>
                        </div>                                            
                        <input id="dbl_prop_end" v-model="drueckblick_proposal.end" class="form-control form-control-md rounded-0 u-datepicker-v1" type="text" data-mask="9999-99-99">
                        <small class="form-text text-muted g-font-size-default g-mt-10">Das Datum bis zu dem dieser Drückblick reicht (meist heute).</small>
                    </div>
                </div>
            </div>  
        </div>                     
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
            <tr is="drueckblick-entry" v-for="(entry, index) in entries" :key="entry.id" 
                                                                         :entry="entry" 
                                                                         :index="index + 1" 
                                                                         :categories="categories" />
        </tbody>
    </table>
</div>


