package studio.hdr.lms.model;

import java.util.Date;

/**
 * Record entity. @author MyEclipse Persistence Tools
 */

public class Record implements java.io.Serializable {

	// Fields

	private Long recordId;
	private User user;
	private Book book;
	private Date recordDate;

	// Constructors

	/** default constructor */
	public Record() {
	}

	/** minimal constructor */
	public Record(User user, Book book) {
		this.user = user;
		this.book = book;
	}

	/** full constructor */
	public Record(User user, Book book, Date recordDate) {
		this.user = user;
		this.book = book;
		this.recordDate = recordDate;
	}

	// Property accessors

	public Long getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return this.book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Date getRecordDate() {
		return this.recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

}