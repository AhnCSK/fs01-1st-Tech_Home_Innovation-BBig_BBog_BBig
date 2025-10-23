package mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;



public class MqttManager implements MqttCallback{


	
	private final String broker = "tcp://192.168.14.168:1883.";
	private final String pubTopic = "";
	private final String subTopic = "";
	
	private MqttClient client;
	
	private SensorService sService = new SensorServiceImpl();

	
	
	
	
	//MqttCallback의 사전정의 함수들
	
	@Override
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
