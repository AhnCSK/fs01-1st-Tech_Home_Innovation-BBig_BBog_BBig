package Controller;

import java.util.List;

import javax.swing.JOptionPane;

import dao.UserDAO;
import dao.UserDAOImpl;
import dao.WarningDAO;
import dao.WarningDAOImpl;
import dto.LoginUserDTO;
import dto.NoticeDTO;
import dto.UserDTO;
import dto.UserSessionDTO;
import dto.WarningDTO;
import mqtt.MqttPubSubServiceImpl;
import service.AdminService;
import service.NoticeService;
import service.NoticeServiceImpl;
import service.SensorService;
import service.SensorServiceImpl;
import service.UserService;
import service.UserServiceImpl;
import util.ConsoleUtils;
import view.AdminView;
import view.DetailView;
import view.MainView;

public class MainController {
	// 현재 로그인한 사용자 정보
	private UserSessionDTO currentUser = null;

	// 메인 화면
	private final MainView view = new MainView();

	// 관리자 화면
	private final AdminView adminView = new AdminView();
	private AdminService adminService;

	// 상세 화면
	private DetailView detailView;

	// 공지 관련
	private NoticeService noticeService;
	private UserService service = new UserServiceImpl();

	// mqtt
	private MqttPubSubServiceImpl mqttpubsub;

	// 기본 생성자
	public MainController() {
		// 서비스 생성 -> 서비스로 뷰
		this.noticeService = new NoticeServiceImpl();
		this.detailView = new DetailView(this.noticeService);
	}

	public MainController(AdminService adminService) {
		this();
		// 기본 생성자 호출로 초기화
		this.adminService = adminService;
	}

	// 컨트롤 실행
	public void run() {
		while (true) {
			// 로그인이 되지 않았을 경우
			if (currentUser == null) {
				handleInitialMenu();
			} else {
				// 로그인 됐을 경우
				handleMainMenu();
			}
		}
	}

// --------------------(실행)-----------------------

	// 로그인 되지 않았을 때 로직
	private void handleInitialMenu() {
		ConsoleUtils.clearConsole();
		// 유저가 선택한 값을 받음
		String choice = view.showInitialMenu();
		switch (choice) {
		case "1":
			// 회원가입
			register();
			break;
		case "2":
			// 로그인
			login();
			break;
		case "9":
			// 프로그램 종료
			exitProgram();
			break;
		default:
			JOptionPane.showMessageDialog(null, "잘못된 값을 입력했습니다.");
		}
	}

