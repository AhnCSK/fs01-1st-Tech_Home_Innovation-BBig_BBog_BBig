package dto;

public class UserDTO {
	// 유저 아이디
	private String userId;
//	// 유저 이름
	private String name;
	// 유저 비밀번호
	private String pass;
	// 유저 핸드폰
	private String phoneNumber;
	// 유저 외출 상태
	private String state;

	private int roomId;

	// 유저 (아파트) 동
	private int building;

	// 유저 (아파트) 호실
	private String roomNum;

	// 기본 생성자
	public UserDTO() {

	}
	
	

	public UserDTO(String userId, String name, String phoneNumber, String state, int building, String roomNum) {
		super();
		this.userId = userId;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.state = state;
		this.building = building;
		this.roomNum = roomNum;
	}



	public UserDTO(String userId, String name, String pass, String phoneNumber, String state, int roomId, int building,
			String roomNum) {
		super();
		this.userId = userId;
		this.name = name;
		this.pass = pass;
		this.phoneNumber = phoneNumber;
		this.state = state;
		this.roomId = roomId;
		this.building = building;
		this.roomNum = roomNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", name=" + name + ", pass=" + pass + ", phoneNumber=" + phoneNumber
				+ ", state=" + state + ", roomId=" + roomId + ", building=" + building + ", roomNum=" + roomNum + "]";
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public int getBuilding() {
		return building;
	}

	public void setBuilding(int building) {
		this.building = building;
	}

}
