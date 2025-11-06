package util;

	public final class ConsoleUtils {

		// 콘솔창 정리 메소드 
	    public static void clearConsole() {
	        // 50~80줄 정도의 빈 줄을 출력해서 이전 내용이 안 보이게 합니다.
	        for (int i = 0; i < 20; i++) {
	            System.out.println("");
	        }
	    }
	}


