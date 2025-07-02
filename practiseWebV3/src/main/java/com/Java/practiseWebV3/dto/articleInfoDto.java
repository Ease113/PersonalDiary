package com.Java.practiseWebV3.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class articleInfoDto {

	private Integer REG_Id;
	private String title;
	private String article;
	private String author;
	private String tag;
	private String category;
	private String series;
	private Integer del_flg;
	private Date reg_sys_time;
	private Date upd_sys_time;
	
	
	
}
