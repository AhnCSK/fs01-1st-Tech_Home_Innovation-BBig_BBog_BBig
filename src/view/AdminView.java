package view;

import java.util.List;
import java.util.Scanner;

import dto.NoticeDTO;
import dto.UserDTO;
import dto.WarningDTO;

// admin으로 로그인했을 경우 출력 화면
public class AdminView {
	private static final Scanner scanner = new Scanner(System.in);

	// 입주민 목록 출력
	public void showResidentList(List<UserDTO> residents) {
		System.out.println();
		System.out.println("╔═══════════════════════════════════════════════════════════════════╗");
		System.out.println("                            🏢 입주민 목록 🏢");
		System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
		System.out.println();
		System.out.printf("%-10s | %-10s | %-15s | %-5s | %-8s | %-6s\n", "UserID", "Name", "PhoneNumber", "State",
				"Building", "Room");
		System.out.println("=========================================================================");
		// 데이터 출력
		for (UserDTO user : residents) {
			System.out.printf("%-10s | %-10s | %-15s | %-5s | %-8d | %-6s\n", user.getUserId(), user.getName(),
					user.getPhoneNumber(), user.getState(), user.getBuilding(), user.getRoomNum());
			System.out.println("-----------------------------------------------------------------------");
		}
		System.out.println("============================================================");
		System.out.print("목록 나가기 >>>> ");
		scanner.nextLine();
	}

	// 경고 수신함
	public void getAllWarning(List<WarningDTO> warningList) {
		System.out.println();
		System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
		System.out.println("                     ⚠️  경고 수신함 ⚠️");
		System.out.println("╚══════════════════════════════════════════════════════════════════════╝");
		System.out.println();
		System.out.printf("%-5s | %-10s | %-8s | %-20s | %-20s | %-20s\n", "Room", "입주민ID", "경고타입", "내용", "연락처",
				"발생날짜");
		System.out.println("-------------------------------------------------------------------------------");

		for (WarningDTO notice : warningList) {
			System.out.printf("%-5d | %-10s | %-8s | %-20s | %-20s | %-20s\n", notice.getRoom_id(), notice.getUser_id(),
					notice.getWarningType(), notice.getMessage(), notice.getPhoneNumber(), notice.getDate());
			
			System.out.println("-----------------------------------------------------------------------------------------------------------");
		}
		

		System.out.println("============================================================");
		System.out.print("경고함 나가기 >>>> ");
		scanner.nextLine();
	}

	// 관리자용 게시판 보기
	public void viewPostAdmin(List<NoticeDTO> noticeList) {
		System.out.println();
		System.out.println("╔═══════════════════════════════════════════════════════════════════════════╗");
		System.out.println("                          📰 아파트 소통 게시판 📰");
		System.out.println("╚═══════════════════════════════════════════════════════════════════════════╝");
		System.out.println();
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
			System.out.println("───────────────────────────────────────────────────────────────────────────");
		}
		System.out.print("게시판 나가기 >>>> ");
		scanner.nextLine();

	}

	// 관리자용 민원 게시글만 조회
	public void getAllPostsComplaint(List<NoticeDTO> complaintList, UserDTO user) {
		System.out.println();
		System.out.println("╔════════════════════════════════════════════════════╗");
		System.out.printf("                  📩 수신된 민원 📩\n");
		System.out.println("╚════════════════════════════════════════════════════╝");

		System.out.println("번호\t | 카테고리 | 제목 \t \t| 내용 \t \t| 작성날짜 \t |");
		System.out.println("-------------------------------------------------------------------------------");
		int size = complaintList.size();

		if (size > 0) {
			for (int i = 0; i < size; i++) {
				NoticeDTO post = complaintList.get(i);
				System.out.print(post.getNoticeId() + "\t | ");
				System.out.print(post.getTitle() + "\t | ");
				System.out.print(post.getType() + "\t | ");
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
}
