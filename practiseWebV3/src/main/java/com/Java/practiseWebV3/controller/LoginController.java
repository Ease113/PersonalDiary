package com.Java.practiseWebV3.controller;
import java.util.ArrayList;
import java.util.UUID;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Java.practiseWebV3.common.Constant;
import com.Java.practiseWebV3.common.utility;
import com.Java.practiseWebV3.dao.LoginDao;
import com.Java.practiseWebV3.dto.userInfoDto;
import com.Java.practiseWebV3.forms.loginForm;
import com.Java.practiseWebV3.services.LoginServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;


@Controller
public class LoginController {
	
	String PG001 = Constant.PG001;
	String PG002 = Constant.PG002;
	String PG003 = Constant.PG003;
	String SYSERROR = Constant.SYSERROR;
	String className = this.getClass().getSimpleName();
	
	utility utility;
	
	@Autowired
	private LoginServices loginServices;
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	/*
	 * Show login page
	 * 
	 * <parameter>
	 * 1. Model : Holder for model attributes
	 * 
	 * <return>
	 * 1. PG001 : login page
	 */
	@GetMapping("/")
	public String showLoginPage (
			@RequestParam(value = "session", required = false) Boolean session, Model model, HttpServletRequest request) {
		
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		logger.info(Constant.STARTLOG, className, methodName);
		try {
			if (!model.containsAttribute("userInfo")) {
				model.addAttribute("userInfo", new loginForm());
			}
			HttpSession sess = request.getSession();
			if (sess.getAttribute("userName") != null) {
				sess.removeAttribute("userName");
				sess.removeAttribute("uniqueToken");
			}
		} catch (Exception e) {
			logger.info(Constant.EXCEPTIONLOG, e);
			return SYSERROR;
			
		} finally {
			logger.info(Constant.ENDLOG, className, methodName);
			
		}

		return PG001;
	}
	
	/*
	 * Request the login info
	 * 
	 * <parameter>
	 * 1. loginForm : Data class for login input
	 * 
	 * 2. Model : Holder for model attributes
	 * 
	 * <return>
	 * 1. redirect:/diary : show diary page if login is succeeded
	 * 
	 * 2. PG001 : login Failure
	 */
	@PostMapping("/Login")
	public String doLogin (@ModelAttribute loginForm loginform, Model model, HttpServletRequest request, HttpServletRequest respose) {
		
		String returnPage = PG001;
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		logger.info(Constant.STARTLOG, className, methodName);
		try {
			int selectCount = loginServices.countUserInfo(loginform);
			if (selectCount > 0) {
				
				loginform.setUserName("");
				loginform.setEmail("");
				ArrayList<userInfoDto> userInfoDtoList = loginServices.selectUserInfoInReg(loginform);
				String userToken = UUID.randomUUID().toString();
				
				HttpSession session = request.getSession(true);
				session.setAttribute("uniqueToken", userToken);
				session.setAttribute("userName", userInfoDtoList.get(0).getUser_name());
				
				returnPage = "redirect:/Diary?session=true";
			} else {
				loginform.setComprehensive_msg(Constant.LOGINFAILLURE);
				model.addAttribute("userInfo", loginform);
			}
			
		} catch (Exception e) {
			logger.info(Constant.EXCEPTIONLOG, e);
			return SYSERROR;
			
		} finally {
			logger.info(Constant.ENDLOG, className, methodName);
			
		}

		return returnPage;
	}
	
