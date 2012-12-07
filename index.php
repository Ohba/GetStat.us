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
	  	$referer = "nothing";
	  	if(isset($info["SERVER"])){
	  		$server = $info["SERVER"];
	  		$referer = $server["HTTP_REFERER"];
			$pattern = '/^((http[s]?|ftp):\/)?\/?([^:\/\s]+)/';
			preg_match($pattern, $referer, $matches);
			return print_r($matches[3]);
	  	}
		return $referer;
	  }

	dispatch_post('/register', 'register');
		function register(){
			$env = env();
			$post = $env['POST'];
			$data = array('name' => $post['name'], 
							'email' => $post['email'],
							'password' => Bcrypt::hash($post['password']));
			$query = $GLOBALS['database']->prepare('INSERT INTO users (name, email, password) VALUES (:name, :email, :password)');
			$query->execute($data);
			$id = $GLOBALS['database']->lastInsertId();
			$name = $post['name'];
			$email = $post['email'];
			if($query->errorCode() == 0) {
				$_SESSION['user'] = array('name' => $name, 'email' => $email, 'id' => $id);
			    return json_encode($_SESSION['user']);
			} else {
			    $errors = $query->errorInfo();
			    status(SERVER_ERROR);
			    return $errors[2];
			}
		}

	dispatch_post('/signin', 'signin');
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

	dispatch('/logout', 'logout');
		function logout(){
			unset($_SESSION['user']);
			redirect_to('');
		}

	dispatch('/admin', 'admin');
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

	dispatch('/admin/url/data/:id', 'urlData');
		function urlData(){
			$id = params('id');
			$urlData = array('id' => $id);
			$urlQuery = $GLOBALS['database']->prepare("SELECT * from urls where id = :id");
			$urlQuery->execute($urlData);
			return json_encode($urlQuery->fetchAll());
		}

	dispatch('/admin/donut/data/:id', 'urlDonutData');
		function urlDonutData(){
			$id = params('id');
			$data = array('id' => $id);
			$STH = $GLOBALS['database']->prepare("SELECT origin.url AS url, COUNT(*) AS count
													FROM origin INNER JOIN stats ON origin.id=stats.origin_id
																INNER JOIN urls ON stats.url_id = urls.id
													WHERE urls.id = :id
													GROUP BY origin_id");
			$STH->execute($data);
			return json_encode($STH->fetchAll());
		}

	dispatch('/admin/line/data/:id', 'urlLineData');
		function urlLineData(){
			$id = params('id');
			$data = array('id' => $id);
			$STH = $GLOBALS['database']->prepare("SELECT DATE_FORMAT(stats.created, '%e %b %Y') AS date, COUNT(*) AS count
													FROM origin INNER JOIN stats ON origin.id=stats.origin_id
																INNER JOIN urls ON stats.url_id = urls.id
													WHERE urls.id = :id
													GROUP BY DATE_FORMAT(stats.created, '%e %b %Y')");
			$STH->execute($data);
			return json_encode($STH->fetchAll());
		}

	dispatch_post('/created','dbInsert');
		function dbInsert(){
			$url=$_POST['url'];
			$randomstring = '';
			if(preg_match("/^http/",$url) == 0){
				$url='http://'.$url;
			}
			do{
				$randomstring = randomString();
				$data = array('short' => $randomstring);
				$STH = $GLOBALS['database']->prepare("SELECT count(*) FROM urls WHERE short= :short");
				$STH->execute($data);
				$row = $STH->fetchColumn();
			} while ($row != 0);
			if(isset($_SESSION['user'])){
				$id = $_SESSION['user']['id'];
			}else{
				$id = 0;
			}
			$data = array('url' => $url, 'short' => $randomstring, 'id' => $id);
			$query = $GLOBALS['database']->prepare("INSERT INTO urls(user_id, destination,short) values(:id, :url, :short)");
			$query->execute($data);
			return $randomstring;
		}

	dispatch('/g/:short', 'stuff');
		function stuff(){
			$info = env();
			$referrer = getReferrer($info);
			$originData = array('referer' => $referrer);
			$originquery = $GLOBALS['database']->prepare('SELECT id FROM origin WHERE url = :referer');
			$originquery->execute($originData);
			$originid=$originquery->fetchColumn();
			if($originid == ""){
				$neworigin = $GLOBALS['database']->prepare('INSERT INTO origin(url) values (:referer)');				
				$neworigin->execute($originData);
				$originid = $GLOBALS['database']->lastInsertId();
			}

			$data = array('stuff' => params('short'));
			$urlquery = $GLOBALS['database']->prepare('SELECT destination, id FROM urls WHERE short = :stuff');
			$urlquery->execute($data);
			$row = $urlquery->fetchAll();
			if(isset($row[0])){
				$urlid = $row[0]['id'];
				$destination = $row[0]['destination'];
			}
			$statsData = array('urlId' => $urlid, 'originId' => $originid);
			$statsquery = $GLOBALS['database']->prepare("INSERT INTO stats(url_id,origin_id) values(:urlId,:originId)");
			$statsquery->execute($statsData);
			return redirect_to($destination);
		}
	function getReferrer($info){
		$referer = "None";
		if(isset($info["SERVER"])){
			$server = $info["SERVER"];
			if(isset($server["HTTP_REFERER"])){
				$referer = $server["HTTP_REFERER"];
				$pattern = '/^((http[s]?|ftp):\/)?\/?([^:\/\s]+)/';
				preg_match($pattern, $referer, $matches);
				$referer = $matches[3];
			}			
		}
		return $referer;
	}

	run();

	function randomString($length = 10) {
  	  	$characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    	$randomString = '';
       	for ($i = 0; $i < $length; $i++) {
        	$randomString .= $characters[rand(0, strlen($characters) - 1)];
    	}
	   		return $randomString;
	}

	function getUsersUrls($id){
		$data = array('userId' => $id);
		$query = $GLOBALS['database']->prepare('SELECT u.* FROM urls u WHERE u.user_id = :userId');
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