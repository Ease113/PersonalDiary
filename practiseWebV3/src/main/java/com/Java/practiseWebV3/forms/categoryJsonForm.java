package com.Java.practiseWebV3.forms;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class categoryJsonForm {
	
	private Integer reg_id;
	private String category;
	private String userName;
	private Integer diary_counts;
	private Integer checkBox;
	private Integer del_flg;

}
