package dto;

import java.sql.Timestamp;

public class WarningDTO {
	
	private int warningId;
	private String sensorId;
	private String message;
	private Timestamp warningDate;
	private String uesrId;
	
	public WarningDTO() {
		
	}

	public WarningDTO(int warningId, String sensorId, String message, Timestamp warningDate, String uesrId) {
		super();
		this.warningId = warningId;
		this.sensorId = sensorId;
		this.message = message;
		this.warningDate = warningDate;
		this.uesrId = uesrId;
	}

	@Override
	public String toString() {
		return "WarningDTO [warningId=" + warningId + ", sensorId=" + sensorId + ", message=" + message
				+ ", warningDate=" + warningDate + ", uesrId=" + uesrId + "]";
	}

	public int getWarningId() {
		return warningId;
	}

	public void setWarningId(int warningId) {
		this.warningId = warningId;
	}

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getWarningDate() {
		return warningDate;
	}

	public void setWarningDate(Timestamp warningDate) {
		this.warningDate = warningDate;
	}

	public String getUesrId() {
		return uesrId;
	}

	public void setUesrId(String uesrId) {
		this.uesrId = uesrId;
	}
	
	
	
	
	
	
	
	

}
