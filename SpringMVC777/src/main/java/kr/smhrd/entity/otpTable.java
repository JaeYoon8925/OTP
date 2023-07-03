package kr.smhrd.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor 
@AllArgsConstructor
@Data
public class otpTable {

	private String id;
	private String otpCode;
	private Date issueDate;
	private Date expiredDate;
	
	
	
}
