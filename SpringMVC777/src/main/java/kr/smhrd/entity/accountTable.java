package kr.smhrd.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor 
@AllArgsConstructor
@Data
public class accountTable {

	private String id;
	private String pw;
	private String name;
	private int loginCnt;
	private Date blockTime; // mysql에서 db 생성시에 DATETIME 타입으로 선언 필요.
	
}
