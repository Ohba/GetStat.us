<?php
	$host = "";
	$port = "";
	$user = "";
	$pass = "";
	$dbname = "";
	$host = "instance27081.db.xeround.com";
	$port = "17739";
	$user = "getstatus";
	$pass = "rohit";
	$dbname = "dev";
	$GLOBALS['database'] = new PDO("mysql:host=$host;port=$port;dbname=$dbname", $user, $pass);
?>