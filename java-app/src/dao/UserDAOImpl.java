package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.UserDTO;
import util.DBUtil;

public class UserDAOImpl implements UserDAO {

	// 유저 재택/외출 상태 변환 메소드
	public int stateUpdate(UserDTO user, String state) {
		String sql = "update user set state = ? where user_id = ?";

		Connection con = null;
		PreparedStatement ptmt = null;
		int rs = 0;

		try {
			con = DBUtil.getConnect();
			ptmt = con.prepareStatement(sql);

			ptmt.setString(1, state);
			ptmt.setString(2, user.getUserId());

			rs = ptmt.executeUpdate();

			Thread.sleep(1000);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(null, ptmt, con);
		}

		return rs;

	}

	// 회원가입 메소드
	@Override
	public int register(UserDTO user) {
		int result = 0;

		// 유저가 입력한 (아파트) 동/호실이 존재하는지 체크 후 유저 추가 101 3001
		String checkRoom = "select * from room where building = ? and room_num = ?";
		String insertUser = "insert into user values(?, ?, ?, ?, 'Home', ?)";
		Connection con = null;
		ResultSet rs = null;

		PreparedStatement ptmtCheck = null;
		PreparedStatement ptmtUser = null;

		try {
			con = DBUtil.getConnect();
			con.setAutoCommit(false); // 트랜잭션 시작

			// 유저가 입력한 (아파트) 동/호실 체크
			ptmtCheck = con.prepareStatement(checkRoom);
			ptmtCheck.setInt(1, user.getBuilding());
			ptmtCheck.setString(2, user.getRoomNum());

			rs = ptmtCheck.executeQuery();

			// where 조건이 만족하지 않아 쿼리문이 실행되지 않았으므로 -1리턴
			if (!rs.next()) {
				return -1;
			}

			// 아닐 경우 테이블에 데이터 추가 작업
			// room 테이블에서 유저가 입력한 동/호수와 일치하는 room_id 값을 변수로 저장
			int room_id = rs.getInt("room_id");

			// (회원가입) 유저 테이블에 정보 추가
			ptmtUser = con.prepareStatement(insertUser);
			ptmtUser.setString(1, user.getUserId());
			ptmtUser.setString(2, user.getName());
			ptmtUser.setString(3, user.getPass());
			ptmtUser.setString(4, user.getPhoneNumber());
			ptmtUser.setInt(5, room_id);

			ptmtUser.executeUpdate();
			con.commit(); // 트랜잭션 확정

			result = 1;

		} catch (java.sql.SQLIntegrityConstraintViolationException e) {
		    // 중복 PK 오류인 경우
		    if (e.getMessage().contains("Duplicate entry")) {
		        System.out.println("이미 존재하는 ID입니다.");
		    } else {
		        System.out.println("회원가입 중 오류가 발생했습니다.");
		    }

		    if (con != null) {
		        try {
		            con.rollback();
		        } catch (SQLException ex) {
		            ex.printStackTrace(); 
		        }
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    DBUtil.close(null, ptmtCheck, con);
		    DBUtil.close(null, ptmtUser, con);
		}

		return result;
	}

	// 로그인 메소드
	// room_id로 Room 테이블과 User 테이블을 JOIN하여 동/호수 데이터를 함께 조회
	@Override
	public UserDTO login(String id, String pass) {
		Connection con = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		UserDTO loginSuccesUser = null;

		String sql = "SELECT u.user_id, u.name, u.pass, u.phone_number, u.state, u.room_id, r.building, r.room_num "
				+ "FROM user u LEFT JOIN room r ON u.room_id = r.room_id " + "WHERE u.user_id = ? AND u.pass = ?";
		try {
			con = DBUtil.getConnect();
			ptmt = con.prepareStatement(sql);
			ptmt.setString(1, id);
			ptmt.setString(2, pass);

			rs = ptmt.executeQuery();

			if (rs.next()) {
				loginSuccesUser = new UserDTO(rs.getString("user_id"), rs.getString("name"), rs.getString("pass"),
						rs.getString("phone_number"), rs.getString("state"), rs.getInt("room_id"),
						rs.getInt("building"), rs.getString("room_num"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, ptmt, con);
		}
		return loginSuccesUser;
	}

	// 유저 정보 업데이트
	@Override
	public int userInfoUpdate(UserDTO user, UserDTO updatedUser) {
		String sql = "update user set name = ?, pass = ?, phone_number = ? where user_id = ?";

		Connection con = null;
		PreparedStatement ptmt = null;
		int rs = 0;

		try {
			con = DBUtil.getConnect();
			ptmt = con.prepareStatement(sql);

			ptmt.setString(1, updatedUser.getName());
			ptmt.setString(2, updatedUser.getPass());
			ptmt.setString(3, updatedUser.getPhoneNumber());
			ptmt.setString(4, user.getUserId());

			rs = ptmt.executeUpdate();

			Thread.sleep(1000);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(null, ptmt, con);
		}

		return rs;
	}

	// (관리자 전용) 입주민 정보를 조회
	@Override
	public List<UserDTO> getAllUsers(UserDTO userDTO) {
		List<UserDTO> users = new ArrayList<>();
		
		// 입주민 목록에 관리자를 제외해서 조회
		String sql = "SELECT u.user_id, u.name, u.phone_number, u.state, r.building, r.room_num "
				+ "FROM user u LEFT JOIN room r ON u.room_id = r.room_id WHERE user_id <> ?";
		Connection con = null;
		PreparedStatement ptmt = null;
		
		try {
			con = DBUtil.getConnect();
			ptmt = con.prepareStatement(sql);
			
			
			ptmt.setString(1, userDTO.getUserId());
			
			ResultSet rs = ptmt.executeQuery();
			
			while(rs.next()) {
				users.add(new UserDTO(
						rs.getString("user_id"),
						rs.getString("name"),
						rs.getString("phone_number"),
						rs.getString("state"),
						rs.getInt("building"),
						rs.getString("room_num")));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}

	
}
