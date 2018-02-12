<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Login -->
    <section class="container g-pt-100 g-pb-20">
      <div class="row justify-content-between">
        <div class="col-md-6 col-lg-5 flex-md-unordered g-mb-80">
          <div class="g-brd-around g-brd-gray-light-v3 g-bg-white rounded g-px-30 g-py-50 mb-4">
            <header class="text-center mb-4">
              <h1 class="h3 g-color-black g-font-weight-300 text-capitalize">An Holarse anmelden</h1>
            </header>

            <!-- Form -->
            <form:form name="loginform" action="/login" method="POST" class="g-py-15">
              <div class="mb-4">
                <div class="input-group g-brd-primary--focus">
                  <span class="input-group-addon g-width-45 g-brd-gray-light-v3 g-color-gray-dark-v5">
                      <i class="icon-finance-067 u-line-icon-pro"></i>
                    </span>
                  <input class="form-control g-color-black g-brd-gray-light-v3 g-py-15 g-px-15" type="login" name="username" placeholder="Benutzername">
                </div>
              </div>

              <div class="mb-4">
                <div class="input-group g-brd-primary--focus mb-4">
                  <span class="input-group-addon g-width-45 g-brd-gray-light-v3 g-color-gray-dark-v5">
                      <i class="icon-media-094 u-line-icon-pro"></i>
                    </span>
                  <input class="form-control g-color-black g-brd-gray-light-v3 g-py-15 g-px-15" name="password" type="password">
                </div>
              </div>

              <div class="row justify-content-between mb-4">
                <div class="col align-self-center">
                  <label class="form-check-inline u-check g-color-gray-dark-v5 g-font-size-13 g-pl-25 mb-0">
                    <input class="g-hidden-xs-up g-pos-abs g-top-0 g-left-0" type="checkbox">
                    <div class="u-check-icon-checkbox-v6 g-absolute-centered--y g-left-0">
                      <i class="fa g-rounded-2" data-check-icon="&#xf00c"></i>
                    </div>
                    Keep signed in
                  </label>
                </div>
                <div class="col align-self-center text-right">
                  <a class="g-font-size-13" href="#">Passwort vergessen?</a>
                </div>
              </div>

              <div class="mb-5">
                <button class="btn btn-block u-btn-primary g-py-13" type="submit">Login</button>
              </div>
            </form:form>
            <!-- End Form -->
          </div>

          <div class="text-center">
            <p class="g-color-gray-dark-v5 mb-0">Noch kein Konto bei Holarse? <a class="g-font-weight-600" href="/register">Hier Konto erstellen</a>
            </p>
          </div>
        </div>

        <div class="col-md-6 flex-md-first g-mb-80">
          <div class="mb-5">
            <h2 class="h1 g-font-weight-300 mb-3">Vorteiles eines Holarse-Kontos</h2>
            <p class="g-color-gray-dark-v5">Ein Konto bei Holarse eröffnet einige Linuxgamer-Dienste für dich.Was man mit seinem Holarse-Konto alles anstellen kann, stellen wir hier kurz vor:</p>
          </div>

          <div class="row">
            <div class="col-lg-9">
              <!-- Icon Blocks -->
              <div class="media mb-4">
                <div class="d-flex mr-3">
                  <span class="align-self-center u-icon-v1 u-icon-size--lg g-color-primary">
                      <i class="icon-finance-168 u-line-icon-pro"></i>
                    </span>
                </div>
                <div class="media-body align-self-center">
                  <h3 class="h5">Mitgestalten und Schreiben</h3>
                  <p class="g-color-gray-dark-v5 mb-0">Die Holarse-Artikel sind für jeden wie als Wiki bearbeitbar. So kannst du dich einfach beteiligen und auch Kommentare schreiben.</p>
                </div>
              </div>
              <!-- End Icon Blocks -->

              <!-- Icon Blocks -->
              <div class="media mb-4">
                <div class="d-flex mr-3">
                  <span class="align-self-center u-icon-v1 u-icon-size--lg g-color-primary">
                      <i class="icon-finance-193 u-line-icon-pro"></i>
                    </span>
                </div>
                <div class="media-body align-self-center">
                  <h3 class="h5">Mumble</h3>
                  <p class="g-color-gray-dark-v5 mb-0">Dein Holarse-Konto ermöglicht dir automatisch auch auf den Mumble-Server zuzugreifen.</p>
                </div>
              </div>
              <!-- End Icon Blocks -->

              <!-- Icon Blocks -->
              <div class="media">
                <div class="d-flex mr-3">
                  <span class="align-self-center u-icon-v1 u-icon-size--lg g-color-primary">
                      <i class="icon-finance-122 u-line-icon-pro"></i>
                    </span>
                </div>
                <div class="media-body align-self-center">
                  <h3 class="h5">Jabber</h3>
                  <p class="g-color-gray-dark-v5 mb-0">Wer noch nicht genug hat, kann mit dem Holarse-Konto direkt unseren Jabber-Server mitbenutzen.</p>
                </div>
              </div>
              <!-- End Icon Blocks -->
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- End Login -->
    
