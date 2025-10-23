package dto;

public class SensorDTO {
	
	private String sensorId;
	private String sensorType;
	private String sensorLocation;
	private String sensorPower;
	
	public SensorDTO() {
		
	}

	public SensorDTO(String sensorId, String sensorType, String sensorLocation, String sensorPower) {
		super();
		this.sensorId = sensorId;
		this.sensorType = sensorType;
		this.sensorLocation = sensorLocation;
		this.sensorPower = sensorPower;
	}

	@Override
	public String toString() {
		return "SensorDTO [sensorId=" + sensorId + ", sensorType=" + sensorType + ", sensorLocation=" + sensorLocation
				+ ", sensorPower=" + sensorPower + "]";
	}

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public String getSensorType() {
		return sensorType;
	}

	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}

	public String getSensorLocation() {
		return sensorLocation;
	}

	public void setSensorLocation(String sensorLocation) {
		this.sensorLocation = sensorLocation;
	}

	public String getSensorPower() {
		return sensorPower;
	}

	public void setSensorPower(String sensorPower) {
		this.sensorPower = sensorPower;
	}
	
	
	

}
