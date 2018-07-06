package com.bookstore.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.bookstore.model.Book;
import com.bookstore.model.BookStore;

@Service
public class BookStoreService implements BookStore {

	private Map<String, Book> books = new HashMap();

	public BookStoreService() {
		initializeBooks();
	}

	@Override
	public Book AddBook(Book book) {
		NavigableMap<String, Book> map = new TreeMap<>(books);
		String newBookId = book.getBookISBN_ID();
		if (!books.containsKey(newBookId)) {
			Book newBook = new Book();
			newBook.setBookISBN_ID(newBookId);
			newBook.setBookName(book.getBookName());
			newBook.setBookCategory(book.getBookCategory());
			newBook.setDescription(book.getDescription());
			books.put(newBookId, newBook);
			return newBook;
		}
		return null;
	}

	public Book getBook(String isbnID) {
		if (books.containsKey(isbnID)) {
			return books.get(isbnID);
		}
		return null;
	}

	public List<Book> getBookByName(String bookName) {
		List<Book> serachBooks = new ArrayList<>();
		for (String key : books.keySet()) {
			Book book = books.get(key);
			if (book.getBookName().replaceAll(" ", "").toLowerCase()
					.contains(bookName.replaceAll(" ", "").toLowerCase())) {
				serachBooks.add(book);
			}
		}
		return serachBooks;
	}

	@Override
	public Map<String, Book> getListOfAvalableBooks() {
		return books;
	}

	private void initializeBooks() {
		Book book = new Book();
		book.setBookISBN_ID("0134685997");
		book.setBookName("Effective Java");
		book.setBookCategory("Technology");
		book.setDescription(
				"Effective Java, The third edition covers language and library features added in Java 7, 8, and 9, including the functional programming constructs that were added to its object-oriented roots");
		books.put("0134685997", book);

		book = new Book();
		book.setBookISBN_ID("9781322131979");
		book.setBookName("Beginning Java 9 Fundamentals");
		book.setBookCategory("Fiction");
		book.setDescription(
				"Learn the basics of Java 9, including basic programming concepts and the object-oriented fundamentals necessary at all levels of Java development.");
		books.put("9781322131979", book);

		book = new Book();
		book.setBookISBN_ID("9781473676404");
		book.setBookName("The Outsider");
		book.setBookCategory("Fiction");
		book.setDescription(
				"The latest from legendary master storyteller Stephen King, a riveting, extraordinarily eerie, and moving story about a man whose mysterious affliction brings a small town together—a timely, upbeat tale about finding common ground despite deep-rooted differences");
		books.put("9781473676404", book);

		book = new Book();
		book.setBookISBN_ID("9780061122415");
		book.setBookName("The Alchemist");
		book.setBookCategory("Fiction");
		book.setDescription(
				"The story of the treasures Santiago finds along the way teaches us, as only a few stories can, about the essential wisdom of listening to our hearts, learning to read the omens strewn along life's path, and, above all, following our dreams.");
		books.put("9780061122415", book);

		book = new Book();
		book.setBookISBN_ID("978-0370309903");
		book.setBookName("The Legacy of Alfred Nobel");
		book.setBookCategory("Stories");
		book.setDescription("the story behind the Nobel prizes");
		books.put("978-0370309903", book);

	}

	public boolean isBookExists(Book book) {
		if (books.containsKey(book.getBookISBN_ID())) {
			return true;
		}
		return false;
	}

	@Override
	public Book updateBook(String id, Book book) {
		if (books.containsKey(id)) {
			Book currentBook = books.get(id);
			currentBook.setBookName(book.getBookName());
			currentBook.setBookCategory(book.getBookCategory());
			currentBook.setDescription(book.getDescription());
			return book;
		}

		return null;
	}

}
