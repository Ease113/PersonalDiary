package com.Java.practiseWebV3.forms;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class seriesJsonForm {

	private Integer reg_id;
	private String series;
	private String userName;
	private Integer diary_counts;
	private Integer checkBox;
	private Integer del_flg;
	
}
