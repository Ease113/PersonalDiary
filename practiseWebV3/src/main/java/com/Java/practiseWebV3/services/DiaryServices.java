package com.Java.practiseWebV3.services;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Java.practiseWebV3.common.utility;
import com.Java.practiseWebV3.controller.LoginController;
import com.Java.practiseWebV3.dao.DiaryDao;
import com.Java.practiseWebV3.dto.articleInfoDto;
import com.Java.practiseWebV3.dto.categoryDto;
import com.Java.practiseWebV3.dto.seriesDto;
import com.Java.practiseWebV3.forms.diaryDetailForm;
import com.Java.practiseWebV3.forms.diaryDetailLineForm;
import com.Java.practiseWebV3.forms.diaryEditForm;
import com.Java.practiseWebV3.forms.diaryForm;
import com.Java.practiseWebV3.forms.diaryLineForm;
import com.Java.practiseWebV3.forms.diaryWriteForm;


@Service
public class DiaryServices {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private DiaryDao diaryDao;
	
	
	
	
	public diaryForm getDiaryEntries (String userName) throws Exception {
		
		// Data form for diary page
		diaryForm diaryform = new diaryForm();

		// ArrayList for entries table iterator
		ArrayList<diaryLineForm> diaryLineFormList = new ArrayList<diaryLineForm>();
		// ArrayList for categories
		ArrayList<String> categories = new ArrayList<String>();
		// ArrayList for series
		ArrayList<String> series = new ArrayList<String>();
		// ArrayList for diaryDto
		ArrayList<articleInfoDto> articleList = new ArrayList<articleInfoDto>();
		// ArrayList for categoryDto
		ArrayList<categoryDto> categoryDto = new ArrayList<categoryDto>();
		// ArrayList for seriesDto
		ArrayList<seriesDto> seriesDto = new ArrayList<seriesDto>();
		
		articleList = diaryDao.selectArticles(userName);
		categoryDto = diaryDao.selectCategories(userName);
		seriesDto = diaryDao.selectSeries(userName);
		
		// Set the diary Entry Section
		for (articleInfoDto articleInfodto : articleList) {
			
			// Data form for list of diary(table type)
			diaryLineForm diaryLineform = new diaryLineForm();
			
			diaryLineform.setReg_id(articleInfodto.getREG_Id());
			diaryLineform.setAuthor(articleInfodto.getAuthor());
			diaryLineform.setTitle(articleInfodto.getTitle());
			
			diaryLineform.setTag(articleInfodto.getTag());
			diaryLineform.setCategory(articleInfodto.getCategory());
			diaryLineform.setSeries(articleInfodto.getSeries());
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			Date upd_sys_date = sdf.parse(articleInfodto.getUpd_sys_time().toString());
			diaryLineform.setUpd_sys_date(sdf.format(upd_sys_date));
			
			Date reg_sys_date = sdf.parse(articleInfodto.getReg_sys_time().toString());
			diaryLineform.setReg_sys_date(sdf.format(reg_sys_date));
			
			diaryLineFormList.add(diaryLineform);
		}
		// Set the Category Section
		for (categoryDto dto : categoryDto) {
			categories.add(dto.getCategory());
		}
		// Add Show all option
		categories.add(0, "Show All");
		
		// Set the Series Section
		for (seriesDto dto : seriesDto) {
			series.add(dto.getSeries());
		}
		// Add Show all option
		series.add(0, "Show All");
		
		diaryform.setDiaryLineForm(diaryLineFormList);
		diaryform.setCategories(categories);
		diaryform.setSeries(series);
		
		return diaryform;
	}
	
	public int insertDiary (diaryWriteForm diaryWriteform) throws Exception{
		
		diaryWriteForm dwform = nullCheck(diaryWriteform);
		utility util = new utility();
		ArrayList<String> param = new ArrayList<>();
		
		param.add(dwform.getTitle());
	
		param.add(util.encoder(dwform.getContent().getBytes()));
		
		param.add(dwform.getUser_profile_name());
		param.add(dwform.getTags());
		param.add(dwform.getCategory());
		param.add(dwform.getSeries());
			
		int result = diaryDao.insertDairy(param);
		
		return result;
	}
	
