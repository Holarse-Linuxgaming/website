<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="v-article-editor" data-nodeid="${nodeId}">

    <!-- Actions -->
    <div class="row align-items-end">
        <div class="col-md-2 offset-md-2">
          <a href="#" v-on:click="abortEdit" class="btn u-btn-deeporange btn-block g-mr-10 g-mb-15">
              <i class="fa fa-close g-mr-5"></i>
              Abbrechen
            </a>       
        </div>
        <div class="col-md-2">
            <a href="#" class="btn u-btn-bluegray btn-block g-mr-10 g-mb-15">
                <i class="fa fa-archive g-mr-5"></i>
                Als Entwurf speichern
              </a> 
        </div>

        <div class="col-md-4">
            <div class="input-group g-brd-primary--focus g-mb-10">
                <input class="form-control form-control-md border-right-0 rounded-0 pr-0" type="text" placeholder="Deine Änderungsnotiz">
                <b class="tooltip tooltip-top-right u-tooltip--v1">Kurze Notiz über die Änderung</b>
                <div class="input-group-append">
                  <span class="input-group-text rounded-0 g-color-gray-dark-v5"><i class="icon-info"></i></span>
                </div>
            </div>
        </div>

        <div class="col-md-2">
            <a href="#" class="btn u-btn-primary btn-block g-mr-10 g-mb-15">
                <i class="fa fa-save g-mr-5"></i>
                Speichern
              </a>
        </div>
      </div>
    
    <!-- Content -->
    <div class="row">
        <div class="col-md-2">
          <!-- Nav tabs -->
          <ul class="nav flex-column u-nav-v8-2" role="tablist" data-target="nav-8-2-primary-ver" data-tabs-mobile-type="slide-up-down" data-btn-classes="btn btn-md btn-block rounded-0 u-btn-outline-primary">
            <li class="nav-item">
              <a class="nav-link active" data-toggle="tab" href="#nav-8-2-primary-ver--1" role="tab">
                <span class="u-nav-v8__icon u-icon-v3 u-icon-size--lg g-rounded-50x g-brd-around g-brd-4 g-brd-white">
                  <i class="fa fa-edit"></i>
                </span>
                <strong class="text-uppercase u-nav-v8__title">Text</strong>
                <em class="u-nav-v8__description">Wiki-Text des Artikels anpassen</em>
              </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#nav-8-2-primary-ver--2" role="tab">
                  <span class="u-nav-v8__icon u-icon-v3 u-icon-size--lg g-rounded-50x g-brd-around g-brd-4 g-brd-white">
                    <i class="fa fa-link"></i>
                  </span>
                  <strong class="text-uppercase u-nav-v8__title">Verlinkungen</strong>
                  <em class="u-nav-v8__description">Herstellerseite, Shops, usw.</em>
                </a>
              </li>                    
            <li class="nav-item">
              <a class="nav-link" data-toggle="tab" href="#nav-8-2-primary-ver--3" role="tab">
                <span class="u-nav-v8__icon u-icon-v3 u-icon-size--lg g-rounded-50x g-brd-around g-brd-4 g-brd-white">
                  <i class="fa fa-file-image-o"></i>
                </span>
                <strong class="text-uppercase u-nav-v8__title">Screenshots</strong>
                <em class="u-nav-v8__description">Screenshots</em>
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" data-toggle="tab" href="#nav-8-2-primary-ver--4" role="tab">
                <span class="u-nav-v8__icon u-icon-v3 u-icon-size--lg g-rounded-50x g-brd-around g-brd-4 g-brd-white">
                  <i class="fa fa-file-video-o"></i>
                </span>
                <strong class="text-uppercase u-nav-v8__title">Videos</strong>
                <em class="u-nav-v8__description">Youtube-Videos</em>
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" data-toggle="tab" href="#nav-8-2-primary-ver--5" role="tab">
                <span class="u-nav-v8__icon u-icon-v3 u-icon-size--lg g-rounded-50x g-brd-around g-brd-4 g-brd-white">
                  <i class="fa fa-file-archive-o"></i>
                </span>
                <strong class="text-uppercase u-nav-v8__title">AnhÃ¤nge</strong>
                <em class="u-nav-v8__description">Patches, Konfigurationen, usw.</em>
              </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#nav-8-2-primary-ver--6" role="tab">
                  <span class="u-nav-v8__icon u-icon-v3 u-icon-size--lg g-rounded-50x g-brd-around g-brd-4 g-brd-white">
                    <i class="fa fa-gears"></i>
                  </span>
                  <strong class="text-uppercase u-nav-v8__title">Konfiguration</strong>
                  <em class="u-nav-v8__description">Sichtbarkeit, Kommentierbarkeit, usw.</em>
                </a>
              </li>                    
          </ul>
          <!-- End Nav tabs -->
        </div>

        <div class="col-md-10">
          <!-- Tab panes -->
          <div id="nav-8-2-primary-ver" class="tab-content g-pt-20 g-pt-0--md">
            <div class="tab-pane g-px-20 fade show active" id="nav-8-2-primary-ver--1" role="tabpanel">
              <div class="u-heading-v1-1 g-bg-main g-brd-gray-light-v2 g-mb-20">
                  <h2 class="h3 u-heading-v1__title">Titel, Tags und Text bearbeiten</h2>
                </div>

               <form class="g-brd-around g-brd-gray-light-v4 g-pa-30 g-mb-30">
                   <div class="form-group g-mb-20">
                                <label class="g-mb-10" for="mainTitle">Titel</label>
                                <div class="row">                            
                                        <div class="col-md-10">                                                        
                                            <input id="mainTitle" v-model="node.mainTitle" class="form-control form-control-md rounded-0" placeholder="Titel des Artikels">
                                        </div>
                                        <div class="col-md-2">
                                            <a href="#" v-on:click="toggleAlternativeTitles" class="btn btn-md u-btn-primary g-mr-10 g-mb-15" title="Klicken für zusätzliche Titel">
                                                <i class="fa fa-database"></i>
                                            </a>
                                        </div>
                                </div>                            
                                <small class="form-text text-muted g-font-size-default g-mt-10">Der Haupttitel dieses Artikels</small>                            
                   </div>

                   <!-- ZusÃ¤tzliche Titel -->
                   <div class="additional-titles">
                        <div class="form-group g-mb-20">
                            <label class="g-mb-10" for="alternativeTitle1">Alternativtitel 1</label>
                            <input id="alternativeTitle1" v-model="node.alternativeTitle1" class="form-control form-control-md rounded-0" placeholder="Alternativtitel 1">
                            <small class="form-text text-muted g-font-size-default g-mt-10">Alternativtitel 1</small>
                        </div>     

                        <div class="form-group g-mb-20">
                                <label class="g-mb-10" for="alternativeTitle2">Alternativtitel 2</label>
                                <input id="alternativeTitle2" v-model="node.alternativeTitle2" class="form-control form-control-md rounded-0" placeholder="Alternativtitel 2">
                                <small class="form-text text-muted g-font-size-default g-mt-10">Alternativtitel 2</small>
                        </div>   

                        <div class="form-group g-mb-20">
                                <label class="g-mb-10" for="alternativeTitle1">Alternativtitel 3</label>
                                <input id="alternativeTitle3" v-model="node.alternativeTitle3" class="form-control form-control-md rounded-0" placeholder="Alternativtitel 3">
                                <small class="form-text text-muted g-font-size-default g-mt-10">Alternativtitel 3</small>
                        </div>                           
                   </div>

                   <div class="form-group g-mb-20">
                        <label class="g-mb-10" for="tags">Tags</label>
                         <input id="tags" v-model="node.tags" class="form-control form-control-md rounded-0" placeholder="Tags eingeben mit AutovervollstÃ¤ndigung">
                         <small class="form-text text-muted g-font-size-default g-mt-10">Tags, die den Artikel eingruppieren und katalogisieren. Nutzt die AutovervollstÃ¤ndigung, um Tags zu finden</small>
                    </div>               

                   <div class="form-group g-mb-20">
                       <label class="g-mb-10" for="content">Inhalt</label>
                        <textarea id="content" v-model="node.content" class="form-control form-control-md rounded-0" rows="25" placeholder="Artikelinhalt"></textarea>
                        <small class="form-text text-muted g-font-size-default g-mt-10">Artikelinhalt geschrieben in <a href="/content/wiki_syntax" title="Info zur Wiki-Syntax" target="_blank">Wiki-Syntax</a></small>
                   </div>
                </form>
            </div>
            <div class="tab-pane g-px-20 fade" id="nav-8-2-primary-ver--2" role="tabpanel">
              <div class="u-heading-v1-1 g-bg-main g-brd-gray-light-v2 g-mb-20">
                  <h2 class="h3 u-heading-v1__title">Links bearbeiten</h2>
                </div>                      
              <p>Equidem recteque sea et. Per detracto iracundia ea, duo nusquam denique omittantur in. Nam ex quas saperet, solum convenire vel in, dicant atomorum his ne. Putant bonorum in nam, nostrud neglegentur accommodare eam ad. Quo cu
                primis delenit.</p>

              <p>Nam ut viris disputando dissentiunt, sumo vocent ad mea. Vel justo debitis neglegentur an. Facer doctus inimicus et est, sed ea sint dicant. Per soleat pertinax complectitur et, pro no porro cetero, mea probo apeirian torquatos
                ut. Habeo dicat errem quo et, ut democritum sententiae eloquentiam cum. Sit ad oblique inciderint reformidans, homero albucius voluptatum pro te, saepe laoreet in est.</p>
            </div>
            <div class="tab-pane g-px-20 fade" id="nav-8-2-primary-ver--3" role="tabpanel">
              <div class="u-heading-v1-1 g-bg-main g-brd-gray-light-v2 g-mb-20">
                  <h2 class="h3 u-heading-v1__title">Screenshots bearbeiten</h2>
                </div>                      
              <p>Equidem recteque sea et. Per detracto iracundia ea, duo nusquam denique omittantur in. Nam ex quas saperet, solum convenire vel in, dicant atomorum his ne. Putant bonorum in nam, nostrud neglegentur accommodare eam ad. Quo cu
                primis delenit.</p>

              <p>Nam ut viris disputando dissentiunt, sumo vocent ad mea. Vel justo debitis neglegentur an. Facer doctus inimicus et est, sed ea sint dicant. Per soleat pertinax complectitur et, pro no porro cetero, mea probo apeirian torquatos
                ut. Habeo dicat errem quo et, ut democritum sententiae eloquentiam cum. Sit ad oblique inciderint reformidans, homero albucius voluptatum pro te, saepe laoreet in est.</p>
            </div>
            <div class="tab-pane g-px-20 fade" id="nav-8-2-primary-ver--4" role="tabpanel">
              <div class="u-heading-v1-1 g-bg-main g-brd-gray-light-v2 g-mb-20">
                  <h2 class="h3 u-heading-v1__title">Videos bearbeiten</h2>
                </div>                      
              <p>Equidem recteque sea et. Per detracto iracundia ea, duo nusquam denique omittantur in. Nam ex quas saperet, solum convenire vel in, dicant atomorum his ne. Putant bonorum in nam, nostrud neglegentur accommodare eam ad. Quo cu
                primis delenit.</p>

              <p>Nam ut viris disputando dissentiunt, sumo vocent ad mea. Vel justo debitis neglegentur an. Facer doctus inimicus et est, sed ea sint dicant. Per soleat pertinax complectitur et, pro no porro cetero, mea probo apeirian torquatos
                ut. Habeo dicat errem quo et, ut democritum sententiae eloquentiam cum. Sit ad oblique inciderint reformidans, homero albucius voluptatum pro te, saepe laoreet in est.</p>
            </div>
            <div class="tab-pane g-px-20 fade" id="nav-8-2-primary-ver--5" role="tabpanel">
                <div class="u-heading-v1-1 g-bg-main g-brd-gray-light-v2 g-mb-20">
                    <h2 class="h3 u-heading-v1__title">AnhÃ¤nge/Downloads bearbeiten</h2>
                  </div>                      
                <p>Equidem recteque sea et. Per detracto iracundia ea, duo nusquam denique omittantur in. Nam ex quas saperet, solum convenire vel in, dicant atomorum his ne. Putant bonorum in nam, nostrud neglegentur accommodare eam ad. Quo cu
                  primis delenit.</p>

                <p>Nam ut viris disputando dissentiunt, sumo vocent ad mea. Vel justo debitis neglegentur an. Facer doctus inimicus et est, sed ea sint dicant. Per soleat pertinax complectitur et, pro no porro cetero, mea probo apeirian torquatos
                  ut. Habeo dicat errem quo et, ut democritum sententiae eloquentiam cum. Sit ad oblique inciderint reformidans, homero albucius voluptatum pro te, saepe laoreet in est.</p>
              </div>   
              <div class="tab-pane g-px-20 fade" id="nav-8-2-primary-ver--6" role="tabpanel">
                  <div class="u-heading-v1-1 g-bg-main g-brd-gray-light-v2 g-mb-20">
                      <h2 class="h3 u-heading-v1__title">Artikeleinstellungen bearbeiten</h2>
                    </div>                        
                  <p>Equidem recteque sea et. Per detracto iracundia ea, duo nusquam denique omittantur in. Nam ex quas saperet, solum convenire vel in, dicant atomorum his ne. Putant bonorum in nam, nostrud neglegentur accommodare eam ad. Quo cu
                    primis delenit.</p>

                  <p>Nam ut viris disputando dissentiunt, sumo vocent ad mea. Vel justo debitis neglegentur an. Facer doctus inimicus et est, sed ea sint dicant. Per soleat pertinax complectitur et, pro no porro cetero, mea probo apeirian torquatos
                    ut. Habeo dicat errem quo et, ut democritum sententiae eloquentiam cum. Sit ad oblique inciderint reformidans, homero albucius voluptatum pro te, saepe laoreet in est.</p>
                </div>                                          
          </div>
          </form>      
          <!-- End Tab panes -->
        </div>
    </div>
</div>    