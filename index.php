<?php 
	require_once 'lib/limonade.php';
	function configure()
	{
		$env = contains('localhost', $_SERVER['HTTP_HOST']) ? ENV_DEVELOPMENT : ENV_PRODUCTION;
    	option('env', $env);
		option('debug', true);
		if(option('env') === ENV_DEVELOPMENT){
			option('base_uri', 'getstatus');
		}	
	}

	dispatch('/', 'hello');
	  function hello(){
	      return render('index.html.php');
	  }

	dispatch("/test", 'testing');
	  function testing(){
	  	  return 'test';
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