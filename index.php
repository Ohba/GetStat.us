<?php
	require_once 'lib/limonade.php';
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
	}

	dispatch('/', 'hello');
	  function hello(){
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
			print('register');
		}

	dispatch_post('/signin', signin);
		function signin(){
			print('signin');
		}
	
//	dispatch('/shortener', shortener)		
//		function shortener(){
//		}

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