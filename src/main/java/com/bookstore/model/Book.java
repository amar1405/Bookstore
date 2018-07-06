package com.bookstore.model;

public class Book {

	private String bookCategory = "";
	private String bookISBN_ID = "";
	private String bookName = "";
	private String description = "";

	public String getBookCategory() {
		return bookCategory;
	}

	public String getBookISBN_ID() {
		return bookISBN_ID;
	}

	public String getBookName() {
		return bookName;
	}

	public String getDescription() {
		return description;
	}

	public void setBookCategory(String bookCategory) {
		this.bookCategory = bookCategory;
	}

	public void setBookISBN_ID(String bookID) {
		this.bookISBN_ID = bookID;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
