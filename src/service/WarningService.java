package service;

public interface WarningService {

	
	//라즈베리파이에서 가져온 경고 메시지를 데이터베이스에 저장하기
	void saveWarningData(String topic, String message);
	
	//센서 번호를 기준으로

}
