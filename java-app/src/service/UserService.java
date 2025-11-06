package service;

import dto.UserDTO;

public interface UserService {
	// UserDTO 타입의 객체(회원정보)를 받음
	int register(UserDTO user);
	// 로그인 기능으로 id와 pass를 받음
	UserDTO login(String id, String pass);
	// 유저의 상태 정보 변환을 위해 user의 id와 state 받음
	int stateUpdate(UserDTO user, String id);
	
	// 유저 정보 수정을 위해 받음 기존 user의 id와 새로운 user 정보를 받음
	int updateUserInfo(UserDTO user, UserDTO updatedUser);
	
}
