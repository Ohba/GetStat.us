<?php
	$db = parse_url($_SERVER["DATABASE_URL"]);
	$host = $db["host"];
	$port = $db["port"];
	$user = $db["user"];
	$pass = $db["pass"];
	$dbname = trim($db["path"],"/");
	$GLOBALS['database'] = new PDO("mysql:host=$host;port=$port;dbname=$dbname", $user, $pass);
?>