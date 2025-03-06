package com.kh.mvc.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.kh.mvc.controller.UserController;
import com.kh.mvc.model.dto.UserDTO;

/**
 * MemberView 클래스는 JDBC실습을 위해 생성하였으며,
 * 사용자에게 입력 및 출력을 수행하는 메서드를 제공합니다
 * 
 * @author : 종로 C강의장
 * @version : 0.1
 * @data : 2025-03-04
 */


/* Run 
 * -> View 객체에서 scanner, controller 생성 (주소값 생김)
 * -> View의 scanner, controller 생성 (주소값 생김)
 * -> Scanner로 주소참조 -> UserController로 주소참조
 * -> UserController에서 dao 생성 -> UserDao로 주소참조
 * -> mainMenu()호출 후 Heap영역에 적재
 */


public class UserView {
	
	
	private Scanner sc = new Scanner(System.in);
	private UserController userController = new UserController();
	
	
	/**
	 * 프로그램 시작 시 사용자에게 보여줄 메인화면을 출력해주는 메서드
	 */
	public void mainMenu() {
		
		while(true) {
			
		System.out.println("--- USER테이블 관리 프로그램 ---");
		System.out.println("1.회원 전체 조회");
		System.out.println("2.회원 추가");
		System.out.println("3.회원 삭제");
		System.out.println("9.프로그램 종료");
		System.out.print("이용할 메뉴 선택 > ");
		
		
		int menuNo = 0;
		
		try {
			menuNo = sc.nextInt();
		} catch (InputMismatchException e) {
			
			sc.nextInt();
			continue;
		}
		
		sc.nextLine();
		switch(menuNo) {
		case 1 : 
				findAll();
				break;
		case 2 : 
			insertUser();
				break;
		case 3 : 
			deleteUser();
				break;
		case 9 : System.out.println("프로그램 종료"); return;
		default : System.out.println("잘못된 메뉴 선택입니다.");
		
		
		}
		
		
		}
		
		
	}
	
	// 회원 전체 정보를 조회해주는 기능
	private void findAll() {
		System.out.println("\n---회원 전체 목록입니다 ---");
		// Controller가 DB에서 회원 전체목록 호출
		List<UserDTO> list = userController.findAll();
		
		System.out.println("\n조회된 총 회원의 수는 " + list.size() + " 명 입니다.");
		
		if(!list.isEmpty()) { 
			System.out.println("======================================");
			
			for(UserDTO user: list) {
				System.out.print(user.getUserName() + "님의 정보 !");
				System.out.print("\t 아이디 : " + user.getUserId());
				System.out.print("\t 가입일 : " + user.getEnrollDate());
				System.out.println();
			}
			
			System.out.println("======================================");
		}else {
			System.out.println("조회된 회원이 없습니다. ");	
		}
	}
	
	/**
	 * TB_USER에 INSERT할 값을 사용자에게 입력받도록 유도하는 화면
	 */
	private void insertUser() {
		System.out.println("--- 회원 추가 페이지입니다. ---");
		

		System.out.println("아이디를 입력하세요 > ");
		String userId = sc.nextLine();
		// UNIQUE했다고 치고 입력받은 아이디 가지고 DB가서 
		// WHERE조건절에 사용자가 입력한 아이디 넣어서 조회결과있으면 출력
		// -> 중복검사 필요
//		if(userId.length() > 30) {
//			System.out.println("아이디 30자 이내로 입력");
//		}
		
		
		System.out.println("비밀번호를 입력하세요 > ");
		String userPw = sc.nextLine();
		System.out.println("이름을 입력하세요 > ");
		String userName = sc.nextLine();
		
		int result = userController.insertUser(userId, userPw, userName);
		
		if(result > 0) {
			System.out.println(userName + "님 가입에 성공하셨습니다");
		}else {
			System.out.println("회원추가 실패, 다시 시도");
		}
	}
	
	private void deleteUser(){
		System.out.println("=== 유저 삭제 페이지입니다. ===");
		
		System.out.println("삭제할 회원의 이름를 입력하세요 > ");
		String userName = sc.nextLine();
		
		System.out.println("삭제할 회원의 아이디를 입력하세요 > ");
		String userId = sc.nextLine();
		
		System.out.println("비밀번호를 입력하세요 > ");
		String userPw = sc.nextLine();
		
		int result = userController.deleteUser(userName, userId, userPw);
		
		if(result > 0) {		
				System.out.println(userName + "님의 정보가 삭제되었습니다");
			}else {
				System.out.println("회원 삭제 실패, 다시 시도");
			}
		}
	
	
	
	
	
	}
	
	

