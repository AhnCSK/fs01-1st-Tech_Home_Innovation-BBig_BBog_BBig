package mqtt;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import service.SensorService;
import service.SensorServiceImpl;
import service.WarningService;
import service.WarningServiceImpl;

public class MqttManager implements MqttCallback{


	
	//MqttManager클래스 안에 
	//퍼브와 서브를 각가 담당하는 기능함수를 구현할것이다
	
	private final String broker = "tcp://192.168.14.168:1883";
	private final String pubTopic = "/home/#";
	private final String subTopic = "/home/#";
	
	//서버(브로커)와의 연결을 담당하는 통신병 객체 =client
	private MqttClient client;
	
	
	/*MQTT 통신에서 클라이언트 ID는 브로커가 클라이언트를 식별하고 관리하는 데 사용됩니다. 이는 각 클라이언트가 시스템에 고유하게 등록되어 다른 클라이언트와 구분될 수 있도록 하는 중요한 식별자이며, 주로 인증, 권한 부여, 그리고 세션 관리와 같은 여러 작업에 활용됩니다. 
	클라이언트 ID의 주요 용도
	식별 및 관리: 클라이언트 ID는 각 MQTT 클라이언트가 브로커에게 자신을 고유하게 식별하는 데 사용됩니다. 브로커는 이 ID를 통해 클라이언트의 연결을 추적하고 관리할 수 있습니다. 
	인증 및 권한 부여: 브로커는 클라이언트 ID를 기반으로 특정 클라이언트가 메시지를 게시하거나 구독할 권한이 있는지 확인할 수 있습니다. 사용자 ID나 인증서와 함께 사용되어 보안을 강화하는 용도로도 사용됩니다. 
	세션 및 상태 유지: 클라이언트가 연결을 끊었다가 다시 연결할 때, 브로커는 클라이언트 ID를 통해 기존의 세션 상태를 복원할 수 있습니다. 이는 클라이언트가 마지막으로 보낸 메시지나 구독 정보를 유지하는 데 도움을 줍니다. 
	*/
	private String mqttClientId;
	
	
	//이건뭐지? 
	//가능성1 = MqttManager서비스와 다른 서비스객체들끼리의 통신인가?
	private SensorService sensorService = new SensorServiceImpl();
	private WarningService warningService = new WarningServiceImpl();

	
	public MqttManager() {
		// TODO Auto-generated constructor stub
	}
	
	public MqttManager(String mqttClientId) {
		super();
		this.mqttClientId = mqttClientId;
		
		try {
	        	
			//서버와 통신하는 객체를 힙메모리에 할당
	        // 유일한 클라이언트 ID 생성 (충돌 방지용)
			
			// 의문 강사님코드안에는 clientId가 이미 있는데 아래의 id가 또 왜 있어야함?
			
			/*답:
			 * 
			 * 
			 * 
			 * 
			 *  javaClientId(강사님코드속 id)= 이건 "비즈니스 로직상 식별자" 입니다.
				즉, 우리 프로그램 내부에서 이 MqttManager 객체를 구분하기 위해 사용됩니다.
				
				
			 *  mqttClientId(강사님코드속 ClientId) = UUID 기반 id (브로커에 실제로 넘겨지는 값)
				이건 "MQTT 브로커가 요구하는 고유 식별자" 입니다.
				브로커는 clientId가 겹치면 이전 연결을 끊고 새 연결을 받아들이므로,
				중복되면 안 됩니다.
			 * 
			 * 
			 * */
	        String javaClientId = "combined_client_" + UUID.randomUUID().toString();
	        client = new MqttClient(broker, javaClientId);
	        
            // 연결 설정을 힙메모리에 할당하기
            MqttConnectOptions connOpts = new MqttConnectOptions();
            //모든 설정을 초기화하기
            connOpts.setCleanSession(true);
            
            //브로커에게 메소드 스팩이 같은 콜백함수를 들록/인식시키기
            //브로커에 함정카드 깔기=번개조 만들고 대기
            // MqttCallback 인터페이스를 현재 클래스가 구현했으므로 this로 설정
            client.setCallback(this);
            
            
            System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);
            System.out.println("Connected.");

            // 연결 후(생성자로 힙에 할당돼자마자) 바로 구독 시작
            this.subscribe();

        } catch (MqttException me) {
            me.printStackTrace();
        }
		
	}
	
	
	
	






	// 브로커&서버로 구독요청을 보내어 들어오는 일방통행 길 개척 함수 / 마법카드
	private void subscribe() {
		// TODO Auto-generated method stub
		
		try {
			client.subscribe(mqttClientId+subTopic);
			System.out.println("SubscribedTopic to topic:"+mqttClientId+subTopic);
			
		}catch (MqttException e) {
			// TODO: handle exception
		}
		
	}
	
	
	// 브로커&서버로 메시지를 보내어 나가는는 일방통행 길 개척 함수 / 마법카드
	
	//mqtt에서 메시지를 보내기위해서는 1-topic 2-message 이 두가지가 있어야하므로
	//매개변수 2개필요
	
	//강사님 코드에서
	//왜 변수명을 content로 하나했더니
	//message를 안에서 또 써야하는구나
	
	public void publish(String topic,String javaMessage) {
		System.out.println("mqtt통신으로 보내게될 메시지:"+javaMessage);
		
		//통신시 String을 바이트로 쪼개고 객체안에 담아내는 코드
		MqttMessage mqttMessage = new MqttMessage(javaMessage.getBytes());
		
		
		//아래는 비필수옵션들
		mqttMessage.setQos(0); // QoS Level 0
		
        try {
        	//브로커로 메시지를 보내서 게시하기
			this.client.publish(topic, mqttMessage);
			
			
		} catch (MqttPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println("Message published.");
		
	}






	//MqttCallback인터페이스안의 사전정의 함수들 오버라이딩
	//번개조의 필수요소들 정의하기 / 함정카드의 필수요소
	
	
	//번개조 상황종료 / 함저발동후 필요 없는 상태
	@Override
	public void connectionLost(Throwable cause) {
		System.out.println("Connection lost: " + cause.getMessage());
		
	}

	
	//IMqttDeliveryToken은 뭐지?
	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}

	// messageArrived 메소드는 메시지가 도착할 때마다 Paho 라이브러리에 의해 자동으로 호출됩니다.
	
	//메시지가 도착할떄마다 데이터베이스안에 저장하는기능을 여기서 구현하면 된다
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		
		if(topic.contains("sensor")) {
        	if(topic.endsWith("dht11")) {
        		sensorService.saveSensorData(topic, new String(message.getPayload()));
        	}
        }else if(topic.contains("warning")) {
        	if(topic.endsWith("dht11")) {
        		warningService.saveWarningData(topic, new String(message.getPayload()));
        	}
		
		
        }

	}
	

}
