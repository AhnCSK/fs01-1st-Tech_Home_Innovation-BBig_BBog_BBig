package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.NoticeDTO;
import dto.UserDTO;
import util.DBUtil;

public class NoticeDAOImpl implements NoticeDAO {

	
	// 게시물 등록
	@Override
	public int insertNotice(UserDTO user, NoticeDTO notice) {
		String sql = "INSERT INTO Notice (notice_id, title, type, user_id, post) values(null, ?, ?, ?, ?)";
		int result = 0;
		
		Connection con = null;
		PreparedStatement ptmt = null;
		
		try {
			con = DBUtil.getConnect();
			ptmt = con.prepareStatement(sql);
			
			ptmt.setString(1,  notice.getTitle());
			ptmt.setString(2,  notice.getType());
			ptmt.setString(3,  user.getUserId());
			ptmt.setString(4,  notice.getPost());
			
			result = ptmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(null, ptmt, con);
		}
		
		return result;
		
	}

	// 유저 로그인 ->> 카테고리가 민원인 것을 제외한 모든 게시글 목록 조회 (소통/공지만 조회)
	@Override
	public List<NoticeDTO> getAllPosts() {
		String sql = "select * from notice where type <> '민원' order by post_date";
		Connection con = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		NoticeDTO notice = null;
		
		List<NoticeDTO> boardlist = new ArrayList<NoticeDTO>();
		try {
			con = DBUtil.getConnect();
			ptmt = con.prepareStatement(sql);
			rs = ptmt.executeQuery();
			
			while(rs.next()) {
				notice = new NoticeDTO(rs.getInt(1), rs.getString(3), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(6));
				boardlist.add(notice);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs, ptmt, con);
		}
		
		return boardlist;
	}

	// 자신이 작성한 게시글 조회
	@Override
	public List<NoticeDTO> getPostById(String id) {
		String sql = "select * from notice where user_id = ? order by post_date";
		Connection con = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		NoticeDTO noticeMy = null;
		
		List<NoticeDTO> myPostlist = new ArrayList<NoticeDTO>();
		try {
			con = DBUtil.getConnect();
			ptmt = con.prepareStatement(sql);
			
			
			ptmt.setString(1, id);
			
			rs = ptmt.executeQuery();
			
			while(rs.next()) {
				noticeMy = new NoticeDTO(rs.getInt(1),
										rs.getString(2),
										rs.getString(3),
										rs.getString(4),
										rs.getString(5),
										rs.getString(6));
				myPostlist.add(noticeMy);
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				DBUtil.close(rs, ptmt, con);
			}
		
		
		return myPostlist;
	}

	// (관리자 전용) 게시판 목록을 모두 조회 (민원/소통/공지)
	@Override
	public List<NoticeDTO> getAllPostsAdmin() {
		String sql = "select * from notice order by post_date";
		Connection con = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		NoticeDTO notice = null;
		
		List<NoticeDTO> boardlist = new ArrayList<NoticeDTO>();
		try {
			con = DBUtil.getConnect();
			ptmt = con.prepareStatement(sql);
			rs = ptmt.executeQuery();
			
			while(rs.next()) {
				notice = new NoticeDTO(rs.getInt(1), rs.getString(3), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(6));
				boardlist.add(notice);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs, ptmt, con);
		}
		
		return boardlist;
	}
	
	// (관리자 전용) 민원 게시글 목록 조회
	@Override
	public List<NoticeDTO> getAllPostsComplaint() {
		String sql = "select * from notice where type = '민원' order by post_date";
		Connection con = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		NoticeDTO notice = null;
		
		List<NoticeDTO> complaintList = new ArrayList<NoticeDTO>();
		try {
			con = DBUtil.getConnect();
			ptmt = con.prepareStatement(sql);
			rs = ptmt.executeQuery();
			
			while(rs.next()) {
				notice = new NoticeDTO(rs.getInt(1), rs.getString(3), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(6));
				complaintList.add(notice);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs, ptmt, con);
		}
		
		return complaintList;
	}

}
