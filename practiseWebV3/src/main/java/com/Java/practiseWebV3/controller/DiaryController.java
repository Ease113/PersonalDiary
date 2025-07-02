package com.Java.practiseWebV3.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Java.practiseWebV3.common.Constant;
import com.Java.practiseWebV3.forms.diaryDetailForm;
import com.Java.practiseWebV3.forms.diaryForm;
import com.Java.practiseWebV3.forms.diaryWriteForm;
import com.Java.practiseWebV3.services.DiaryServices;
import com.Java.practiseWebV3.services.LoginServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class DiaryController {

    private final LoginServices loginServices;
	
	String PG003 = Constant.PG003;
	String PG004 = Constant.PG004;
	String PG005 = Constant.PG005;
	String SYSERROR = Constant.SYSERROR;
	String className = this.getClass().getSimpleName();
	
	@Autowired
	DiaryServices diaryServices;
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    DiaryController(LoginServices loginServices) {
        this.loginServices = loginServices;
    }
	
	/*
	 * Show Diary page
	 * 
	 * <parameter>
	 * 1. @RequestParam(value = "session", required = false) Boolean session
	 * 
	 * 2. Model : Holder for model attributes
	 * 
	 * 3.HttpServletRequest : request and response for using session
	 * 
	 * <return>
	 * 1. PG003 : Diary page
	 * 
	 * 2. redirect:/?session=false : 
	 * 	  If this page is redirected even if the session has been already closed
	 * 	  then, redirect to login page with mark of session is closed
	 * 
	 */
	@GetMapping("/Diary")
	public String showDiary ( 
			@RequestParam(value = "session", required = false) Boolean session,
			Model model, HttpServletRequest request, HttpServletRequest response) {
		
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		logger.info(Constant.STARTLOG, className, methodName);
		String returnPage = PG003;
		
		try {
			// Get session, if it's not exist, generate session
			HttpSession userSession = request.getSession();
			
			// Prevent redirect from back-forward cache
			if (userSession.getAttribute("userName") == null) {
				session = false;
			}
			if (session == null || !session) {
				// If session has been already closed, redirect to login page
				returnPage = "redirect:/?session=false";
			} else {
				String userName = userSession.getAttribute("userName").toString();
				// Data form for diary page
				diaryForm diaryform = diaryServices.getDiaryEntries(userName);
				// Data form for list of diary(table type)
				
				diaryform.setUser_profile_name(userName);
				
				model.addAttribute("diaryForm", diaryform);
			}
		} catch (Exception e) {
			logger.info(Constant.EXCEPTIONLOG, e);
			return SYSERROR;
			
		} finally {
			logger.info(Constant.ENDLOG, className, methodName);
			
		}
	return returnPage;
	}
	
	@GetMapping("/Diary/Write")
	public String showWrite (@RequestParam(value = "userName") String userName, Model model, HttpServletRequest request, HttpServletRequest response) {
		
		 diaryWriteForm diaryWriteform = new diaryWriteForm();
		 diaryWriteform.setUser_profile_name(userName);
		 
		model.addAttribute("diaryWriteForm", diaryWriteform);
		
		return PG004;
	}
	
	@PostMapping("/Diary/Write/saveDiary")
	public String saveDiary (diaryWriteForm diaryWriteform, Model model) {
		
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		logger.info(Constant.STARTLOG, className, methodName);
		String returnPage = PG004;
		
		try {
			
			//diaryWriteForm wf = diaryServices.nullCheck(diaryWriteform);
			
			int result = diaryServices.insertDiary(diaryWriteform);
			if (result <= 0) {
				diaryWriteForm form = new diaryWriteForm();
				form = diaryWriteform;
				form.setTitle("Error has occurred during the saving entry");		
			} else {
				returnPage = "redirect:/Diary?session=true";
			}
		
		} catch (Exception e) {
			logger.info(Constant.EXCEPTIONLOG, e);
			return SYSERROR;
		}
		
		return returnPage;
	}
	
	@GetMapping("/Diary/Detail")
	public String showDetail (@RequestParam(value = "Id") int Id, 
							  @RequestParam(value = "userName") String userName, 
							  @RequestParam(value = "series") String series, Model model) {
		
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		logger.info(Constant.STARTLOG, className, methodName);
		diaryDetailForm diaryDetailform2 = new diaryDetailForm();
		try {
			diaryDetailForm diaryDetailform = new diaryDetailForm();
			
			diaryDetailform.setReg_id(Id);
			diaryDetailform.setUser_profile_name(userName);
			diaryDetailform.setSeries(series);
			
			diaryDetailform2 = diaryServices.getDiaryDetail(diaryDetailform);
		} catch (Exception e) {
			logger.info(Constant.EXCEPTIONLOG, e);
			return SYSERROR;
		}
		
		model.addAttribute("diaryDetailForm", diaryDetailform2);
		
		return PG005;
	}
	
	
	 @PostMapping("Diary/Delete") public String deleteEntry (diaryForm diaryform, Model model) { 
		 String returnPage = "redirect:/Diary?session=true"; 
		 try { 

			 int result = diaryServices.deleteEntry(diaryform); 
			 if (result <= 0) { 
				 diaryForm form = diaryServices.getDiaryEntries(diaryform.getAuthor());
				 form.setErrorMsg("Error has been occurred during deleting entry"); 
				 } else {
					 returnPage = "redirect:/Diary?session=true"; 
				 } 
			 } 
		 catch (Exception e) {
			 logger.info(Constant.EXCEPTIONLOG, e); return SYSERROR; 
		 }
		 return returnPage; 
	 }
	 
}
