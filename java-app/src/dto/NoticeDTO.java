package dto;

public class NoticeDTO {
	private int noticeId; // 게시판 번호
	private String title; // 제목
	private String type; // 민원/커뮤
	private String userId; // 유저 아이디
	private String post; // 내용
	private String postDate; // 작성날짜

	public NoticeDTO() {

	}

	public NoticeDTO(int noticeId, String title, String type, String userId, String post, String postDate) {
		super();
		this.noticeId = noticeId;
		this.title = title;
		this.type = type;
		this.userId = userId;
		this.post = post;
		this.postDate = postDate;
	}

	@Override
	public String toString() {
		return "Notice [noticeId=" + noticeId + ", title=" + title + ", type=" + type + ", userId=" + userId + ", post="
				+ post + ", postDate=" + postDate + "]";
	}

	public int getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getPostDate() {
		return postDate;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

}
