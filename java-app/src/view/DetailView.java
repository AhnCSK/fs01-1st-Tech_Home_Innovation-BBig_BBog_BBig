package view;

import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import dao.NoticeDAO;
import dto.NoticeDTO;
import dto.UserDTO;
import service.NoticeService;


// 유저 로그인 출력 화면
public class DetailView {
	private static final Scanner scanner = new Scanner(System.in);
	private NoticeService noticeService;

	public DetailView() {

	}

	public DetailView(NoticeService noticeService) {
		super();
		this.noticeService = noticeService;
	}

	// 1. 장치제어 - 제어할 방 선택
	public String selectLocation(UserDTO user) {
		while (true) {
			System.out.println();
			System.out.println("╔════════════════════════════════════════════════════╗");
			System.out.printf("     %s님, 제어할 방을 선택해주세요.%n", user.getUserId());
			System.out.println("╚════════════════════════════════════════════════════╝");
			System.out.printf("현재 상태: %s | 동: %d | 호실: %s%n", user.getState(), user.getBuilding(), user.getRoomNum());
			System.out.println();
			System.out.println("   [1] 방1 💡");
			System.out.println("   [2] 방2 📊");
			System.out.println("   [3] 거실 🛋️");
			System.out.println("   [4] 부엌 🍳");
			System.out.println("   [5] 이전 메뉴로 돌아가기");
			System.out.println("────────────────────────────────────────────────────");
			System.out.print("> 입력: ");

			String choice = scanner.next();
			switch (choice) {
			case "1":
				return "room1";
			case "2":
				return "room2";
			case "3":
				return "living";
			case "4":
				return "kitchen";
			case "5":
				return "PREV_MENU";
			default:
				JOptionPane.showMessageDialog(null, "잘못된 값을 입력했습니다.");
			}
		}
	}

	// 1-1. 장치 종류 선택
	// 이전 메뉴에서 선택한 room 데이터와, room마다 생성된 센서 리스트를 불러옴
	public String selectSensorType(String selectedRoom, List<String> sensors) {
		while (true) {
			System.out.println();
			System.out.println("╔════════════════════════════════════════════════════╗");
			System.out.printf("         %s 장치 제어 시스템%n", selectedRoom);
			System.out.println("╚════════════════════════════════════════════════════╝");
			for (int i = 0; i < sensors.size(); i++) {
				System.out.printf("   [%d] %s%n", i + 1, sensors.get(i));
			}
			System.out.printf("   [%d] 이전 메뉴로 돌아가기%n", sensors.size() + 1);
			System.out.println("────────────────────────────────────────────────────");
			System.out.print("> 입력: ");

			try {
				int choice = Integer.parseInt(scanner.next());
				if (choice >= 1 && choice <= sensors.size())
					return sensors.get(choice - 1);
				else if (choice == sensors.size() + 1)
					return "PREV_MENU";
				else
					JOptionPane.showMessageDialog(null, "잘못된 선택입니다. 다시 입력해주세요.", "알림", JOptionPane.INFORMATION_MESSAGE);
			} catch (NumberFormatException e) {
				System.out.println("숫자만 입력해주세요.");
			}
		}
	}

	// 1-2 장치 ON/OFF 제어 메뉴
	// 이전 메뉴에서 선택한 장치를 가져옴
	public String onOffMenu(String selectedSensor) {
		while (true) {
			System.out.println();
			System.out.println("╔════════════════════════════════════════════════════╗");
			System.out.printf("        %s 장치 제어 시스템%n", selectedSensor);
			System.out.println("╚════════════════════════════════════════════════════╝");
			System.out.println("   [1] ON");
			System.out.println("   [2] OFF");
			System.out.println("   [3] 이전 메뉴로 돌아가기");
			System.out.println("────────────────────────────────────────────────────");
			System.out.print("> 입력: ");

			String onOff = scanner.next();
			switch (onOff) {
			case "1":
				return "ON";
			case "2":
				return "OFF";
			case "3":
				return "PREV_MENU";
			default:
				JOptionPane.showMessageDialog(null, "잘못된 값을 입력했습니다.");
			}
		}
	}

