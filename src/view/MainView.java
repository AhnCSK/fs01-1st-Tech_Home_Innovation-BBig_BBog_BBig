package view;



import java.util.Scanner;

import dto.UserDTO;

public class MainView {
	private static final Scanner scanner = new Scanner(System.in);

	
	
	// 앱 실행 첫 화면
	public String showInitialMenu() {
		System.out.println("\n==================================================");
		System.out.println("     🖥 테크 홈 이노베이션 - 삑뽁삑(BBig BBog BBig) 🖥");
		System.out.println("==================================================");
		System.out.println("\n환영합니다! 원하시는 메뉴를 선택해주세요.\n");
		System.out.println("  [1] 회원가입");
		System.out.println("  [2] 로그인");
		System.out.println("  [9] 프로그램 종료");
		System.out.println("\n--------------------------------------------------");
		System.out.print("> 입력: ");

		return scanner.nextLine();
	}
	
	// 회원가입 정보를 입력받아 DTO 객체로 반환하는 메소드
	public UserDTO showRegistrationForm() {
		System.out.println("\n=========================");
		System.out.println("              회원가입           ");
		System.out.println("---------------------------");
		UserDTO newUser = new UserDTO();
		
		System.out.println("아이디: ");
		newUser.setUserId(scanner.next());
		
		while (true) {
			System.out.println("비밀번호: ");
			String password = scanner.next();
			System.out.println("비밀번호 확인");
			String passwordConfirm = scanner.next();
			
			if(password.equals(passwordConfirm)) {
				newUser.setPass(passwordConfirm);
				break;
			} else {
				System.out.println("\n 비밀번호가 일치하지 않습니다.");
			}
		}
		

		System.out.println("핸드폰 번호: ");
		newUser.setPhoneNumber(scanner.next());
		
		System.out.println("(아파트) 동: ");
		newUser.setBuilding(scanner.nextInt());
		System.out.println("(아파트) 호실: ");
		newUser.setRoomNum(scanner.next());
		
		System.out.println("-----------------");
		
		return newUser;
	}
	
	
	public static void main(String[] args) {


	}

}
