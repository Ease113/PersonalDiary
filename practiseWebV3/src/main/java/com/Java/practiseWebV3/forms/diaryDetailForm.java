package com.Java.practiseWebV3.forms;

import java.util.ArrayList;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class diaryDetailForm {
	
	private Integer reg_id;
	private String user_profile_name;
	private Boolean seriesFlag;
	private String author;
	private String title;
	private String article;
	private String series;
	private String upd_sys_time;
	private ArrayList<diaryDetailLineForm> diaryDetailLineFormList;
	
}
