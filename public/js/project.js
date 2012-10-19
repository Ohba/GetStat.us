$(document).ready(function(){
	//Selectors
	$('#signin-help .clickable').on('click', loadRegister);
	$('#register-help .clickable').on('click', loadSignIn);
	$('#signInModal').on('click', '.signin', signIn);
	$('#signInModal').on('click', '.register', register);


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
		var form = $('.signin-form');
		if(_validate(form)){
			var action = $(e.target).attr('data-action'),
				data = form.serialize();
				_clearAlerts();
				$.post(action, data)
					.success(signInSuccess)
					.error(modalError);
		}
	}

	function register(e){
		var form = $('.register-form');
		if(_validate(form)){
			var action = $(e.target).attr('data-action'),
				data = form.serialize();
			_clearAlerts();
			$.post(action, data)
				.success(signInSuccess)
				.error(modalError);
		}
	}

	//AJAX Callbacks
	function signInSuccess(data){
		console.log(data);
	}

	function modalError(data){
		var alertInfo = $('#signInModal .alert-error');
		alertInfo.text(data.responseText)
			.fadeIn();
	}

	//PRIVATE FUNCTIONS
	function _clearAlerts(){
		$('.alert').fadeOut();
	}

	function _validate(form){
		var errors = {};
		var inputs = form.find('input:visible');
		inputs.each(function(index, input){
			$(input).removeClass('red');
			_validation[$(input).attr('type')](errors, input);
		});
		if(!$.isEmptyObject(errors)){
			var list = $('<ul>');
			for(var i in errors){
				$(errors[i]).addClass('red');
				var item = $('<li>').text(i);
				list.append(item);
			}
			$('#signInModal .alert-error').html(list).fadeIn();
			return false;
		}else{
			return true;
		}
	}

	var _validation= {
		password: function(errors, input){
			if($(input).val().trim() === ""){
				errors['password may not be empty'] = input;
			}
		},

		email: function(errors, input){
			if($(input).val().trim() === ""){
				errors['email may not be empty'] = input;
			}
		},

		text: function(errors, input){
			if($(input).val().trim() === ""){
				errors['text may not be empty'] = input;
			}
		}
	};
});