	// 회원가입
	private void register() {
		ConsoleUtils.clearConsole();

		// 회원가입 뷰에서 사용자가 입력한 데이터를 UserDTO에서 받음
		UserDTO user = view.showRegistrationForm();
		int result = service.register(user);

		new Thread(() -> {
			if (result == 1) {
				JOptionPane.showMessageDialog(null, "회원가입이 성공했습니다.");
			} else if (result == 0) {
				JOptionPane.showMessageDialog(null, "회원가입이 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
			} else if (result == -1) {
				JOptionPane.showMessageDialog(null, "존재하지 않는 동/호실 입니다.");
			}
		}).start();
	}

	// 로그인
	private void login() {
		ConsoleUtils.clearConsole();

		// 유저가 입력한 값 받음
		LoginUserDTO loginUser = view.handleLogin();

		// service.login()에서 DB를 조회해서 일치하는 유저 정보를 반환
		UserDTO loginSuccessUser = service.login(loginUser.getUserId(), loginUser.getPass());

		if (loginSuccessUser != null) {
			// 현재 로그인한 사용자 정보를 세션 객체에 저장
			currentUser = new UserSessionDTO(loginSuccessUser);

			// mqtt Subscriber 실행
			startMqttSubscriber(loginSuccessUser);

			// userId로 관리자/유저 구분
			if ("admin".equals(loginSuccessUser.getUserId())) {
				JOptionPane.showMessageDialog(null, "관리자로 로그인했습니다.");
				adminMainMenu();
			} else {
				JOptionPane.showMessageDialog(null, "로그인에 성공했습니다.");
				handleMainMenu();
			}
		} else {
			JOptionPane.showMessageDialog(null, "로그인 실패");
			login();
		}
	}

	// 로그인 유저 정보를 받아, 해당 사용자에 맞는 mqtt 구독 시작
	private void startMqttSubscriber(UserDTO user) {
		mqttpubsub = new MqttPubSubServiceImpl(user);

		// MQTT 구독 서비스를 지속적으로 유지
		new Thread(() -> {
			try {
				while (true)
					Thread.sleep(1000); // 스레드 유지
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}

// --------------------(관리자 로그인)-----------------------

	// 관리자 메뉴
	private void adminMainMenu() {
		while (true) {
			ConsoleUtils.clearConsole();
			String choice = view.adminMainMenu(currentUser.getLoginUser());
			switch (choice) {
			case "1":
				// 입주민 정보 조회
				handleListResidents();
				break;
			case "2":
				// 아파트 게시판 기능
				noticeBoard();
				break;
			case "3":
				// 경고 수신함 조회
				handleListWarning();
				break;
			case "4":
				logout();
				return;
			default:
				JOptionPane.showMessageDialog(null, "잘못된 값을 입력했습니다.");
			}
		}
	}

	// 1번 (관리자) 입주민 정보 조회
	private void handleListResidents() {
		ConsoleUtils.clearConsole();
		List<UserDTO> residents = adminService.getResidentList(currentUser.getLoginUser());
		adminView.showResidentList(residents);
	}

	// 2번 아파트 게시판
	public void noticeBoard() {
		ConsoleUtils.clearConsole();
		UserDTO user = currentUser.getLoginUser();
		
		// 모든 게시글
		List<NoticeDTO> noticeList = noticeService.getAllPosts();
		// 자신이 작성한 게시글
		List<NoticeDTO> postMyList = noticeService.getPostById(user.getUserId());
		// 관리자의 모든 게시글 조회
		List<NoticeDTO> noticeAdmin = noticeService.getAllPostsAdmin();
		// 관리자 전용 민원 수신
		List<NoticeDTO> complaintList = noticeService.getComplaint();

		// 사용자가 선택한 값을 받아옴
		int choice = detailView.noticeMenu(user);
		switch (choice) {
		case 1:
			// 게시글 작성
			detailView.writePost(user);
			JOptionPane.showMessageDialog(null, "게시글이 작성됐습니다.");
			// 작성 후 이전 메뉴로 돌아감
			noticeBoard();
			break;
		case 2:
			// 게시글 조회 
			// (관리자) 관리자 전용 뷰 / (유저) 유저 전용 뷰 구분
			if ("admin".equals(user.getUserId())) {
				adminView.viewPostAdmin(noticeAdmin);
			} else {
				detailView.viewPost(noticeList);
			}
			noticeBoard();
			break;
		case 3:
			// 로그인한 user가 작성한 게시글 조회
			detailView.getPostById(postMyList, user);
			noticeBoard();
			break;
		case 4:
			// 관리자 전용 민원 조회 
			if ("admin".equals(user.getUserId())) {
				adminView.getAllPostsComplaint(complaintList, user);
				noticeBoard();
				break;
			}else {
				JOptionPane.showMessageDialog(null, "권한이 없습니다.");
				System.out.println("권한이 없습니다.");
			}
		case 5:
			// 이전 메뉴로 돌아가기
			// 관리자와 유저의 메인 뷰가 다르므로 구분
			if ("admin".equals(user.getUserId()))
				adminMainMenu();
			else
				handleMainMenu();
			break;
		default:
			JOptionPane.showMessageDialog(null, "잘못된 값을 입력했습니다.");
		}
	}

	// 3번 (관리자) 경고 수신함 조회
	private void handleListWarning() {
		ConsoleUtils.clearConsole();

		WarningDAO warningService = new WarningDAOImpl();
		List<WarningDTO> warningList = warningService.getAllWarning();

		adminView.getAllWarning(warningList);
	}

// ------------------------(유저 로그인)---------------------------------------	

	// 로그인 성공 시 메인 메뉴
	private void handleMainMenu() {
		ConsoleUtils.clearConsole();
		String choice = view.showMainMenu(currentUser.getLoginUser());
		switch (choice) {
		case "1":
			// 장치 제어
			handleSensorMenu();
			break;
		case "2":
			// 로그인한 유저 정보 조회
			showInfo();
			break;
		case "3":
			// 로그인한 유저 정보 수정
			userInfoUpdate();
			break;
		case "4":
			// 아파트 게시판
			noticeBoard();
			break;
		case "5":
			// 로그인한 유저 상태(외출/재택) 변경
			handleStateUpdate();
			break;
		case "6":
			logout();
			break;
		default:
			JOptionPane.showMessageDialog(null, "잘못된 값을 입력했습니다.");
		}
	}

	// 1번 장치 제어
	private void handleSensorMenu() {
		UserDTO user = currentUser.getLoginUser();

		// 장치 제어 메뉴 루프 (위치 선택)
		while (true) {
			// 유저가 제어할 위치를 선택
			String selecteRoom = detailView.selectLocation(user);

			// '이전 메뉴'를 선택하면 return으로 이전으로 돌아감
			if ("PREV_MENU".equals(selecteRoom))
				return;

			SensorService sensorService = new SensorServiceImpl();

			// 선택한 위치에 설치된 장치 목록 가져옴
			List<String> sensorList = sensorService.getSensorByRoom(selecteRoom);

			// 값이 없으면 선택 단계로 돌아감
			if (sensorList == null || sensorList.isEmpty())
				continue;

			// 장치 제어 메뉴 루프 (위치 -> 장치 선택)
			while (true) {
				String selectedSensor = detailView.selectSensorType(selecteRoom, sensorList);

				// '이전 메뉴' 선택하면 break로 빠져나와 위치 선택 단계로 돌아감
				if ("PREV_MENU".equals(selectedSensor))
					break;

				// 선택한 장치에 대해 on/off 동작 선택
				String action = detailView.onOffMenu(selectedSensor);

				// '이전 메뉴' 선택하면 장치 선택 단계로 돌아감
				if ("PREV_MENU".equals(action))
					continue;

				SensorControl sensor = new SensorControl(user);

				// 로그인한 유저의 동/호수, 유저가 선택한 센서종류 및 동작상태를 전달하여 constrol 실행
				sensor.control(user.getBuilding(), user.getRoomNum(), selectedSensor, action, selecteRoom);
			}
		}
	}

	// 로그인한 유저 정보 조회
	private void showInfo() {
		if (currentUser != null) {
			// 화면에 한 번만 출력하는 용도 이기 때문에 UserDTO 지역 변수를 만들지 않음
			detailView.showUserInfo(currentUser.getLoginUser());
		} else {
			System.out.println("로그인된 사용자가 없습니다.");
		}
	}

	// 로그인한 유저 정보 수정
	private void userInfoUpdate() {
		// 로그인한 유저 정보를 받음
		UserDTO user = currentUser.getLoginUser();
		// 사용자가 새롭게 입력한 유저 정보를 받음
		UserDTO updatedUser = detailView.userInfoUpdate(user);

		if (updatedUser != null) {
			// 정보 업데이트 수행
			service.updateUserInfo(user, updatedUser);
			JOptionPane.showMessageDialog(null, "사용자 정보가 수정됐습니다.");
		} else {
			JOptionPane.showMessageDialog(null, "수정이 취소됐습니다.");
		}
	}

	// 3번 아파트 게시판 기능은 admin 로그인 코드 쪽에 배치

	// 4번 로그인한 유저의 외출/재택 상태 변환
	private void handleStateUpdate() {

		// DB 접근을 위한 DAO 객체 생성
		UserDAO dao = new UserDAOImpl();
		// 현재 로그인한 사용자 정보 받음

		// 유저가 선택한 외출/재택 값을 받음
		UserDTO user = currentUser.getLoginUser();
		String choice = detailView.stateUpdate(user);

		String newState = null;

		// MQTT Publish를 위한 기본 토픽
		String pubTopic = "home/"+user.getBuilding()+"/"+user.getRoomNum();

		// 사용자의 선택에 따라 상태 및 MQTT 메시지 처리
		if ("1".equals(choice)) {
			// 외출 선택
			newState = "Away";
			mqttpubsub.publish(pubTopic, "security_on");
		} else if ("2".equals(choice)) {
			newState = "Home";
			// 재택 선택
			mqttpubsub.publish(pubTopic, "security_off");
		} else {
			JOptionPane.showMessageDialog(null, "잘못된 값을 입력했습니다.");
			return;
		}

		// DB에 사용자 상태 업데이트
		dao.stateUpdate(user, newState);
		// 현재 로그인한 사용자 객체도 변경 상태 반영
		user.setState(newState);
		JOptionPane.showMessageDialog(null, newState + "상태로 변경 됐습니다.");
	}

	
	// 로그아웃
	private void logout() {
		currentUser = null;
		if (mqttpubsub != null)
			// MQTT 구독/발행 서비스 실행중이면 리소스 해제
			mqttpubsub.close();
		JOptionPane.showMessageDialog(null, "로그아웃 됐습니다.");
		run();
	}
	
	// 프로그램 종료
	private void exitProgram() {
		ConsoleUtils.clearConsole();
		JOptionPane.showMessageDialog(null, "프로그램을 종료합니다.");
		MainView.exitProgram();
	}
}
