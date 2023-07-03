package kr.smhrd.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.smhrd.entity.accountTable;
import kr.smhrd.entity.otpTable;

@Mapper
public interface accountTableMapper {
	
	// 아이디 존재 확인
	public accountTable accountCheck(accountTable account);

	// 아이디 비밀번호 유효 확인
	public accountTable login(accountTable account);
	
	// 오티피 생성 insert문
	public int otpCreate(otpTable otpCodeBind);

	@Update("UPDATE accountTable SET blockTime = 0 WHERE id=#{id}")
	public int loginCntReset(accountTable account);



}