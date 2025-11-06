package service;

import java.util.List;

import dao.NoticeDAO;
import dao.NoticeDAOImpl;
import dto.NoticeDTO;
import dto.UserDTO;

public class NoticeServiceImpl implements NoticeService {
	private NoticeDAO noticeDAO;
	
	public NoticeServiceImpl() {
		this.noticeDAO = new NoticeDAOImpl();
	}

	public NoticeServiceImpl(NoticeDAO noticeDAO) {
		super();
		this.noticeDAO = noticeDAO;
	}

	@Override
	public int writePost(UserDTO user, NoticeDTO notice) {
		// TODO Auto-generated method stub
		return noticeDAO.insertNotice(user, notice);
	}

	@Override
	public List<NoticeDTO> getAllPosts() {
		// TODO Auto-generated method stub
		return noticeDAO.getAllPosts();
	}

	@Override
	public List<NoticeDTO> getPostById(String id) {
		// TODO Auto-generated method stub
		return noticeDAO.getPostById(id);
	}

	@Override
	public List<NoticeDTO> getAllPostsAdmin() {
		// TODO Auto-generated method stub
		return noticeDAO.getAllPostsAdmin();
	}

	@Override
	public List<NoticeDTO> getComplaint() {
		// TODO Auto-generated method stub
		return noticeDAO.getAllPostsComplaint();
	}

}
