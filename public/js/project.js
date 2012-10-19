$(document).ready(function(){
	//Selectors
	$('#signin-help .clickable').on('click', loadRegister);
	$('#register-help .clickable').on('click', loadSignIn);
	$('.signin').on('click', signIn);
	$('.register').on('click', register);


	//Callbacks
	/*
	* Change form to accept user registration
	*/
	function loadRegister(e){
		$('.register-name').fadeIn();
		$('.signin').text('Register')
			.removeClass('signin')
			.addClass('register')
			.attr('data-action', '/register');
		$('.signin-form').addClass('register-form')
			.removeClass('signin-form');
		$('#signin-help').addClass('hide');
		$('#register-help').removeClass('hide');
		$('#signInModal h3').text('Register');
	}
	/*
	* Change form to accept user signin
	*/
	function loadSignIn(e){
		$('.register-name').fadeOut();
		$('.register').text('Sign In')
			.removeClass('register')
			.addClass('signin')
			.attr('data-action', '/signin');
		$('.register-form').addClass('signin-form')
			.removeClass('register-form');
		$('#signin-help').removeClass('hide');
		$('#register-help').addClass('hide');
		$('#signInModal h3').text('Sign In');
	}

	function signIn(e){
		var action = $(e.target).attr('data-action'),
			data = $('.signin-form').serialize();

		$.post(action, data).success(signInSuccess);
	}

	function register(e){
		var action = $(e.target).attr('data-action'),
			data = $('.register-form').serialize();

		$.post(action, data).success(registerSuccess);
	}

	//AJAX Callbacks
	function signInSuccess(data){
		console.log(data);
	}

	function registerSuccess(data){
		console.log(data);
	}
});