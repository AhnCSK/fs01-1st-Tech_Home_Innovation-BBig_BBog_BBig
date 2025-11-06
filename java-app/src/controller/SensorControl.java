package controller;

import dto.UserDTO;
import mqtt.MqttPubSubService;
import mqtt.MqttPubSubServiceImpl;

public class SensorControl {
    private final MqttPubSubServiceImpl mqttManager;

    // 로그인된 사용자 전달
    public SensorControl(UserDTO user) {
        if (user == null) {
            throw new IllegalArgumentException("사용자 정보가 null입니다.");
        }
        mqttManager = new MqttPubSubServiceImpl(user); // 이미 연결+구독 처리
        System.out.println("SensorControl 초기화 완료, MQTT 연결됨");
    }

    // 로그인된 유저의 동/호수, 선택한 장치(ex: led), 선택한 제어(on/off), 선택한 장소(ex: 거실)
    public void control(int building, String roomNum, String selectedSensor, String action, String selecteRoom) {
        // 토픽 설정  (ex: 101/3001/)
    	String address = building + "/" + roomNum + "/";
    	
    	// 보낼 메시지 (ex: living/led_on)
        String message = selecteRoom + "/" + selectedSensor.toLowerCase() + "_" + action.toLowerCase();

        System.out.println("MQTT 전송 -> Topic: home/" + address + ", Message: " + message);

        // 센서 제어 메시지
        mqttManager.publish("home/" + address, message);

        // LCD 메시지
        System.out.println("home/lcd 메시지: " + message + "/ 기능을 수행합니다.");
        mqttManager.publish("home/lcd", message + "/ 기능을 수행합니다.");

    }
}
