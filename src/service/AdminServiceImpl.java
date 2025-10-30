package service;

import java.util.List;

import dao.UserDAO;
import dto.UserDTO;

public class AdminServiceImpl implements AdminService {
	private UserDAO userDAO;
	
	public AdminServiceImpl(UserDAO userDAO) {
		super();
		this.userDAO = userDAO;
	}

	// 입주민 리스트
	@Override
	public List<UserDTO> getResidentList(UserDTO userDTO) {
		
		return userDAO.getAllUsers(userDTO);
	}

}
