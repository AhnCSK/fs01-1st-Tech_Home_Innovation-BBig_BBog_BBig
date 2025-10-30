package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import view.DetailView;

public class SensorServiceImpl implements SensorService {

	DetailView view = new DetailView();
	
	// 유저가 선택한 장소별 장치 리스트 저장
	public List<String> getSensorByRoom(String roomChoice) {
		List<String> sensors = new ArrayList<>();
		
		while(true) {
			switch(roomChoice) {
			// 방1 -> led, window(servo)
			case "room1":
				sensors = Arrays.asList("LED", "window");
				break;
			// 방2 -> led, window(servo)
			case "room2":
				sensors = Arrays.asList("LED", "window");
				break;
			// 거실 -> led, pump(화분 물 주기)
			case "living":
				sensors = Arrays.asList("LED", "pump");
				break;
			// 부엌 -> led
			case "kitchen":
				sensors = Arrays.asList("LED");
				break;
			}
			return sensors;
		}
		
	}	

}
