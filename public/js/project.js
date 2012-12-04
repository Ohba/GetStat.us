$(document).ready(function(){
	//Selectors
	$('#signin-help .clickable').on('click', loadRegister);
	$('#register-help .clickable').on('click', loadSignIn);
	$('#signInModal').on('click', '.signin', signIn);
	$('#signInModal').on('click', '.register', register);
	$('#signInModal').on('hide', modalClose);
	$('.form-search').submit(shorten);
	$('.form-search input').change(inputChanged);

	//Callbacks
	/*
	* Change form to accept user registration
	*/
	function loadRegister(e){
		var form = $('.signin-form');
		_clearErrors(form);
		$('.register-name').fadeIn();
		$('.signin').text('Register')
			.removeClass('signin')
			.addClass('register')
			.attr('data-action', '/register');
		form.addClass('register-form')
			.removeClass('signin-form');
		$('#signin-help').addClass('hide');
		$('#register-help').removeClass('hide');
		$('#signInModal h3').text('Register');
	}
	/*
	* Change form to accept user signin
	*/
	function loadSignIn(e){
		var form = $('.register-form');
		_clearErrors(form);
		$('.register-name').fadeOut();
		$('.register').text('Sign In')
			.removeClass('register')
			.addClass('signin')
			.attr('data-action', '/signin');
		form.addClass('signin-form')
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

	function modalClose(e){
		var modal = $(e.target),
			form = $(modal.find('form'));
		_clearErrors(form);
		_clearForm(form);
	}

	function shorten(e){
		var form = $(e.target),
			input = form.find('input'),
			url = input.val();
		if(url.trim() === ""){
			input.addClass('red');
			$('.main.alert').text('URL must not be empty').show();
		}else if(input.hasClass('submitted')){
			$('.main.alert').text('You already Shortened this url.').show();
		}else{
			$('#shortened .progress').removeClass('hide');
			input.removeClass('red');
			$('.main.alert').text('').hide();
			input.addClass('submitted');
			$.post(window.location.origin + '/created', form.serialize())
				.success(function(short){
					$('#shortened')
						.text(window.location.origin + '/g/' + short)
						.closest('.text-center')
						.removeClass('loader-fix');
				})
				.error(function(){
					input.removeClass('submitted');
				});
		}
		return false;
	}

	function inputChanged(e){
		$(e.target).removeClass('submitted');
	}

	//AJAX Callbacks
	function signInSuccess(data){
		window.location = window.location.origin + "/admin";
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
		_clearErrors(form);
		inputs.each(function(index, input){
			_validation[$(input).attr('type')](errors, input);
		});
		if(!$.isEmptyObject(errors)){
			var list = $('<ul>');
			for(var i in errors){
				$(errors[i]).closest('.control-group').addClass('error');
				var item = $('<li>').text(i);
				list.append(item);
			}
			$('#signInModal .alert-error').html(list).fadeIn();
			return false;
		}else{
			return true;
		}
	}

	function _clearErrors(form){
		form.find('.control-group').removeClass('error');
		form.find('.alert').fadeOut().html('');
	}

	function _clearForm(form){
		form.find('input').val('');
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

	if(typeof window.location.origin === 'undefined'){
		window.location.origin = window.location.protocol + '//' + window.location.host;
	}
});