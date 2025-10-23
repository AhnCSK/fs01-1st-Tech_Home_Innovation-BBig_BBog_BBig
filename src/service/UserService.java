package service;

import dto.UserDTO;

public interface UserService {
	// UserDTO 타입의 객체(회원정보)를 받음
	int register(UserDTO user);
	// 로그인 기능으로 id와 pass를 받음
	UserDTO login(String id, String pass);
}
