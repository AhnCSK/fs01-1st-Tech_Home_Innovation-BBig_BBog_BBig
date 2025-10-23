package service;

public interface SensorService {
	
	//라즈베리파이에서 가져온 센서데이터를 데이터베이스에 저장하기
	public void saveSensorData(String topic,String message);
	
	//센서 번호를 기준으로

}
