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
		UserDTO user = dao.login(id, pass);
		System.out.println(user);
		return user;
	}

	@Override
	public int stateUpdate(UserDTO user, String id) {
		// DAO 호출 → 반환값은 영향을 받은 행(row) 수
		int result = dao.stateUpdate(user, id);
		
		return result;
	}

	// 유저 정보 수정
	@Override
	public int updateUserInfo(UserDTO user, UserDTO updatedUser) {
		
		int result = dao.userInfoUpdate(user, updatedUser);
		
		return result;
	}

}
