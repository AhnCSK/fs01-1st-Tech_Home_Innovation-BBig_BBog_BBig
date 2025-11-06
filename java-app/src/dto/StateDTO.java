package dto;

public class StateDTO {
	private String state;   // 유저 재택/외출 상태

	public StateDTO() {
		
	}

	public StateDTO(String state) {
		super();
		this.state = state;
	}
	
	
	@Override
	public String toString() {
		return "StateDTO [state=" + state + "]";
	}


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	

}
