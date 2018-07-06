package com.bookstore.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.bookstore.exception.BookStoreExceptionHandler;
import com.bookstore.model.Book;
import com.bookstore.service.BookStoreService;

@RestController
@RequestMapping("/books")
public class BookStoreController {

	@Autowired
	BookStoreService bookStoreService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> addBook(@RequestBody Book book, UriComponentsBuilder ucBuilder) {

		if (!bookStoreService.isBookExists(book)) {
			Book newBook = bookStoreService.AddBook(book);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/books/{id}").buildAndExpand(newBook.getBookISBN_ID()).toUri());
			return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		}

		System.out.println("A Book with ISBN " + book.getBookISBN_ID() + " already exist");
		throw new BookStoreExceptionHandler(HttpStatus.CONFLICT,
				String.format("A Book with ISBN %s already exist", book.getBookISBN_ID()));

	}

	@RequestMapping(value = "allbooks", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Book>> getAllBooks() {
		Map<String, Book> books = bookStoreService.getListOfAvalableBooks();
		if (books.isEmpty()) {
			return new ResponseEntity<Map<String, Book>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Map<String, Book>>(books, HttpStatus.OK);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<Book> getBookById(@PathVariable("id") String id) {
		Book book = bookStoreService.getBook(id);
		if (book == null) {
			System.out.println("Book with id " + id + " not found");
			throw new BookStoreExceptionHandler(HttpStatus.NOT_FOUND, String.format("Book with id %s not found", id));

		}
		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Book> getBookById(@PathVariable("id") String id, @RequestBody Book book) {
		System.out.println("Updating Book " + id);
		book.setBookISBN_ID(id);
		if (bookStoreService.isBookExists(book)) {
			Book currentBook = bookStoreService.updateBook(id, book);
			return new ResponseEntity<Book>(currentBook, HttpStatus.OK);
		}

		System.out.println("Book with id %s not found for update");
		throw new BookStoreExceptionHandler(HttpStatus.NOT_FOUND, String.format("Book with id %s not found", id));

	}

	@RequestMapping(value = "search/{name}", method = RequestMethod.PUT)
	public ResponseEntity<List<Book>> getBookByName(@PathVariable("name") String name) {
		List<Book> searchBooks = bookStoreService.getBookByName(name);
		if (searchBooks.size() == 0) {
			System.out.println("No books available for given book name search.");
			throw new BookStoreExceptionHandler(HttpStatus.NOT_FOUND,
					String.format("No books available for given book name search."));
		}
		return new ResponseEntity<List<Book>>(searchBooks, HttpStatus.OK);
	}

}
