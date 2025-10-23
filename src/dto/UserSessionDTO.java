package dto;

public class UserSessionDTO {
	UserDTO loginUser;

	
	public UserSessionDTO() {
		
	}

	public UserSessionDTO(UserDTO loginUser) {
		super();
		this.loginUser = loginUser;
	}
	
	
	
	@Override
	public String toString() {
		return "UserSessionDTO [loginUser=" + loginUser + "]";
	}


	public UserDTO getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(UserDTO loginUser) {
		this.loginUser = loginUser;
	}
	
	
	
}