	// 2. 사용자 정보 조회
	public void showUserInfo(UserDTO user) {
		String message = "=== 내 정보 조회 ===\n" + "아이디: " + user.getUserId() + "\n" + "이름: " + user.getName() + "\n"
				+ "전화번호: " + user.getPhoneNumber() + "\n" + "상태: " + user.getState() + "\n" + "건물: "
				+ user.getBuilding() + "\n" + "호실: " + user.getRoomNum();
		JOptionPane.showMessageDialog(null, message, "내 정보", JOptionPane.INFORMATION_MESSAGE);
	}

	// 3. 사용자 정보 수정
	public UserDTO userInfoUpdate(UserDTO user) {
		System.out.println();
		System.out.println("╔════════════════════════════════════════════════════╗");
		System.out.println("             ⚙️ 사용자 정보 수정 ⚙️");
		System.out.println("╚════════════════════════════════════════════════════╝");
		System.out.printf("%s님의 정보를 수정합니다.%n", user.getUserId());
		System.out.print("현재 비밀번호를 입력하세요: ");
		String password = scanner.next();

		// 정보 수정 전 현재 비밀번호를 확인
		if (!password.equals(user.getPass())) {
			JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.");
			return null;
		}

		System.out.print("새 비밀번호: ");
		String newPass = scanner.next();
		System.out.print("새 비밀번호 확인: ");
		String confirmPass = scanner.next();

		// 변경할 비밀번호 재확인
		if (!newPass.equals(confirmPass)) {
			JOptionPane.showMessageDialog(null, "비밀번호 확인이 일치하지 않습니다.");
			return null;
		}

		System.out.printf("새 이름 (현재: %s): ", user.getName());
		String newName = scanner.next();
		System.out.printf("새 전화번호 (현재: %s): ", user.getPhoneNumber());
		String newPhone = scanner.next().replaceAll("[^0-9]", "");

		// 전화번호 (-) 상관없이 입력받음
		if (newPhone.length() == 11)
			newPhone = newPhone.substring(0, 3) + "-" + newPhone.substring(3, 7) + "-" + newPhone.substring(7);

		user.setPass(newPass);
		user.setName(newName);
		user.setPhoneNumber(newPhone);

		System.out.println("────────────────────────────────────────────────────");
		System.out.println("✅ 정보가 성공적으로 수정되었습니다.");
		
		// 입력받은 값 return
		return user;
	}

	// 4. 게시판 메뉴
	public int noticeMenu(UserDTO user) {
		System.out.println();
		System.out.println("╔════════════════════════════════════════════════════╗");
		System.out.println("              🏢 아파트 게시판 🏢");
		System.out.println("╚════════════════════════════════════════════════════╝");

		// 관리자로 로그인 했을 경우 출력화면
		if ("admin".equals(user.getUserId())) {
			System.out.println("   [1] 공지사항 작성");
			System.out.println("   [2] 게시글 목록 보기");
			System.out.println("   [3] 작성한 공지사항 보기");
			System.out.println("   [4] 수신된 민원 보기");
		} 
		// 일반 유저로 로그인 했을 경우 출력화면
		else {
			System.out.println("   [1] 게시글 작성");
			System.out.println("   [2] 게시글 목록 보기");
			System.out.println("   [3] 내가 작성한 글 보기");
		}
		System.out.println("   [5] 이전 메뉴로 돌아가기");
		System.out.println("────────────────────────────────────────────────────");
		System.out.print("> 입력: ");
		return scanner.nextInt();
	}

