package service;

import java.util.List;

import dto.NoticeDTO;
import dto.UserDTO;

public interface NoticeService {
	
	// 모든 게시판 조회 (민원글 제외)
	List<NoticeDTO> getAllPosts();
	
	// 게시판 작성
	int writePost(UserDTO user, NoticeDTO notice);
	
	// 내가(id) 작성한 게시판 조회
	List<NoticeDTO> getPostById(String id);
	
	// 관리자 - 모든 게시판 조회 (민원글 포함)
	List<NoticeDTO> getAllPostsAdmin();
	
	List<NoticeDTO> getComplaint();
}
