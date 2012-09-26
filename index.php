<?php 
	require_once 'lib/limonade.php';

	dispatch('/', 'hello');
	  function hello(){
	      return render('index.html.php');
	  }

	dispatch_get('/test', 'testing');
	  function testing(){
	  	  return "test";
	  } 
	run();
?>