	public diaryDetailForm getDiaryDetail (diaryDetailForm diaryDetailform) throws Exception{
		
		diaryDetailForm detailForm = new diaryDetailForm();
		utility util = new utility();
		
		// ArrayList for diaryDto
		ArrayList<articleInfoDto> articleList1 = new ArrayList<articleInfoDto>();
		ArrayList<articleInfoDto> articleList2 = new ArrayList<articleInfoDto>();
		ArrayList<diaryDetailLineForm> diaryDetailLineFormList = new ArrayList<diaryDetailLineForm>();
		
		// ArrayList for param
		ArrayList<String> param1 = new ArrayList<>();
		ArrayList<String> param2 = new ArrayList<>();
		param1.add(diaryDetailform.getSeries());
		param1.add(diaryDetailform.getUser_profile_name());
		
		param2.add(diaryDetailform.getUser_profile_name());
		param2.add(diaryDetailform.getReg_id().toString());
		
		articleList1 = diaryDao.selectDiaryTitleBySeries(param1);
		articleList2 = diaryDao.selectDiaryDetail(param2);
		
		for (articleInfoDto dto : articleList1) {
			
			diaryDetailLineForm lineForm = new diaryDetailLineForm();
			lineForm.setReg_id(dto.getREG_Id());
			lineForm.setTitle(dto.getTitle());
			
			diaryDetailLineFormList.add(lineForm);
			
		}
		
		detailForm.setTitle(articleList2.get(0).getTitle());
		detailForm.setAuthor(articleList2.get(0).getAuthor());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Date upd_sys_date = sdf.parse(articleList2.get(0).getUpd_sys_time().toString());
		detailForm.setUpd_sys_time(sdf.format(upd_sys_date));
		
		detailForm.setArticle(util.decoder(articleList2.get(0).getArticle()));
		detailForm.setSeries(diaryDetailform.getSeries());
		detailForm.setDiaryDetailLineFormList(diaryDetailLineFormList);
		return detailForm;
	}
	
	public int deleteEntry (diaryForm diaryform) throws Exception{
		
		ArrayList<String> param = new ArrayList<>();
		param.add(diaryform.getReg_id().toString());
		param.add(diaryform.getAuthor());
		
		int result = diaryDao.deleteEntry(param);
		
		return result;
	}
	
	public diaryWriteForm nullCheck (diaryWriteForm diaryWriteform) {
				
		if (diaryWriteform.getTags().isBlank()) {
			diaryWriteform.setTags("");
		}
		if (diaryWriteform.getCategory().isBlank()) {
			diaryWriteform.setCategory("");
		}
		if (diaryWriteform.getSeries().isBlank()) {
			diaryWriteform.setSeries("");
		}
		
		//String encode = Base64.getEncoder().encodeToString((diaryWriteform.getSummary().getBytes()));
		
		return diaryWriteform;
	}
	
	public diaryEditForm getEditDetail (diaryEditForm diaryEditform) throws Exception {
		
		ArrayList<String> param = new ArrayList<>();
		utility util = new utility();
		param.add(diaryEditform.getReg_id().toString());
		
		param.add(diaryEditform.getAuthor());
		
		articleInfoDto articleDto = diaryDao.selectEditDetail(param);
		
		diaryEditform.setTitle(articleDto.getTitle());
		diaryEditform.setTags(articleDto.getTag());
		diaryEditform.setArticle(util.decoder(articleDto.getArticle()));
		diaryEditform.setCategory(articleDto.getCategory());
		diaryEditform.setSeries(articleDto.getSeries());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Date upd_sys_date = sdf.parse(articleDto.getUpd_sys_time().toString());
		diaryEditform.setUpd_sys_date(sdf.format(upd_sys_date));
		
		Date reg_sys_date = sdf.parse(articleDto.getReg_sys_time().toString());
		diaryEditform.setReg_sys_date(sdf.format(reg_sys_date));
		
		diaryEditform.setUser_profile_name(articleDto.getAuthor());
		
		return diaryEditform;
	}

}
