package com.bookstore.model;

import java.util.Map;

public interface BookStore {

	public Book AddBook(Book book);

	public Map<String, Book> getListOfAvalableBooks();

	public Book updateBook(String id, Book book);

}
