package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.UserDTO;
import util.DBUtil;

public class UserDAOImpl implements UserDAO {

	// 회원가입 메소드
	@Override
	public int register(UserDTO user) {
		int result = 0;
		
		// 유저가 입력한 (아파트) 동/호실이 존재하는지 체크 후 유저 추가   101 3001 
		String checkRoom = "select * from room where building = ? and room_num = ?";
		String insertUser = "insert into user values(?, ?, ?, null)";
		String updateRoom = "update room set user_id = ? where building = ? and room_num = ?";
		Connection con = null;
		ResultSet rs = null;
		
		PreparedStatement ptmtCheck = null;
		PreparedStatement ptmtUser = null;
		PreparedStatement ptmtRoom = null;
		
		
		
		try {
			con = DBUtil.getConnect();
			con.setAutoCommit(false); // 트랜잭션 시작
			
			// 유저가 입력한 (아파트) 동/호실 체크
			ptmtCheck = con.prepareStatement(checkRoom);
			ptmtCheck.setInt(1, user.getBuilding());
			ptmtCheck.setString(2, user.getRoomNum());
			
			rs = ptmtCheck.executeQuery();
			
			// where 조건이 만족하지 않아 쿼리문이 실행되지 않았으므로 -1리턴
			if(!rs.next()) {
				return -1;
			}
			// 아닐 경우 테이블에 데이터 추가 작업
			
			// (회원가입) 유저 테이블에 정보 추가
			ptmtUser = con.prepareStatement(insertUser);
			ptmtUser.setString(1, user.getUserId());
			ptmtUser.setString(2, user.getPass());
			ptmtUser.setString(3, user.getPhoneNumber());
			
			ptmtUser.executeUpdate(); 
			
			// (회원가입) 유저가 입력한 동/ 호실 데이터는 Room 테이블에서 조회 후
			// Room 테이블에 존재하는 거주지가 맞으면 userid 추가
			ptmtRoom = con.prepareStatement(updateRoom);
			ptmtRoom.setString(1, user.getUserId());
			ptmtRoom.setInt(2, user.getBuilding());
			ptmtRoom.setString(3, user.getRoomNum());
			
			ptmtRoom.executeUpdate();
			con.commit(); // 트랜잭션 커밋
			
			result = 1;
			
		}catch(SQLException e) {
	        e.printStackTrace();
	        if (con != null) {
	            try {
	                con.rollback();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	    } finally {
			DBUtil.close(null, ptmtCheck, con);
			DBUtil.close(null, ptmtUser, con);
			DBUtil.close(null, ptmtRoom, con);
		}
		
		return result;
	}

	@Override
	public UserDTO login(String id, String pass) {
		// TODO Auto-generated method stub
		return null;
	}

}
