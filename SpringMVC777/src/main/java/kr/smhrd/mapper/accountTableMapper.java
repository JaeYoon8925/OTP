package kr.smhrd.mapper;

import java.util.Date;

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
	
	// 오티피 생성문
	public int otpCreate(otpTable otpCodeBind);
	
	// 오티피 체크문
	public int otpCheck(otpTable otp);
	
	// 오티피 딜리트 문
	public int otpDelete(otpTable otp);

//	@Update("UPDATE accountTable SET blockTime = 0 WHERE id=#{id}")
	
	// 로그인 카운트 조회
	public int loginCntCheck(accountTable account);
	// 로그인 카운트 +
	public void loginCntUpdate(accountTable account);
	// 블록타임 업데이트
	public int blockTimeUpdate(accountTable account);
	
	
	
	
}
