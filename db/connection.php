<?php
	$host = "";
	$port = "";
	$user = "";
	$pass = "";
	$dbname = "";
	$GLOBALS['database'] = new PDO("mysql:host=$host;port=$port;dbname=$dbname", $user, $pass);
?>