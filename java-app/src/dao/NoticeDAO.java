package dao;

import java.util.List;

import dto.NoticeDTO;
import dto.UserDTO;

public interface NoticeDAO {
	// 게시글 등록
	int insertNotice(UserDTO user, NoticeDTO notice);
	
	// 게시글 목록 조회
	List<NoticeDTO> getAllPosts();

	// 게시글 상세 조회
	List<NoticeDTO> getPostById(String id);

	// (관리자) 게시글 목록 조회
	List<NoticeDTO> getAllPostsAdmin();

	// (관리자) 민원 게시글 목록 조회
	List<NoticeDTO> getAllPostsComplaint();


	
}
