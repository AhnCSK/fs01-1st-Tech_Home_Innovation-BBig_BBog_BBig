package service;

import java.util.ArrayList;
import java.util.List;

import view.DetailView;

public interface SensorService {
	
	// 방 번호를 받으면 장치 목록 반환
	List<String> getSensorByRoom(String roomName);

}
