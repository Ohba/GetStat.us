<!DOCTYPE html>
<html>
<head>
	<title>GetStat.us</title>
	<link href="public/css/bootstrap.css" rel="stylesheet" />
	<link href="public/css/project.css" rel="stylesheet" />
	<script type="text/javascript" src="public/js/project.js"></script>
</head>
<body>
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
	            <a href="#">Sign In</a>
	          </li>
	        </ul>
	      </div>
	    </div>
	  </div>
	</div>
	<div class="container">
		<div class="main">
			<span>Enter a URL below to be shortened</span>
			<form method="post" action="index.php/randstring">
				<input type="text" name="url" class="fun" placeholder="http://"/>
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
