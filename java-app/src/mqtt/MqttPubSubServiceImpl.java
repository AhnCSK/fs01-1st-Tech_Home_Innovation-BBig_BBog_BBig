package mqtt;

import java.util.UUID;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import dao.WarningDAO;
import dao.WarningDAOImpl;
import dto.UserDTO;

public class MqttPubSubServiceImpl implements MqttPubSubService, MqttCallback {

    // 로그인된 사용자 정보
    private UserDTO userDTO;

    // 인스턴스별로 독립적인 client 생성
    private MqttClient client;
    private final String broker = "tcp://192.168.14.39:1883"; // 라즈베리파이 MQTT 브로커

    // 토픽 설정: 전체 구독
    private final String subTopic = "home/warning"; 

    // 기본 생성자 (로그인 전용)
    public MqttPubSubServiceImpl() {
        try {
            String clientId = "pubsub_" + UUID.randomUUID();
            client = new MqttClient(broker, clientId);

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            client.setCallback(this);

            System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);
            System.out.println("Connected as " + clientId);

            subscribe();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // 로그인 후 사용자 기준 생성자
    public MqttPubSubServiceImpl(UserDTO userDTO) {
        if (userDTO == null) throw new IllegalArgumentException("UserDTO cannot be null");
        this.userDTO = userDTO;

        try {
            String clientId = "mqtt_controller_" + UUID.randomUUID();
            client = new MqttClient(broker, clientId);

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            client.setCallback(this);

            System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);
            System.out.println("Connected as " + clientId);

            // 사용자 기준 토픽 구독: 기존 코드 구조 유지
            subscribe();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // ===== MqttCallback =====
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String payload = new String(message.getPayload());

        // WarningService 선언
        WarningDAO warningService = new WarningDAOImpl();

        try {
            // 라즈베리에서 온 메시지인지 확인 (topic 체크)
            if (topic.startsWith("home/")) { 
                if ("Fire Outbreak".equals(payload) || "Intruder Detection".equals(payload)) {
                    warningService.saveWarning(userDTO, topic, payload);
                } else {
                    //System.out.println("⚠️ DB 저장 제외 메시지: " + payload);
                }
            } else {
                //System.out.println("⚠️ 알림 토픽 제외: " + topic);
            }

            // 팝업 메시지 처리
            if ("home/warning".equals(topic) || "fire".toLowerCase().contains(payload) 
                    || payload.toLowerCase().contains("intrusion")) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, payload, "센서 경고", JOptionPane.INFORMATION_MESSAGE);
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 연결 끊겼을 때
    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("⚠️ Connection lost: " + cause.getMessage());
    }

    // 메시지 성공적 발행
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("✅ Message delivery complete.");
    }

    // 연결 종료
    @Override
    public void close() {
        try {
            if (client != null && client.isConnected()) {
                client.disconnect();
                client.close();
                System.out.println("🔌 Disconnected.");
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        // 필요 시 스레드에서 호출
    }

    @Override
    public void setMessageListener(MessageListener listener) {
        // 필요 시 구현
    }

    // 메시지 받기 (Sub)
    @Override
    public void subscribe() {
        try {
            client.subscribe(subTopic);
            System.out.println("Subscribed to topic: " + subTopic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // 메시지 보내기 (Pub)
    @Override
    public void publish(String topic, String message) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(0);
            client.publish(topic, mqttMessage);
            System.out.println("📤 Published to " + topic + ": " + message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
