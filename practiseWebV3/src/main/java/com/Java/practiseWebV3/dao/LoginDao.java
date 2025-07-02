package com.Java.practiseWebV3.dao;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Java.practiseWebV3.common.Constant;
import com.Java.practiseWebV3.common.utility;
import com.Java.practiseWebV3.controller.LoginController;
import com.Java.practiseWebV3.dto.userInfoDto;

import jakarta.annotation.PostConstruct;




@Repository
public class LoginDao {
	
	String className = this.getClass().getSimpleName();
	
	private JdbcTemplate jdbcTemplate;
	
	private utility utility;
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	public void init() {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.utility = new utility();
		
	}
	
	public int countUserInfo (ArrayList<String> param, String baseSQL) throws Exception {
		
		String replacedSQL = utility.replace(baseSQL, param);
		int count = jdbcTemplate.queryForObject(replacedSQL, Integer.class);
		
		return count;
	}
	
	public ArrayList<userInfoDto> selectUserInfoInReg (ArrayList<String> param, String baseSQL) throws Exception {
		
		ArrayList<userInfoDto> userInfoDtoList = new ArrayList<>();
		
		String replacedSQL = utility.replace(baseSQL, param);
		// Mapper for multiple column
		RowMapper<userInfoDto> userInfoDtoMapper = new BeanPropertyRowMapper<userInfoDto>(userInfoDto.class);
		userInfoDtoList = (ArrayList<userInfoDto>) jdbcTemplate.query(replacedSQL, userInfoDtoMapper);
		
		return userInfoDtoList;
	}
	
	@Transactional
	public int insertUserInfo (ArrayList<String> param, String baseSQL) {
		int result = 0;
		// Starting log
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		logger.info(Constant.STARTLOG, className, methodName);
		try {
			// Insert userInfo
			String replacedSQL = utility.replace(baseSQL, param);
			jdbcTemplate.update(replacedSQL);
			result ++;
		} catch (Exception e){
			logger.info(Constant.EXCEPTIONLOG, e);
			throw e;
		}
		// Ending log
		logger.info(Constant.ENDLOG, className, methodName);
		return result;

	}
}
