package com.Java.practiseWebV3.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Java.practiseWebV3.common.Constant;
import com.Java.practiseWebV3.common.sqlList;
import com.Java.practiseWebV3.common.utility;
import com.Java.practiseWebV3.controller.LoginController;
import com.Java.practiseWebV3.dto.categoryDto;
import com.Java.practiseWebV3.dto.seriesDto;
import com.Java.practiseWebV3.forms.categoryJsonForm;
import com.Java.practiseWebV3.forms.diaryForm;
import com.Java.practiseWebV3.forms.diaryWriteForm;
import com.Java.practiseWebV3.forms.seriesJsonForm;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

@RestController
@Repository
public class getCatSerApi {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	@Getter
	@Setter
	HttpServletRequest request;
	
	@Autowired
	@Getter
	@Setter
	HttpServletRequest response;
	
	private JdbcTemplate jdbcTemplate;
	
	utility utility;
	
	String className = this.getClass().getSimpleName();
	
	@PostConstruct
	public void init() {
		
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.utility = new utility();
		
	}
	
	/*
	 * Get categories by using ajax 
	 * 
	 * <parameter>
	 * 
	 * <return>
	 * 1. JsonMapList : Category Map List {{"id" : reg_id}, {"name" : category}}
	 */
	@GetMapping("/Diary/Write/getCategories")
	public ArrayList<Map<String, String>> getCategoriesJson (@RequestParam(value="userName", required=false) String userName) {
		
		ArrayList<categoryDto> categoryDtoList = new ArrayList<>();
		ArrayList<Map<String, String>> JsonMapList = new ArrayList<>();
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		//ArrayList<String> param = new ArrayList<>();
		
		logger.info(Constant.STARTLOG, className, methodName);
		try {
			
			//HttpSession userSession = request.getSession();
			//String userName = userSession.getAttribute("userName").toString();
			//String userName = cateogryJsonform.getUserName();
			RowMapper<categoryDto> categoryDtoMapper = new BeanPropertyRowMapper<categoryDto>(categoryDto.class);
			
			String SQL = utility.replaceOne(sqlList.SQLNB004, userName);
			categoryDtoList = (ArrayList<categoryDto>) jdbcTemplate.query(SQL, categoryDtoMapper);
			
			for (categoryDto categorydto : categoryDtoList) {
				Map<String, String> JsonMap = new HashMap<String, String>();
				String reg_id = Integer.toString(categorydto.getReg_id());
				String category = categorydto.getCategory();
				int counts = categorydto.getCount();
				
				JsonMap.put("id", reg_id);
				JsonMap.put("name", category);
				JsonMap.put("counts", Integer.toString(counts));
				JsonMapList.add(JsonMap);
			}
			return JsonMapList;
			
		} catch (Exception e) {
			logger.info(Constant.EXCEPTIONLOG, e);
			return JsonMapList;
			
		} finally {
			logger.info(Constant.ENDLOG, className, methodName);
			
		} 
	}
	
	/*
	 * Get series by using ajax 
	 * 
	 * <parameter>
	 * 
	 * <return>
	 * 1. JsonMapList : Category Map List {{"id" : reg_id}, {"name" : series}}
	 */
	@GetMapping("/Diary/Write/getSeries")
	public ArrayList<Map<String, String>> getSeriesJson (@RequestParam(value="userName", required=false) String userName) {
		
		ArrayList<seriesDto> seriesDtoList = new ArrayList<>();
		ArrayList<Map<String, String>> JsonMapList = new ArrayList<>();
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		
		logger.info(Constant.STARTLOG, className, methodName);
		try {
			String SQL = utility.replaceOne(sqlList.SQLNB005, userName);
			
			RowMapper<seriesDto> seriesDtoMapper = new BeanPropertyRowMapper<seriesDto>(seriesDto.class);
			
			seriesDtoList = (ArrayList<seriesDto>) jdbcTemplate.query(SQL, seriesDtoMapper);
			
			for (seriesDto seriesdto : seriesDtoList) {
				Map<String, String> JsonMap = new HashMap<String, String>();
				String reg_id = Integer.toString(seriesdto.getReg_id());
				String series = seriesdto.getSeries();
				int counts = seriesdto.getCount();
				
				JsonMap.put("id", reg_id);
				JsonMap.put("name", series);
				JsonMap.put("counts", Integer.toString(counts));
				JsonMapList.add(JsonMap);
			}
			
			return JsonMapList;
		} catch (Exception e) {
			logger.info(Constant.EXCEPTIONLOG, e);
			return JsonMapList;
			
		} finally {
			logger.info(Constant.ENDLOG, className, methodName);
			
		}
	}	
	
