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
import com.Java.practiseWebV3.common.sqlList;
import com.Java.practiseWebV3.common.utility;
import com.Java.practiseWebV3.controller.LoginController;
import com.Java.practiseWebV3.dto.articleInfoDto;
import com.Java.practiseWebV3.dto.categoryDto;
import com.Java.practiseWebV3.dto.seriesDto;
import com.Java.practiseWebV3.forms.diaryDetailForm;

import jakarta.annotation.PostConstruct;

@Repository
public class DiaryDao {
	
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
	
	public ArrayList<articleInfoDto> selectArticles (String userName) throws Exception {
		
		ArrayList<articleInfoDto> articleList = new ArrayList<articleInfoDto>();
		
		String SQL = utility.replaceOne(sqlList.SQLNB010, userName);
		
		RowMapper<articleInfoDto> articleInfoDtoMapper = new BeanPropertyRowMapper<articleInfoDto>(articleInfoDto.class);
		
		articleList = (ArrayList<articleInfoDto>) jdbcTemplate.query(SQL, articleInfoDtoMapper);
		
		return articleList;
	}
	
	public ArrayList<categoryDto> selectCategories (String userName) throws Exception {
		
		ArrayList<categoryDto> categoryList = new ArrayList<categoryDto>();
		
		String SQL = utility.replaceOne(sqlList.SQLNB004, userName);
		
		RowMapper<categoryDto> categoryInfoDtoMapper = new BeanPropertyRowMapper<categoryDto>(categoryDto.class);
		
		categoryList = (ArrayList<categoryDto>)jdbcTemplate.query(SQL, categoryInfoDtoMapper);
		
		return categoryList;
	}
	
	public ArrayList<seriesDto> selectSeries (String userName) throws Exception {
		
		ArrayList<seriesDto> seriesList = new ArrayList<seriesDto>();
		
		String SQL = utility.replaceOne(sqlList.SQLNB005, userName);
		
		RowMapper<seriesDto> seriesInfoDtoMapper = new BeanPropertyRowMapper<seriesDto>(seriesDto.class);
		
		seriesList = (ArrayList<seriesDto>)jdbcTemplate.query(SQL, seriesInfoDtoMapper);
		
		return seriesList;
	}
	@Transactional
	public int insertDairy (ArrayList<String> param) {
		
		int result = 0;
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		logger.info(Constant.STARTLOG, className, methodName);
		try {
			// Insert diary
			String replacedSQL = utility.replace
					(sqlList.SQLNB011, param);
			jdbcTemplate.update(replacedSQL);
			result =+ 1;
		} catch (Exception e){
			logger.info(Constant.EXCEPTIONLOG, e);
			throw e;
		}
		// Ending log
		logger.info(Constant.ENDLOG, className, methodName);
		return result;
	}
	
	public ArrayList<articleInfoDto> selectDiaryDetail (ArrayList<String> param) throws Exception {
		
		ArrayList<articleInfoDto> articleList = new ArrayList<articleInfoDto>();
		
		String SQL = utility.replace(sqlList.SQLNB012, param);
		
		RowMapper<articleInfoDto> articleInfoDtoMapper = new BeanPropertyRowMapper<articleInfoDto>(articleInfoDto.class);
		
		articleList = (ArrayList<articleInfoDto>) jdbcTemplate.query(SQL, articleInfoDtoMapper);
		
		return articleList;
	}
	
	public ArrayList<articleInfoDto> selectDiaryTitleBySeries (ArrayList<String> param) throws Exception {
		
		ArrayList<articleInfoDto> articleList = new ArrayList<articleInfoDto>();
		
		String SQL = utility.replace(sqlList.SQLNB013, param);
		
		RowMapper<articleInfoDto> articleInfoDtoMapper = new BeanPropertyRowMapper<articleInfoDto>(articleInfoDto.class);
		
		articleList = (ArrayList<articleInfoDto>) jdbcTemplate.query(SQL, articleInfoDtoMapper);
		
		return articleList;
	}
	
	public int deleteEntry (ArrayList<String> param) {
		int result = 0;
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		logger.info(Constant.STARTLOG, className, methodName);
		try {
			
			String replacedSQL = utility.replace(sqlList.SQLNB014, param);
			
			jdbcTemplate.update(replacedSQL);
			result =+ 1;
		} catch (Exception e) {
			logger.info(Constant.EXCEPTIONLOG, e);
			throw e;
		}
		logger.info(Constant.ENDLOG, className, methodName);
		return result;
	}
}
