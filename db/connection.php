<?php
	$host = "instance33457.db.xeround.com";//instance27081.db.xeround.com";
	$port = "14462";//"17739";
	$user = "getstatus";
	$pass = "rohit";
	$dbname = "getstatus";//dev";
	$GLOBALS['database'] = new PDO("mysql:host=$host;port=$port;dbname=$dbname", $user, $pass);
?>