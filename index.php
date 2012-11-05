<?php
	require_once 'lib/limonade.php';
	require_once 'lib/bcrypt.php';
	if(isset($_SERVER["DATABASE_URL"])){
		require_once 'db/heroku_connection.php';
	}else{
		require_once 'db/connection.php';
	}
	function configure()
	{
		$env = contains('localhost', $_SERVER['HTTP_HOST']) ? ENV_DEVELOPMENT : ENV_PRODUCTION;
    	option('env', $env);
		option('debug', true);
		layout('layout.html.php');
	}

	dispatch('/', 'hello');
	  function hello(){
	  	if(isset($_SESSION['user'])){
	  		set('user', serialize($_SESSION['user']));
	  	}else{
	  		set('user', '');
	  	}
	    return render('index.html.php');
	  }

	dispatch('/test', 'testing');
	  function testing(){
	  	$info = env();
	  	$test = "nothing";
	  	if(isset($info["SERVER"])){
	  		$server = $info["SERVER"];
	  		$test = $server["HTTP_REFERER"];
	  	}
		return $test;
	  }

	dispatch_post('/info', info);
		function info(){
			$info = env();
			$post = $info["POST"];
			return print_r($post['url']);
		}

	dispatch('/helloWorld', helloWorld);
		function helloWorld(){
			print("Hello World");
		}

	dispatch_post('/register', register);
		function register(){
			$env = env();
			$post = $env['POST'];
			$data = array('name' => $post['name'], 
							'email' => $post['email'],
							'password' => Bcrypt::hash($post['password']));
			$query = $GLOBALS['database']->prepare('INSERT INTO users (name, email, password) VALUES (:name, :email, :password)');
			$query->execute($data);

			if($query->errorCode() == 0) {
			    return json_encode(array('name' => $post['name'], 'email' => $post['email']));
			} else {
			    $errors = $query->errorInfo();
			    status(SERVER_ERROR);
			    return $errors[2];
			}
		}

	dispatch_post('/signin', signin);
		function signin(){
			$env = env();
			$post = $env['POST'];
			$data = array('email' => $post['email']);
			$query = $GLOBALS['database']->prepare('SELECT * FROM users WHERE email = :email');
			$query->execute($data);
			$row = $query->fetchAll();
			$is_correct = false;
			if(isset($row[0])){
				$id = $row[0]['id'];
				$name = $row[0]['name'];
				$email = $row[0]['email'];
				$password = $row[0]['password'];
				$is_correct = Bcrypt::check($post['password'], $password);
			}
			if($query->errorCode() == 0 && $is_correct) {
				$_SESSION['user'] = array('name' => $name, 'email' => $email, 'id' => $id);
			    return json_encode($_SESSION['user']);
			} elseif(!$is_correct) {
				status(SERVER_ERROR);
			    return 'Password and Email do not match';
			}else{
			    $errors = $query->errorInfo();
			    status(SERVER_ERROR);
			    return $errors[2];
			}
		}
	dispatch('/logout', logout);
		function logout(){
			unset($_SESSION['user']);
			redirect_to('');
		}
	dispatch('/admin', admin);
		function admin(){
			if(isset($_SESSION['user'])){
				set('user', serialize($_SESSION['user']));
				$urls = serialize(getUsersUrls($_SESSION['user']['id']));
				set('urls', $urls);
				return render('admin.html.php');
			}else{
				redirect_to('');
			}
		}

//$url=$_POST['url'];
		

	dispatch('/created',dbInsert);
		function dbInsert($url,$sql){
			$url=$_POST['url'];
			$sql="INSERT INTO urls(destination,short) values('$url','$randomstring') ";
			echo "$url $randomString"; 
		}


	//dispatch_post()		


	dispatch_post('/randstring',randomString);
		function randomString($length = 10) {
	  	  	$characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
	    	$randomString = '';
	    	
	    	for ($i = 0; $i < $length; $i++) {
	        $randomString .= $characters[rand(0, strlen($characters) - 1)];
	    	}
 	   		return $randomString;
		}

	dispatch('/g/:short', 'stuff');
		function stuff(){
			$data = array('stuff' => params('short'));
			$STH = $GLOBALS['database']->prepare('SELECT destination FROM urls WHERE short = :stuff');
			$STH->execute($data);
			$row = $STH->fetchColumn();
			return redirect_to($row);
		}


	run();

	function getUsersUrls($id){
		$data = array('userId' => $id);
		$query = $GLOBALS['database']->prepare('SELECT u.* FROM urls u INNER JOIN lookup l ON l.url_id = u.id INNER JOIN users us ON us.id = l.user_id WHERE us.id = :userId');
		$query->execute($data);
		$row = $query->fetchAll();
		return $row;
	}

	function contains($substring, $string) {
        $pos = strpos($string, $substring);
        if($pos === false) {
            return false;
        }
        else {
            return true;
        }
	}
?>