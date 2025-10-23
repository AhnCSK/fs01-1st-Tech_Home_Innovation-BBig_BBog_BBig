package Controller;

import javax.swing.JOptionPane;

import dto.UserDTO;
import dto.UserSessionDTO;
import service.UserService;
import service.UserServiceImpl;
import util.ConsoleUtils;
import view.MainView;

public class MainController {
	// 현재 로그인한 사용자 정보
	private UserSessionDTO currentUser = null;
	// 화면을 담당하는 View 객체
	private final MainView view = new MainView();
	private final UserService service = new UserServiceImpl();
//	private MqttManager mqttManager;
	
	public void run() {
		while(true) {
			if (currentUser == null) {
				// 로그인되지 않았을 때의 로직 처리
				handleInitialMenu();
			}else {
				// 로그인된 후의 로직 처리
//				handleMainMenu();
				System.out.println("로그인");
			}
		}
	}
	
	// 로그인 되지 않았을 때 로직
	// 1번 로그인 2번 회원가입 9번 로그아웃 출력
	private void handleInitialMenu() {
		ConsoleUtils.clearConsole();
		// view는 메뉴를 보여주고 입력만 받아서 전달
		String choice = view.showInitialMenu();  
		switch(choice) {
		case "1":
			register();
			break;
		case "2":
//			login();
			break;
		case "9":
//			exitProgram();
			break;
		default:
//			view.showMessage("(!) 잘못된 입력입니다.");
		
		}
	
	}

	
	// 회원가입
	private void register() {
		// 콘솔창 클리어
		ConsoleUtils.clearConsole();
		
		// View에 현재 사용자 이름을 넘겨주어 메뉴를 보여줌
		UserDTO user = view.showRegistrationForm();
		int result = service.register(user);
		
		new Thread(() -> {
			if (result == 1) {
				JOptionPane.showMessageDialog(null, "회원가입이 성공했습니다.");
			}else if(result == 0){
				JOptionPane.showMessageDialog(null, "회원가입이 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
			}else if(result == -1){
				JOptionPane.showMessageDialog(null, "존재하지 않는 동/호실 입니다.");
			}
		}).start();   // 스레드 시작
	}
	
	
}	
