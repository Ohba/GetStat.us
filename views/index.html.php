<div class="modal hide fade" id="signInModal">
  <div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	<h3>Sign In</h3>
  </div>
  <div class="modal-body">
	<form class="form-horizontal signin-form">
	  <div class="alert alert-error hide"></div>
	  <div class="control-group hide register-name">
		<label class="control-label" for="inputName">Name</label>
		<div class="controls">
		  <input type="text" name="name" id="inputName" placeholder="Name">
		</div>
	  </div>
	  <div class="control-group">
		<label class="control-label" for="inputEmail">Email</label>
		<div class="controls">
		  <input type="email" name="email" id="inputEmail" placeholder="Email">
		</div>
	  </div>
	  <div class="control-group">
		<label class="control-label" for="inputPassword">Password</label>
		<div class="controls">
		  <input type="password" name="password" id="inputPassword" placeholder="Password">
		</div>
	  </div>
	  <div class="control-group">
		<div class="controls">
		  <span id="signin-help">Not yet a user? <a class="clickable" >Register</a></span>
		  <span id="register-help" class="hide">Already yet a user? <a class="clickable" >Sign In</a></span>
		</div>
	  </div>
	</form>
  </div>
  <div class="modal-footer">
	<a class="clickable btn btn-primary signin" data-action="/signin">Sign In</a>
  </div>
</div>
<div class="main alert alert-error hide"></div>
<div class="main hero-unit text-center">
	<h2>Enter a URL below to be shortened</h2>
	<form method="post" action="created" class="form-search">
		<input type="text" name="url" class="fun input-medium search-query" placeholder="http://"/>
		<button class="btn btn-primary">Go!</button>
	</form>
</div>
<div class="text-center">
	<h2 id="shortened"></h2>
</div>