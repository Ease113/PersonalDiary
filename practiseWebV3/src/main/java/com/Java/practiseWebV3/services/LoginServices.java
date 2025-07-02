package com.Java.practiseWebV3.services;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.Java.practiseWebV3.common.sqlList;
import com.Java.practiseWebV3.common.utility;
import com.Java.practiseWebV3.dao.LoginDao;
import com.Java.practiseWebV3.dto.userInfoDto;
import com.Java.practiseWebV3.forms.loginForm;

import jakarta.annotation.PostConstruct;

@Service
public class LoginServices {
	
	@Autowired
	private LoginDao loginDao;
	
	public int countUserInfo (loginForm loginform) throws Exception {
		
		int count = 0;
		// arrayList for param
		ArrayList<String> param = new ArrayList<String>();
		
		param.add(loginform.getUserId());
		param.add(loginform.getPassWord());
		
		String baseSQL = sqlList.SQLNB001;
		//String replacedSQL = utility.replace(baseSQL, param);
		
		count = loginDao.countUserInfo(param, baseSQL);

		return count;
	}
	
	public ArrayList<userInfoDto> selectUserInfoInReg (loginForm loginform) throws Exception {
		// arrayList for multiple column
		ArrayList<userInfoDto> userInfoDtoList = new ArrayList<>();
		// arrayList for param
		ArrayList<String> param = new ArrayList<String>();
		
		param.add(loginform.getUserId());
		param.add(loginform.getUserName());
		param.add(loginform.getEmail());
		
		String baseSQL = sqlList.SQLNB003;
		
		userInfoDtoList = loginDao.selectUserInfoInReg(param, baseSQL);
		
		return userInfoDtoList;
	}
	
	public int insertUserInfo (loginForm loginform) throws Exception {
		// arrayList for multiple column
		ArrayList<userInfoDto> userInfoDtoList = new ArrayList<>();
		// arrayList for param
		ArrayList<String> param = new ArrayList<String>();
		
		param.add(loginform.getUserId());
		param.add(loginform.getPassWord());
		param.add(loginform.getUserName());
		param.add(loginform.getEmail());
		
		String baseSQL = sqlList.SQLNB002;
		
		int result = loginDao.insertUserInfo(param, baseSQL);
		
		return result;
	}

}
