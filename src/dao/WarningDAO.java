package dao;

import dto.UserDTO;
import dto.UserSessionDTO;
import dto.WarningDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public interface WarningDAO {
	
	// 경고 목록 조회
	List<WarningDTO> getAllWarning();

	// 라즈베리파이로부터 받은 메시지 저장
	void saveWarning(UserDTO user, String topic, String payload);
}
