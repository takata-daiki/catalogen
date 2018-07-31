package org.yangchigi.dto;

import java.util.List;

public class Today {
	
	private int id;
	private String date;
	private int like;
	private int userId;
	
	private User user;
	private List<Idea> ideaList;

	public List<Idea> getIdeaList() {
		return ideaList;
	}

	public void setIdeaList(List<Idea> ideaList) {
		this.ideaList = ideaList;
	}

	public Today(String date, int like, int userId) {
		this.date = date;
		this.like = like;
		this.userId = userId;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String getDate() {
		return date;
	}


	public int getUserId() {
		return userId;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public boolean isSameUser(User user) {
		if (user == null) {
			return false;
		}
		return this.userId == user.getId();
	}

	public void removePrivateIdea(User user) {
		if (isSameUser(user)) {
			return;
		}
		
		for (int i = 0; i < ideaList.size(); i++) {
			if (ideaList.get(i).getIsPrivate())
				ideaList.remove(i);
		}
	}
}
