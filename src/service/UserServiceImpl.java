package service;

import dao.UserDAO;
import dao.UserDAOImpl;
import dto.UserDTO;

public class UserServiceImpl implements UserService {

	private final UserDAO dao = new UserDAOImpl();
	
	@Override
	public int register(UserDTO user) {
		int result = dao.register(user);
		return result;
	}

	@Override
	public UserDTO login(String id, String pass) {
		// TODO Auto-generated method stub
		return null;
	}

}
