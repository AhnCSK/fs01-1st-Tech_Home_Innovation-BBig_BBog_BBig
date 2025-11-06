package dao;

import dto.UserDTO;
import dto.WarningDTO;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarningDAOImpl implements WarningDAO {


    // User 테이블에 저장되어 있는 room_id를 JOIN함
    // Warning 테이블 데이터 출력과 해당 room에 입주 중인 모든 유저 출력 
	// 로그인한 유저의 동/호수에 사는 모든 사람을 출력하는 이유 -> 비상시 연락이 안 됐을 경우를 위한 대비
	@Override
	public List<WarningDTO> getAllWarning() { 
	    String sql = "select w.room_id, user_id, w.warning_type, w.message, w.date, u.phone_number "
	               + "from warning w join user u on w.room_id = u.room_id order by w.date";
	    Connection con = null;
	    PreparedStatement ptmt = null;
	    ResultSet rs = null;

	    List<WarningDTO> warningList = new ArrayList<>();
	    try {
	        con = DBUtil.getConnect();
	        ptmt = con.prepareStatement(sql);
	        rs = ptmt.executeQuery();

	        while (rs.next()) {
	            WarningDTO warning = new WarningDTO(
	                rs.getInt("room_id"),
	                rs.getString("user_id"),
	                rs.getString("warning_type"),
	                rs.getString("message"),
	                rs.getString("phone_number"),
	                rs.getString("date")
	            );
	            warningList.add(warning);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DBUtil.close(rs, ptmt, con);
	    }

	    return warningList;
	}


    // 로그인한 유저 기준 DB 저장 (화재, 침입자 감지만 저장)
    @Override
    public void saveWarning(UserDTO user, String topic, String payload) {
        if (user == null || payload == null || payload.isEmpty()) return;

        // 오직 화재발생(Fire Outbreak)과 침입자 감지(Intruder Detection)만 저장
        String warning_type = null;
        if ("Fire Outbreak".equals(payload)) {
            warning_type = "화재발생";
        } else if ("Intruder Detection".equals(payload)) {
            warning_type = "침입자 감지";
        } else {
            // 그 외 메시지는 저장하지 않음
            // System.out.println("⚠️ DB 저장 제외 메시지: " + payload);
            return;
        }

        // 로그인한 유저의 roomId 가져오기
        int userRoomId = user.getRoomId();

        String sql = "INSERT INTO warning (room_id, warning_type, message) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userRoomId);
            pstmt.setString(2, warning_type);
            pstmt.setString(3, payload);
            pstmt.executeUpdate();

            System.out.println("✅ 경고 메시지 DB 저장 완료: " + payload);

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ 경고 메시지 저장 실패: " + payload);
        }
    }


}
