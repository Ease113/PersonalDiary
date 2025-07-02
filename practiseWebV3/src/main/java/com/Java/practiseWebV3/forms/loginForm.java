package com.Java.practiseWebV3.forms;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class loginForm {
	
	// User ID
	private String userId;
	// Password
	private String passWord;
	// Password for confirming password input whether it is matched or not
	private String passWord_confirm;
	// User Name
	private String userName;
	// Email
	private String email;
	// Email for confirming email input whether it is matched or not
	private String email_confirm;
	// comprehensive message for show the result of registration or login attempted
	private String comprehensive_msg;
	// Error Messages area for User ID
	private String userId_errormsg;
	// Error Messages area for User Name
	private String userName_errormsg;
	// Error Messages area for Email
	private String email_errormsg;
	

	
}
