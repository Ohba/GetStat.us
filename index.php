<?php 
	require_once 'lib/limonade.php';
	require_once 'db/connection.php';
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
		$STH = $GLOBALS['database']->query('SELECT * FROM urls');
		$row = $STH->fetch();
		return print_r($row);
	  }

	dispatch('/g/:short', 'stuff');
		function stuff(){
			$data = array('stuff' => params('short'));
			$STH = $GLOBALS['database']->prepare('SELECT * FROM urls WHERE request = :stuff');
			$STH->execute($data);
			$row = $STH->fetch();
			return print_r($row);
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