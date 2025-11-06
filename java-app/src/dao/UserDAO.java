package dao;

import java.util.List;

import dto.UserDTO;

public interface UserDAO {
	
	// 회원가입
	int register(UserDTO user);
	
	// 로그인
	UserDTO login(String id, String pass);
	
	// 재택/외출 상태 변환
	int stateUpdate(UserDTO user, String state);
	
	// 유저 정보 수정
	int userInfoUpdate(UserDTO user, UserDTO updatedUser);

    // 관리자용 전체 조회
    List<UserDTO> getAllUsers(UserDTO userDTO);
}
