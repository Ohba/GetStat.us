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
	<div class="navbar navbar-fixed-top">
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
			  <li id="logoutLink" class="<?php if(!h($name)){ echo 'hide';} ?>">
				<a href="/logout">Logout</a>
			  </li>
			  <li id="signinLink" class="<?php if(h($name)){ echo 'hide';} ?>">
				<a data-toggle="modal" data-target="#signInModal">Sign In</a>
			  </li>
			</ul>
			<?php if(h($name)){ ?>
				<a id="user" class="pull-right welcome">Welcome <?php echo h($name)?>!</a>
			<?php } else { ?>
				<a id="user" class="pull-right welcome hide"></a>
			<?php } ?>
		  </div>
		</div>
	  </div>
	</div>
	<div class="container">
		<?php echo $content; ?>
	</div>
</body>
</html>