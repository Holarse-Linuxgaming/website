<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Singup -->
<section class="container g-py-100">
    <div class="row justify-content-center">
        <div class="col-sm-10 col-md-9 col-lg-6">
            <div class="g-brd-around g-brd-gray-light-v4 rounded g-py-40 g-px-30">
                <header class="text-center mb-4">
                    <h2 class="h2 g-color-black g-font-weight-600">Signup</h2>
                </header>

                <!-- Form -->
                <form class="g-py-15" action="/register" method="POST">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>	
                    <div class="row">
                        <div class="col-xs-12 col-sm-12 mb-0">
                            <label class="g-color-gray-dark-v2 g-font-weight-600 g-font-size-13">Login</label>
                            <input class="form-control g-color-black g-bg-white g-bg-white--focus g-brd-gray-light-v4 g-brd-primary--hover rounded g-py-15 g-px-15" name="login" type="text" placeholder="John">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12 col-sm-12 mb-0">
                            <label class="g-color-gray-dark-v2 g-font-weight-600 g-font-size-13">Email:</label>
                            <input class="form-control g-color-black g-bg-white g-bg-white--focus g-brd-gray-light-v4 g-brd-primary--hover rounded g-py-15 g-px-15" name="email" type="email" placeholder="johndoe@gmail.com">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12 col-sm-6 mb-4">
                            <label class="g-color-gray-dark-v2 g-font-weight-600 g-font-size-13">Password:</label>
                            <input class="form-control g-color-black g-bg-white g-bg-white--focus g-brd-gray-light-v4 g-brd-primary--hover rounded g-py-15 g-px-15" name="password" type="password" placeholder="********">
                        </div>

                        <div class="col-xs-12 col-sm-6 mb-4">
                            <label class="g-color-gray-dark-v2 g-font-weight-600 g-font-size-13">Password wiederholen:</label>
                            <input class="form-control g-color-black g-bg-white g-bg-white--focus g-brd-gray-light-v4 g-brd-primary--hover rounded g-py-15 g-px-15" name="password_confirmation" type="password" placeholder="Password">
                        </div>
                    </div>

                    <div class="row justify-content-between mb-5">
                        <div class="col-8 align-self-center">
                            <label class="form-check-inline u-check g-color-gray-dark-v5 g-font-size-13 g-pl-25">
                                <input class="g-hidden-xs-up g-pos-abs g-top-0 g-left-0" type="checkbox">
                                <div class="u-check-icon-checkbox-v6 g-absolute-centered--y g-left-0">
                                    <i class="fa" data-check-icon="&#xf00c"></i>
                                </div>
                                I accept the <a href="#">Terms and Conditions</a>
                            </label>
                        </div>
                        <div class="col-4 align-self-center text-right">
                            <button class="btn btn-md u-btn-primary rounded g-py-13 g-px-25" type="submit">Konto registrieren</button>
                        </div>
                    </div>
                </form>
                <!-- End Form -->

                <footer class="text-center">
                    <p class="g-color-gray-dark-v5 g-font-size-13 mb-0">Du hast schon ein Konto? Dann <a class="g-font-weight-600" href="/login">einloggen</a>.
                    </p>
                </footer>
            </div>
        </div>
    </div>
</section>
<!-- End Singup -->
