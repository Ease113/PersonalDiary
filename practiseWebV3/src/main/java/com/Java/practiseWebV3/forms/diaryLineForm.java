package com.Java.practiseWebV3.forms;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class diaryLineForm {
	
	private Integer reg_id;
	
	private String title;
	
	private String author;
	
	private String reg_sys_date;
	
	private String upd_sys_date;
	
	private String tag;
	
	private String category;
	
	private String series;

}