	// 4-1. 게시글 작성
	public int writePost(UserDTO user) {
		if (scanner.hasNextLine())
			scanner.nextLine();

		System.out.println();
		System.out.println("╔════════════════════════════════════════════════════╗");
		System.out.println("               📝 게시글 작성 📝");
		System.out.println("╚════════════════════════════════════════════════════╝");

		// 관리자로 로그인했을 경우 공지사항만 작성 가능
		if ("admin".equals(user.getUserId()))
			System.out.println("   [3] 공지사항");
		else {
			// 일반 유저로 로그인했을 경우 민원/소통만 작성 가능
			System.out.println("   [1] 민원");
			System.out.println("   [2] 소통");
		}

		System.out.print("> 카테고리 입력: ");
		String type = scanner.nextLine();
		switch (type) {
		case "1":
			type = "민원";
			break;
		case "2":
			type = "소통";
			break;
		case "3":
			type = "공지";
			break;
		}

		System.out.print("제목: ");
		String title = scanner.nextLine();
		System.out.print("내용: ");
		String post = scanner.nextLine();

		NoticeDTO notice = new NoticeDTO();
		notice.setType(type);
		notice.setTitle(title);
		notice.setPost(post);

		System.out.println("────────────────────────────────────────────────────");
		System.out.println("📬 게시글이 등록되었습니다.");
		return noticeService.writePost(user, notice);
	}

	// 4-2. 전체 게시글 보기
	public void viewPost(List<NoticeDTO> noticeList) {
		scanner.nextLine();
		System.out.println();
		System.out.println("╔════════════════════════════════════════════════════╗");
		System.out.println("             🏘 아파트 게시판 목록 🏘");
		System.out.println("╚════════════════════════════════════════════════════╝");
		System.out.println("번호\t | 카테고리 | 제목 \t \t| 내용 \t \t| 작성날짜 \t |");
		System.out.println("-------------------------------------------------------------------------------");
		int size = noticeList.size();

		for (int i = 0; i < size; i++) {
			NoticeDTO notice = noticeList.get(i);
			System.out.print(notice.getNoticeId() + "\t | ");
			System.out.print("[" + notice.getTitle() + "]" + "\t | ");
			System.out.print(notice.getType() + "\t | ");

			System.out.print(notice.getPost() + "\t | ");
			System.out.print(notice.getPostDate() + "\t | ");
			System.out.println();
			System.out.println("────────────────────────────────────────────────────");
		}
		System.out.print("게시판 나가기 >>>>");
		scanner.nextLine();
	}

	// 4-3. 내가 작성한 게시글 보기
	public void getPostById(List<NoticeDTO> postList, UserDTO user) {
		scanner.nextLine();
		System.out.println();
		System.out.println("╔════════════════════════════════════════════════════╗");
		System.out.printf("      ✍ %s님이 작성한 게시글 ✍%n", user.getUserId());
		System.out.println("╚════════════════════════════════════════════════════╝");

		System.out.println("번호\t | 카테고리 | 제목 \t \t| 내용 \t \t| 작성날짜 \t |");
		System.out.println("-------------------------------------------------------------------------------");
		int size = postList.size();

		if (size > 0) {
			for (int i = 0; i < size; i++) {
				NoticeDTO post = postList.get(i);
				System.out.print(post.getNoticeId() + "\t | ");
				System.out.print("[" + post.getType() + "]" + "\t | ");
				System.out.print(post.getTitle() + "\t | ");

				System.out.print(post.getPost() + "\t | ");
				System.out.print(post.getPostDate() + "\t | ");
				System.out.println();
				System.out.println("\n------------------------------------------------------------------------");
			}
		} else {
			System.out.println("           작성된 게시글이 없습니다");
		}
		System.out.println("────────────────────────────────────────────────────");
		System.out.print("게시판 나가기 >>>>");
		scanner.nextLine();
	}

	// 5. 외출/재택 상태 변경
	public String stateUpdate(UserDTO user) {
		System.out.println();
		System.out.println("╔════════════════════════════════════════════════════╗");
		System.out.println("          🚪 외출 상태 변환 시스템 🚪");
		System.out.println("╚════════════════════════════════════════════════════╝");
		System.out.printf("%s님, 현재 상태: %s | 동: %d | 호실: %s%n", user.getUserId(), user.getState(), user.getBuilding(),
				user.getRoomNum());
		System.out.println();
		System.out.println("   [1] 외출");
		System.out.println("   [2] 재택");
		System.out.println("   [3] 이전 메뉴로 돌아가기");
		System.out.println("────────────────────────────────────────────────────");
		System.out.print("> 입력: ");
		return scanner.next();
	}

}