	/*
	 * Update categories by using ajax 
	 * 
	 * <parameter>
	 * 1. categoryJsonForm : object for category json
	 * 
	 * <return>
	 * 1. ResonseEntity : ResponseEntity for confirm whether ajax was ok or not
	 * 					  ResponseEntity.ok(true or false)
	 */
	@Transactional
	@PostMapping("/Diary/Write/setCategories")
	public ResponseEntity<Boolean> setCategoriesJson (@RequestBody categoryJsonForm categoryJson) {
		
		boolean response = true;
		ArrayList<String> param = new ArrayList<>();
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		try {
			param.add(categoryJson.getCategory());
			param.add(categoryJson.getUserName());
			param.add("0");
			String SQL = utility.replace(sqlList.SQLNB006, param);
			
			jdbcTemplate.update(SQL);
			
			jdbcTemplate.execute(sqlList.RESETAUTOINCREMENTCATEGORY);
			
		} catch (Exception e) {
			response = false;
			logger.info(Constant.EXCEPTIONLOG, e);
		} finally {
			logger.info(Constant.ENDLOG, className, methodName);
		}
		return ResponseEntity.ok(response);
	}
	
	/*
	 * Update series by using ajax 
	 * 
	 * <parameter>
	 * 1. seriesJsonForm : object for series json
	 * 
	 * <return>
	 * 1. ResonseEntity : ResponseEntity for confirm whether ajax was ok or not
	 * 					  ResponseEntity.ok(true or false)
	 */
	@Transactional
	@PostMapping("/Diary/Write/setSeries")
	public ResponseEntity<Boolean> setSeriesJson (@RequestBody seriesJsonForm seriesJson) {
		
		boolean response = true;
		ArrayList<String> param = new ArrayList<>();
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		try {
			param.add(seriesJson.getSeries());
			param.add(seriesJson.getUserName());
			param.add("0");
			String SQL = utility.replace(sqlList.SQLNB007, param);
			
			jdbcTemplate.update(SQL);
			
			jdbcTemplate.execute(sqlList.RESETAUTOINCREMENTSERIES);
			
		} catch (Exception e) {
			response = false;
			logger.info(Constant.EXCEPTIONLOG, e);
		} finally {
			logger.info(Constant.ENDLOG, className, methodName);
		}
		return ResponseEntity.ok(response);
	}
	
	/*
	 * Delete categories by using ajax 
	 * 
	 * <parameter>
	 * 1. categoryJsonForm : object for category json
	 * 
	 * <return>
	 * 1. ResonseEntity : ResponseEntity for confirm whether ajax was ok or not
	 * 					  ResponseEntity.ok(true or false)
	 */
	@Transactional
	@PostMapping("/Diary/Write/delCategories")
	public ResponseEntity<Boolean> delCategoriesJson (@RequestBody categoryJsonForm categoryJson) {
		
		boolean response = true;
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		try {

			String SQL = utility.replaceOne(sqlList.SQLNB008, Integer.toString(categoryJson.getReg_id()));
			
			jdbcTemplate.update(SQL);
			
			jdbcTemplate.execute(sqlList.RESETAUTOINCREMENTCATEGORY);
			
		} catch (Exception e) {
			response = false;
			logger.info(Constant.EXCEPTIONLOG, e);
		} finally {
			logger.info(Constant.ENDLOG, className, methodName);
		}
		return ResponseEntity.ok(response);
	}

	/*
	 * Delete categories by using ajax 
	 * 
	 * <parameter>
	 * 1. categoryJsonForm : object for category json
	 * 
	 * <return>
	 * 1. ResonseEntity : ResponseEntity for confirm whether ajax was ok or not
	 * 					  ResponseEntity.ok(true or false)
	 */
	@Transactional
	@PostMapping("/Diary/Write/delSeries")
	public ResponseEntity<Boolean> delSeriesJson (@RequestBody seriesJsonForm seriesJson) {
		
		boolean response = true;
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		try {

			String SQL = utility.replaceOne(sqlList.SQLNB009, Integer.toString(seriesJson.getReg_id()));
			
			jdbcTemplate.update(SQL);
			
			jdbcTemplate.execute(sqlList.RESETAUTOINCREMENTCATEGORY);
			
		} catch (Exception e) {
			response = false;
			logger.info(Constant.EXCEPTIONLOG, e);
		} finally {
			logger.info(Constant.ENDLOG, className, methodName);
		}
		return ResponseEntity.ok(response);
	}
	
	/*
	 * @Transactional
	 * 
	 * @PostMapping("/Diary/Delete") public ResponseEntity<Boolean> deleteEntry
	 * (@RequestBody seriesJsonForm seriesJson) { boolean response = true; String
	 * methodName = new Object() {}.getClass().getEnclosingMethod().getName();
	 * String returnPage = "redirect:/Diary?session=true"; try { ArrayList<String>
	 * param = new ArrayList<>(); param.add(seriesJson.getReg_id().toString());
	 * param.add(seriesJson.getUserName());
	 * 
	 * String replacedSQL = utility.replace(sqlList.SQLNB014, param);
	 * 
	 * jdbcTemplate.update(replacedSQL);
	 * 
	 * } catch (Exception e) { response = false; logger.info(Constant.EXCEPTIONLOG,
	 * e); } finally { logger.info(Constant.ENDLOG, className, methodName); } return
	 * ResponseEntity.ok(response); }
	 */

}
