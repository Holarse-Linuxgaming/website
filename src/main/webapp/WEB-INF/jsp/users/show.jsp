<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="card" style="width: 18rem;">
  <img class="card-img-top" src="assets/img/testbild.png" alt="">
  <div class="card-body">
      <h5 class="card-title">${user.login}</h5>
    <p class="card-text">${user.signature}</p>
    <a href="${user.url}/edit" class="card-link"><i class="fa fa-cogs"></i></a>
  </div>
</div>
