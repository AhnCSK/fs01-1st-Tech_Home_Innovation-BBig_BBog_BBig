package dao;

import dto.UserDTO;

public interface UserDAO {
	int register(UserDTO user);
	UserDTO login(String id, String pass);
}
