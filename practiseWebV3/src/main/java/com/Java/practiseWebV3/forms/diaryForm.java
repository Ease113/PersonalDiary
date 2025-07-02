package com.Java.practiseWebV3.forms;

import java.util.ArrayList;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class diaryForm {
	
	private String user_profile_name;
	private ArrayList<String> categories;
	private ArrayList<String> series;
	private ArrayList<diaryLineForm> diaryLineForm;
	private String errorMsg;
	private Integer reg_id;
	private String author;
	
}
