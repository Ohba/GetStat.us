<?php
	$db = parse_url($_SERVER["DATABASE_URL"]);
	$host = $db["host"];
	$user = $db["user"];
	$pass = $db["pass"];
	$dbname = trim($db["path"],"/");
	$GLOBALS['database'] = new PDO("mysql:host=$host;dbname=$dbname", $user, $pass);
?>