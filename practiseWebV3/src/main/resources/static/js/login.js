
/*
* Function for Validation of User Input(Account Registration)
*/
function checkValidation () {

	var checkResult = true;
	var user_id = $("#userId").val().replaceAll(" ", "");
	var passWord = $("#passWord").val().replaceAll(" ", "");
	var passWord_confirm = $("#passWord_confirm").val().replaceAll(" ", "");
	var userName = $("#userName").val().replaceAll(" ", "");
	var email = $("#email").val().replaceAll(" ", "");
	var email_confirm = $("#email_confirm").val().replaceAll(" ", "");
	// Definition of non-Alphanumeric characters
	var nonAlphanumeric = /[^a-zA-Z0-9]/g;

	// Validate userId
	if (user_id.length === 0 || user_id.length > 15 || user_id.length < 8 || nonAlphanumeric.test(user_id)) {
		checkResult = false;
		$("#userId_errormsg").text(errorMsgBuilder("User ID"));
	} 
	// Validate passWord
	if (passWord.length === 0 || passWord.length < 8 || !nonAlphanumeric.test(passWord)) {
		checkResult = false;
		$("#passWord_errormsg").text(errorMsgBuilder("Password"));
	} else if (passWord !== passWord_confirm) {
		checkResult = false;
		$("#passWord_errormsg").text(errorMsgBuilder("Password_confirm"));
	};
	// Validate userName
	if (userName.length === 0 || userName.lenght < 4 || userName.length > 50 || nonAlphanumeric.test(userName)) {
		checkResult = false;
		$("#userName_errormsg").text(errorMsgBuilder("User Name"));
	}
	// Validate Email
	if (!document.getElementById("email").checkValidity()) {
		checkResult = false;
		$("#email_errormsg").text(errorMsgBuilder("Email"));
	} else if (email !== email_confirm) {
		checkResult = false;
		$("#email_errormsg").text(errorMsgBuilder("Email_confirm"));
	}

	if (checkResult) {
		$("#accountForm").submit();
	} else {
		return false;
	}
};

/*
* Function for Validation of User Input(Login)
*/
function checkLoginValidation () {
	var checkResult = true;
	var user_id = $("#userId").val().replaceAll(" ", "");
	var passWord = $("#passWord").val().replaceAll(" ", "");
	// Definition of non-Alphanumeric characters
	var nonAlphanumeric = /[^a-zA-Z0-9]/g;
	
	// Erase all Messages before validation
	$("#comprehensive_mgs").text("");
	$("#userId_errormsg").text("");
	$("#passWord_errormsg").text("");
	// Validate userId
	if (user_id.length === 0 || user_id.length > 15 || user_id.length < 8 || nonAlphanumeric.test(user_id)) {
		checkResult = false;
		$("#userId_errormsg").text(errorMsgBuilder("User ID"));
	} 
	// Validate passWord
	if (passWord.length === 0 || passWord.length < 8 || !nonAlphanumeric.test(passWord)) {
		checkResult = false;
		$("#passWord_errormsg").text(errorMsgBuilder("Password"));
	}
	
	if (checkResult) {
		$("#loginForm").submit();
	} else {
		return false;
	}
}

/*
* Function for Error Message Build
*/
function errorMsgBuilder (type) {
	var errormsg = "Please check your {0} again \n {0} should follow the rule below"
	switch (type) {
		case "User ID":
			errormsg += "\n" + "1. length of characters should be within 8 to 15" + "\n" + "2. Please don't use blank or any special symbols on User ID";
			errormsg = errormsg.replaceAll("{0}", type);
			break
			
		case "Password":
			errormsg += "\n 1. length of characters should be over 8 \n 2. At least one special symbol should be on password"
			errormsg = errormsg.replaceAll("{0}", type);
			break
			
		case "Password_confirm":
			errormsg = "your Password is not correct \n Please check both sides of Password input"
			break
			
		case "User Name":
			errormsg += "\n 1. length of characters should be within 4 to 50 \n 2. Please don't use blank or any special symbols on User Name";
			errormsg = errormsg.replaceAll("{0}", type);
			break
			
		case "Email":
			errormsg += "\n 1. The Email addresses should be like '0000@gmail.com'";
			errormsg = errormsg.replaceAll("{0}", type);
			break
			
		case "Email_confirm":
			errormsg = "your Email is not correct \n Please check both sides of Email input"
			break
	}
	return errormsg;
}

/*
* Function for Controling password input visibility
*/
function toggleVisibility () {
	var checkBox = $("#toggleBox");
	if (checkBox.is(':checked')) {
		$('#passWord').get(0).type = 'text';
	} else {
		$('#passWord').get(0).type = 'password';
	}
}

/*
* Function for Clearing current error messages
*/
function clearErrorMsg () {
	$("#userId_errormsg").text("");
	$("#passWord_errormsg").text("");
	$("#userName_errormsg").text("");
	$("#email_errormsg").text("");
}