	/*
	 * Show Account registration page
	 * 
	 * <parameter>
	 * 1. Model : Holder for model attributes
	 * 
	 * <return>
	 * 1. PG002 : Registration page
	 */
	@GetMapping("/Registration")
	public String makeAccountPage (Model model) {
		
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		logger.info(Constant.STARTLOG, this.getClass().getSimpleName(), methodName);
		try {
			model.addAttribute("accountInfo", new loginForm());

		} catch (Exception e) {
			logger.info(Constant.EXCEPTIONLOG, e);
			return SYSERROR;
			
		} finally {
			logger.info(Constant.ENDLOG, className, methodName);
			
		}

		return PG002;
	}
	/*
	 * Make account
	 * Redirect from "/Registration/Validate"
	 * 
	 * <parameter>
	 * 1. loginForm : Data class for login input
	 *    passed from doValidate()
	 *    
	 * 2. Model : Holder for model attributes
	 * 
	 * <return>
	 * 1. redirect:/ : Login page after registration
	 * 
	 */
	@GetMapping("/Registration/doRegistration")
	public String makeAccount (@ModelAttribute("loginform") loginForm loginform, Model model, RedirectAttributes redirectAttributes) {
		
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		logger.info(Constant.STARTLOG, this.getClass().getSimpleName(), methodName);
		try { 
			loginServices.insertUserInfo(loginform);
			
			loginForm loginform2 = new loginForm();
			loginform2.setComprehensive_msg("Registration Succeeded!");
			
			redirectAttributes.addFlashAttribute("userInfo", loginform2);
			
		} catch (Exception e) {
			logger.info(Constant.EXCEPTIONLOG, e);
			return SYSERROR;
			
		}   finally {
			logger.info(Constant.ENDLOG, className, methodName);
			
		}

		return "redirect:/";
	}
	
	/*
	 * Input validation
	 * 
	 * <parameter>
	 * 1. loginForm : Data class for login input
	 * 
	 * 2. Model : Holder for model attributes
	 * 
	 * 3. RedirectAttributes : Holder for redirectattributes
	 * 
	 * <return>
	 * 1. PG002 : Registration page
	 *			  if validation is failed 
	 *
	 * 2. redirect:/Registration/doRegistration : Registration processing if validation is fine
	 * 
	 */
	@PostMapping("/Registration/Validate")
	public String doValidate (@ModelAttribute loginForm loginform, Model model, RedirectAttributes redirectAttributes) {
		// account register form
		String returnPage = PG002;
		// Starting log
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		logger.info(Constant.STARTLOG, this.getClass().getSimpleName(), methodName);
		// validate result
		Boolean checkResult = true; 
		ArrayList<userInfoDto> userInfoDtoList = new ArrayList<>();
		
		try { 
			// userId that is inputted from registration form
			String userId_Inputted = loginform.getUserId();
			// passWord that is inputted from registration form
			String passWord_Inputted = loginform.getPassWord();
			// userName that is inputted from registration form;
			String userName_Inputted = loginform.getUserName();
			// email that is inputted from registration form
			String email_Inputted = loginform.getEmail();
			
			// user form is confirmed
			userInfoDtoList = loginServices.selectUserInfoInReg(loginform);
			for (userInfoDto dto : userInfoDtoList) {
				
				//userInfoDto userInfoDTO = 
				// userId that is selected from user_info
				String userId_Selected = dto.getUser_Id();
				// userName that is selected from user_info
				String userName_Selected = dto.getUser_name();
				// email that is selected from user_info
				String email_Selected = dto.getUser_email();
				
				if (userId_Inputted.equals(userId_Selected)) {
					checkResult = false;
					loginform.setUserId_errormsg(utility.replaceOne(Constant.EXISTERROR, "User ID"));
				}
				if (email_Inputted.equals(email_Selected)) {
					checkResult = false;
					loginform.setEmail_errormsg(utility.replaceOne(Constant.EXISTERROR, "Email"));
				}
				if (userName_Inputted.equals(userName_Selected)) {
					checkResult = false;
					loginform.setUserName_errormsg(utility.replaceOne(Constant.EXISTERROR, "User Name"));
				}
			}
				
			if (!checkResult) {
				model.addAttribute("accountInfo", loginform);
				
			} else {
				returnPage = "redirect:/Registration/doRegistration";
				redirectAttributes.addFlashAttribute("loginform", loginform);
			}
			
		} catch (Exception e) {
			logger.info(Constant.EXCEPTIONLOG, e);
			return SYSERROR;
			
		}   finally {
			logger.info(Constant.ENDLOG, className, methodName);
			
		}
		
		return returnPage;
	}
	
	@GetMapping("/logOut")
	public String logOut (HttpServletRequest request, HttpServletRequest respose) {
		
		HttpSession session = request.getSession();
		session.removeAttribute("uniqueToken");
		session.removeAttribute("userName");
		
		return "redirect:/?session=false";
	}
	/*
	 * public String RedirectHub (String function, RedirectAttributes
	 * redirectAttributes) { String redirectURL;
	 * 
	 * switch(function) { case "1": break;
	 * 
	 * }
	 * 
	 * 
	 * 
	 * return "redirect:/"; }
	 */
	
}
