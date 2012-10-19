<!DOCTYPE html>
<html>
<head>
	<title>GetStat.us</title>
	<link type="text/css" href="public/css/bootstrap.css" rel="stylesheet" />
	<link type="text/css" href="public/css/project.css" rel="stylesheet" />
	<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
	<script type="text/javascript" src="public/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="public/js/project.js"></script>
</head>
<body>
	<div class="modal hide fade" id="signInModal">
	  <div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h3>Sign In</h3>
	  </div>
	  <div class="modal-body">
	  	<div class="alert alert-error hide"></div>
		<form class="form-horizontal signin-form">
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
	<div class="navbar navbar-inverse navbar-fixed-top">
	  <div class="navbar-inner">
		<div class="container">
		  <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
		  </button>
		  <a class="brand" href="/">GetStat.us</a>
		  <div class="nav-collapse collapse">
			<ul class="nav">
			  <li>
				<a data-toggle="modal" data-target="#signInModal">Sign In</a>
			  </li>
			</ul>
		  </div>
		</div>
	  </div>
	</div>
	<div class="container">
		<div class="main hero-unit text-center">
			<h2>Enter a URL below to be shortened</h2>
			<form method="post" action="randstring" class="form-search">
				<input type="text" name="url" class="fun input-medium search-query" placeholder="http://"/>
				<button class="btn btn-primary">Go!</button>
			</form>
		</div>
	</div>
</body>
</html>


<?php
if(isset($_post['url'])){
	$url=$_post['url'];
	$sql="INSERT INTO urls(destination,short) VALUES('$url','$randomstring')";
}

?>
