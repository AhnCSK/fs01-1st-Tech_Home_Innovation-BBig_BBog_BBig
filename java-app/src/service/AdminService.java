package service;

import java.util.List;

import dto.UserDTO;

public interface AdminService {
	// 관리자를 제외한 입주민 정보 조회
	List<UserDTO> getResidentList(UserDTO userDTO);
}
