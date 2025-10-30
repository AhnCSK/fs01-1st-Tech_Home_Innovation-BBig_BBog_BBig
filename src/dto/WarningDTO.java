package dto;

import java.sql.Date;
import java.sql.Timestamp;
// warning_id(auto)    room_id      sensor     warning_type   message  date(auto)

public class WarningDTO {
	private int warningId;
	private int room_id;
	private String user_id;
	private String warningType;
	private String message;
	private String date;
	private String phoneNumber;

	public WarningDTO() {
	}

	// DTO에 맞춘 생성자
	public WarningDTO(int room_id, String user_id, String warningType, String message, String phoneNumber,
			String date) {
		this.room_id = room_id;
		this.user_id = user_id;
		this.warningType = warningType;
		this.message = message;
		this.phoneNumber = phoneNumber;
		this.date = date;

	}

	public WarningDTO(int warningId, int room_id, String user_id, String warningType, String message,
			String phoneNumber, String date) {
		super();
		this.warningId = warningId;
		this.room_id = room_id;
		this.user_id = user_id;
		this.warningType = warningType;
		this.message = message;
		this.date = date;
		this.phoneNumber = phoneNumber;
	}

	public String getUser_id() {
		return user_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDate() {
		return date;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getWarningId() {
		return warningId;
	}

	public void setWarningId(int warningId) {
		this.warningId = warningId;
	}

	public int getRoom_id() {
		return room_id;
	}

	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}

	public String getWarningType() {
		return warningType;
	}

	public void setWarningType(String warningType) {
		this.warningType = warningType;
	}

	@Override
	public String toString() {
		return "WarningDTO [warningId=" + warningId + ", room_id=" + room_id + ", user_id=" + user_id + ", warningType="
				+ warningType + ", date=" + date + ", phoneNumber=" + phoneNumber + "]";
	}

}
