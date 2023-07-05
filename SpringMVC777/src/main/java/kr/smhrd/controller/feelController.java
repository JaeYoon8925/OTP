package kr.smhrd.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.smhrd.entity.accountTable;
import kr.smhrd.entity.otpTable;
import kr.smhrd.mapper.accountTableMapper;

@Controller
public class feelController {

	@Autowired
	private accountTableMapper mapper;

	@RequestMapping(value = "/")
	public String index() {
		System.out.println("ㅇㅇ");
		return "loginPage";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET) // UrlMapping , 요청명 , Get 방식으로 들어왔을 때 실행
//	@GetMapping("/")
	public String index(accountTable account, Model model) {

		String goal = "otpPage";

		// 두 Date 값을 비교하기 위해 포맷 통일을 위한 정의
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.MINUTE, 3); // 분 연산

		Date calDate = new Date(cal1.getTimeInMillis());
		Date date = new Date();

		// 로그인 시도
		System.out.println("로그인 시도");
		accountTable check = mapper.accountCheck(account);
		// 로그인 실패시 info 는 null이 리턴됌.

		// 계정 존재 확인 > 계정 블록타임 확인
		// >> 셀렉트문으로 blocktime을 가져와야함.

		// 계정 내 블록 타임
		Date blockTime = check.getBlockTime();
		System.out.println("계정의 블록타임 : " + blockTime);

		// 현재 시간 = date

		System.out.println("자바에서 구한 현재 시간 : " + date);

		// 시간 비교 ( 0 이하 = 블록타임이 지난 후 / 0 초과 = 블록 타임이 지나기 전)
		int blockResult = blockTime.compareTo(date);
		// 블록타임 체크 + 로그인 카운트 체크도 동시에 해줘야 함.
		if (blockResult <= 0) { // 0 이하 = 블록타임이 지난 후
			System.out.println("블록타임 후"); // 문제 없이 로그인 로직 진행.
//			int loginCntResetsResult = mapper.loginCntReset(account);
		} else { // 0 초과 = 블록타임이 지나기 전
			System.out.println("블록타임 중");
			System.out.println("로그인 시도 불가능.");
			goal = "loginPage"; // 로그인 실패 시 로그인 페이지에 그대로 있는 것처럼 ㅇㅇ
		}

		// 로그인 카운트 확인
		int cntCheck = mapper.loginCntCheck(account);

		// 블록 타임에 안걸리고 로그인 카운트가 5회 이하인 경우 = 카운트++
		if (blockResult <= 0 && cntCheck <= 5) {
			cntCheck++;

			// 블록 타임에 안걸리고 로그인 카운트가 5회 초과인 경우 = 블록타임 갱신 = 현재시간 +10분
		} else if (blockResult <= 0 && 5 < cntCheck) {
			Calendar blockUp = Calendar.getInstance();
			blockUp.add(Calendar.MINUTE, 10); // 분 연산
			Date blockUpDate = new Date(cal1.getTimeInMillis());
			mapper.blockTimeUpdate(blockUpDate);

			System.out.println("로그인 횟수 제한에 걸렸습니다.");
			goal = "loginPage";
			// 그 외의 모든 경우. ( 로그인 카운트에 무관하게 블록타임에 걸리는 경우 )
		} else {
			
			System.out.println("지금은 블록타임 기간 내 입니다.");
			goal = "loginPage";
		}

		
		
		// 블록타임과 카운트를 모두 만족하면 goal이 처음 선언한 otpPage 그대로임. >> 로그인 시도
		if (goal == "otpPage") {

			accountTable info = mapper.login(account);
			// 로그인 성공
			if (info != null) {
				// 로그인 카운트 초기화 업데이트 문

				// 오티피 발급 문
				String id = info.getId();

				// 모델에 로그인 정보 저장.
				model.addAttribute("account", info);

				// 랜덤 오티피 생성 로직
				Random random = new Random();
				String otp = "";

				for (int i = 1; i < 9; i++) {
					otp = otp + String.valueOf(random.nextInt(10));
				}

//			String issueDate = formatter.format(date);
//			String expiredDate = formatter.format(calDate);

				System.out.println("로그인 id : " + id);
				System.out.println("오티피 발급 : " + otp);
				System.out.println(date);
				System.out.println(calDate);

				otpTable otpCodeBind = new otpTable();
				otpCodeBind.setId(id);
				otpCodeBind.setOtpCode(otp);
				otpCodeBind.setIssueDate(date);
				otpCodeBind.setExpiredDate(calDate);

				System.out.println(otpCodeBind);
				// 오티피 테이블에 행 추가 (오티피 발급)
				int otpCode = mapper.otpCreate(otpCodeBind);
				// , issueDate, expiredDate
				System.out.println("실행1");
				model.addAttribute("otp", otpCodeBind);
				System.out.println("실행2");
//			// 로그인 실패 > 블로타임 내 , 외 (신경써야할까?, 신경써야함.)
//			// 로그인 블록여부 확인 (아이디의 존재 여부는 체크 해줘야함.)
//			// 아이디 조차 존재 x 시.
//			info.getLoginCnt();
//			// 성공시
//				// 로그인 정보 받아오기 (뷰에는 아이디만 출력 + otp 입력폼)
//				model.addAttribute("vo", info);
				//
//				// otp 번호 무작위로 생성 ( id, otpCode, issueDate, expiredDate )
//				// otpTable 에 insert문 작동
//				// 오티피입력 페이지로
				//
//			// 실패시
//				// blockTime 과 systime 을 비교
//				// loginCnt +1
//				// 다시 원페이지로 / 알람만 띄우기.
//				
				//

			}

			// 로그인 실패 info = null
			else {
				// 로그인 카운트 +1 업데이트 문

			}

		}

		return goal; // 리턴값 변경 필요
	}

}
